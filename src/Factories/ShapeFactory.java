package Factories;

import Loader.ShapesLoader;
import Shapes.Shape;
import Shapes.ShapeState;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ShapeFactory {
    private List<Class<? extends Shape>> loadedClass;
    private Map<String, BufferedImage> mp; // Flyweight Design Pattern
    private ShapesLoader sL;
    public static ShapeFactory instance;

    /**
     * Singleton Design Pattern
     * @return Single instance of this class
     */
    public synchronized static ShapeFactory getInstance() {
        if (instance == null) {
            instance = new ShapeFactory();
        }
        return instance;
    }

    /**
     * Initializing Factory
     */
    private ShapeFactory() {
        loadedClass = new ArrayList<>();
        mp = new HashMap<>();
        sL = new ShapesLoader(loadedClass, mp);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith("jar");
            }
        };

        File f = new File("Plugins");
        if (!f.exists()) {
            f.mkdir();
        }
        sL.loadDirectory(f);
        ;
        if (loadedClass.isEmpty()) {
            throw new RuntimeException("Plugins can't be Empty, There needs to be at least one class for this game to run");
        }
    }

    /**
     * Returns a Random Shape Object with indexes between 0 and count-1
     * Note: Currently it uses default constructor (Empty)
     * @param count Number of Different Shapes
     * @return A random Shape Object
     */
    public Shape getRandomShape(int count, int posX, int posY, int screenWidth, int screenHeight, ShapeState state){
        if(count > loadedClass.size()){
            throw new RuntimeException("There is no enough classes for this Command");
        }
        Random rand = new Random();
        Shape sh = null;
        int idx = rand.nextInt(count);
        try{
            sh = (Shape) loadedClass.get(idx).getDeclaredConstructor(new Class[]{int.class, int.class, int.class, int.class, ShapeState.class}).newInstance(
                    new Object[]{posX, posY, screenWidth, screenHeight, state });

        } catch (IllegalAccessException e) {
            System.out.println("Can't Access this class " + loadedClass.get(idx));
            e.printStackTrace();
        } catch (InstantiationException | NoSuchMethodException e) {
            System.out.println("Can't Create an object of this class " + loadedClass.get(idx) );
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return sh;
    }
    /**
     * Returns a BufferedImage object with the given name if its loaded
     * @param name name of the image with extension (.png) only
     * @return Returns a BufferedImage Object representing the image
     * @exception throws expection if the image doesn't exist
     */
    public BufferedImage getImage(String name) {
        if(!mp.containsKey(name)){
            throw new RuntimeException("Image doesn't exist");
        }
        return mp.get(name);
    }
    public int getSupportedShapesCount(){
        return loadedClass.size();
    }


}
