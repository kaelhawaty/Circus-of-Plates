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
        final int[] flag = {0};
        final boolean[] disable = {false};

        JLabel trials = new JLabel("    No of trials  "+String.valueOf(3-flag[0])+"  ");
        trials.setBackground(new Color(250, 250, 250));
        trials.setForeground(Color.black);
        menuBar.add(trials);

        JButton btnLine = new JButton("Undo");
        btnLine.setBackground(new Color(255, 255, 255));
        btnLine.setForeground(new Color(0, 0, 0));
        btnLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    if (myWorld.Undo()){
                        flag[0]++;
                        trials.setText("    No of trials  "+String.valueOf(3-flag[0])+"  ");
                    }
                if(flag[0] ==3)
                    btnLine.setEnabled(false);
            }
        });
        btnLine.setBounds(839, 69, 91, 31);
        menuBar.add( btnLine);


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