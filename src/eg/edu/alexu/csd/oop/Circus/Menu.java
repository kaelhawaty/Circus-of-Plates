package eg.edu.alexu.csd.oop.Circus;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class Menu {
    String currentLevel="";
    public void getLevel() throws IOException {
        JFrame frame;
        frame = new JFrame();
        frame.setBounds(100, 100, 306, 329);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Lets Play");
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, 0, 300, 300);
        lblNewLabel.setForeground(Color.BLACK);
        Image img= ImageIO.read(getClass().getClassLoader().getResourceAsStream("menu3.jpg"));
        frame.getContentPane().setLayout(null);
        lblNewLabel.setIcon(new ImageIcon(img));
        frame.getContentPane().add(lblNewLabel);
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(10, 85, 126, 27);
        comboBox.setForeground(new Color(255, 255, 0));
        comboBox.setBackground(new Color(51, 102, 153));
        comboBox.setMaximumRowCount(4);

        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Choose Level ","Level 1", "Level 2", "Level 3"}));
        comboBox.setToolTipText("Levels");
        comboBox.setSelectedIndex(0);
        frame.getContentPane().add(comboBox);
        frame.setVisible(true);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentLevel=(String)comboBox.getSelectedItem();
                if((!currentLevel.equals(""))&&(!currentLevel.equals("Choose Level "))){
                    start st =new start(currentLevel);
                    frame.setVisible(false);
                }
            }
        });
    }

}



