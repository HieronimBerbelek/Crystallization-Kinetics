package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.DataModel;

public class SelectionDialog extends JDialog implements ActionListener, ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int BASIC=1;
	public static final int AVRAMI=2;
	public static final int OZAWA=3;
	public static final int MO=4;
	public static final int NUCLEA_NEAT=5;
	public static final int NUCLEA_NUCL = 6;
	public static final int ENERGY=7;
	
	private static final String S_BASIC = "Basic analysis";
	private static final String INF_BASIC = "Pick serie to save its basic results";
	private static final String S_AVRAMI = "Avrami analysis";
	private static final String INF_AVRAMI = "Pick serie to save its Avrami results";
	private static final String S_OZAWA = "Ozawa analysis";
	private static final String INF_OZAWA = "Pick multiple series to save Ozawa results";
	private static final String S_MO = "Mo analysis";
	private static final String INF_MO = "Pick multiple series to save Mo results";
	private static final String S_NUCLEA_NEAT = "Nucleation activity";
	private static final String INF_NUCLEA_NEAT = "Pick multiple series for neat sample";
	private static final String S_NUCLEA_NUCL = "Nucleation activity";
	private static final String INF_NUCLEA_NUCL = "Pick multiple series for nucleated sample";
	private static final String S_ENERGY = "Effective activation energy";
	private static final String INF_ENERGY = "Pick multiple series to save EAE results";
	
	private DataModel model;
	private String title;
	private DataListPanel data;
	private JPanel buttonsPanel;
	private JButton buttonConfirm;
	private JButton buttonCancel;
	private String labelInstruction;
	private JLabel instruction;
	private int[] selection;
	
	
	public SelectionDialog(JFrame frame, DataModel model, int modelID){
		super(frame, true);
		this.model = model;
		getID(modelID);
		this.instruction = new JLabel(labelInstruction);

		
		data = new DataListPanel(model);
		buttonsPanel = new JPanel(new BorderLayout());
		buttonConfirm = new JButton("Confirm");
		buttonConfirm.addActionListener(this);
		buttonConfirm.setEnabled(false);
		buttonCancel = new JButton("Cancel ");
		buttonCancel.addActionListener(this);
		buttonsPanel.add(buttonConfirm, BorderLayout.WEST);
		buttonsPanel.add(buttonCancel, BorderLayout.EAST);
		ListSelectionModel listSelectionModel = data.getSelectionModel();
	    listSelectionModel.addListSelectionListener(this);
		
		super.getContentPane().add(instruction, BorderLayout.NORTH);
		super.getContentPane().add(data, BorderLayout.CENTER);
		super.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		
		super.setLocationRelativeTo(frame);
		super.setTitle(title);
		super.pack();
		super.setVisible(true);
	}
	private void getID(int modelID){
		if(modelID==BASIC){
			title = S_BASIC;
			labelInstruction = INF_BASIC;
		}
		else if(modelID==AVRAMI){
			title = S_AVRAMI;
			labelInstruction = INF_AVRAMI;
		}
		else if(modelID==OZAWA){
			title = S_OZAWA;
			labelInstruction = INF_OZAWA;
		}
		else if(modelID==MO){
			title = S_MO;
			labelInstruction = INF_MO;
		}
		else if(modelID==NUCLEA_NEAT){
			title = S_NUCLEA_NEAT;
			labelInstruction = INF_NUCLEA_NEAT;
		}
		else if(modelID==NUCLEA_NUCL){
			title = S_NUCLEA_NUCL;
			labelInstruction = INF_NUCLEA_NUCL;
		}
		else if(modelID==ENERGY){
			title = S_ENERGY;
			labelInstruction = INF_ENERGY;
		}
		else{
			title = "";
			labelInstruction = "sumtin went wrong mate!";
		}
	}
	public int[] getSelection(){
		return selection;
	}
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==buttonCancel){
			dispose();
		}
		if(event.getSource()==buttonConfirm){
			if (data.getSelectedIndices().length>0) selection = data.getSelectedIndices();
			dispose();
		}
		
	}
	public void valueChanged(ListSelectionEvent arg0) {
		if(title==S_BASIC||title==S_AVRAMI){
			if (data.getSelectedIndices().length>=1) enableConfirmButton();
			else disableConfirmButton();
		}
		else{
			if (data.getSelectedIndices().length>=2) enableConfirmButton();
			else disableConfirmButton();
		}
		
	}
	private void disableConfirmButton(){
		buttonConfirm.setEnabled(false);
	}
	private void enableConfirmButton(){
		buttonConfirm.setEnabled(true);
	}
}
