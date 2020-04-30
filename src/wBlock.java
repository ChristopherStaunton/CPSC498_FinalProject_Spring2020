import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

//individual block in puzzle matrix
@SuppressWarnings("serial")
public class wBlock extends JButton {
    //character shown
    protected char value;
    //coordinates
    protected int x, y;
    //if part of actual word in puzzle
    protected boolean answer;
    // U, uR, R, dR, D, lD, L, uL
    protected String direction;
    //length of total word
    protected ArrayList<Integer> l;
    //the total word
    protected ArrayList<String> words;
    //index in total string
    protected ArrayList<Integer> indexes;
    //number of words used for in puzzle
    protected int count;
    //if already pressed
    protected boolean alreadyPressed = false;
    //if part of found word
    protected boolean found = false;
    
    public void hasFound() {
    	found = true;
    	setBackground(Color.GREEN);
    	alreadyPressed = false;
    }
    
    public void upUse() {
        count++;
    }
    
    public int obtainX() {
    	return x;
    }
    
    public int obtainY() {
    	return y;
    }
    
    //find/found word method
    
    public void press() {
    	if (alreadyPressed && found) {
    		setBackground(Color.GREEN);
    		alreadyPressed = false;
    	}
    	else if (alreadyPressed && !found) {
    		setBackground(Color.WHITE);
    		alreadyPressed = false;
    	}
    	else if (!alreadyPressed && found) {
    		setBackground(Color.CYAN);
    		alreadyPressed = true;
    	}
    	else if (!alreadyPressed && !found) {
    		setBackground(Color.YELLOW);
    		alreadyPressed = true;
    	}
    }
    
    private void startUpVar() {
        l = new ArrayList<Integer>();
        words = new ArrayList<String>();
        indexes = new ArrayList<Integer>();
    }
    
    public wBlock(String word, int x, int y, String direction, int index) {
        super("" + (word.charAt(index)));
        startUpVar();
        value = word.charAt(index);
        this.x = x;
        this.y = y;
        answer = true;
        this.direction = direction;
        l.add(word.length());
        words.add(word);
        indexes.add(index);
        count = 1;
    }
    
    public wBlock(char letter, int x, int y) {
        super("" + letter);
        startUpVar();
        value = letter;
        this.x = x;
        this.y = y;
        answer = false;
        direction = null;
        l = null;
        words = null;
        indexes = null;
        count = 0;
    }
    
}
