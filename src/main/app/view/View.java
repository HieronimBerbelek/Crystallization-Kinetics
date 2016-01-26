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
import javax.swing.JMenuItem;
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
	
	private static final String S_FILE = "File";
	private static final String S_NEW = "New datalist";
	private static final String S_OPEN = "Open datalist";
	private static final String S_SAVE = "Save datalist";
	private static final String S_SAVE_AS = "Save datalist as...";
	private static final String S_EXIT = "Exit";
	
	private static final String S_CALC = "Calculations";
	private static final String S_BASIC = "Basic analysis";
	private static final String S_AVRAMI = "Avrami analysis";
	private static final String S_OZAWA = "Ozawa analysis";
	private static final String S_MO = "Mo analysis";
	private static final String S_NUCLEA = "Nucleation activity";
	private static final String S_ENERGY = "Effective activation energy";
	
	private static final String S_HELP = "Help";
	private static final String S_HELP_CONT = "Help contents";
	private static final String S_ABOUT = "About";
	
	private DataModel model;
	private JPanel mainPanel;
	
	private GuiListener guiListener;
	private JButton add;
	private JButton remove;
	private final JFileChooser addChooser;
	private final JFileChooser saveOpenChooser;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem itemNew;
	private JMenuItem itemOpen;
	private JMenuItem itemSave;
	private JMenuItem itemSaveAs;
	private JMenuItem itemExit;
	
	private JMenu calculationMenu;
	private JMenuItem itemBasic;
	private JMenuItem itemAvrami;
	private JMenuItem itemOzawa;
	private JMenuItem itemMo;
	private JMenuItem itemNucleation;
	private JMenuItem itemEnergy;
	
	private JMenu helpMenu;
	private JMenuItem itemAbout;
	private JMenuItem itemHelp;
	
	private DataListPanel dataPanel;
	
	public View(DataModel model){
		super("Crystallization Kinetics");
		super.setLayout(new BorderLayout());
		
		addChooser = new JFileChooser();
		saveOpenChooser = new JFileChooser();
		
		model.addListDataListener(this);
		setModel(model);
		
		initMenuBar();
		initMainPanel();
		
		super.add(mainPanel, BorderLayout.CENTER);
		
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.pack();
		super.setVisible(true);
	}

	public void setModel(DataModel model){
		this.model = model;
	}
	public void setGuiListener(GuiListener listener){
		guiListener = listener;
	}
	private void initMainPanel(){
		
		add = new JButton("ADD DATA");
		add.addActionListener(this);
		
		remove = new JButton("REMOVE DATA");
		remove.addActionListener(this);
		remove.setEnabled(false);
		
		dataPanel = new DataListPanel(model);
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(add, BorderLayout.PAGE_START);
		mainPanel.add(dataPanel, BorderLayout.CENTER);
		mainPanel.add(remove, BorderLayout.PAGE_END);
		mainPanel.setVisible(true);
	}
	
	private void initMenuBar() {
		menuBar = new JMenuBar();
		//add file menu...
		fileMenu = new JMenu(S_FILE);
		itemNew = new JMenuItem(S_NEW);
		itemNew.addActionListener(this);
		itemSave = new JMenuItem(S_SAVE);
		itemSave.addActionListener(this);
		itemSaveAs = new JMenuItem(S_SAVE_AS);
		itemSaveAs.addActionListener(this);
		itemOpen = new JMenuItem(S_OPEN);
		itemOpen.addActionListener(this);
		itemExit = new JMenuItem(S_EXIT);
		itemExit.addActionListener(this);
		fileMenu.add(itemNew);
		fileMenu.addSeparator();
		fileMenu.add(itemOpen);
		fileMenu.add(itemSave);
		fileMenu.add(itemSaveAs);
		fileMenu.addSeparator();
		fileMenu.add(itemExit);
		menuBar.add(fileMenu);		
		//add calculation menu
		calculationMenu = new JMenu(S_CALC);
		itemBasic = new JMenuItem(S_BASIC);
		itemBasic.addActionListener(this);
		itemAvrami = new JMenuItem(S_AVRAMI);
		itemAvrami.addActionListener(this);
		itemOzawa = new JMenuItem(S_OZAWA);
		itemOzawa.addActionListener(this);
		itemMo = new JMenuItem(S_MO);
		itemMo.addActionListener(this);
		itemNucleation = new JMenuItem(S_NUCLEA);
		itemNucleation.addActionListener(this);
		itemEnergy = new JMenuItem(S_ENERGY);
		itemEnergy.addActionListener(this);
		calculationMenu.add(itemBasic);
		calculationMenu.add(itemAvrami);
		calculationMenu.add(itemOzawa);
		calculationMenu.add(itemMo);
		calculationMenu.add(itemNucleation);
		calculationMenu.add(itemEnergy);
		menuBar.add(calculationMenu);
		
		helpMenu = new JMenu(S_HELP);
		itemHelp = new JMenuItem(S_HELP_CONT);
		itemHelp.addActionListener(this);
		itemAbout = new JMenuItem(S_ABOUT);
		itemAbout.addActionListener(this);
		helpMenu.add(itemHelp);
		helpMenu.add(itemAbout);
		menuBar.add(helpMenu);
		
		super.setJMenuBar(menuBar);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==add){
			guiListener.addPerformed();
		}
		if(event.getSource()==remove){
			guiListener.removePerformed(dataPanel.getSelectedIndices());
			dataPanel.clearSelection();
		}	
		if(event.getSource()==itemNew){
			guiListener.newPerformed();
		}
		if(event.getSource()==itemOpen){
			guiListener.openPerformed();
		}
		if(event.getSource()==itemSave){
			guiListener.savePerformed();
		}
		if(event.getSource()==itemSaveAs){
			guiListener.saveAsPerformed();
		}
		if(event.getSource()==itemExit){
			guiListener.exitPerformed();
		}
		if(event.getSource()==itemBasic){
			guiListener.basicPerformed();
		}
	}
	public File[] showAddFileChooser(){
		addChooser.setMultiSelectionEnabled(true);
		int chooserVal = addChooser.showOpenDialog(this);
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return addChooser.getSelectedFiles();
		}
		else return null;
	}
	public File showSaveFileChooser(){
		int chooserVal = saveOpenChooser.showSaveDialog(this);
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return saveOpenChooser.getSelectedFile();
		}
		else return null;
	}
	public File showOpenFileChooser(){
		int chooserVal = saveOpenChooser.showOpenDialog(this);
		if(chooserVal == JFileChooser.APPROVE_OPTION){
			return saveOpenChooser.getSelectedFile();
		}
		else return null;
	}
	public int[] showSelectionDialog(int modelID){
		return AnalysisDialog.showSelectionDialog(this, model, modelID);
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
	public void showSaveComplete(){
		JOptionPane.showMessageDialog(this,
			    "Save complete!",
			    "SAVE",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	public void showDataError(){
		JOptionPane.showMessageDialog(this,
			    "Your DSC data is invalid!",
			    "ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	public void showOpenComplete(){
		JOptionPane.showMessageDialog(this,
			    "Loading complete!",
			    "OPEN",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	public int showAlreadyLoadedMessage(){
		return JOptionPane.showConfirmDialog(this,
			    "DSC data with this identity is already loaded! "
			    + "Do You want to overwrite it?",
			    "INFO",
			    JOptionPane.YES_NO_CANCEL_OPTION);
	}
	public int showIfInSingleFile(){
		return JOptionPane.showConfirmDialog(this,
			    "Do you want to save it in single file?",
			    "DO YA?",
			    JOptionPane.YES_NO_CANCEL_OPTION);
	}
	public int showAreUSureMessage(){
		return JOptionPane.showConfirmDialog(this,
			    "Dude, You sure You want to do this?",
			    "RLLY?",
			    JOptionPane.YES_NO_OPTION);
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