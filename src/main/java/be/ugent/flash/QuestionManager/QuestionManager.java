package be.ugent.flash.QuestionManager;


import be.ugent.flash.SceneSwitcher.QuestionController;
import be.ugent.flash.SceneSwitcher.SceneManager;
import be.ugent.flash.jdbc.DataAccesException;
import be.ugent.flash.jdbc.DataAccesProvider;
import be.ugent.flash.jdbc.Question;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Algemene klasse om viewer bij te houden en quiz af te werken
 */
public class QuestionManager {

    private final DataAccesProvider dataAccesProvider;
    private final Map<String, QuestionFactory> factories= Map.of("mcs",new McsFactory(),"mcc",new MccFactory(),
            "mci",new MciFactory(),"mr",new MrFactory(),"open",new OpenFactory(),"openi",new OpenIFactory());
    private final ArrayList<Question> questions;
    private QuestionController currentQuestion;
    private final SceneManager sceneManager;

    //geef DataAccesprovider uit de main klasse mee en vraag alle vragen op// maak een scenemanager aan met juiste stage
    public QuestionManager(DataAccesProvider dataAccesProvider, Stage stage) throws DataAccesException {
        this.dataAccesProvider=dataAccesProvider;
        this.sceneManager=new SceneManager(stage);
        questions = dataAccesProvider.getDataAccessContext().getQuestionDAO().allQuestionData();
    }

    //laad eerste vraag in en verander naar de juiste scene
    public void start() {
        this.currentQuestion= factories.get(questions.get(0).question_type()).CreateFlashcard(questions.get(0),this, true);
        sceneManager.changeScene(currentQuestion.getfxml(),currentQuestion,currentQuestion.getTitle());
    }

    public void next() {
        Question prev=questions.remove(0);
        //stop programma als er geen vragen meer zijn
        if(questions.isEmpty() && currentQuestion.getCorrect()){
            Platform.exit();
        }else {

            //voeg vraag achter in de rij toe indien fout
            if (!currentQuestion.getCorrect()){
                questions.add(prev);
            }
            currentQuestion=factories.get(questions.get(0).question_type()).CreateFlashcard(questions.get(0),this, currentQuestion.getCorrect());
            sceneManager.changeScene(currentQuestion.getfxml(), currentQuestion, currentQuestion.getTitle());

        }
    }



    public DataAccesProvider getProvider(){
        return dataAccesProvider;
    }

}
