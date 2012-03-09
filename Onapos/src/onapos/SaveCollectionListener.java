package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class SaveCollectionListener implements ActionListener {

	private Collection toSave;
	
	/**
	 * Set which collection we want to save (call before saving)
	 * @param c the collection we want to save
	 */
	public void setCollection(Collection c) {
		toSave = c;
	}
	
	/**
	 * Save the collection we set earlier (make sure it's set!)
	 * @param e unused
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(toSave==null) {
			if(Onapos.DEBUG_MODE) {
				System.err.println("Warning: tried to save nothing (nothing selected?)");
			}
			return;
		}
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		if(fc.getSelectedFile() == null) return; // if the frame is disposed before we get a file (avoids a NullPointerException)
		CollectionFile cf = new CollectionFile(fc.getSelectedFile());
		cf.write(toSave);
	}

}
