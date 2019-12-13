package Shapes;


import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

public class Clown extends ImageObject {
    Stack<GameObject> rightStick;
    Stack<GameObject> leftStick;

    public Clown(int x, int y, String path, int width, int height) {
        super(x, y, path, width, height);
        try {
            setSpriteImages(new BufferedImage[]{getSpriteImages()[0], createResizedCopy(ImageIO.read(getClass().getClassLoader().getResourceAsStream("clownF.png")), width, height, false)});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setY(int Y){
        return;
    }
}