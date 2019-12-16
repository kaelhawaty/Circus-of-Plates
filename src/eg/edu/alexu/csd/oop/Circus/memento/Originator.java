package eg.edu.alexu.csd.oop.Circus.memento;

import java.util.List;
import eg.edu.alexu.csd.oop.game.GameObject;

public class Originator {
	private static int no=0;
	private List<GameObject> state;
	
	public void setState(List<GameObject> gameScreen) {
		state = gameScreen;
	}
	
	public List<GameObject> getState(){
		return state;
	}

	public void setStateNo(int n){
		no=n;
	}

	public int getStateNo(){
		return no;
	}

	public Memento saveToMemento() {
		return new Memento(state);
	}
	
	public void getFromMemento(Memento Memento) {
		state = Memento.getState();
	}
}
