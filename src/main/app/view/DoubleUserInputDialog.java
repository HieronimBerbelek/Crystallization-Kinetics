package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DoubleUserInputDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 233543899558554198L;
	private static final String ERROR_MSG = "Not a number!";
	private double userInput;
	JFormattedTextField field;
	JPanel buttonsPanel;
	JButton buttonConfirm;
	JButton buttonCancel;
	JLabel instruction;
	public DoubleUserInputDialog(JFrame frame,String title, String info){
		super(frame, true);
		field = new JFormattedTextField(NumberFormat.getInstance());
		
		instruction = new JLabel(info);
		buttonsPanel = new JPanel(new BorderLayout());
		buttonConfirm = new JButton("Confirm");
		buttonConfirm.addActionListener(this);
		buttonCancel = new JButton("Cancel ");
		buttonCancel.addActionListener(this);
		buttonsPanel.add(buttonConfirm, BorderLayout.WEST);
		buttonsPanel.add(buttonCancel, BorderLayout.EAST);
		
		super.getContentPane().add(instruction, BorderLayout.NORTH);
		super.getContentPane().add(field, BorderLayout.CENTER);
		super.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		super.setLocationRelativeTo(frame);
		super.setTitle(title);
		super.pack();
		super.setVisible(true);
	}
	public double getUserInput(){
		return userInput;
	}
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==buttonCancel){
			dispose();
		}
		if(event.getSource()==buttonConfirm){
			if(Double.isFinite(Double.parseDouble(field.getText()))){
				userInput = Double.parseDouble(field.getText());
				dispose();
			}
			else{
				field.setText(ERROR_MSG);
			}
		}
	}
}
