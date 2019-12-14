package eg.edu.alexu.csd.oop.Circus;

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


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        MyWorld myWorld = new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 10, 5, 3, 5, 4, 1);

        JButton btnLine = new JButton("Undo");
        btnLine.setBackground(new Color(255, 255, 255));
        btnLine.setForeground(new Color(0, 0, 0));
       // btnLine.setFont(new Font("Bell MT", Font.BOLD, 19));
        btnLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                            myWorld.Undo();
                        }
                });

        btnLine.setBounds(839, 69, 91, 31);
        menuBar.add( btnLine);

        JButton btnLine2 = new JButton("Redo");
        btnLine2.setBackground(new Color(255, 255, 255));
        btnLine2.setForeground(new Color(0, 0, 0));
        // btnLine.setFont(new Font("Bell MT", Font.BOLD, 19));
        btnLine2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myWorld.Redo();
            }
        });

        btnLine2.setBounds(939, 69, 91, 31);
        menuBar.add( btnLine2);

final GameEngine.GameController gameController = GameEngine.start("Very Simple Game in 99 Line of Code",myWorld , menuBar);
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.changeWorld(new MyWorld(1280, 800, 10, 5, 3, 2, 3, 2));
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
