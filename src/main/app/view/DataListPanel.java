package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.DataModel;
import wrappers.CrystallizationData;

public class DataListPanel extends JPanel {
	private JList<CrystallizationData> dataList;
	private JScrollPane scrollPane;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5683320143820953014L;
	public DataListPanel(DataModel model){
		super(new BorderLayout());
		dataList = new JList<CrystallizationData>(model);
		
		dataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		dataList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		dataList.setVisibleRowCount(-1);
		dataList.setVisibleRowCount(10);
		
		scrollPane = new JScrollPane(dataList);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		super.add(scrollPane, BorderLayout.CENTER);
	}
	public int[] getSelectedIndices() {
		return dataList.getSelectedIndices();
	}
	public void clearSelection() {
		dataList.clearSelection();		
	}
}
