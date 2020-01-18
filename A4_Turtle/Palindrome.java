import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/** An instance is JPanel that allow the user to call function
 * isPalindrome in the GUI */
public class Palindrome extends JPanel {

    private JLabel jl= new JLabel("Try isPalindrome. Type a string in the field and hit the return/enter key");
    private JTextField jf= new JTextField(""); 
    private JLabel jAnswer= new JLabel(" ");

    /** Constructor: A panel with a field for a String and a second
     * JLabel for the answer */
    public Palindrome() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(new JLabel(" "));
        
        A4.addComponent(jl, this);
        A4.addComponent(jf, this);
        A4.addComponent(jAnswer, this);
       
        jf.addActionListener(new IsPalindromeCaller());
    }

    /** Procedure actionPerformed calls isPalindrome and outputs accordingly. */
    public class IsPalindromeCaller implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String s= jf.getText();
            if (isPalindrome(s)) {
                jAnswer.setText("It's a palindrome!");
            }
            else {
                jAnswer.setText("It's not a palindrome.");
            }
            repaint();
        }
    }
    
    /** Return true iff s is a palindrome */
    public static boolean isPalindrome(String s) {
        if (s.length() == 0 || s.length() == 1) {
        	return true;}
        else {
        	return s.charAt(0) == s.charAt(s.length()-1) && 
        			isPalindrome(s.substring(1,s.length()-1));}
    }
}