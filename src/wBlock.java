import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

/*
 * Individual JButton blocks that make up letters in word search puzzle
 */
@SuppressWarnings("serial")
public class wBlock extends JButton {
	
    protected char value;
    protected int x, y;
    protected boolean answer;
    protected String direction;
    protected ArrayList<Integer> l;
    protected ArrayList<String> words;
    protected ArrayList<Integer> indexes;
    protected boolean alreadyPressed = false;
    protected boolean found = false;
    
    /*
     * Adjusts wBlock for when it is part of a found word
     */
    public void hasFound() {
    	found = true;
    	setBackground(Color.GREEN);
    	alreadyPressed = false;
    }
    
    /*
     * Gets x coordinate for wBlock
     * @return int x coordinate
     */
    public int obtainX() {
    	return x;
    }
    
    /*
     * Gets y coordinate for wBlock
     * @return int y coordinate
     */
    public int obtainY() {
    	return y;
    }
    
    /*
     * Alters wBlock for when JButton is clicked on
     */
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
    
    /*
     * Initializes main variables
     */
    private void startUpVar() {
        l = new ArrayList<Integer>();
        words = new ArrayList<String>();
        indexes = new ArrayList<Integer>();
    }
    
    /*
     * Constructor for wBlocks that are part of words
     * @param word String of word wBlock is part of
     * @param x int coordinate in puzzle
     * @param y int coordinate in puzzle
     * @param direction String of direction word faces
     * @param index int location of character in word that wBlock displays
     */
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
    }
    
    /*
     *Constructor for wBlocks that hold a random character
     *@param letter char that wBlock displays
     *@param x int coordinate in puzzle
     *@param y int coordinate in puzzle
     */
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
    }
    
}
