package eg.edu.alexu.csd.oop.Circus;

import eg.edu.alexu.csd.oop.Circus.Shapes.Clown;
import eg.edu.alexu.csd.oop.Circus.Shapes.ImageObject;
import eg.edu.alexu.csd.oop.Circus.Shapes.Shape;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

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
    private ObjectPool objectPool;
    private int activeCount;
    private long waveTime;
    private long lastWave;
    private Random rand = new Random();
    private int shelfLevel;
    private final double distBetwnRod = 0.08;
    private final double scaleHeightRod = 0.009;
    private final double scaleWidthRod = 5/18.0;
    private final double scaleBtnRods = 2;
    private final long deadTime = 10*1000;
    public MyWorld(int screenWidth, int screenHeight, int activeCount, double averageVelocity, int diffShapes, int waveTime, int shelfLevel) {
        width = screenWidth;
        height = screenHeight;
        this.activeCount = activeCount;
        this.waveTime = waveTime*1000;
        this.shelfLevel = shelfLevel;
        objectPool = new ObjectPool(deadTime, width, height, averageVelocity, diffShapes, shelfLevel, distBetwnRod);
        constant.add(new ImageObject(0 , 0, "Background.png", width, height));
        initializeShelves();
        control.add(new Clown(width/2, height- (int) Math.round(height*0.25) , "clown.png",(int) Math.round(width*0.10), (int) Math.round(height*0.23), this));
        int spawnFirst = rand.nextInt(activeCount);
        this.activeCount-= spawnFirst;
        for(int i=0; i < spawnFirst; i++)
            moving.add(objectPool.getShape());
        lastWave = System.currentTimeMillis();
    }
    private void initializeShelves() {
        int w = (int) Math.round(scaleWidthRod * width);
        int h = (int) Math.round(distBetwnRod * height);
        for (int i = 0; i < shelfLevel; i++){
            constant.add(new ImageObject(0, h ,"rod.png", w, (int) Math.round(scaleHeightRod * height)));
            w /= scaleBtnRods;
            h += (int) Math.round(distBetwnRod * height);
        }
        w = (int) Math.round(scaleWidthRod * width);
        h = (int) Math.round(distBetwnRod * height);
        for(int i = 0; i < shelfLevel; i++){
            constant.add((new ImageObject((int) (width - w), h, "rod.png", w, (int) Math.round(scaleHeightRod * height))));
            w /= scaleBtnRods;
            h += (int) Math.round(distBetwnRod * height);

        }
    }
    private void changeState(Shape s){
        int shelfNumber = Math.round(s.getY()+s.getHeight())/(int) Math.round(distBetwnRod * height);
        if(s.getState().getVelocityX() > 0 && s.getX()+s.getWidth()/2 > constant.get(shelfNumber).getX()+constant.get(shelfNumber).getWidth()){
            s.getState().setParameters(0.005, 0.001, 0.2);
        }
        else if(s.getState().getVelocityX() < 0 && s.getX()+s.getWidth()/2 < constant.get(shelfNumber + shelfLevel).getX()){
            s.getState().setParameters(0.005, 0.001, 0.2);
        }
    }
    @Override
    public boolean refresh() {
        boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
        long timeSinceLastWave = System.currentTimeMillis() - lastWave;
        List<GameObject> toRemove = new ArrayList<>();
        for(GameObject m : moving){
            Shape s = (Shape) m;
            s.move();
            if(Math.abs(s.getState().getAcceleration()) < 1e-9){
                changeState(s);
            }
            for(GameObject t: control){
                Clown clown = (Clown) t;
                if( clown.checkIntersectAndAdd(m)){
                    toRemove.add(m);
                    objectPool.releaseShape((Shape) m);
                }
            }
        }
        if(timeSinceLastWave > waveTime && activeCount > 0) {
            int spawnFirst = Math.min(rand.nextInt(activeCount)+1,activeCount);
            activeCount -= spawnFirst;
            for (int i = 0; i < spawnFirst; i++)
                moving.add(objectPool.getShape());
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
            objectPool.releaseShape((Shape) m);
        }
        return !timeout;
    }
    @Override public int getSpeed() 		{	return 20;	}
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
