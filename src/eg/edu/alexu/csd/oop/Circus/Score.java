package eg.edu.alexu.csd.oop.Circus;

import java.util.Observable;
import java.util.Observer;

public class Score implements Observer {
    int score = 0;
    @Override
    public void update(Observable observable, Object o) {
        score++;
    }
    public int getScore(){
        return score;
    }
}
