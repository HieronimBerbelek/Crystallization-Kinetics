package view;

import javax.swing.JFrame;

import model.DataModel;

public class AnalysisDialogMenago {
	public static int[] showSelectionDialog(JFrame frame, DataModel model, int modelID){
		SelectionDialog dialog = new SelectionDialog(frame, model, modelID);
		return dialog.getSelection();
	}
	public static double showDoubleUserInputDialog(JFrame frame, String title, String info){
		DoubleUserInputDialog dialog = new DoubleUserInputDialog(frame, title, info);
		return dialog.getUserInput();
	}
}
