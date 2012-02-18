package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DelItemListener implements ActionListener {

	private OnaposUI context;
	
	/**
	 * Listens for the Delete Item button being pressed
	 * @param c the OnaposUI that created this listener
	 */
	public DelItemListener(OnaposUI c) {
		context = c;
	}
	
	/**
	 * Deletes the currently selected item from the currently selected collection
	 * @param arg0 unused
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Collection toDeleteFrom = context.getSelectedCollection();
		Item toDelete = context.getSelectedItem();
		if(toDelete == null) return; // nothing selected or bug
		toDeleteFrom.delItem(toDelete.getUID());
		context.removeSelectedRow();
	}

}
