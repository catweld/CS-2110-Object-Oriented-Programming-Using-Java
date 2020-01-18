import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/** An instance is a turtle in a Jpanel.
 The turtle is a spot on the panel, facing a certain direction
 and holding a colored pen. You can move the turtle from place
 to place, drawing as the turtle moves. You can ask the
 turtle to pick the pen up so that moving doesn't draw. You can draw
 rectangles and circles of any size around the turtle. */
public class Turtle extends JPanel { 
    /** The graphics associated with the panel. */
    protected Graphics graphics;

    /** The panel on which the turtle moves size (width, height). */
    protected int width= 600;

    /** The panel on which the turtle moves has size (width, height). */
    protected int height= 700;

    /** The part of the jPanel below down pixels is generally used for graphics.*/
    protected int down= 150;


    /** The turtle is at point (x, y). */
    private double x, y;

    /** The turtle points in direction angle: 0 <= angle < 360.
     0: east, 90:north, 180: west, 270 south. */
    private double angle; 

    /** = "the pen is down". */
    private boolean penDown= true;

    /** the pen color. */
    private Color turtleColor= Color.black;

    /** Constructor: a black turtle starting at the middle of this JPanel
     with angle 0 (looking east) but down pixels
     East (right) is angle 0; north (up), 90; west (left), 180;
     South (down). 270. The pen is down.*/
    public Turtle() {
        this(700/2, 400/2, 0);
    }

    /** Constructor: a black turtle starting at (x,y) of this JPanel with angle ang.
     East (right) is angle 0; north (up), 90; west (left), 180;
     South (down), 270. The pen is down. */
    public Turtle(double x, double y, double ang) {
        this.x= x;
        this.y= y;
        angle= ang;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(width, down + height));

