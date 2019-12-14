package eg.edu.alexu.csd.oop.Circus.memento;

import eg.edu.alexu.csd.oop.Circus.MyWorld;
import eg.edu.alexu.csd.oop.Circus.Shapes.Clown;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.LinkedList;
import java.util.List;

public class UnRe {
    MyWorld myWorld;
    Clown clown ;

   private List<GameObject> current;
   private List<GameObject> pre;
    public boolean Undo(Originator originator, CareTaker careTaker, MyWorld myWorld) {
        this.myWorld=myWorld;
        if(originator.getStateNo()>1){
            originator.setStateNo(originator.getStateNo()-1);
            originator.getFromMemento(careTaker.get(originator.getStateNo()-1));
            if(myWorld.getConstantObjects().size()>originator.getState().size()) {
                myWorld.getConstantObjects().remove((originator.getState().size()));
              //  clown.removeFromStick(originator.getState().get((originator.getState().size())-1));
            }
            else
                myWorld.getConstantObjects().add(originator.getState().get((originator.getState().size())-1));
            return true;
        }
        else if(originator.getStateNo()==1){
            myWorld.getConstantObjects().remove((originator.getState().size())-1);
            originator.setStateNo(originator.getStateNo()-1);

        }
        return false;
    }

    public boolean Redo(Originator originator, CareTaker careTaker, MyWorld myWorld) {
        this.myWorld=myWorld;
        if(originator.getStateNo()<careTaker.getMementoSize()){
            originator.setStateNo(originator.getStateNo()+1);
            originator.getFromMemento(careTaker.get(originator.getStateNo()-1));
            myWorld.getConstantObjects().add(originator.getState().get((originator.getState().size())-1));
            return true;
        }
        return false;
    }
}
