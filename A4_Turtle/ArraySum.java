import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;
import java.util.Arrays;

/** An instance is a JPanel that allows the user to call function
 * arraySum in the GUI */
public class ArraySum extends JPanel {

    /** the value in the GUI field that contains the dimensions*/
    private String dims;

    /** the value in the GUI field that contains the number of non-zero values */
    private int numberOfValues;

    /** the value in the GUI field that contains the non-zero sum of array elements */
    private int sum;

    /** label giving instructions. */
    private JLabel jl= new JLabel(" Try arraySum. Give dimensions, number of non-zero " +
            "values, and a desired sum. Click Ready!", JLabel.LEFT);

    /** contains all the fields and accompanying labels. */
    Box valuesSumStuff= new Box(BoxLayout.X_AXIS);

    /** Field for size of each dimension, with annotating label. */
    private JLabel dimLabel= new JLabel(" Put in desired size of each dimension, separated by " +
            "blanks (empty means type Integer). Eg. 2 3", JLabel.LEFT);
    private JTextField dimField= new JTextField(40);

    /** Field for the number of non-zero values to put in array, with
     * annotating label */
    private JLabel valuesLabel= new JLabel(" desired number of non-zero values: ");
    private JTextField valuesField= new JTextField(10);

    /** Field for the value the array elements should sum to, with annotating label */
    private JLabel sumLabel= new JLabel("with this sum: ");
    private JTextField sumField= new JTextField(10);

    /** Button to click to obtain GUI field values */
    private JButton ready= new JButton("Ready!");

    /** Will contain the array */
    private JLabel jArray= new JLabel(" ");

    /** Will contain the answer or error messages */
    private JLabel jAnswer= new JLabel(" ");

    /** Constructor: A panel with a field for a String and a second
     * JLabel for the answer */
    public ArraySum() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        add(new JLabel("  "));
        A4.addComponent(jl, this);

        add(new JLabel("  "));
        A4.addComponent(dimLabel, this);
        A4.addComponent(dimField, this);

        dimField.setPreferredSize(new Dimension(200, 20));
        dimField.setMinimumSize(new Dimension(200, 20));
        dimField.setMaximumSize(new Dimension(200, 20));

        add(new JLabel("  "));
        A4.addLabelField(valuesLabel, valuesField, valuesSumStuff, 30, 20);

        valuesSumStuff.add(new JLabel("   "));
        A4.addLabelField(sumLabel, sumField, valuesSumStuff, 30, 20);

        valuesSumStuff.add(ready);
        valuesSumStuff.add( Box.createHorizontalGlue() );

        add(valuesSumStuff);

        add(new JLabel("  "));

        A4.addComponent(jArray, this);
        A4.addComponent(jAnswer, this);

