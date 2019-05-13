package Solution;

import Provided.*;
import org.junit.ComparisonFailure;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoryTesterImpl implements StoryTester {
    @Override
    public void testOnInheritanceTree(String story, Class<?> testClass) throws Exception {
        if (testClass == null) {
            throw new IllegalArgumentException();
        }
        checkStory(story, testClass,null);
    }
    @Override
    public void testOnNestedClasses(String story, Class<?> testClass) throws Exception {
        testOnNestedClassesAux(story,testClass,null);
    }

    public void testOnNestedClassesAux(String story, Class<?> testClass,Object sugarDady) throws Exception {
        if (testClass == null) {
            throw new IllegalArgumentException();
        }
        try {
            checkStory(story, testClass,sugarDady);
        } catch (GivenNotFoundException e1) {

            for (Class innerClass : (testClass.getClasses())) {
                try {
                    //for each sub class, try to run the story REGULARLY until successful
                    //or until all the innerClass had run out.
                    Object newObject = createObject(testClass,sugarDady);
                    testOnNestedClassesAux(story, innerClass,newObject);
                } catch (GivenNotFoundException e2) {
                    continue;
                }
                return;
            }
            //if reached here then for all the for iterations, there were
            //exceptions and the function was not found
            //and so we throw the same exception we received
            throw e1;
        }
    }

    public static Method AnnotationsMethod(Class<?> testClass, LegalSentence sentence)
            throws WordNotFoundException {
        if (testClass == null) {//TODO: check
            switch (sentence.getType()) {
                case Given:
                    throw new GivenNotFoundException();
                case When:
                    throw new WhenNotFoundException();
                case Then:
                    throw new ThenNotFoundException();
            }
        }
        ArrayList<Method> toRet = Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> methodIsTypedAs(m, sentence.getType()))
                .collect(Collectors.toCollection(ArrayList::new));
        toRet = toRet.stream()
                .filter(m -> (sentence.getComparable()
                        .equals(AnnotationsToComparable(getAnnoValue(m, sentence.getType())))))
                .collect(Collectors.toCollection(ArrayList::new));
        if (toRet.isEmpty()) {
            return AnnotationsMethod(testClass.getSuperclass(), sentence);
        }
//
        return toRet.get(0);
    }

    private static ArrayList<String> storyToSentenceList(String story) {
        String[] array = story.split("\n");
        return Arrays.stream(array)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //return the value of the annotation
    private static String getAnnoValue(Method func, LegalSentence.Type type) {
        switch (type) {
            case Given:
                return func.getAnnotation(Given.class).value();
            case When:
                return func.getAnnotation(When.class).value();
            case Then:
                return func.getAnnotation(Then.class).value();
            default:
                return "Error";//no need default; TODO: maybe to throw exception here
        }
    }

    //gets an annotation (not an instance with parameters)
    //returns the comparable version of it
    private static String AnnotationsToComparable(String str) {
        String[] array = str.split(" ");
        StringBuilder toRet = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (array[i].charAt(0) == '&') {
                array[i] = "&";
            }
            toRet.append(" ").append(array[i]);
        }
        return toRet.toString().split(" ", 2)[1];
    }

    public static boolean methodIsTypedAs(Method func, LegalSentence.Type type) {
        return (type == LegalSentence.Type.Given && func.getAnnotation(Given.class) != null
                || type == LegalSentence.Type.When && func.getAnnotation(When.class) != null
                || type == LegalSentence.Type.Then && func.getAnnotation(Then.class) != null);

    }

    /**
     * fix strings to integer if needed
     *
     * @param parameters the current layer of parameters
     * @param method     the method we want to invoke with the given parameters
     * @return array of OBJECT(for having INTEGER+STRING both)
     */
    private static Object[] fixParameters(ArrayList<String> parameters,
                                          Method method) {
        int i = 0;//index of argument in parameters(the given sentence)
        Object[] toRet = new Object[method.getParameterTypes().length];
        for (Class<?> typeOfParameter : method.getParameterTypes()) {
            if (typeOfParameter == Integer.class) {
                toRet[i] = Integer.parseInt(parameters.get(i));
            } else {
                toRet[i] = parameters.get(i);
                i++;
            }
        }
        return toRet;
    }

    /**
     * making back up object
     *
     * @param objTest the object we want to clone/copy
     * @return the new backUp object
     * @throws Exception //TODO
     */
    private static Object makeBackUp(Object objTest, Class<?> testClass) throws Exception {
        Object backUp = objTest.getClass().getConstructor().newInstance();
        for (Field fFrom : objTest.getClass().getDeclaredFields()) {
            Object fieldTemp = new Object();
            //Clone:
            if (fFrom.get(objTest) instanceof Cloneable) {//TODO:
                Method cloneMethod = fFrom.getType().getDeclaredMethod("clone");
                cloneMethod.setAccessible(true);
                cloneMethod.invoke(objTest);
                //Copy constructor:
            } else {
                try {
                    fieldTemp = fFrom.getType().getDeclaredConstructor(fFrom.getType()).newInstance(fFrom.get(objTest));
                } catch (NoSuchMethodException e) {//just save the object
                    fieldTemp = fFrom.get(objTest);
                }
            }
            backUp.getClass().getField(fFrom.getName()).set(backUp, fieldTemp);
        }
        return backUp;
    }

    /**
     * main function, gets a story and test class,
     * and run the matching methods to the annotations in the story
     *
     * @param story     the story given, as String.
     * @param testClass the test class given from user, includes all the methods with annotations
     * @throws Exception //TODO
     */
    private static void checkStory(String story, Class<?> testClass,Object sugarDady) throws Exception {
        //Object objTest = testClass.getEnclosingConstructor()
        //TODO: find the Given from the normal class
        Object objTest = createObject(testClass,sugarDady);
        Object objBackUp = objTest;
        int thenFailedCounter = 0;
        String firstThenFailed = "";
        ArrayList<String> firstThenFailedExpected = new ArrayList<>();
        ArrayList<String> firstThenFailedActual = new ArrayList<>();
        LegalSentence lastLegalSentence = null;
        for (String sentence : storyToSentenceList(story)) {
            //for EACH sentence do:
            LegalSentence currLegalSentence = new LegalSentence(sentence);
            Method tempMethod = AnnotationsMethod(testClass, currLegalSentence);
            ArrayList<ArrayList<String>> parameters = currLegalSentence.getParameters();
            //BackUp:
            if (lastLegalSentence != null) {
                if (lastLegalSentence.getType() == LegalSentence.Type.Given
                        ||
                        (lastLegalSentence.getType() == LegalSentence.Type.Then
                                && (currLegalSentence.getType() == LegalSentence.Type.When))) {
//                    objBackUp = makeBackUp(objTest, testClass);
                }
            }
            boolean methodThenSuccessFlag = false;//used for Then sentence with "or"'s
            for (ArrayList<String> layerOfParameters : parameters) {
                try {
                    tempMethod.setAccessible(true);
                    tempMethod.invoke(objTest, fixParameters(layerOfParameters, tempMethod));
                    methodThenSuccessFlag = true;
                    break;
                    //Given/When run only one time- fine
                    //for Then- if one of them was successful, we want to stop check.
                } catch (InvocationTargetException e) {
                    assert (currLegalSentence.getType() == LegalSentence.Type.Then);
                    try {
                        throw e.getTargetException();
                    } catch (ComparisonFailure comparisonFailure) {
                        System.out.println("HIIIIIII ComparisonFailure");
                        firstThenFailedExpected.add(comparisonFailure.getExpected());
                        firstThenFailedActual.add(comparisonFailure.getActual());

                    } catch (Throwable throwable) {
                        System.out.println("HIIIIIII Throwable");
                        throwable.printStackTrace();
                    }

                } catch (Exception ex) {
                    System.out.println("HIIIIIII Exception");

                }
            }
            if (!methodThenSuccessFlag) { // if flag is false- means 'Then' FAILED
                assert (currLegalSentence.getType() == LegalSentence.Type.Then);
                if (thenFailedCounter == 0) {
                    firstThenFailed = currLegalSentence.getInput();
                }
                thenFailedCounter++;
                //Restore from backUp:
        //        objTest = objBackUp;
            }
            lastLegalSentence = currLegalSentence;
        }
        if (thenFailedCounter > 0) {
            throw new StoryTestExceptionImpl(firstThenFailed, firstThenFailedExpected,
                    firstThenFailedActual, thenFailedCounter);
        }
    }
    private static Object createObject(Class<?> testClass, Object sugarDady) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object objTest;
        if(sugarDady == null)
        {
            objTest  = testClass.getConstructor().newInstance();
        }
        else
        {
            objTest = testClass.getConstructor(sugarDady.getClass()).newInstance(sugarDady);
        }
        return  objTest;
    }
}

