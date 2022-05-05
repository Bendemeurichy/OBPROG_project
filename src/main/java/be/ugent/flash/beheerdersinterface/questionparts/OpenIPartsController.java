package be.ugent.flash.beheerdersinterface.questionparts;

public class OpenIPartsController extends OpenPartsController {
    @Override
    public String getCorrectAnswer() {
        if (answerfield.getText().matches("^-?\\d+$")){
            return answerfield.getText();
        }
        throw new IllegalArgumentException("Antwoord moet een geheel getal zijn");
    }
}
