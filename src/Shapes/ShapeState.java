package Shapes;

public class ShapeState {
    private int vX;
    private int vY;
    private int aY;
    private double a;
    private double b;
    public ShapeState(int vX, int vY, double dragCoeffX, double dragCoeffY, int aY){
        this.vX = vX;
        this.vY = vY;
        this.aY = aY;
        a = dragCoeffX;
        b = dragCoeffY;
    }
    public void move(Shape s){
        int x = s.getX();
        int y = s.getY();
        int newX = x + vX ;
        int newY = y + vY ;
        if(newX+s.getWidth()/2 > s.getScreenWidth() || newX+s.getWidth()/2 < 0){
            reflect(newX+s.getWidth()/2 > s.getScreenWidth());
            newX =  (x + vX);
            newY = (y + vY);
        }
        s.setX(newX);
        s.setY(newY);
        update();
    }
    public void update(){
        if(Math.abs(vX) > 1) {
            vX = (int) (vX * Math.exp(-a));
        }
        vY = (int)Math.round((aY - (aY - b*vY) * Math.exp(-b))/b);

    }
    public void reflect(boolean right){
        if(vX > 0 && right) {
            vX = (int) (-vX * 0.5);
        }else if( vX < 0 && !right){
            vX = (int) (-vX * 0.5);
        }
    }
}
