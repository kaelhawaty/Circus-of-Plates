package eg.edu.alexu.csd.oop.Circus.memento;

import java.util.ArrayList;
import java.util.List;


public class CareTaker {
	ArrayList<Memento> mementoList = new ArrayList<Memento>();
	
	public void add (Memento state) {
		mementoList.add(state);
	}
	
	public Memento get (int index) {
		return mementoList.get(index);
	}

	public int getMementoSize(){
		return mementoList.size();
	}

	public void remove(int i){
		mementoList.remove(i);
	}
}
