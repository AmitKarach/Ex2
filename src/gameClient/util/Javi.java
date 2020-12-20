package gameClient.util;

import gameClient.Ex2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Javi extends JFrame implements ActionListener {
    private JTextField levelEnter;
    private JTextField idEnter;
    private JLabel headLine;
    private JLabel headLine2;
    private JButton button;
    private int level;
    private Long id;

    public Javi() {
        levelEnter = new JTextField();
        idEnter = new JTextField();
        headLine = new JLabel("choose level: ");
        headLine2 = new JLabel("enter id: ");
        button = new JButton("start");

        button.addActionListener(this);
        headLine.setBounds(50, 40, 120, 20);
        levelEnter.setBounds(50, 70, 150, 20);
        headLine2.setBounds(50, 100, 120, 20);
        idEnter.setBounds(50, 130, 150, 20);


        button.setBounds(80, 170, 80, 25);

        this.add(levelEnter);
        this.add(idEnter);
        this.add(headLine);
        this.add(headLine2);
        this.add(button);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
       

        if (e.getSource().equals(button)) {
            String levelString = levelEnter.getText();
            String idString = idEnter.getText();
            if (!isANumber(levelString) || !isANumber(idString)) {
                System.err.println("your id or your level is not a number!");
                return;
            }

            level = Integer.parseInt(levelString);
            id = Long.parseLong(idString);
            String yourId = Long.toString(id);
            this.dispose();


        }

        Thread ex2Thread = new Ex2();
        ex2Thread.start();
    }

    public int getlevel() {
        return level;
    }

    public Long getId() {
        return id;
    }

    public static boolean isANumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
