import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Creates the word search puzzle
 */
@SuppressWarnings("serial")
public class wordSearchOne extends JFrame {
    
    private ArrayList<ArrayList<wBlock>> puzzle;
    private ArrayList<String> words;
    private wBlock tempBlock;
    private ArrayList<Point> pressed = new ArrayList<Point>();
    private String a;
    private String b;
    private char c;
    private Point n;
    private Point m;
    private int setup;
    private int tempIndex;
    private int tempX;
    private int tempY;
    private String wordList;
    private boolean testMode;
    
    /*
     * Checks if all words are found then lets user know that game is over
     */
    private void checkAndUpdate() {
    	if (words.size() == 0) {
    		JOptionPane.showMessageDialog(wordSearchOne.this, new JLabel("<html><hr><>All Words Found</i><br>Puzzle Complete"));
    	}
    }
    
    /*
     * Checks if currently selected letters are horizontal
     * @return boolean if letters are horizontal
     */
    private boolean goodH() {
    	tempX = (int)pressed.get(0).getX();
    	for (int i = 1; i < pressed.size(); i++) {
    		if (tempX != (int)pressed.get(i).getX()) {
    			return false;
    		}
    	}
    	for (int j = 0; j < pressed.size(); j++) {
    		for (int i = 1; i < pressed.size(); i++) {
    			n = pressed.get(i - 1);
    			m = pressed.get(i);
    			if (n.getY() > m.getY()) {
    				pressed.set(i - 1, m);
    				pressed.set(i, n);
    			}
    		}
    	}
    	tempY = (int)pressed.get(0).getY();
    	for (int i = 1; i < pressed.size(); i++) {
    		if (tempY + i != (int)pressed.get(i).getY()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /*
     * Checks if currently selected letters are vertical
     * @return boolean if letters are vertical
     */
    private boolean goodV() {
    	tempY = (int)pressed.get(0).getY();
    	for (int i = 1; i < pressed.size(); i++) {
    		if (tempY != (int)pressed.get(i).getY()) {
    			return false;
    		}
    	}
    	for (int j = 0; j < pressed.size(); j++) {
    		for (int i = 1; i < pressed.size(); i++) {
    			n = pressed.get(i - 1);
    			m = pressed.get(i);
    			if (n.getX() > m.getX()) {
    				pressed.set(i - 1, m);
    				pressed.set(i, n);
    			}
    		}
    	}
    	tempX = (int)pressed.get(0).getX();
    	for (int i = 1; i < pressed.size(); i++) {
    		if (tempX + i != (int)pressed.get(i).getX()) {
    			return false;
    		}
    	}
    	return true;
    }    
    
    /*
     * Checks if selected letters are in a straight line formation
     * @return boolean if letters are lined up
     */
    private boolean inAGoodLine() {
    	setup = -1;
    	if (goodH()) {
    		setup = 0;
    		return true;
    	}
    	else if (goodV()) {
    		setup = 1;
    		return true;
    	}
    	return false;
    }
    
    /*
     * Removes the Point coordinates for a wBlock in pressed if the wBlock is already selected
     * @return boolean if Point was removed from pressed
     */
    private boolean removePoint(int x, int y) {
    	for (int i = 0; i < pressed.size(); i++) {
    		if (pressed.get(i).getX() == x && pressed.get(i).getY() == y) {
    			pressed.remove(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    /*
     * Checks if selected letters are lined up using inAGoodline()
     * @return boolean if selected letters are lined up
     */
    private boolean orderPressed() {
    	if (inAGoodLine()) {
    		if (setup == 0) {
    			return true;
    		}
    		else if (setup == 1) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else {
    		return false;
    	}
    }
    
    /*
     * Informs each selected letter (wBlock) that it is part of a found word
     */
    private void foundAWord() {
    	for (Point p : pressed) {
    		puzzle.get((int)p.getX()).get((int)p.getY()).hasFound();
    	}
    	pressed.clear();
    }
    
    /*
     * Checks if currently selected letters match a word
     */
    private void checkIfWord() {
    	a = "";
    	b = "";
    	if (orderPressed()) {
    		for (int i = 0; i < pressed.size(); i++) {
        		c = puzzle.get((int)pressed.get(i).getX()).get((int)pressed.get(i).getY()).value;
        		a = a + c;
        		b = c + b;
        	}
    		if (testMode) {
    			testPrintD();
    			System.out.println(a);
        		System.out.println(b);
    		}
    		for (int i = 0; i < words.size(); i++) {
    			if (a.equals(words.get(i))) {
    				tempIndex = i;
    				foundAWord();
    	    		words.remove(tempIndex);
    				break;
    			}
    			if (b.equals(words.get(i))) {
    				tempIndex = i;
    				foundAWord();
    	    		words.remove(tempIndex);
    				break;
    			}
    		}
    	}
    }
    
    /*
     * Returns a string of remaining undiscovered words in puzzle
     * @return String of remaining words
     */
    private String remainingWordsString() {
    	wordList = "";
    	if (words.size() != 0) {
    		wordList = "<html><hr><>" + words.get(0) + "</i>";
    		for (int i = 1; i < words.size(); i++) {
    			wordList += "<br>" + words.get(i);
    		}
    	}
    	return wordList;
    }
    
    /*
     * Test method that prints out the puzzle to the console
     * @param z ArrayList<ArrayList<wBlock>> puzzle data
     */
    private void testPrintA(ArrayList<ArrayList<wBlock>> z) {
    	System.out.println("");
    	for (int x = 0; x < z.size(); x++) {
    		System.out.print(x + " --- ");
    		for (int y = 0; y < z.get(0).size(); y++) {
    			System.out.print(z.get(x).get(y).value);
    		}
    		System.out.println("");
    	}
    }
    
    /*
     * Test method that prints out a lists of words
     * @param r ArrayList<String> lists of words
     */
    private void testPrintB(ArrayList<String> r) {
    	System.out.println("");
    	for (String g : r) {
    		System.out.println(g);
    	}
    }
    
    /*
     * Test method that prints out the puzzle's dimensions
     * @param z ArrayList<ArrayList<wBlock>> puzzle data
     */
    private void testPrintC(ArrayList<ArrayList<wBlock>> z) {
    	System.out.println("");
    	System.out.println(z.size() + " by " + z.get(0).size());
    }
    
    /*
     * Test method that prints out currently selected wBlock coordinates
     */
    private void testPrintD() {
    	System.out.println();
    	for (Point p : pressed) {
    		System.out.println("x: " + p.getX() + " y: " + p.getY());
    	}
    }
    
    /*
     * Test method that prints out all wBlock coordinates
     */
    private void testPrintE() {
    	for (int i = 0; i < puzzle.size(); i++) {
    		for (int j = 0; j < puzzle.get(0).size(); j++) {
    			System.out.print(puzzle.get(i).get(j).obtainX() + "," + puzzle.get(i).get(j).obtainY() + "-");
    		}
    		System.out.println();
    	}
    }
    
    /*
     * Closes puzzle game
     */
    private void finsishPartB() {
    	this.dispose();
    }
    
    /*
     * Constructor for puzzle game
     */
    public wordSearchOne(ArrayList<ArrayList<wBlock>> wordSearchData, ArrayList<String> w, String topic, boolean tMode) {
    	words = w;
        puzzle = wordSearchData;
        testMode = tMode;
        if (testMode) {
        	testPrintA(wordSearchData);
        	testPrintB(w);
        	testPrintC(wordSearchData);
            testPrintE();
        }
        setTitle(topic + " Word Search");
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        JMenu words = new JMenu("Game");
        menu.add(words);
        ActionListener pressLetter = new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		tempBlock = (wBlock)e.getSource();
        		tempX = tempBlock.obtainX();
        		tempY = tempBlock.obtainY();
        		tempBlock.press();
        		if (!removePoint(tempBlock.x, tempBlock.y)) {
        			pressed.add(new Point(tempX,tempY));
        		}
        		if (pressed.size() > 0) {
        			checkIfWord();
        		}
        		checkAndUpdate();
        		if (testMode) {
        			testPrintD();
        		}
        	}
        };
        JMenuItem showWords = words.add("Word List");
        showWords.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		JOptionPane.showMessageDialog(wordSearchOne.this, new JLabel(remainingWordsString()));
        		}
        	});
        JMenuItem end = words.add("Quit");
        end.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		finsishPartB();
        		}
        	});
        JPanel game = new JPanel(new GridLayout(puzzle.size(),puzzle.size()));
        add(game);
        for (int x = 0; x < puzzle.size(); x++) {
            for (int y = 0; y < puzzle.get(0).size(); y++) {
                tempBlock = puzzle.get(x).get(y);
                tempBlock.addActionListener(pressLetter);
                game.add(tempBlock);
            }
        }
        pack();
        setSize(750,750);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
}
