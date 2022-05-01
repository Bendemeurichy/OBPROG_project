package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.jdbc.Parts;
import be.ugent.flash.jdbc.Question;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class Partsloader {
    private  Question question;
    private final Map<String, QuestionPartsFactory> factories= Map.of("mcs",new McsPartsFactory(),"mcc",new MccPartsFactory(),
            "mci",new MciPartsFactory(),"mr",new MrPartsFactory(),"open",new OpenPartsFactory(),"openi",new OpenIPartsFactory());
    public void loadParts(VBox answers, Question question, File file) {
        factories.get(question.question_type()).create(question,answers,file);
    }

    public ArrayList<Parts> getParts() {
        return null;
    }
}
