import java.awt.*;

public class Square extends Shape
{
    public Square(int xPos, int yPos, int size, Color c)
    {
        super.type = 2;
        super.colour = c;
        super.x[0] = xPos;
        super.y[0] = yPos;
        super.width = size;
    }
}
