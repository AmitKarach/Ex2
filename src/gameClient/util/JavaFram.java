package gameClient.util;

import api.game_service;

import javax.swing.*;
import java.awt.*;

public class JavaFram extends JFrame
{
    JPanal jp;
    game_service game;

    public JavaFram (String avi)
    {
        super(avi);
        jp =new JPanal(game);
        this.add(jp);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim.width, dim.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
