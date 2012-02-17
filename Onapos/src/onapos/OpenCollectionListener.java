package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class OpenCollectionListener implements ActionListener {
	
	private OnaposUI context;
	
	/**
	 * A listener for the Open Collection menu item
	 * @param o the OnaposUI that created this listener
	 */
	public OpenCollectionListener(OnaposUI o) {
		context = o;
	}
	
	/**
	 * Prompts the user for a file to open, then loads a collection from it
	 * and adds it to the UI
	 * @paam arg0 unused
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		if(fc.getSelectedFile() == null) return; // avoids a NullPointerException from CollectionFile if the frame is disposed without a file being selected
		CollectionFile cf = new CollectionFile(fc.getSelectedFile());
		context.addCollection(cf.read());
		context.refreshCollectionList();
	}

}
