package Shapes;


import Factories.ShapeFactory;
import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.Stack;

public class Clown extends ImageObject {
    Stack<GameObject> shapes;
    public Clown(int x, int y, String path, int width, int height) {
        super(x, y, path, width, height);
    }
    public void addShape(GameObject shape){
        if (shapes.size() > 2 && checkTop(0, shape)){
            shapes.pop();
            shapes.pop();
        }else{
            shapes.add(shape);
        }
    }
    private boolean checkTop(int n, GameObject shape){
        if (n == 2)
            return true;
        GameObject p = shapes.pop();
        if (ShapeFactory.getInstance().equalColor(p, shape))
            checkTop(n+1, shape);
        else
            return false;
        shapes.add(p);
        return false;
    }
    @Override
    public void setY(int Y){
        return;
    }
}