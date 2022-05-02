package be.ugent.flash.beheerdersinterface.questionparts;

import javafx.scene.control.CheckBox;

public class MrPartsController extends McsPartsController {
    @Override
    protected void selectCorrect() {
        String[] correctlist=question.correct_answer().split("");
        for(CheckBox box:boxlist){
            if (correctlist[boxlist.indexOf(box)].equals("T")){
                box.setSelected(true);
            }
        }
    }

    public String getCorrectAnswer() {
        StringBuilder answer= new StringBuilder();
        for(CheckBox checkBox:boxlist){
            answer.append(checkBox.isSelected() ? "T" : "F");
        }

        if(! String.valueOf(answer).contains("T")){
            return String.valueOf(answer);
        }
        throw new IllegalArgumentException("Er moet minstens 1 oplossing gekozen zijn");
    }
}
