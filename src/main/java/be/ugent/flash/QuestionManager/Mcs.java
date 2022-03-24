package be.ugent.flash.QuestionManager;

import be.ugent.flash.jdbc.Question;

public class Mcs extends GeneralQuestion{
    public Mcs(Question question){super(question);}

    @Override
    boolean checkAnswer(String answer) {
        return false;
    }
}
