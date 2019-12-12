import Factories.ShapeFactory;
import Shapes.ImageObject;
import Shapes.Shape;
import Shapes.ShapeState;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.awt.*;
import java.util.ArrayList;
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
    private int activeCount;
    private double averageVelocity;
    private int diffShapes;
    private long waveTime;
    private long lastWave;
    private Random rand = new Random();
    private int shelfLevel;

    public MyWorld(int screenWidth, int screenHeight, int activeCount, double averageVelocity, int diffShapes, int waveTime, int shelfLevel) {
        width = screenWidth;
        height = screenHeight;
        this.activeCount = activeCount;
        this.diffShapes = diffShapes;
        this.waveTime = waveTime*1000;
        this.shelfLevel = shelfLevel;
        this.averageVelocity = averageVelocity;
        constant.add(new ImageObject(0 , 0, "Background.png", width, height));
        initializeShelves();
        int spawnFirst = rand.nextInt(activeCount);
        this.activeCount-= spawnFirst;
        for(int i=0; i < spawnFirst; i++)
            spawnShape();
        lastWave = System.currentTimeMillis();
    }
    private void initializeShelves() {
        int w = (int) Math.round(5 / 18.0 * width);
        int h = (int) Math.round(0.1 * height);
        for (int i = 0; i < shelfLevel; i++){
            constant.add(new ImageObject(0, h ,"rod.png", w, (int) Math.round(0.018 * height)));
            w /= 2;
            h += (int) Math.round(0.1 * height);
        }
        w = (int) Math.round(5 / 18.0 * width);
        h = (int) Math.round(0.1 * height);
        for(int i = 0; i < shelfLevel; i++){
            constant.add((new ImageObject((int) (width - w), h, "rod.png", w, (int) Math.round(0.018 * height))));
            w /= 2;
            h += (int) Math.round(0.1 * height);

        }



    }
    private void spawnShape(){
        boolean state = rand.nextDouble() > 0.5;
        Shape sh = ShapeFactory.getInstance().getRandomShape(diffShapes,
                (state ? 0 : width),
                (int) Math.round(0.1*height),
                width, height,
                new ShapeState(getRandomDouble(state ? 3 : Math.min(-averageVelocity, -3), state ? Math.max(averageVelocity, 3) : -3), 0, 0, 0.0000000001, 0));
        sh.setY(sh.getY()-sh.getHeight());
        moving.add(sh);

    }
    private double getRandomDouble(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();
        return randomValue;
    }
    private boolean intersect(GameObject o1, GameObject o2){
        return (Math.abs((o1.getX()+o1.getWidth()/2) - (o2.getX()+o2.getWidth()/2)) <= o1.getWidth()) && (Math.abs((o1.getY()+o1.getHeight()/2) - (o2.getY()+o2.getHeight()/2)) <= o1.getHeight());
    }
    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        long timeSinceLastWave = System.currentTimeMillis() - lastWave;
        for(GameObject m : moving){
            Shape s = (Shape) m;
            s.move();
            if(s.getState().getVelocityX() > 0 && s.getX()+s.getWidth()/2 > constant.get(1).getX()+constant.get(1).getWidth()){
                s.getState().setParameters(0.01, 0.001, 0.2);
            }
            if(s.getState().getVelocityX() < 0 && s.getX()+s.getWidth()/2 < constant.get(2).getX()){
                s.getState().setParameters(0.01, 0.001, 0.2);
            }
        }
        List<GameObject> toRemove = new ArrayList<>();
        if(timeSinceLastWave > waveTime && activeCount > 0) {
            int spawnFirst = Math.min(rand.nextInt(activeCount)+1,activeCount);
            activeCount -= spawnFirst;
            for (int i = 0; i < spawnFirst; i++)
                spawnShape();
            lastWave = System.currentTimeMillis();
        }
        for (GameObject m : moving) {
            if (m.getY() > height) {
                toRemove.add(m);
                m = null;
                activeCount++;
            }
        }
        for(GameObject m :toRemove){
            moving.remove(m);
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