        ready.addActionListener(new SumCaller());
    }

    /** Procedure actionPerformed reads in values from
     * GUI, saves them in fields, creates the desired array,
     * calls the recursive function arraySum, and puts the answer in the GUI.
     * Of course, errors may cause this not to happen, with an explanation on the GUI. */
    public class SumCaller implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            jAnswer.setText("");
            jArray.setText("");
            if (!saveFields()) {   // save all the GUI data in fields
                return;
            }
            Vector<Integer> v= getDimVector(); // v is a Vector of dimension sizes
            if (v == null) return;

            if (vSize(v) == null) {  // vSize(v): total number of array elements (null if error)
                return;
            }

            Object a= dimArray(v); // a contains the array (null if an error), filled with 0s
            if (a == null) return;

            // Populate the array with numberOfValues non-zero values, which sum to sum
            if (a instanceof Integer[]) setToSum((Integer[])a, numberOfValues, sum);
            if (a instanceof Integer[][]) setToSum((Integer[][])a, numberOfValues, sum);
            if (a instanceof Integer[][][]) setToSum((Integer[][][])a, numberOfValues, sum);

            setArrayLabel(a);         // Set the array label to contain the array, as a string
            jAnswer.setText("array sum is " + arraySum(a));
            repaint();
        }
    }

    /** Save the GUI fields in fields dims, dims, numberOfValues, and sum.
     * Return true iff only error occurred. */
    public boolean saveFields() {
        dims= dimField.getText().trim();
        Integer nValues= A4.getInt(valuesField.getText(),
                "Number-of-values field does not contain an integer", jAnswer);
        if (nValues == null) return false;
        numberOfValues= nValues;

        Integer sumInt= A4.getInt(sumField.getText(),
                "Sum field does not contain an integer", jAnswer);
        if (sumInt == null) return false;
        sum= sumInt;
        return true;
    }

    /** Store array b as a string in label jArray.  */
    public void setArrayLabel(Object b) {
        if (b instanceof Integer) jArray.setText("" + b);
        if (b instanceof Integer[]) jArray.setText(Arrays.deepToString((Integer[])b));
        if (b instanceof Integer[][]) jArray.setText(Arrays.deepToString((Integer[][])b));
        if (b instanceof Integer[][][]) jArray.setText(Arrays.deepToString((Integer[][][])b));
    }

    /** Return a vector of desired dimensions. If there is an error, put a message
     * in jAnswer and return null. */
    public Vector<Integer> getDimVector() {
        dims= dims.trim();
        Vector<Integer> v= new Vector<Integer>();
        while (!dims.equals("")) {
            int k= (dims + " ").indexOf(" ");
            Integer val= A4.getInt(dims.substring(0,k),
                    "dimensions field can contain only digits and blanks", jAnswer);
            if (val == null) return null;
            v.add(val);
            dims= dims.substring(k).trim();
        }
        return v;
    }

    /** Return the number of elements given by dimensions in v.
     * If the number is greater than field sum, store a message and return null.
     * If the number of elements is 1 and sum is 0, give error message, rturn null.  */
    public Integer vSize(Vector<Integer> v) {
        int n= 1;
        for (Integer k : v) {
            n= n * k;
        }

        if (n < numberOfValues) {
            jAnswer.setText("number of desired non-zero values > array size.");
            return null;
        }
        if (n == 1  &&  sum == 0) {
            jAnswer.setText("Only 1 value in array, and desired sum is 0. Won't work.");
            return null;
        }
        return n;
    }

    /** Return the array or Integer corresponding to the dimensions in dims.
     * filled with 0's. */
    public Object dimArray(Vector<Integer> v) {
        switch (v.size()) {
            case 0: return new Integer(sumField.getText().trim());
            case 1: {
                Integer[] b= new Integer[v.get(0)];
                setTo0(b);
                return b;
            }
            case 2: {
                Integer[][] b= new Integer[v.get(0)][v.get(1)];
                setTo0(b);
                return b;
            }
            case 3: {
                Integer[][][] b= new Integer[v.get(0)][v.get(1)][v.get(2)];
                setTo0(b);
                return b;
            }

            default: jAnswer.setText("GUI works only for 0 to 3 dimensions");
            return null;
        }
    }

    /** Set elements of b to 0. */
    public static void setTo0(Integer[][][][] b) {
        for (int k= 0; k < b.length; k= k+1) {
            setTo0(b[k]);
        }
    }

    /** Set elements of b to 0. */
    public static void setTo0(Integer[][][] b) {
        for (int k= 0; k < b.length; k= k+1) {
            setTo0(b[k]);
        }
    }

    /** Set elements of b to 0. */
    public static void setTo0(Integer[][] b) {
        for (int k= 0; k < b.length; k= k+1) {
            setTo0(b[k]);
        }
    }

    /** Set elements of b to 0. */
    public static void setTo0(Integer[] b) {
        Arrays.fill(b, 0);
    }

    /** Set k values of b to non-zero elements to that b sums to sum.
     * Precondition: k < b.length  and (k = 0 OR sum != 0) */
    public static void setToSum(Integer[] b, int k, int sum) {
        for (int h= 0; h < k; h= h+1) {
            b[h]= h;
            sum= sum - h;
            if (sum == 0) {
                b[h]= b[h] + 1;
                sum= sum-1;
            }
            b[0]= sum;
        }
    }

    /** Set k values of b to non-zero elements to that b sums to sum.
     * Precondition: k < b.length  and sum != 0 and b is rectangular */
    public static void setToSum(Integer[][] b, int k, int sum) {
        if (k == 0) return; // To simplify below, make sure there is at least one row.
        
        int rowN= k / b.length;
        int overage= k % b.length;
        int sumForRow= sum / b.length;
        // Fill rows 1..b.length-1, in reverse order
        // invariant: sum is the number of values left to put in, and sum != 0.
        for (int h= b.length - 1; h > 0; h= h-1) {
            int n= rowN + (h < overage ? 1 : 0); // number of non-zero elements for row b[h]
            if (n > 0) {
                int sumForRowH= sumForRow;    // the sum for row h
                sum= sum - sumForRowH;        // the sum for rows 0..h-1
                // make sure sum != 0 and sumForRowH != 0
                if (sumForRowH == 0 && sum == 1) {
                    sumForRowH= 2;
                    sum= sum - 2;
                } else if (sumForRowH == 0) {
                    sumForRowH= 1;
                    sum= sum - 1;
                }
                setToSum(b[h], n, sumForRowH);
            }
        }
        
        // Fill row 0.
        int n= rowN + (0 < overage ? 1 : 0); // number of non-zero elements for row b[0]
        if (n > 0) {
            setToSum(b[0], n, sum);
        }
        
    }

    /** Set k values of b to non-zero elements to that b sums to sum.
     * Precondition: k < b.length  and sum != 0 and b is rectangular */
    public static void setToSum(Integer[][][] b, int k, int sum) {
        if (k == 0) return; // To simplify below, make sure there is at least one row.
        
        int rowN= k / b.length;
        int overage= k % b.length;
        int sumForRow= sum / b.length;
        // Fill rows 1..b.length-1, in reverse order
        // invariant: sum is the number of values left to put in, and sum != 0.
        for (int h= b.length - 1; h > 0; h= h-1) {
            int n= rowN + (h < overage ? 1 : 0); // number of non-zero elements for row b[h]
            if (n > 0) {
                int sumForRowH= sumForRow;    // the sum for row h
                sum= sum - sumForRowH;        // the sum for rows 0..h-1
                // make sure sum != 0 and sumForRowH != 0
                if (sumForRowH == 0 && sum == 1) {
                    sumForRowH= 2;
                    sum= sum - 2;
                } else if (sumForRowH == 0) {
                    sumForRowH= 1;
                    sum= sum - 1;
                }
                setToSum(b[h], n, sumForRowH);
            }
        }
     
        // Fill row 0.
        int n= rowN + (0 < overage ? 1 : 0); // number of non-zero elements for row b[0]
        if (n > 0) {
            setToSum(b[0], n, sum);
        }
    }

    /** Return the sum of all integer values in obj.
     * Precondition: obj is one of the classes: Integer, Integer[], Integer[][], Integer[][][], etc.
     * If obj is an array, none of its elements is null.
     * Examples: Below, an integer like 4 represents an Integer object that
     * contains that integer.
     * For the argument 5, the value 5 is returned.
     * For the array {1, 2, 3}, 6 is returned because 1+2+3 = 6.
     * For the array {{1, 2, 5}, {3, 4}}, 15 is returned because 1+2+5+3+4 = 15.
     * For the array {{{1}, {0, 3}, {}}, {{1,2,3}, {3}}}, 13 is returned because 1+0+3+0+1+2+3+3 = 13.
     */
    public static int arraySum(Object obj) {
        if (obj instanceof Integer) {
        	return (Integer) obj;}
        else {
        	Object [] ary = (Object[]) obj;
        	int n = ary.length;
        	int x = 0;
        	int sum = 0;
        	while (x < n) {
        		sum = sum + arraySum(ary[x]);
        		x = x+1;}
        	return sum;
        }    
    }
}