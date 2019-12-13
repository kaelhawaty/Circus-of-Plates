package eg.edu.alexu.csd.oop.Circus.Shapes;


import eg.edu.alexu.csd.oop.Circus.Factories.ShapeFactory;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Stack;

public class Clown extends ImageObject {
    LinkedList<GameObject> left;
    LinkedList<GameObject> right;
    World myWorld;
    ImageObject stickLeft;
    ImageObject stickRight;
    public Clown(int x, int y, String path, int width, int height, World myWorld) {
        super(x, y, path, width, height);
        left = new LinkedList<>();
        right = new LinkedList<>();
        this.myWorld = myWorld;
        stickLeft = new ImageObject(x-(int) Math.round(0.43*width), (int) (y - Math.round(0.20*height)), "LeftStick.png",(int) Math.round(0.5*width),(int) Math.round(0.5*height));
        stickRight = new ImageObject(x+(int) Math.round(0.92*width), (int) (y-Math.round(0.20*height)), "RightStick.png",(int) Math.round(0.5*width),(int) Math.round(0.5*height));
        myWorld.getConstantObjects().add(stickLeft);
        myWorld.getConstantObjects().add(stickRight);
    }
    public boolean checkIntersectAndAdd(GameObject shape){
        int midX = shape.getX()+shape.getWidth()/2;
        int y = shape.getY()+ shape.getHeight();
        if(left.isEmpty()){
            //System.out.println("stickLeft X: " + stickLeft.getX() + " midX " + midX + " stickLeft x2: " + (stickLeft.getX()+stickLeft.getWidth()/2) );
           // System.out.println(Math.abs(stickLeft.getY() - y));
            if(stickLeft.getX() <= midX && midX <= (stickLeft.getX()+stickLeft.getWidth()/2) && Math.abs(stickLeft.getY() - y) < 15){
                shape.setY(stickLeft.getY()-shape.getHeight());
                return addShape(shape, left);
            }
        }else{
            GameObject top = left.peekLast();
            if(top.getX() <= midX && midX <= (top.getX()+top.getWidth()) && Math.abs(top.getY() - y) < 15){
                shape.setY(top.getY()-shape.getHeight());
                return addShape(shape, left);
            }
        }
        if(right.isEmpty()){
            if((stickRight.getX()+stickRight.getWidth()/2) <= midX && midX <= (stickRight.getX()+stickRight.getWidth()) && Math.abs(stickRight.getY() - y) < 15){
                shape.setY(stickRight.getY()-shape.getHeight());
                return addShape(shape, right);
            }
        }else{
            GameObject top = right.peekLast();
            if(top.getX() <= midX && midX <= (top.getX()+top.getWidth()) && Math.abs(top.getY() - y) < 15){
                shape.setY(top.getY()-shape.getHeight());
                return addShape(shape, right);
            }
        }
        return false;
    }
    public boolean addShape(GameObject shape, LinkedList<GameObject> stk){
        if (stk.size() >= 2 && checkTop(0, shape, stk)){
            myWorld.getConstantObjects().remove(stk.pop());
            myWorld.getConstantObjects().remove(stk.pop());
        }else{
            stk.add(shape);
            myWorld.getConstantObjects().add(shape);
        }
        return true;
    }

    private boolean checkTop(int n, GameObject shape, LinkedList<GameObject> stk){
        if (n == 2)
            return true;
        GameObject p = stk.removeLast();
        boolean flag = false;
        if (ShapeFactory.getInstance().equalColor(p, shape)) {
            flag = checkTop(n+1, shape, stk);
        }
        stk.add(p);
        return flag;
    }
    @Override
    public void setX(int x){
        int vec = x-this.x;
        if(stickLeft.getX()+(x-this.x) <= 0 ){
            vec = (-stickLeft.getX());
        }else if(stickRight.getX()+stickRight.getWidth()+(x-this.x) >= myWorld.getWidth()){
            vec = myWorld.getWidth()-(stickRight.getX()+stickRight.getWidth());
        }
        stickLeft.setX(stickLeft.getX()+vec);
        stickRight.setX(stickRight.getX()+vec);
        for(GameObject o : right){
            o.setX(o.getX()+vec);
        }
        for(GameObject o : left){
            o.setX(o.getX()+vec);
        }
        this.x += vec;
    }
    @Override
    public void setY(int Y){
        return;
    }
}