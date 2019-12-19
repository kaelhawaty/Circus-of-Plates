package eg.edu.alexu.csd.oop.Circus;

import eg.edu.alexu.csd.oop.game.GameEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class start {
    String level="";
    Boolean first;
    MyWorld myWorld ;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static GameEngine.GameController gameController;

    public void call(){
        logging log=new logging();
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
        JButton btnLine = new JButton("Replay");
        btnLine.setBackground(new Color(255, 255, 255));
        btnLine.setForeground(new Color(0, 0, 0));
        btnLine.addActionListener(e -> {
                    myWorld.replay();
                }
        );
        btnLine.setBounds(839, 69, 91, 31);
        menuBar.add( btnLine);

        if(first) {
            gameController = GameEngine.start("Very Simple Game in 99 Line of Code", myWorld, menuBar);
        }
        else {
            gameController.changeWorld(myWorld);
        }
        newMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                setLevel(level,first);
                gameController.changeWorld(myWorld);
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.pause();
                log.help().info("the game is paused");
                btnLine.setEnabled(false);
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                gameController.resume();
                log.help().info("the game is resumed");
                btnLine.setEnabled(true);
            }
        });
    }
    public void setLevel(String level,Boolean first){
        this.level=level;
        this.first=first;
        if(level.equals("Easy"))
            myWorld= new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 8, 4, 2, 7, 1, 1,level,this);
        else if(level.equals("Medium"))
            myWorld = new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 15, 7, 3, 5, 3, 2,level,this);
        else
            myWorld = new MyWorld((int) (0.75*screenSize.getWidth()), (int) (0.75*screenSize.getHeight()), 20, 10, 3, 4, 4, 3,level,this);
    }
}