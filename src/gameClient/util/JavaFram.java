package gameClient.util;

import javax.swing.*;
import java.awt.*;

public class JavaFram extends JFrame
{
    JPanal jp;

    public JavaFram (String avi)
    {
        super(avi);
        jp =new JPanal();
        this.add(jp);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim.width, dim.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
