package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

/**
 * Klassen partscontroller zorgen dat alle parts juist verwerkt worden(laden uit db, terruggeven om op te slaan, correct antwoord opslaan,...)
 */
public abstract class QuestionPartsController {
    /**
     * @param question de vraag die is ingeladen
     * @param answerbox de vbox die onder de algemene delen van de vraag staan waar de antwoorden in moeten komen
     * @param file db om parts uit te kunnen opvragen van de vraag
     * @param interfacecontroller main controller van beheerdersinterface meegegeven om tableview te disablen indien nodig
     */
    abstract void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller);

    //vraag arraylist van parts op, kunnen gewone parts zijn of imageParts(aparte record)
    abstract ArrayList getParts();

    public abstract String getCorrectAnswer();
}
