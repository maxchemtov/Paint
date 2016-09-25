import java.awt.*;

public class Circle extends Shape
{
    public Circle(int x, int y, int size, Color c)
    {
        super.colour = c;
        super.type = 4;
        super.x[0] = x;
        super.y[0] = y;
        super.width = size;
    }
}