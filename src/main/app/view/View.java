package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import model.DataModel;
import wrappers.CrystallizationData;

public class View extends JFrame implements ActionListener, ListDataListener {
	private static final long serialVersionUID = 1L;
	private DataModel model;
	private DataListListener guiListener;
	
	private JPanel dataPanel;
	private JList<CrystallizationData> dataList; 
	private JButton add;
	private JButton remove;
	private final JFileChooser addChooser = new JFileChooser();
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu calculationMenu;
	private JMenu helpMenu;
	
	public View(DataModel model){
		super("Crystallization Kinetics");
		
		setModel(model);
		initMenuBar();
		initMainPanel();
		
		super.add(dataPanel);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(new Dimension(600, 200));
		super.setVisible(true);
	}

	public void setModel(DataModel model){
		this.model = model;
	}
	public void setGuiListener(DataListListener listener){
		guiListener = listener;
	}
	private void initMainPanel(){		
		dataList = new JList<CrystallizationData>(model);
		model.addListDataListener(this);
		
		dataList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		dataList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		dataList.setVisibleRowCount(-1);
		dataList.setVisibleRowCount(10);
		
		JScrollPane dataListScroller = new JScrollPane(dataList);
		
		add = new JButton("ADD");
		add.addActionListener(this);
		
		remove = new JButton("REMOVE");
		remove.addActionListener(this);
		remove.setEnabled(false);
		
		dataPanel = new JPanel(new BorderLayout());
		dataPanel.add(dataListScroller, BorderLayout.CENTER);
		dataPanel.add(add, BorderLayout.PAGE_START);
		dataPanel.add(remove, BorderLayout.PAGE_END);
		dataPanel.setVisible(true);
		dataPanel.repaint();
	}
	
	private void initMenuBar() {
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		calculationMenu = new JMenu("Calculations");
		menuBar.add(calculationMenu);
		
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		super.setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==add){
			guiListener.addPerformed();
		}
		if(event.getSource()==remove){
			guiListener.removePerformed(dataList.getSelectedIndices());
			dataList.clearSelection();
		}		
	}
	public File[] showFileChooser(){
		addChooser.setMultiSelectionEnabled(true);
		int chooserVal = addChooser.showOpenDialog(this);
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return addChooser.getSelectedFiles();
		}
		else return null;
	}
	public void showIOExceptionMessage(){
		JOptionPane.showMessageDialog(this,
			    "Can't access the file!",
			    "ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	public void showDscExceptionMessage(){
		JOptionPane.showMessageDialog(this,
			    "Can't access the DSC data!",
			    "ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	public int showAlreadyLoadedMessage(){
		return JOptionPane.showConfirmDialog(this,
			    "DSC data with this identity is already loaded! "
			    + "Do You want to overwrite it?",
			    "INFO",
			    JOptionPane.YES_NO_CANCEL_OPTION);
	}
	public void contentsChanged(ListDataEvent arg0) {
		if(model.isEmpty()){
			remove.setEnabled(false);
		}	
		else if(remove.isEnabled());//do nothing
		else {
			remove.setEnabled(true);
		}
	}
	public void intervalAdded(ListDataEvent arg0) {
		remove.setEnabled(true);		
	}
	public void intervalRemoved(ListDataEvent arg0) {
		if(model.isEmpty()){
			remove.setEnabled(false);
		}		
	}
}