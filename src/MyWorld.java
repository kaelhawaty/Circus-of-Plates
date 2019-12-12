import Factories.ShapeFactory;
import Shapes.Shape;
import Shapes.ShapeState;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MyWorld implements World {
    private static int MAX_TIME = 1 * 60 * 1000;	// 1 minute
    private int score = 0;
    private long startTime = System.currentTimeMillis();
    private final int width;
    private final int height;
    private final List<GameObject> constant = new LinkedList<GameObject>();
    private final List<GameObject> moving = new LinkedList<GameObject>();
    private final List<GameObject> control = new LinkedList<GameObject>();
    public MyWorld(int screenWidth, int screenHeight) {
        width = screenWidth;
        height = screenHeight;
        Random rand = new Random();
        for(int i=0; i < 10; i++)
            moving.add(ShapeFactory.getInstance().getRandomShape( 3,rand.nextInt(width) ,rand.nextInt((int) (0.1 *height)), screenWidth, screenHeight, new ShapeState(5.5, 0 , 0.001, 0.0001, 0.25)));
    }


    private boolean intersect(GameObject o1, GameObject o2){
        return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) && (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
    }
    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        for(GameObject m : moving){
            Shape s = (Shape) m;
            s.move();
        }
        return !timeout;
    }
    @Override public int getSpeed() 		{	return 30;	}
    @Override public int getControlSpeed() 	{	return 20;	}
    @Override public List<GameObject> getConstantObjects() 	{	return constant;	}
    @Override public List<GameObject> getMovableObjects() 	{	return moving;		}
    @Override public List<GameObject> getControlableObjects() {	return control;		}
    @Override public int getWidth() {	return width;  }
    @Override public int getHeight() { return height; }
    @Override
    public String getStatus() {
        return "Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (System.currentTimeMillis()-startTime))/1000);	// update status
    }
}
