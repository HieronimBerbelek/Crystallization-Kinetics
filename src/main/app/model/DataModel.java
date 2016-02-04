package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import wrappers.CrystallizationData;

public class DataModel implements ListModel<CrystallizationData>, Serializable {
	/**
	 * both lists have to be vector, because of synchronization
	 */
	private static final long serialVersionUID = -573438047342988784L;
	Vector<CrystallizationData> list;
	transient Vector<ListDataListener> listeners;
	
	public DataModel(){
		list = new Vector<CrystallizationData>();
		listeners = new Vector<ListDataListener>();
	}
	
	public DataModel(Vector<CrystallizationData>input){
		list = input;
		listeners = new Vector<ListDataListener>();
	}
	public void setData(DataModel model){
		this.list=model.list;
		fireAddedEvent(ListDataEvent.CONTENTS_CHANGED, 0, model.list.size()-1);
	}
	public void add(CrystallizationData in){
		list.add(in);
		fireAddedEvent(ListDataEvent.INTERVAL_ADDED, list.size()-1,list.size()-1);
	}
	public void clear(){
		if(list.size()>0) fireRemovedEvent(ListDataEvent.INTERVAL_REMOVED, 0, list.size()-1);
		else fireRemovedEvent(ListDataEvent.INTERVAL_REMOVED, 0, 0);
		list = new Vector<CrystallizationData>();
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
	public ArrayList<CrystallizationData> getElementsAt(int[] arg){
		ArrayList<CrystallizationData> toReturn = new ArrayList<CrystallizationData>();
		for(int i=0;i<arg.length;i++){
			toReturn.add(list.get(arg[i]));
		}
		return toReturn;
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
			if(serie.getIdentity().equals(other.getIdentity())){
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}
	public void overwrite(CrystallizationData other){
		int i=0;
		for(;i<list.size();i++){
			if(list.get(i).getIdentity().equals(other.getIdentity())){
				remove(i);
				break;
			}
		}
		add(other);
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
