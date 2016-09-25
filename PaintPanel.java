import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
public class PaintPanel extends JPanel
{
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    
    private final int RECTANGLE = 0;
    private final int OVAL = 1;
    private final int SQUARE = 2;
    private final int TRIANGLE = 3;
    private final int CIRCLE = 4;
    private final int LINE = 5;
    private final int MOVE = 6;
    private final int DELETE = 7;
    private int shape = RECTANGLE;
    
    private int clickX, clickY, mouseX, mouseY, x1, y1, x2, y2, x3, y3, size;
    private int clickCount = 0;
    private int selectedShape;
    
    private boolean listen = false;
    private boolean undone = false;
    
    private Color colour = new Color(0, 0, 0);
    
    private JPanel panelButtons = new JPanel();
    private JPanel panelBlank = new JPanel();
    private JButton buttonRectangle = new JButton("\u25AE");
    private JButton buttonOval = new JButton("<OVAL>");
    private JButton buttonSquare = new JButton("\u25A0");
    private JButton buttonTriangle = new JButton("\u25B2");
    private JButton buttonCircle = new JButton("\u25CF");
    private JButton buttonLine = new JButton("\u007C");
    private JButton buttonMove = new JButton("Move");
    private JButton buttonDelete = new JButton("Del");
    private JButton buttonUndo = new JButton("Del Last");
    private JButton buttonClear = new JButton("Clear");
    private JButton buttonColour = new JButton("  ");
    
    private BorderLayout layout = new BorderLayout();
    
    private ActionHandler actions = new ActionHandler();
    private MouseHandler mouse = new MouseHandler();
    private MotionHandler motion = new MotionHandler();

