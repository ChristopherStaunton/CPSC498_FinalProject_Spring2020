import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class puzzleGameOne extends JFrame {
    
    private ArrayList<String> data;
    private ArrayList<ArrayList<wBlock>> puzzle;
    private String searchTopic;
    JLabel inputTitle;
    private int longestStringLength;
    private final int maxPermittedWordLength = 10 - 1;
    private final int maxWordLimit = 10;
    private final int minWordLimit = 5;
    private final int loadTimeDelay = 5;
    private final boolean testMode = true;
    private ArrayList<String> usedWords;
    private ArrayList<String> wordsToRemove;
    private Random pickDirection = new Random();
    private int randomDirection;
    private ArrayList<Character> randomChar = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));
    private customWarning customWarn;

    
    private void getData() throws IOException, InterruptedException {
    	File_IO.setInput(searchTopic);
    	customWarn = new customWarning("Loading: Please Wait");
    	File_IO.runScraper();
    	TimeUnit.SECONDS.sleep(loadTimeDelay);
    	data = File_IO.getOutput();
    }
    
    private boolean checkData() {
    	if (data == null || data.isEmpty() || data.size() == 0) {
    		return false;
    	}
    	else if (data.size() < minWordLimit) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    private void hideIntro() {
        setVisible(false);
    }
    
    private boolean corDist(String s, String d, int y, int x) {
        int l = s.length();
        boolean fit = true;
        int wordIndex = 0;
        if (d.equals("R")) {
            fit = true;
            wordIndex = 0;
            if (l <= puzzle.get(y).size() - x) {
                for (int a = x; (a < puzzle.get(y).size() && a < l + x && wordIndex < l); a++) {//?
                    if (puzzle.get(y).get(a) == null || s.charAt(wordIndex) == puzzle.get(y).get(a).value) {
                        wordIndex++;
                    }
                    else {
                        fit = false;
                        break;
                    }
                }
                wordIndex = 0;
                if (fit) {
                    for (int a = x; (a < puzzle.get(y).size() && a < l + x && wordIndex < l ); a++) {//?
                        if (puzzle.get(y).get(a) == null) {
                            puzzle.get(y).set(a, new wBlock(s, y, a, d, wordIndex));
                        }
                        wordIndex++;
                    }
                    return true;
                }
            }
        }
        else if (d == "U") {
        	fit = true;
            wordIndex = 0;
            if (l <=  y) {
                for (int a = y; (a >= 0 && a > y - l && wordIndex < l); a--) {//?
                    if (puzzle.get(a).get(x) == null || s.charAt(wordIndex) == puzzle.get(a).get(x).value) {
                        wordIndex++;
                    }
                    else {
                        fit = false;
                        break;
                    }
                }
                wordIndex = 0;
                if (fit) {
                    for (int a = y; (a >= 0 && a > y - l && wordIndex < l); a--) {//?
                        if (puzzle.get(a).get(x) == null) {
                            puzzle.get(a).set(x, new wBlock(s, a, x, d, wordIndex));
                        }
                        wordIndex++;
                    }
                    return true;
                }
            }
        }
        else if (d == "D") {
        	fit = true;
            wordIndex = 0;
            if (l <= puzzle.get(y).size() - y) {
                for (int a = y; (a < puzzle.get(y).size() && a < l + y && wordIndex < l); a++) {//?
                    if (puzzle.get(a).get(x) == null || s.charAt(wordIndex) == puzzle.get(a).get(x).value) {
                        wordIndex++;
                    }
                    else {
                        fit = false;
                        break;
                    }
                }
                wordIndex = 0;
                if (fit) {
                    for (int a = y; (a < puzzle.get(y).size() && a < l + y && wordIndex < l); a++) {//?
                        if (puzzle.get(a).get(x) == null) {
                            puzzle.get(a).set(x, new wBlock(s, a, x, d, wordIndex));
                        }
                        wordIndex++;
                    }
                    return true;
                }
            }
        }
        else if (d == "L") {
        	fit = true;
            wordIndex = 0;
            if (l <= x) {
                for (int a = x; (a >= 0 && a > x - l && wordIndex < l); a--) {
                    if (puzzle.get(y).get(a) == null || s.charAt(wordIndex) == puzzle.get(y).get(a).value) {
                        wordIndex++;
                    }
                    else {
                        fit = false;
                        break;
                    }
                }
                wordIndex = 0;
                if (fit) {
                    for (int a = x; (a >= 0 && a > x - l && wordIndex < l); a--) {
                        if (puzzle.get(y).get(a) == null) {
                            puzzle.get(y).set(a, new wBlock(s, y, a, d, wordIndex));
                        }
                        wordIndex++;
                    }
                    return true;
                }
            }
        }
        else {
            return false;
        }
        return false;
    }
    
    private boolean checkAdded(String s, int x, int y) {
        //check up
    	randomDirection = pickDirection.nextInt((3 - 0) + 1) + 0;
    	
    	
        if (randomDirection == 0 && corDist(s, "U", x, y)) {
            return true;
        }
        else if (randomDirection == 1 && corDist(s, "D", x, y)) {
        	return true;
        }
        else if (randomDirection == 2 && corDist(s, "L", x, y)) {
        	return true;
        }
        else if (randomDirection == 3 && corDist(s, "R", x, y)) {
        	return true;
        }
        
        return false;
    }
    
    private boolean isWordAdded(String s) {
        for (int x = 0; x < puzzle.size(); x++) {
            for (int y = 0; y < puzzle.get(0).size(); y++) {
                if (checkAdded(s, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //puts words from data list
    //into puzzle matrix
    private void convertData() {
    	usedWords = new ArrayList<String>();
        for (String s : data) {
            if (usedWords.size() < maxWordLimit && isWordAdded(s)) {
               usedWords.add(s);
            }
            else {
            	break;
            }
        }
    }
    
    private char getRandomLetter() {
    	if (testMode) {
    		return ' ';
    	}
    	else {
    		return randomChar.get(pickDirection.nextInt((randomChar.size() - 1 - 0) + 1) + 0);
    	}
    }
    
    private void fillBlank() {
        for (int x = 0; x < puzzle.size(); x++) {
            for (int y = 0; y < puzzle.get(x).size(); y++) {
                if (puzzle.get(x).get(y) == null) {
                    puzzle.get(x).set(y, new wBlock(getRandomLetter(), x, y));
                }
            }
        }
    }
    
    private void removeWordsB(String t) {
    	int r = -1;
    	for (int i = 0; i < data.size(); i++) {
    		if (data.get(i).equals(t)) {
    			r = i;
    			break;
    		}
    	}
    	if (r != -1) {
    		data.remove(r);
    	}
    	else {
    		System.out.println("Could not find word to remove from data");
    	}
    }
    
    private void removeWordsA() {
    	for (String s : wordsToRemove) {
    		removeWordsB(s);
    	}
    }
    
    private boolean isValidInput(String s) {
    	if (s == null || s.equals("")) {
    		JOptionPane.showMessageDialog(puzzleGameOne.this, new JLabel("<html><hr><>Invalid Input</i><br>Topic cannot be blank"));
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    private void buildBlankPuzzle() {
        longestStringLength = 0;
        wordsToRemove = new ArrayList<String>();
        for (String s : data) {
            if (s.length() > longestStringLength && maxPermittedWordLength >= s.length()) {
                longestStringLength = s.length();
            }
            else if (maxPermittedWordLength < s.length()) {
            	wordsToRemove.add(s);
            }
        }
        removeWordsA();
        for (int x = 0; x < longestStringLength + 5; x++) {
            puzzle.add(new ArrayList<wBlock>());
            for (int y = 0; y < longestStringLength + 5; y++) {
                puzzle.get(x).add(null);
            }
        }
    }
    
    private boolean manage() {
        puzzle = new ArrayList<ArrayList<wBlock>>();
        try {
			getData();
		}
        catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
        if (!checkData()) {
        	customWarn.closeThat();
            return false;
        }
        else {
        	buildBlankPuzzle();
            convertData();
            fillBlank();
            customWarn.closeThat();
            return true;
        }
    }
    
    private void finsishPartA() {
    	this.dispose();
    }
    
    public puzzleGameOne() {
    	
    	setTitle("Word Search");
        
        JPanel inputTopic = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
        add(inputTopic);
        
        inputTitle = new JLabel("Input Topic");
        inputTopic.add(inputTitle);
        
        JTextField input = new JTextField(20);
        inputTopic.add(input);
        
        JButton startButton = new JButton("Start");
        inputTopic.add(startButton);
        
        ActionListener startButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isValidInput(input.getText())) {             	
                	System.out.println("Invalid Input");
                }
                else {
                    searchTopic = input.getText();
                    if (manage()) {
                        @SuppressWarnings("unused")
						wordSearchOne theGame = new wordSearchOne(puzzle, usedWords, searchTopic, testMode);
                        hideIntro();
                        finsishPartA();
                    }
                    else {
                    	JOptionPane.showMessageDialog(puzzleGameOne.this, new JLabel("<html><hr><>Word Search Failed</i><br>Could not find sufficient words"));
                    	System.out.println("Search Failed");
                    }
                }
            }
        };
        startButton.addActionListener(startButtonListener);
        pack();
        setSize(300,150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        JFrame frame = new puzzleGameOne();
        frame.setVisible(true);
    }
    
}
