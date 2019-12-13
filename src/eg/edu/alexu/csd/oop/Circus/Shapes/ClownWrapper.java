package eg.edu.alexu.csd.oop.Circus.Shapes;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ClownWrapper implements GameObject {
    List<Clown> list = new ArrayList<>();
    int width;
    int controlSpeed;
    int x;
    public ClownWrapper(int width){
        this.width = width;
        this.x = (int) Math.round(width/2.0);
    }
    public void addClown(Clown clown){
        list.add(clown);
    }
    public List<Clown> getClowns(){
        return list;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        int vec = x-this.x;
        Clown left = list.get(0);
        Clown right = list.get(list.size()-1);
        if(left.getMaxLeft()+(x-this.x) <= 0 ){
            vec = (-left.getMaxLeft());
        }else if(right.getMaxRight()+(x-this.x) >= width){
            vec = width-(right.getMaxRight());
        }
       for(Clown c: list){
           c.setX(c.getX()+vec);
       }
        this.x += vec;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {
        return;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return new BufferedImage[]{new BufferedImage(1,1,1)};
    }

}
