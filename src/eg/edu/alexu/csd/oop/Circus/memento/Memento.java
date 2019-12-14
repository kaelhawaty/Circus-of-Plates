package eg.edu.alexu.csd.oop.Circus.memento;

import java.util.List;
import eg.edu.alexu.csd.oop.game.GameObject;

public class Memento {
	private List<GameObject> state;
	
	public Memento(List<GameObject> constant ) {
		state = constant;
	}
	
	public List<GameObject> getState(){
		return state;
	}


}
