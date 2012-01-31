package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class OpenCollectionListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		CollectionFile cf = new CollectionFile(fc.getSelectedFile());
		cf.read();
		// TODO: find a way to send this Collection back to OnaposUI
	}

}
