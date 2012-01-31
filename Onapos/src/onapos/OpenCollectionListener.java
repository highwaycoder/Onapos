package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class OpenCollectionListener implements ActionListener {
	
	private OnaposUI context;
	
	public OpenCollectionListener(OnaposUI o) {
		context = o;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(null);
		CollectionFile cf = new CollectionFile(fc.getSelectedFile());
		context.addCollection(cf.read());
		context.refreshCollectionList();
	}

}
