import java.awt.*;

public class Line extends Shape
{
    public Line(int x1, int y1, int x2, int y2, Color c)
    {
        super.type = 5;
        super.colour = c;
        super.x[0] = x1;
        super.y[0] = y1;
        super.x[1] = x2;
        super.y[1] = y2;
    }
}