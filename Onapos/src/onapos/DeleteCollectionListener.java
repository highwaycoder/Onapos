package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCollectionListener implements ActionListener {

	OnaposUI context;
	
	/**
	 * Constructor
	 * @param c the OnaposUI that created this Listener
	 */
	public DeleteCollectionListener(OnaposUI c) {
		context = c;
	}
	
	/**
	 * Deletes whichever collection is currently selected in the list
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		context.deleteCollection(context.getSelectedCollection());
	}

}
