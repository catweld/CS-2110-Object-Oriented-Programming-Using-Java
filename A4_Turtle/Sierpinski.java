import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


/** An instance is Turtle that allow the user to call function
 * sierpinski in the GUI. */
public class Sierpinski extends Turtle {

    private int sideLength= 0; // length f a side of largest Sierpinski Triangle
    private int depth= 0; // The depth of recursion

    /** labels giving instructions. */
    private JLabel jl0= new JLabel(" Try drawing a Sierpinski triangle. Fill in the length " +
            "length of the side (e.g. 200), ");
    private JLabel jl1= new JLabel(" and the depth of recursion and hit button Ready.");

    /** contains all the fields and accompanying labels. */
    Box spiralFields= new Box(BoxLayout.X_AXIS);

    /** Field for the length of a side, with annotating label. */
    private JLabel lengthLabel= new JLabel("length of side: ");
    private JTextField lengthField= new JTextField("200", 3);

    /** Fields for the depth of recursion, with annotating label. */
    private JLabel depthLabel= new JLabel("depth of recursion: ");
    private JTextField depthField= new JTextField("0", 3);

    /** Button to click to obtain GUI field values */
    private JButton ready= new JButton("Ready!");

    /** Will contain error messages, if necessary. */
    private JLabel jAnswer= new JLabel(" ");

    /** Constructor: A JPanel with a turtle sierpinski fields */
    public Sierpinski() {
        super();
        graphics= getGraphics();

        add(new JLabel(" "));

        A4.addComponent(jl0, this);
        A4.addComponent(jl1, this);

        add(new JLabel("   "));
        A4.addLabelField(lengthLabel, lengthField, spiralFields, 30, 20);
        A4.addLabelField(depthLabel, depthField, spiralFields, 30, 20);

        spiralFields.add(ready);
        spiralFields.add(Box.createHorizontalGlue());
        add(spiralFields);

        add(new JLabel("   "));
        A4.addComponent(jAnswer, this);

        ready.addActionListener(new SpiralCaller());
    }

    /** Procedure actionPerformed stores the GUI fields for Sierpinski in
     * in fields sideLength and depth, checking for errors. */
    public class SpiralCaller implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Integer val= A4.getInt(lengthField.getText(),
                    "Length-of-first-line field does not contain an integer", jAnswer);
            if (val == null) return;

            sideLength= val;

            val= A4.getInt(depthField.getText(),
                    "The angle field does not contain an integer", jAnswer);
            if (val == null) return;
            depth= val;
            if (depth > 6) {
                jAnswer.setText("We don't allow depth > 6 because it may take too long.");
                depth= 0;
                return;
            }
            repaint();
        }
    }

    public @Override void paint(Graphics g) {
        super.paint(g);
        graphics= g;
        clear();
        placeInCenter();
        sierpinski(10, sideLength + down, depth, sideLength);
    }

    /** Draw a Sierpinksi triangle of depth d with side length s and with
     * the bottom left corner (x, y). 
     * Precondition: d >= 0 and s > 0.*/
    private void sierpinski(double x, double y, int d, double s){
    	if (d == 0){
            jumpTo(x,y,90);
            fillTriangle(s);}
        else{
            sierpinski(x,y,d-1,s/2);
            sierpinski(x+s/2,y,d-1,s/2);
            sierpinski(x+s/4,y-Math.sin(Math.PI / 3.0)*s/2,d-1,s/2);}
    }
}