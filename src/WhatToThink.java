import java.util.ArrayList;


//http://rahular.com/twitter-sentiment-analysis/

public class WhatToThink {

    public static void main(String[] args) {
        ArrayList<String> tweets = new ArrayList<>();
        tweets.add("Donald Trump is the worst.");
        tweets.add("I really think Donald Trump would be disastrous and worst for the country if he were elected president.");
        NLP nlp = new NLP();
        nlp.init();
        
        for(String tweet : tweets) {
            System.out.println(tweet + " : " + nlp.findSentiment(tweet));
        }
    }
}
