import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** An instance is JPanel that allows the user to call function
 * commafy in the GUI. */
public class TurtleDemo extends Turtle {

    // Instructions for the TurtleDemo thing. */
    private JLabel jl= new JLabel(" The shapes are drawn by method calls in " +
            "paint(). Take a look", JLabel.LEFT);

    /** Constructor: A panel with a field for a String and a second
     * JLabel for the answer */
    public TurtleDemo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        down= 0;
        height= 400;

        add(new JLabel(" "));
        A4.addComponent(jl, this); //Put label jl in the GUI, left adjusted
    }

    /** Draw a black line 30 pixels to the right (east) and then
    a blue line 35 pixels down (south).
    Precondition: the turtle is facing east.*/
    public void drawTwoLines() {
        Color save= getColor();

        moveAhead(30); // draw a line 30 pixels to the right (east) 
        addAngle(270); // add 270 degrees to the angle
        setColor(Color.blue);
        moveAhead(35);

        setColor(save);
    }

    /** Draw an equilateral triangle of side length e and color c at the turtle's
     * current position. UPON COMPLETION, THE following properties SHOULD BE THE
     * SAME AS WHEN THE METHOD STARTED: turtle position, turtle direction,
     * pen color, and whether the pen is up or down.
     * Precondition: the pen is down. */
    public void drawTriangle(int e, Color c) {
        Color save= getColor();
        setColor(c);
        for (int i= 1; i <= 3; i= i+1) {
            moveAhead(e);
            addAngle(120);
        }
        setColor(save);
    }

    /** Draw six green equilateral triangles with side lengths d to
    form a hexagon, starting at the turtle's current position and
    angle.  Precondition: the pen is down. */
    public void drawGreenHex(int d) {
        for (int i= 1; i <= 6; i= i+1) {
            drawTriangle(d, Color.green);
            addAngle(60);
        }
    }

    public @Override void paint(Graphics g) {
        super.paint(g);
        graphics= g;
        placeInCenter();

        g.drawString("CS2110, Fall 2013", 10, 100);
        g.drawLine(10, 110, 80, 110);
        g.setColor(Color.red);
        g.fillRect(140, 80, 30, 50);

        putPenUp();         // Put pen up so that moveAhead does not draw a line
        moveAhead(-100);

        putPenDown();
        drawTwoLines();

        putPenUp();
        setAngle(0);
        moveAhead(60);
        putPenDown();
        
        drawTriangle(30, Color.red);

        putPenUp();
        moveAhead(100);
        putPenDown();
        
        drawGreenHex(40);
    }

}