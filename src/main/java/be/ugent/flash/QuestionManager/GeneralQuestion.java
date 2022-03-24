package be.ugent.flash.QuestionManager;

import be.ugent.flash.jdbc.Question;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

public abstract class GeneralQuestion {
    protected final Question question;
    protected GeneralQuestion(Question question){
        this.question=question;
    }

    public String getTitle(){
        return question.title();
    }

    public int getId(){
        return question.question_id();
    }

    public Image getImage() {
        return new Image(new ByteArrayInputStream(question.image_part())); }

    public String getAnswer() {
        return question.correct_answer(); }

    abstract boolean checkAnswer(String answer);

}
