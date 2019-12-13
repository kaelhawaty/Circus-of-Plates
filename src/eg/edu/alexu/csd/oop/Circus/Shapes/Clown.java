package eg.edu.alexu.csd.oop.Circus.Shapes;


import eg.edu.alexu.csd.oop.Circus.Factories.ShapeFactory;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Clown extends ImageObject {
    Stack<GameObject> left;
    Stack<GameObject> right;
    World myWorld;
    ImageObject stickLeft;
    ImageObject stickRight;
    public Clown(int x, int y, String path, int width, int height, World myWorld) {
        super(x, y, path, width, height);
        this.myWorld = myWorld;
        stickLeft = new ImageObject(x-(int) Math.round(0.43*width), (int) (y - Math.round(0.20*height)), "LeftStick.png",(int) Math.round(0.5*width),(int) Math.round(0.5*height)){
            @Override
            public void setY(int y){

            }
            @Override
            public void setX(int x){
                this.x = x;
            }
        };
        myWorld.getControlableObjects().add(stickLeft);
        stickRight = new ImageObject(x+images[0].getWidth(), (int) (y+Math.round(0.25*images[0].getHeight())), "RightStick.png",(int) Math.round(0.5*width),(int) Math.round(0.5*height));
        myWorld.getControlableObjects().add(stickRight);
    }
    public void checkIntersectAndAdd(GameObject Shape){



    }
    public void addShape(GameObject shape){
        if (left.size() > 2 && checkTop(0, shape, left)){
            left.pop();
            left.pop();
        }else{
            left.add(shape);
        }
    }
    public void removeShape(GameObject shape){
        myWorld.getControlableObjects().remove(shape);
        //objectpool.release(shape)
    }

    private boolean checkTop(int n, GameObject shape, Stack<GameObject> stk){
        if (n == 2)
            return true;
        GameObject p = stk.pop();
        if (ShapeFactory.getInstance().equalColor(p, shape)) {
            boolean flag = checkTop(n+1, shape, stk);
            stk.add(p);
            return flag;
        }
        return false;
    }
    @Override
    public void setX(int x){
        if(stickLeft.getX()+(x-this.x) <= 0 ){
            this.x -= stickLeft.getX();
            return;
        }
        if(stickRight.getX() == myWorld.getWidth()){
            return;
        }
        this.x = x;
    }
    @Override
    public void setY(int Y){
        return;
    }
}