    public PaintPanel()
    {     
        buttonRectangle.addActionListener(actions);
        buttonOval.addActionListener(actions);
        buttonSquare.addActionListener(actions);
        buttonTriangle.addActionListener(actions);
        buttonCircle.addActionListener(actions);
        buttonLine.addActionListener(actions);
        buttonMove.addActionListener(actions);
        buttonDelete.addActionListener(actions);
        buttonUndo.addActionListener(actions);
        buttonClear.addActionListener(actions);
        buttonColour.addActionListener(actions);
        buttonRectangle.setBackground(Color.WHITE);
        buttonUndo.setFocusable(false);
        buttonClear.setFocusable(false);
        buttonColour.setFocusable(false);
        
        buttonRectangle.setToolTipText("RECTANGLE: drag from corner to corner");
        buttonOval.setToolTipText("ELLIPSE: drag to choose height and width");
        buttonSquare.setToolTipText("SQUARE: drag from a corner");
        buttonTriangle.setToolTipText("TRIANGLE: click 3 points");
        buttonCircle.setToolTipText("CIRCLE: drag from the center");
        buttonLine.setToolTipText("LINE: drag from one end to the other");
        buttonMove.setToolTipText("Drag to move your shapes");
        buttonDelete.setToolTipText("Click on a shape to delete it");
        buttonUndo.setToolTipText("Remove last shape drawn");
        buttonClear.setToolTipText("Clear the screen");
        buttonColour.setToolTipText("Pick a colour");
        
        panelButtons.add(buttonRectangle);
        panelButtons.add(buttonOval);
        panelButtons.add(buttonSquare);
        panelButtons.add(buttonTriangle);
        panelButtons.add(buttonCircle);
        panelButtons.add(buttonLine);
        panelButtons.add(buttonMove);
        panelButtons.add(buttonDelete);
        panelButtons.add(buttonUndo);
        panelButtons.add(buttonClear);
        panelButtons.add(buttonColour);
        buttonColour.setBackground(colour);
        
        panelBlank.addMouseListener(mouse);
        panelBlank.addMouseMotionListener(motion);
        panelBlank.setOpaque(false);
        
        setLayout(layout);
        add(panelButtons, layout.NORTH);
        add(panelBlank, layout.CENTER);
    }
    public void paintComponent(Graphics g)
    {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
                        
        if(! shapes.isEmpty())
        {
            for(int x = 0; x < shapes.size(); x ++)
            {
                g.setColor(shapes.get(x).colour);
                if(shapes.get(x).type == RECTANGLE)
                {
                    g.fillRect(shapes.get(x).x[0], shapes.get(x).y[0], shapes.get(x).width, shapes.get(x).height);
                }
                else if(shapes.get(x).type == OVAL)
                {
                    g.fillOval(shapes.get(x).x[0], shapes.get(x).y[0], shapes.get(x).width, shapes.get(x).height);
                }
                else if(shapes.get(x).type == SQUARE)
                {
                    g.fillRect(shapes.get(x).x[0], shapes.get(x).y[0], shapes.get(x).width, shapes.get(x).width);
                }
                else if(shapes.get(x).type == TRIANGLE)
                {
                    g.fillPolygon(shapes.get(x).x, shapes.get(x).y, 3);
                }
                else if(shapes.get(x).type == CIRCLE)
                {
                    g.fillOval(shapes.get(x).x[0] - shapes.get(x).width, shapes.get(x).y[0] - shapes.get(x).width, 2 * shapes.get(x).width, 2 * shapes.get(x).width);
                }
                else if(shapes.get(x).type == LINE)
                {
                    g.drawLine(shapes.get(x).x[0], shapes.get(x).y[0], shapes.get(x).x[1], shapes.get(x).y[1]);
                }
            }
        }
    }
    private void dehighlightButtons()
    {
        buttonRectangle.setBackground(null);
        buttonOval.setBackground(null);
        buttonSquare.setBackground(null);
        buttonTriangle.setBackground(null);
        buttonCircle.setBackground(null);
        buttonLine.setBackground(null);
        buttonMove.setBackground(null);
        buttonDelete.setBackground(null);
    }
    private void identifyShape()
    {
        if(! shapes.isEmpty())
        {
            boolean done = false;
            for(int x = shapes.size() - 1; x >= 0; x --)
            {
                switch(shapes.get(x).type)
                {
                    case RECTANGLE:
                        if(clickX > shapes.get(x).x[0] && clickY > shapes.get(x).y[0] && clickX < shapes.get(x).x[0] + shapes.get(x).width && clickY < shapes.get(x).y[0] + shapes.get(x).height)
                        {
                            selectedShape = x;
                            done = true;
                        }
                        break;
                    case OVAL:
                        if(Math.pow(clickX - shapes.get(x).x[0] - shapes.get(x).width / 2, 2) / Math.pow(shapes.get(x).width / 2, 2) + Math.pow(clickY - shapes.get(x).y[0] - shapes.get(x).height / 2, 2) / Math.pow(shapes.get(x).height / 2, 2) <= 1)
                        {
                            selectedShape = x;
                            done = true;
                        }
                        break;
                    case SQUARE:
                        if(clickX > shapes.get(x).x[0] && clickY > shapes.get(x).y[0] && clickX < shapes.get(x).x[0] + shapes.get(x).width && clickY < shapes.get(x).y[0] + shapes.get(x).width)
                        {
                            selectedShape = x;
                            done = true;
                        }
                        break;
                    case TRIANGLE:
                        if(new Polygon(shapes.get(x).x, shapes.get(x).y, 3).contains(clickX, clickY))
                        {
                            selectedShape = x;
                            done = true;
                        }
                        break;
                    case CIRCLE:
                        if(Math.pow(clickX - shapes.get(x).x[0], 2) + Math.pow(clickY - shapes.get(x).y[0], 2) <= Math.pow(shapes.get(x).width, 2))
                        {
                            selectedShape = x;
                            done = true;
                        }
                        break;
                    case LINE:
                        double slope = (double)(shapes.get(x).y[0] - shapes.get(x).y[1]) / (double)(shapes.get(x).x[0] - shapes.get(x).x[1]);
                        double initValue = shapes.get(x).y[0] - slope * shapes.get(x).x[0];
                        double zero = shapes.get(x).x[0] - shapes.get(x).y[0] / slope;
                        if(clickX - 5 <= clickY / slope + zero && clickX + 5 >= clickY / slope + zero && clickY - 5 <= clickX * slope + initValue && clickY + 5 >= clickX * slope + initValue && clickX - 5 <= Math.max(shapes.get(x).x[0], shapes.get(x).x[1]) && clickX + 5 >= Math.min(shapes.get(x).x[0], shapes.get(x).x[1]) && clickY - 5 <= Math.max(shapes.get(x).y[0], shapes.get(x).y[1]) && clickY + 5 >= Math.min(shapes.get(x).y[0], shapes.get(x).y[1]))
                        {
                            selectedShape = x;
                            done = true;
                        }
                }
                if(done)
                {
                    break;
                }
            }
            if(! done)
            {
                selectedShape = -1;
            }
        }
        else
        {
            selectedShape = -1;
        }
    }
    private class ActionHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            clickCount = 0;
            if(ae.getSource() == buttonRectangle)
            {
                shape = RECTANGLE;
                dehighlightButtons();
                buttonRectangle.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonOval)
            {
                shape = OVAL;
                dehighlightButtons();
                buttonOval.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonSquare)
            {
                shape = SQUARE;
                dehighlightButtons();
                buttonSquare.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonTriangle)
            {
                shape = TRIANGLE;
                dehighlightButtons();
                buttonTriangle.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonCircle)
            {
                shape = CIRCLE;
                dehighlightButtons();
                buttonCircle.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonLine)
            {
                shape = LINE;
                dehighlightButtons();
                buttonLine.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonMove)
            {
                shape = MOVE;
                dehighlightButtons();
                buttonMove.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonDelete)
            {
                shape = DELETE;
                dehighlightButtons();
                buttonDelete.setBackground(Color.WHITE);
            }
            else if(ae.getSource() == buttonUndo)
            {
                if(shapes.size() > 0)
                {
                    shapes.remove(shapes.size() - 1);
                }
            }
            else if(ae.getSource() == buttonClear)
            {
                shapes.clear();
            }
            else if(ae.getSource() == buttonColour)
            {
                Color tempColour = new Color(0, 0, 0);
                tempColour = JColorChooser.showDialog(null, "Colour", colour);
                if(tempColour != null)
                {
                    colour = tempColour;
                }
                buttonColour.setBackground(colour);
            }
            repaint();
        }
    }
    private class MouseHandler implements MouseListener
    {
        public void mouseEntered(MouseEvent me)
        {
            
        }
        public void mouseExited(MouseEvent me)
        {
            
        }
        public void mousePressed(MouseEvent me)
        {
            clickX = me.getX();
            clickY = me.getY() + panelButtons.getHeight();
            switch(shape)
            {
                case RECTANGLE:
                    shapes.add(new Rectangle(clickX, clickY, 1, 1, colour));
                    break;
                case OVAL:
                    shapes.add(new Oval(clickX, clickY, 1, 1, colour));
                    break;
                case SQUARE:
                    shapes.add(new Square(clickX, clickY, 1, colour));
                    break;
                case CIRCLE:
                    shapes.add(new Circle(clickX, clickY, 1, colour));
                    break;
                case LINE:
                    shapes.add(new Line(clickX, clickY, clickX, clickY, colour));
                    break;
                case MOVE:
                    identifyShape();
                    if(selectedShape != -1)
                    {
                        x1 = shapes.get(selectedShape).x[0];
                        y1 = shapes.get(selectedShape).y[0];
                        x2 = shapes.get(selectedShape).x[1];
                        y2 = shapes.get(selectedShape).y[1];
                        x3 = shapes.get(selectedShape).x[2];
                        y3 = shapes.get(selectedShape).y[2];
                    }
            }
            listen = true;
        }
        public void mouseReleased(MouseEvent me)
        {
            listen = false;
            repaint();
        }
        public void mouseClicked(MouseEvent me)
        {
            if(shape == TRIANGLE)
            {
                clickCount ++;
                switch(clickCount)
                {
                    case 1:
                        x1 = clickX;
                        y1 = clickY;
                        break;
                    case 2:
                        x2 = clickX;
                        y2 = clickY;
                        break;
                    case 3:
                        x3 = clickX;
                        y3 = clickY;
                        shapes.add(new Triangle(x1, y1, x2, y2, x3, y3, colour));
                        clickCount = 0;
                }
            }
            else if(shape == DELETE)
            {
                identifyShape();
                if(selectedShape != -1)
                {
                    shapes.remove(selectedShape);
                }
            }
            repaint();
        }
    }
    private class MotionHandler implements MouseMotionListener
    {
        public void mouseMoved(MouseEvent me)
        {
            
        }
        public void mouseDragged(MouseEvent me)
        {
            mouseX = me.getX();
            mouseY = me.getY() + panelButtons.getHeight();
            if(listen)
            {
                switch(shape)
                {
                    case RECTANGLE:
                        shapes.get(shapes.size() - 1).x[0] = Math.min(clickX, mouseX);
                        shapes.get(shapes.size() - 1).y[0] = Math.min(clickY, mouseY);
                        shapes.get(shapes.size() - 1).width = Math.abs(clickX - mouseX);
                        shapes.get(shapes.size() - 1).height = Math.abs(clickY - mouseY);
                        break;
                    case OVAL:
                        shapes.get(shapes.size() - 1).x[0] = Math.min(clickX, mouseX);
                        shapes.get(shapes.size() - 1).y[0] = Math.min(clickY, mouseY);
                        shapes.get(shapes.size() - 1).width = Math.abs(clickX - mouseX);
                        shapes.get(shapes.size() - 1).height = Math.abs(clickY - mouseY);
                        break;
                    case SQUARE:
                        shapes.get(shapes.size() - 1).width = Math.max(Math.abs(clickX - mouseX), Math.abs(clickY - mouseY));
                        if(mouseX > clickX)
                        {
                            shapes.get(shapes.size() - 1).x[0] = clickX;
                        }
                        else
                        {
                            shapes.get(shapes.size() - 1).x[0] = clickX - shapes.get(shapes.size() - 1).width;
                        }
                         if(mouseY > clickY)
                        {
                            shapes.get(shapes.size() - 1).y[0] = clickY;
                        }
                        else
                        {
                            shapes.get(shapes.size() - 1).y[0] = clickY - shapes.get(shapes.size() - 1).width;
                        }
                        break;
                    case CIRCLE:
                        shapes.get(shapes.size() - 1).x[0] = clickX;
                        shapes.get(shapes.size() - 1).y[0] = clickY;
                        shapes.get(shapes.size() - 1).width = (int) Math.sqrt(Math.pow(clickX - mouseX, 2) + Math.pow(clickY - mouseY, 2));
                        break;
                    case LINE:
                        shapes.get(shapes.size() - 1).x[1] = mouseX;
                        shapes.get(shapes.size() - 1).y[1] = mouseY;
                        break;
                    case MOVE:
                        if(selectedShape != -1)
                        {
                            shapes.get(selectedShape).x[0] = x1 + mouseX - clickX;
                            shapes.get(selectedShape).y[0] = y1 + mouseY - clickY;
                            shapes.get(selectedShape).x[1] = x2 + mouseX - clickX;
                            shapes.get(selectedShape).y[1] = y2 + mouseY - clickY;
                            shapes.get(selectedShape).x[2] = x3 + mouseX - clickX;
                            shapes.get(selectedShape).y[2] = y3 + mouseY - clickY;
                        }
                }
                repaint();
            }
        }
    }
}