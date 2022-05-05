package be.ugent.flash.beheerdersinterface.questionparts;

import be.ugent.flash.beheerdersinterface.BeheerdersinterfaceController;
import be.ugent.flash.jdbc.Question;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public abstract class MultipleChoicePartsController extends QuestionPartsController {
    protected final ArrayList<TextArea> partslist = new ArrayList<>();
    protected Question question;
    protected final GridPane answerPane = new GridPane();

    protected final ArrayList<CheckBox> boxlist = new ArrayList<>();
    protected final ArrayList<Button> crossbox = new ArrayList<>();
    protected BeheerdersinterfaceController interfacecontroller;
    protected final Map<String, String> labelmessage = Map.of("mcs", "keuzes worden onder elkaar getoond - met knoppen A,B,C...", "mcc", "Keuzes worden naast elkaar op knoppen getoond",
            "mci", "Keuzes zijn antwoorden die op knoppen worden getoond", "mr", "Keuzes worden onder elkaar getoond met checkboxes naast");

    @Override
    void initParts(Question question, VBox answerbox, File file, BeheerdersinterfaceController interfacecontroller) {
        this.interfacecontroller = interfacecontroller;
        this.question = question;
        answerPane.setVgap(5);
        answerPane.setHgap(5);
        Button addPart = new Button("Optie toevoegen");
        addPart.setOnAction(this::addPart);
        ScrollPane answers = new ScrollPane();
        answers.setContent(answerPane);
        answers.setHmax(600);
        answers.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        answers.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox layoutbox = new VBox(new Label(labelmessage.get(question.question_type())), answers, addPart);
        TitledPane partpane = new TitledPane("Mogelijke antwoorden", layoutbox);
        partpane.setPrefSize(450, 400);
        partpane.setCollapsible(false);
        layoutbox.setSpacing(5);
        answerbox.getChildren().add(partpane);
    }

    abstract void addPart(ActionEvent event);


    @Override
    public abstract String getCorrectAnswer();
}
