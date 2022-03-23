package be.ugent.flash.QuestionManager;

import be.ugent.flash.jdbc.Question;

public abstract class GeneralQuestion {
    private Question question;

    public GeneralQuestion(Question question){
        this.question=question;
    }

    public String getTitle(){
        return question.title();
    }

    public int getId(){
        return question.question_id();
    }
    
}
