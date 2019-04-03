package edu.asu.heal.reachv3.api.models;

public class SwapQuestion {

    private int questionId;
    private String questionText;
    private String answerText;

    public SwapQuestion(){

    }

    public SwapQuestion(int questionId, String questionText, String answerText){
        this.questionId=questionId;
        this.questionText=questionText;
        this.answerText=answerText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
