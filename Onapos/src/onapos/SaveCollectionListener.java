package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class SaveCollectionListener implements ActionListener {

	private Collection toSave;
	
	public void setCollection(Collection c) {
		toSave = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(toSave==null) {
			System.err.println("Warning: tried to save nothing (nothing selected?)");
			return;
		}
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		if(fc.getSelectedFile() == null) return; // if the frame is disposed before we get a file (avoids a NullPointerException)
		CollectionFile cf = new CollectionFile(fc.getSelectedFile());
		cf.write(toSave);
	}

}
