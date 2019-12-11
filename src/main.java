import Factories.ShapeFactory;
import Shapes.Shape;
import eg.edu.alexu.csd.oop.game.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    public static void main(String[] args) {
        /* -------------------------------------------------------------------- */
        /* allow pause, resume, and restart multiple worlds in the same frame */
        JMenuBar menuBar = new JMenuBar();;
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        menu.add(newMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);
        //final GameEngine.GameController gameController = GameEngine.start("Very Simple Game in 99 Line of Code", new eg.edu.alexu.csd.oop.game.sample.world.StarWar(400, 700), menuBar, Color.BLACK);
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                //gameController.changeWorld(new eg.edu.alexu.csd.oop.game.sample.world.StarWar(400, 700));
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                //gameController.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               // gameController.resume();
            }
        });
    }
}
