package com.example.test.triviaquiz;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shant on 2/18/2018.
 */

public class Question {
    int questionNumber;
    String question;
    String imageUrl;
    ArrayList<String> options;
    int correntChoice;

    public Question(int questionNumber, String question, String imageUrl, ArrayList<String> options, int correntChoice) {
        this.questionNumber = questionNumber;
        this.question = question;
        this.imageUrl = imageUrl;
        this.options = options;
        this.correntChoice = correntChoice;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNumber=" + questionNumber +
                ", question='" + question + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", options=" + options +
                ", correntChoice=" + correntChoice +
                '}';
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getCorrentChoice() {
        return correntChoice;
    }

    public void setCorrentChoice(int correntChoice) {
        this.correntChoice = correntChoice;
    }
}
