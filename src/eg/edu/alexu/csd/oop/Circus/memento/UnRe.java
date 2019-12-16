package eg.edu.alexu.csd.oop.Circus.memento;

import eg.edu.alexu.csd.oop.Circus.MyWorld;
import eg.edu.alexu.csd.oop.Circus.Shapes.Clown;
import eg.edu.alexu.csd.oop.Circus.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.LinkedList;
import java.util.List;

public class UnRe {
    MyWorld myWorld;
    Clown clown ;
    GameObject gameObject;
   private List<GameObject> current;
   private List<GameObject> pre;
    public boolean Undo(Originator originator, CareTaker careTaker, MyWorld myWorld) {
        this.myWorld=myWorld;
        if(originator.getStateNo()>1){
            originator.setStateNo(originator.getStateNo()-1);
            originator.getFromMemento(careTaker.get(originator.getStateNo()-1));
            if(myWorld.getConstantObjects().size()>originator.getState().size()) {
               gameObject= myWorld.getConstantObjects().remove((originator.getState().size()));
               myWorld.getObjectPool().releaseShape((Shape) gameObject);
            }
            else {
                gameObject=originator.getState().get((originator.getState().size()) - 1);
                myWorld.getConstantObjects().add(gameObject);
            }
            return true;
        }
        else if(originator.getStateNo()==1){
           gameObject = myWorld.getConstantObjects().remove((originator.getState().size())-1);
            originator.setStateNo(originator.getStateNo()-1);
            myWorld.getObjectPool().releaseShape((Shape) gameObject);
            return true;
        }
        return false;
    }
}
