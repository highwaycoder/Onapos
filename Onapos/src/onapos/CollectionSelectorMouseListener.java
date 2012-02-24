package onapos;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JPopupMenu;

public class CollectionSelectorMouseListener implements MouseListener {

	private JPopupMenu popup;
	private JList collectionSelector;
	
	/**
	 * Constructs the mouse listener
	 * @param p the popupmenu to display on right-click events
	 * @param cs the JList we want to move the selection on
	 */
	public CollectionSelectorMouseListener(JPopupMenu p,JList cs) {
		popup = p;
		collectionSelector = cs;
	}

	/**
	 * Helper function, converts a Point to a row index of collectionSelector
	 * @param p the Point we want to convert
	 * @return the row index of collectionSelector that was clicked on
	 */
	private int getRow(Point p) {
		return collectionSelector.locationToIndex(p);
	}
	
	/**
	 * Select a row, show a popup if it was a right-click
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		collectionSelector.setSelectedIndex(getRow(e.getPoint()));
		maybeShowPopup(e);
	}

	/**
	 * Select a row, show a popup if it was a right-click
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		collectionSelector.setSelectedIndex(getRow(e.getPoint()));
		maybeShowPopup(e);
	}

	/**
	 * Checks if the MouseEvent that triggered this should trigger a popup
	 * @param e the MouseEvent that trigged this
	 */
	private void maybeShowPopup(MouseEvent e) {
		if(e.isPopupTrigger()) {
			popup.show(e.getComponent(),e.getX(),e.getY());
		}
	}
	
	/**
	 * Unused
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	/**
	 * Unused
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Unused
	 */
	@Override
	public void mouseExited(MouseEvent e) {}
}
