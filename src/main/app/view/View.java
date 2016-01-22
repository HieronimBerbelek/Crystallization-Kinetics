package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import dataWrappers.CrystallizationData;
import model.DataModel;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private DataModel model;
	
	private JList<CrystallizationData> dataList; 
	
	public View(DataModel model){
		super("Crystallization Kinetics");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(new Dimension(400, 400));
		super.setVisible(true);
		setModel(model);
		
		initDataList();
	}
	public void setModel(DataModel model){
		this.model = model;
	}
	private void initDataList(){
		dataList = new JList<CrystallizationData>(model.getDataList());
		
		dataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		dataList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		dataList.setVisibleRowCount(-1);
		
		JScrollPane dataListScroller = new JScrollPane(dataList);
		dataListScroller.setPreferredSize(new Dimension(250, 80));
		dataListScroller.setVisible(true);
	}
}