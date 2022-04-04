package be.ugent.flash.SceneSwitcher.questionDataManager;

import be.ugent.flash.jdbc.Question;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

public class GeneralQuestion {
    private final Question question;
    public GeneralQuestion(Question question){
        this.question=question;
    }

    public String getTitle(){
        return question.title();
    }

    public String getText() {
        return question.text_part();
    }

    public int getId(){
        return question.question_id();
    }

    public Image getImage() {
        return new Image(new ByteArrayInputStream(question.image_part())); }

    public String getAnswer() {
        return question.correct_answer(); }

    public boolean checkAnswer(String answer) {
        return answer.equals(getAnswer());
    }



}
