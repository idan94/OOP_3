package Solution;

import Provided.StoryTestException;

import java.util.List;

public class StoryTestExceptionImpl extends StoryTestException {
    public String sentence;
    public List<String> storyExpected;
    public List<String> testResult;
    public int numFail;

    public StoryTestExceptionImpl(String sentence, List<String> storyExpected, List<String> testResult, int numFail) {
        this.sentence = sentence;
        this.storyExpected = storyExpected;
        this.testResult = testResult;
        this.numFail = numFail;
    }

    public String getSentance() {
        return this.sentence;
    }

    public List<String> getStoryExpected() {
        return this.storyExpected;
    }

    public List<String> getTestResult() {
        return this.testResult;
    }

    public int getNumFail() {
        return this.numFail;
    }
}
