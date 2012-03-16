package onapos;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class OnaposSplashScreen extends JWindow {
	private JProgressBar progress;
	
	public OnaposSplashScreen() {
		JPanel panel = new JPanel();
		progress = new JProgressBar(0,100);
		progress.setValue(0);
		JLabel label = new JLabel("Loading...");
		panel.add(label);
		panel.add(progress);
		setContentPane(panel);
		setSize(250, 100);
		setLocationRelativeTo(null);
		setVisible(true);
		update(getGraphics());
	}
	
	public void setProgress(int to) {
		progress.setValue(to);
		update(getGraphics());
	}
	
}
