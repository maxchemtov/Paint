import java.awt.*;

public class Triangle extends Shape
{
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color c)
    {
        super.type = 3;
        super.colour = c;
        super.x[0] = x1;
        super.y[0] = y1;
        super.x[1] = x2;
        super.y[1] = y2;
        super.x[2] = x3;
        super.y[2] = y3;
    }
}