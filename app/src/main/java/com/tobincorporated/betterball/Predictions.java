package com.tobincorporated.betterball;

import java.util.Random;

public class Predictions {
    private static Predictions predictions;
    private String[] answers;

    private Predictions(){
        answers = new String[]{
                "I've told you this already",
                "Watch the video",
                "Read the Instructions",
                "Did you watch the video?"
        };
    }

    public static Predictions get(){
        if(predictions==null){
            predictions = new Predictions();
        }
        return predictions;
    }

    public String getAnswer(){
        Random rand = new Random();

        return answers[rand.nextInt(answers.length)];
    }
}
