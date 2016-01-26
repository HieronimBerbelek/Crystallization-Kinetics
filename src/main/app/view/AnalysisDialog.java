package view;

import javax.swing.JFrame;

import model.DataModel;

public class AnalysisDialog {
	public static int[] showSelectionDialog(JFrame frame, DataModel model, int modelID){
		SelectionDialog dialog = new SelectionDialog(frame, model, modelID);
		return dialog.getSelection();
	}
}
