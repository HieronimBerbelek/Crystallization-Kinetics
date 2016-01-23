package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import dataWrappers.CrystallizationData;

public class DataModel implements ListModel<CrystallizationData> {
	ArrayList<CrystallizationData> list;
	Vector<ListDataListener> listeners;
	
	public DataModel(){
		list = new ArrayList<CrystallizationData>();
		listeners = new Vector<ListDataListener>();
	}
	
	public DataModel(ArrayList<CrystallizationData>input){
		list = input;
		listeners = new Vector<ListDataListener>();
	}
	
	public void add(CrystallizationData in){
		list.add(in);
		fireEvent(ListDataEvent.INTERVAL_ADDED, list.size()-1,list.size()-1);
	}
	public void clear(){
		fireEvent(ListDataEvent.INTERVAL_REMOVED, 0, list.size()-1);
		list = new ArrayList<CrystallizationData>();
	}
	public void addListDataListener(ListDataListener arg0) {
		listeners.addElement(arg0);		
	}

	public CrystallizationData getElementAt(int arg0) {
		return list.get(arg0);
	}

	public int getSize() {
		return list.size();
	}
	
	public boolean contains(CrystallizationData other){
		boolean toReturn = false;
		for(CrystallizationData serie : list){
			if(serie.getIdentity() == other.getIdentity()) toReturn = true;
		}
		return toReturn;
	}

	public void removeListDataListener(ListDataListener arg0) {
		listeners.remove(arg0);		
	}

	private void fireEvent(int type, int index0, int index1){
		for (ListDataListener listener : listeners){
			listener.intervalAdded(new ListDataEvent(this, type, index0, index1));
		}
	}
}
