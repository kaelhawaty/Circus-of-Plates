import Factories.ShapeFactory;
import Shapes.Shape;
import eg.edu.alexu.csd.oop.game.GameEngine;

import javax.swing.*;
import java.awt.*;
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
        final GameEngine.GameController gameController = GameEngine.start("Very Simple Game in 99 Line of Code", new MyWorld(1920, 1080, 10, 10, 3, 5, 4), menuBar);
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.changeWorld(new MyWorld(1280, 800, 5, 10, 3, 2, 3));
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               gameController.resume();
            }
        });
    }
}
