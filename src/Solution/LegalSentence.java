package Solution;

import java.util.ArrayList;

public class LegalSentence {


    public enum Type {Given, When, Then}

    private String input;
    private Type type;
    private ArrayList<ArrayList<String>> parameters;
    private String comparable;

    //Constructor. get sentence and compute:type, parameters and comparable string.
    public LegalSentence(String str) {
        //"this.input" compute:
        this.input = str;
        //"this.type" compute:
        String temp_type = str.split(" ", 2)[0];
        switch (temp_type) {
            case ("Given"):
                this.type = Type.Given;
                break;
            case ("When"):
                this.type = Type.When;
                break;
            case ("Then"):
                this.type = Type.Then;
                break;
        }
        //"parameters.type" and "comparable.type" compute:
        parameters = new ArrayList<ArrayList<String>>();
        String[] sentences = str.split(" or");
        comparable = sentences[0].split(" ", 2)[1];
        for (String subStr : sentences) {
            //There is more then one layer only in Then sentences,
            //in case there are "or" in the sentence
            String[] arrayTemp = subStr.split(" and");
            ArrayList<String> comparableTemp = new ArrayList<String>();
            for (String S : arrayTemp) {
                String[] temp = S.split(" ");
                comparableTemp.add(temp[temp.length - 1]);
            }
            parameters.add(comparableTemp);
        }
        for (String param : parameters.get(0)) {
            //remove the parameter from the string for comparing with Annotation.value
            comparable = comparable.replace(param, "&");
        }
    }

    //Copy constructor:
    public LegalSentence(LegalSentence cent) {
        this.input = cent.input;
        this.type = cent.type;
        this.parameters = new ArrayList<>();
        for (ArrayList<String> param : cent.parameters) {
            this.parameters.add(new ArrayList<>(param));
        }
        this.comparable = cent.comparable;
    }

    public String getInput() {
        return input;
    }

    public Type getType() {
        return type;
    }

    public ArrayList<ArrayList<String>> getparameters() {
        ArrayList<ArrayList<String>> toRet = new ArrayList<ArrayList<String>>();
        for (ArrayList<String> a : parameters) {
            toRet.add(new ArrayList<String>(a));
        }
        return toRet;
    }

    public ArrayList<ArrayList<String>> getParameters() {
        return parameters;
    }


    public String getComparable() {
        return comparable;
    }


}