/** Time spent:  hh hours and mm minutes
 * 
 *  Name:
 *  NetID:
 *  What I thought about this assignment:
 *  
 *  
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*
; 

/** Assignment A4 */
public class A4 extends JFrame implements ActionListener {

    private Box north= new Box(BoxLayout.Y_AXIS);    // contains a jJLabel and buttons
    private Box  buttons= new Box(BoxLayout.X_AXIS); // buttons for methods to call
    JLabel description= new JLabel("Click a button to change the GUI to deal with a" +
            " recursive method", JLabel.LEFT);

    private JRadioButton palindrome= new JRadioButton("isPalindrome");
    private JRadioButton commifier= new JRadioButton("Commafy");
    private JRadioButton arraySum= new JRadioButton("ArraySum");
    private JRadioButton sierpinski= new JRadioButton("Sierpinski");
    private JRadioButton grisly= new JRadioButton("Grisly");
    private JRadioButton spiral= new JRadioButton("Spiral");
    private JRadioButton turtleDemo= new JRadioButton("TurtleDemo");

    JPanel methodPanel; // The JPanel that contains info about the method being exercised.

    /** Start the GUI for A4 recursion. */
    public static void main(String[] pars) {
        A4 gui= new A4();
    }

    /** Constructor: A GUI for A4: recursive functions */
    public A4() {
        super("A4: Recursive functions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        north.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);

        north.add(description);
        north.add(new JLabel(" "));
        north.add(buttons);
        fillInButtons();
        Container cp= getContentPane();
        cp.add(north, BorderLayout.NORTH);
        cp.add(new JLabel("    "), BorderLayout.SOUTH);
        methodPanel= new Palindrome();
        pack();
        setVisible(true);
    }

    /** Fill in field buttons, including listeners for them. */
    public void fillInButtons() {
        // Group the radio buttons
        ButtonGroup group= new ButtonGroup();
        group.add(commifier);
        group.add(palindrome);
        group.add(turtleDemo);
        group.add(spiral);
        group.add(sierpinski);
        group.add(grisly);
        group.add(arraySum);

        buttons.add(commifier);
        buttons.add(palindrome);
        buttons.add(turtleDemo);
        buttons.add(spiral);
        buttons.add(sierpinski);
        buttons.add(grisly);
        buttons.add(arraySum);

        commifier.addActionListener(this);
        palindrome.addActionListener(this);
        turtleDemo.addActionListener(this);
        spiral.addActionListener(this);
        sierpinski.addActionListener(this);
        grisly.addActionListener(this);
        arraySum.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Container cp= getContentPane();
        if (methodPanel != null) {
            cp.remove(methodPanel);
        }

        if (e.getSource() == commifier) {
            methodPanel= new Commification();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }

        if (e.getSource() == palindrome) {
            methodPanel= new Palindrome();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }
        
        if (e.getSource() == turtleDemo) {
            methodPanel= new TurtleDemo();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }

        if (e.getSource() == spiral) {
            methodPanel= new Spiral();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }
        
        if (e.getSource() == sierpinski) {
            methodPanel= new Sierpinski();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }
        
        if (e.getSource() == grisly) {
            methodPanel= new Grisly();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        } 
        
        
        
        if (e.getSource() == arraySum) {
            methodPanel= new ArraySum();
            cp.add(methodPanel, BorderLayout.CENTER);
            pack();
        }
    }

    /** Return the integer in s.
     * If s does not contain a sequence of digits (with perhaps leading/trailing
     * white space), Store e as the text in j and return null. */

    public static Integer getInt(String s, String mess, JLabel j) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            j.setText(mess);
            return null;
        }
    }
    
    /** Add j to JPanel jp, with horizontal glue after it. */
    public static void addComponent(JComponent j, JPanel jp) {
        Box b= new Box(BoxLayout.X_AXIS);
        b.add(j);
        b.add(Box.createHorizontalGlue());
        jp.add(b);
    }
    
    /** Add j and f to b, setting the dimension of f to (w, h). */
    public static void addLabelField(JLabel j, JTextField f, Box b, int w, int h) {
        b.add(j);
        b.add(f);
        f.setPreferredSize(new Dimension(w, h));
        f.setMinimumSize(new Dimension(w, h));
        f.setMaximumSize(new Dimension(w, h));
    }

}