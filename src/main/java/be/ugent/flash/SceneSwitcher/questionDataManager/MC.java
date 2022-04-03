package be.ugent.flash.SceneSwitcher.questionDataManager;

import be.ugent.flash.jdbc.Question;

public class MC extends GeneralQuestion{

    public MC(Question question) {
        super(question);
    }

    @Override
    public boolean checkAnswer(String answer) {
        return answer.equals(getAnswer());
    }

}
