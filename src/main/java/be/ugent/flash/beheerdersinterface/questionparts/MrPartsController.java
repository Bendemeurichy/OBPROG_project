package be.ugent.flash.beheerdersinterface.questionparts;

import javafx.scene.control.CheckBox;

public class MrPartsController extends McsPartsController {
    @Override
    protected void selectCorrect() {
        String[] correctlist = question.correct_answer().split("");
        for (CheckBox box : boxlist) {
            if (correctlist[boxlist.indexOf(box)].equals("T")){
                box.setSelected(true);
            }
        }
    }

    public String getCorrectAnswer() {
        StringBuilder answer = new StringBuilder();
        for (CheckBox checkBox : boxlist) {
            answer.append(checkBox.isSelected() ? "T" : "F");
        }
        if (! answer.isEmpty()){
            return String.valueOf(answer);
        } else {
            throw new IllegalArgumentException("Antwoordenlijst mag niet leeg zijn");
        }
    }
}
