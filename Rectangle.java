import java.awt.*;

public class Rectangle extends Shape
{
    public Rectangle(int xPos, int yPos, int width, int height, Color c)
    {
        super.type = 0;
        super.colour = c;
        super.x[0] = xPos;
        super.y[0] = yPos;
        super.width = width;
        super.height = height;
    }
}