import java.awt.*;

public class Oval extends Shape
{
    public Oval(int xPos, int yPos, int width, int height, Color c)
    {
        super.type = 1;
        super.colour = c;
        super.x[0] = xPos;
        super.y[0] = yPos;
        super.width = width;
        super.height = height;
    }
}