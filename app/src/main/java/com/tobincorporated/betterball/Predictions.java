package com.tobincorporated.betterball;

import java.util.Random;

public class Predictions {
    private static Predictions predictions;
    private String[] answers;

    private Predictions(){
        answers = new String[]{
                "SlammaJamma1",
                "SlammaJamma2",
                "MonkeyBones"
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
