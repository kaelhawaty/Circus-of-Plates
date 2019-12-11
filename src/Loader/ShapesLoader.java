package Loader;

import Shapes.Shape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ShapesLoader {
    private char fS = File.separatorChar;
    private List<Class<? extends Shape>> loadedClass;
    private Set<Class<? extends Shape>> st;
    private Map<String, BufferedImage> mp;

    public ShapesLoader(List<Class<? extends Shape>> loadedClass, Map<String, BufferedImage> mp) {
        this.loadedClass = loadedClass;
        st = new HashSet<>();
        this.mp = mp;
    }

    /**
     * Takes full class path and returns a class name with dots instead of /
     * @param fileName class path
     * @return String representing class path
     */
    private String getClassName(String fileName) {
        String newName = fileName.replace(fS, '.');
        newName = newName.replace('/', '.');
        return newName.substring(0, newName.length() - 6);
    }

    /**
     * Takes a Folder, Iterates through it and add any classes implementing the shape interface
     * @param directory Folder File
     *
     */
    public void loadDirectory(File directory) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File f, String name) {
                return name.endsWith("jar") || f.isDirectory();
            }
        };
        File[] files = directory.listFiles(filter);
        Set<Class<? extends Shape>> st = new HashSet<>();
        for (File f : files) {
            if(f.isDirectory()){
                loadDirectory(f);
            }else {
                loadJar(f);
            }
        }
    }

    /**
     * Loads all Images for the target class with specific names "Classname[0-9]*.png"
     * @param jar Target Jar file
     * @param cls Class object for which we need to load its images
     * @param className Name of the class
     *
     */
    public void loadClassImages(File jar, Class<? extends Shape> cls, String className) {
        URL[] forLoad = new URL[0];
        try {
            forLoad = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = URLClassLoader.newInstance(forLoad, getClass().getClassLoader());
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {
                if (entry.getName().matches(className + "[0-9]*\\.png")) {
                    mp.put(entry.getName(), ImageIO.read(loader.getResourceAsStream(entry.getName())));
                }
                entry = jis.getNextJarEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Iterates through the jar searching for classes which implement the shape interface and loads all its resources.
     * @param jar Target Jar
     */
    public void loadJar(File jar) {
        URL[] forLoad = new URL[0];
        try {
            forLoad = new URL[]{jar.toURI().toURL()};
            URLClassLoader loader = URLClassLoader.newInstance(forLoad, getClass().getClassLoader());
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            while (entry != null) {
                if (entry.getName().endsWith(".class")) {
                    String className = getClassName(entry.getName());
                    Class<?> cls = Class.forName(className, true, loader);
                    if (!cls.isInterface()
                            && !Modifier.isAbstract(cls.getModifiers())
                            && Shape.class.isAssignableFrom(cls) && !st.contains(cls)
                           /* && constructorAvailableTest((Class<? extends Shape>) cls)*/) {
                        loadedClass.add((Class<? extends Shape>) cls);
                        st.add((Class<? extends Shape>) cls);
                        loadClassImages(jar, (Class<? extends Shape>) cls, className);

                    }
                }
                entry = jis.getNextJarEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the existence of a specific constructor
     * @param cls Target class
     * @return A boolean representing the existence of a specific  constructor
     */
    public boolean constructorAvailableTest(Class<? extends Shape> cls) {

        try {
            Constructor<?> contructor = cls.getConstructor();
        } catch (NoSuchMethodException nsme) {
            return false;
        }
        return true;
    }
}
