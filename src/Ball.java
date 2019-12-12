import Factories.ShapeFactory;
import Shapes.Shape;
import Shapes.ShapeState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ball implements Shape {
    private BufferedImage[] images;
    private int x;
    private int y;
    private boolean visible = true;
    public static final int SPRITE_WIDTH = 40;
    private ShapeState state;
    private int screenHeight;
    private int screenWidth;
    public Ball(int posX, int posY, int screenWidth, int screenHeight, ShapeState state){
        x = posX;
        y = posY;
        this.state = state;
        Random rand = new Random();
        int idx = rand.nextInt(4);

        images = new BufferedImage[]{new BufferedImage(SPRITE_WIDTH, SPRITE_WIDTH,	BufferedImage.TYPE_INT_ARGB)};
        Graphics2D g2 = images[0].createGraphics();
        g2.setColor(Color.RED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double x = SPRITE_WIDTH/2;
        double y = SPRITE_WIDTH/2;
        int x1 = (int) ((SPRITE_WIDTH / 2.0) - x);
        int y1 = (int) ((SPRITE_WIDTH / 2.0) - y);
        int x2 = (int) ((SPRITE_WIDTH / 2.0) + x);
        int y2 = (int) ((SPRITE_WIDTH / 2.0) + y);
        g2.setStroke(new BasicStroke(3));
        g2.fillOval(x1+3, y1+3, x2-6, y2-6);
        g2.setColor(Color.WHITE);
        g2.drawArc(x1+12, y1+8, x2-22, y2-22, 5, 80);
        g2.dispose();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return SPRITE_WIDTH;
    }

    @Override
    public int getHeight() {
        return SPRITE_WIDTH;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return images;
    }


    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void move() {
        state.move(this);
    }

    @Override
    public ShapeState getState() {
        return state;
    }

    @Override
    public void setState(ShapeState state) {
        this.state = state;
    }

    @Override
    public int getScreenWidth() {
        return screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight;
    }
}
