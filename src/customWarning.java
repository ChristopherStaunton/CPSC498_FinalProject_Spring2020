import javax.swing.JFrame;

@SuppressWarnings("serial")
public class customWarning extends JFrame {
	
	public void closeThat() {	
		setVisible(false);
		this.dispose();
	}
	
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
