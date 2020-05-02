import javax.swing.JFrame;

/*
 * Used for creating temporary warnings
 */
@SuppressWarnings("serial")
public class customWarning extends JFrame {
	
	/*
	 * Closes the warning
	 */
	public void closeThat() {	
		setVisible(false);
		this.dispose();
	}
	
	/*
	 * Constructor
	 * @param m String message to display
	 */
	public customWarning(String m) {
		setTitle(m);
		pack();
		setSize(250,0);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
	}
	
}