        graphics= getGraphics();
    }

    /** = x-coordinate of the turtle. */
    public double getXCoord() {
        return x;   
    }

    /** = y-coordinate of the turtle. */
    public double getYCoord() {
        return y;   
    }

    /** = angle of the turtle (in degrees). East (right) is angle 0;
     north (up), 90; west (left), 180; South (down), 270. */
    public double getAngle() {
        return angle;   
    }

    /** = width of panel. */
    public int getWidth() {
        return width;   
    }

    /** = height of the panel. */
    public int getHeight() {
        return height;   
    }

    /** = "the pen is up". */
    public boolean isPenUp() {
        return ! penDown;
    }

    /** Set the angle to ang degrees. East (right) is angle 0;
     north (up), 90; west (left), 180; South (down), 270.*/
    public void setAngle(double ang) {
        angle= mod(ang, 360);
    }

    /** Add ang degrees to the angle. */
    public void addAngle(double ang) {
        angle= mod (angle + ang, 360);
    }

    /** Lift pen. */
    public void putPenUp() {
        penDown= false;   
    }

    /** Put pen down. */
    public void putPenDown() {
        penDown= true;   
    }

    /** Set the color of the turtle to c. */
    public void setColor(Color c) {
        turtleColor= c;
    }

    /** = the turtle's current color. */
    public Color getColor() {
        return turtleColor;
    }

    /** Place the turtle in the center of the panel, facing east, and
    clear the window (make it white). */
    public void placeInCenter() {
        jumpTo(getWidth()/2, down/2 + getHeight()/2, 0);
        clear();
    }

    /** Move the turtle to (x,y), without drawing,
     and face it at angle ang --who ever heard of turtles jumping? */
    public void jumpTo(double x, double y, double ang) {
        this.x= x;
        this.y= y;
        angle= ang;
    }

    /** Draw a circle of diameter d with center at the turtle's position. */
    public void drawCircle(double d) {
        int id= (int) Math.round(d);
        Color save= graphics.getColor();
        graphics.setColor(turtleColor);
        graphics.drawOval((int)Math.round(x-d/2),
                (int)Math.round(y-d/2), id, id);
        graphics.setColor(save);
    }

    /** Fill triangle with lower left point at the turtle position and side length s.
        Don't move the turtle.*/
    public void fillTriangle(double s) {
        int[] xpts= {(int)Math.round(x), (int)Math.round(x+s), (int)Math.round(x+s/2)};
        int[] ypts= {(int)Math.round(y), (int)Math.round(y), (int)(y - s*Math.sqrt(.75))};
        graphics.fillPolygon(xpts, ypts, 3);
    }

    /** Fill a hexagon of side length s, with middle (x, y). */
    public void fillHex(double s, double x, double y) {
        double h= s*Math.sqrt(.75); 
        int x1= (int)Math.round(x-s/2);
        int x2= (int)Math.round(x+s/2);
        int x3= (int)Math.round(x+s);
        int x6= (int)Math.round(x-s);
        int ylo= (int)Math.round(y+h);
        int ymi= (int)Math.round(y);
        int yhi= (int)Math.round(y-h);

        int[] xpts= {x1, x2, x3, x2, x1, x6};
        int[] ypts= {ylo, ylo, ymi, yhi, yhi, ymi};
        graphics.fillPolygon(xpts, ypts, 6);

    }

    /** Fill a circle of diameter d with center at the turtle's position. */
    public void fillCircle(double d) {
        int id= (int) Math.round(d);
        Color save= graphics.getColor();
        graphics.setColor(turtleColor);
        graphics.fillOval((int)Math.round(x-d/2),
                (int)Math.round(y-d/2), id, id);
        graphics.setColor(save);
    }

    /** Draw a rectangle of width w, height h with center at the turtle's position. */
    public void drawRect(double w, double h) {
        Color save= graphics.getColor();
        graphics.setColor(turtleColor);
        graphics.drawRect((int)Math.round(x-w/2), (int)Math.round(y-h/2),
                (int)Math.round(w), (int)Math.round(h));
        graphics.setColor(save);
    }

    /** Fill a rectangle of width w, height h with center at the turtle's position. */
    public void fillRect(double w, double h) {
        Color save= graphics.getColor();
        graphics.setColor(turtleColor);
        graphics.fillRect((int)Math.round(x-w/2), (int)Math.round(y-h/2),
                (int)Math.round(w), (int)Math.round(h));
        graphics.setColor(save);
    }

    /** Move the turtle d units in its current direction.
     If the pen is down, a line will be drawn; if the pen
     is up, it won't be drawn.*/
    public void moveAhead(double d) {
        double rAngle= (angle * Math.PI) / 180;
        double newX= x + Math.cos(rAngle) * d;
        double newY= y - Math.sin(rAngle) * d;
        if (penDown) {
            Color save= graphics.getColor();
            graphics.setColor(turtleColor);

            graphics.drawLine((int)Math.round(x), (int)Math.round(y),
                    (int)Math.round(newX), (int)Math.round(newY));
            graphics.setColor(save);
        }
        x= newX;
        y= newY;
    }

    /** Move the turtle d units in the opposite (backward) direction.
     If the pen is down, a line will be drawn; if the pen
     is up, it won't be drawn.*/
    public void moveBack(double d) {
        double save= angle;
        addAngle(180);
        moveAhead(d);
        angle= save;
    }

    /** = x mod y. Precondition: y != 0. */
    private double mod(double x, double y) {
        double ans= x % y;
        if (ans < 0) 
            ans= ans + y;
        return ans;
    }

    /** Clear the panel below down pixels (make that part all white). */
    public void clear() {
        Color save= graphics.getColor();
        graphics.setColor(Color.white);
        graphics.fillRect(0, down, width, height);
        graphics.setColor(save);
    }

    /** Pause the program for millisec milliseconds. */
    public void pause(int millisec) {
        try {Thread.currentThread().sleep(millisec);}
        catch (InterruptedException e) { }
    } 
}

