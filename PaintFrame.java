import java.awt.*;
import javax.swing.*;
public class PaintFrame extends JFrame
{
    public PaintFrame(){
        PaintPanel panel = new PaintPanel();
        setContentPane(panel);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}