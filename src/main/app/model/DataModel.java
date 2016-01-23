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
		fireAddedEvent(ListDataEvent.INTERVAL_ADDED, list.size()-1,list.size()-1);
	}
	public void clear(){
		list = new ArrayList<CrystallizationData>();
		fireRemovedEvent(ListDataEvent.INTERVAL_REMOVED, 0, list.size()-1);
	}
	public void remove(int items){
		list.remove(items);
		fireRemovedEvent(ListDataEvent.INTERVAL_REMOVED, items, items);
	}
	public void remove(int[] items){
		for(int index=0;index<items.length;index++){
			list.remove(items[index]-index);
		}		
		fireRemovedEvent(ListDataEvent.INTERVAL_REMOVED, items[0], items[items.length-1]);
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
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public boolean contains(CrystallizationData other){
		boolean toReturn = false;
		for(CrystallizationData serie : list){
			if(serie.getIdentity().equals(other.getIdentity())) toReturn = true;
		}
		return toReturn;
	}
	
	public boolean contains(String other){
		boolean toReturn = false;
		for(CrystallizationData serie : list){
			if(serie.getIdentity() == other) toReturn = true;
		}
		return toReturn;
	}

	public void removeListDataListener(ListDataListener arg0) {
		listeners.remove(arg0);		
	}

	private void fireAddedEvent(int type, int index0, int index1){
		for (ListDataListener listener : listeners){
			listener.intervalAdded(new ListDataEvent(this, type, index0, index1));
		}
	}
	private void fireRemovedEvent(int type, int index0, int index1){
		for (ListDataListener listener : listeners){
			listener.intervalRemoved(new ListDataEvent(this, type, index0, index1));
		}
	}
}
