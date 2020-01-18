import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** An instance is a JPanel with a Turtle, set up to help the user draw spirals. */
public class Spiral extends Turtle {
    private int n= 4; // number of lines to draw n a spiral
    private int d= 20; // The length of the first pixel to draw in the spiral
    private double ang= 90;  // The angle between adjacent lines of the spiral

    /** jl0, jl1, and jl2 give instructions for drawing a spiral. */
    private JLabel jl0= new JLabel(" Try drawing a spiral. Fill in the number of lines" +
            " (try 300 for a real spiral), length of first line ");
    private JLabel jl1= new JLabel(" (1 for a real spiral), the angle between " +
            "lines (try different ones, e.g. " + "45, 90, 89, 91,");
    private JLabel jl2= new JLabel(" 135, any number of 190..200). Values " +
            "given are good for testing.");
    
    /** The box that contains all the components. */
    Box spiralFields= new Box(BoxLayout.X_AXIS);

    /** the field for the number of lines and a comment for it. */
    private JLabel linesLabel= new JLabel(" number: ");
    private JTextField linesField= new JTextField("10", 3);

    /** the field for the length of the first line and a comment for it. */
    private JLabel lengthLabel= new JLabel("length of first: ");
    private JTextField lengthField= new JTextField("20", 3);

    /** the field for the angle between lines and a comment for it. */
    private JLabel angleLabel= new JLabel("angle: ");
    private JTextField angleField= new JTextField("90", 3);

    private JButton ready= new JButton("Ready!");  // Button to click when input is ready

    /** field for the answer. */
    private JLabel jAnswer= new JLabel(" "); // Will contains the answer

    /** Constructor: a JPanel with a Turtle and fields for inputting three items 
     * for constructing a spiral: the number of lines, the length of the first line,
     * and the angle to be placed between lines. Also a "Ready" button. */
    public Spiral() {
        super();
        graphics= getGraphics();
        height= 600;
        add(new JLabel(" "));

        A4.addComponent(jl0, this);
        A4.addComponent(jl1, this);
        A4.addComponent(jl2, this);

        add(new JLabel("   "));
        A4.addLabelField(linesLabel, linesField, spiralFields, 30, 20);
        A4.addLabelField(lengthLabel, lengthField, spiralFields, 30, 20);
        A4.addLabelField(angleLabel, angleField, spiralFields, 30, 20);

        spiralFields.add(ready);
        spiralFields.add( Box.createHorizontalGlue() );

        add(spiralFields);

        ready.addActionListener(new SpiralCaller());
    }

    /** Procedure actionPerformed reads fields and stores them in fields
     * n, d, and ang, and repaints the JPanel. */
    public class SpiralCaller implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer val= A4.getInt(linesField.getText(),
                    "Number-of-lines field does not contain an integer", jAnswer);
            if (val == null) return;
            n= val;

            val= A4.getInt(lengthField.getText(),
                    "Length-of-first-line field does not contain an integer", jAnswer);
            if (val == null) return;
            d= val;

            val= A4.getInt(angleField.getText(),
                    "The angle field does not contain an integer", jAnswer);
            if (val == null) return;
            ang= val;

            repaint();
        }
    }

    public @Override void paint(Graphics g) {
        super.paint(g);
        graphics= g;
        clear();
        placeInCenter();
        drawSpiral(1, Color.blue);
    }

    /**  This method uses fields g, n, ang.
     * Draw lines i..n (i.e., lines i, i+1, ..., n) of a spiral, beginning at the
     * current turtle position and angle. 
     * Put the value of ang (a field) between adjacent lines.
     * 
     * Line i should be d*i pixels long(d is a field); line i+1 should be
     * (i+1)*d pixels, etc.
     * 
     * The lines should alternate between blue, red, and green, in that
     * order, with the line i being color c.
     * 
     *  Note: if i > n, which is allowed, there is nothing to draw. */
    public void drawSpiral(int i, Color c) {
    	if (i > n) {
    		return;}
    	setColor(c);
    	moveAhead(d*i);
    	addAngle(ang);
    	if (i % 3 == 1) {
    		c = Color.red;
    		drawSpiral(i+1, c);}
    	else if (i % 3 == 2) {
    		c = Color.green;
    		drawSpiral(i+1, c);}
    	else {
    		c = Color.blue;
    		drawSpiral(i+1, c);}
    }
}