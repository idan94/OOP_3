package Solution;

import java.util.ArrayList;

public class Story {

    private String input;
    private ArrayList<LegalSentence> Sentences;

    public Story (String str)
    {
        input = str;
        Sentences = new ArrayList<>();
        String[] array = str.split("\n");
        for(String sentence:array)
        {
            Sentences.add(new LegalSentence(sentence));
        }
    }

    public String getInput() {
        return input;
    }

    public ArrayList<LegalSentence> getSentence() {
        ArrayList<LegalSentence> toRet = new ArrayList<>();
        for (LegalSentence sentence:Sentences)
        {
            toRet.add(new LegalSentence(sentence));
        }
        return toRet;
    }

}
