package main;

import javax.swing.SwingUtilities;

import controller.Controller;
import model.DataModel;
import view.View;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runApp();
			}
		});
	}
	private static void runApp(){
		DataModel model = new DataModel();
		View view = new View(model);
		Controller controller = new Controller(model, view);
	}
}
