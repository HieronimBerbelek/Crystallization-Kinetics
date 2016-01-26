package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DataModel;

public class SelectionDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int BASIC=1;
	public static final int AVRAMI=2;
	public static final int OZAWA=3;
	public static final int MO=4;
	public static final int NUCLEA=5;
	public static final int ENERGY=6;
	
	private static final String S_BASIC = "Basic analysis";
	private static final String S_AVRAMI = "Avrami analysis";
	private static final String S_OZAWA = "Ozawa analysis";
	private static final String S_MO = "Mo analysis";
	private static final String S_NUCLEA = "Nucleation activity";
	private static final String S_ENERGY = "Effective activation energy";
	
	private DataModel model;
	private DataListPanel data;
	private JPanel buttonsPanel;
	private JButton buttonConfirm;
	private JButton buttonCancel;
	private int modelID;
	private int[] selection;
	
	
	public SelectionDialog(JFrame frame, DataModel model, int modelID){
		super(frame, true);
		this.model = model;
		this.modelID = modelID;
		
		data = new DataListPanel(model);
		buttonsPanel = new JPanel(new BorderLayout());
		buttonConfirm = new JButton("Confirm");
		buttonConfirm.addActionListener(this);
		buttonCancel = new JButton("Cancel ");
		buttonCancel.addActionListener(this);
		buttonsPanel.add(buttonConfirm, BorderLayout.WEST);
		buttonsPanel.add(buttonCancel, BorderLayout.EAST);
		
		super.getContentPane().add(data, BorderLayout.CENTER);
		super.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		
		
		super.setLocationRelativeTo(frame);
		super.setTitle(getTitle(modelID)+" - pick series");
		super.pack();
		super.setVisible(true);
	}
	private String getTitle(int modelID){
		if(modelID==BASIC) return S_BASIC;
		else if(modelID==AVRAMI) return S_AVRAMI;
		else if(modelID==OZAWA) return S_OZAWA;
		else if(modelID==MO) return S_MO;
		else if(modelID==NUCLEA) return S_NUCLEA;
		else if(modelID==ENERGY) return S_ENERGY;
		else return "";
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
}
