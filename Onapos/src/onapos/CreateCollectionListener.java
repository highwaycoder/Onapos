package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class CreateCollectionListener implements ActionListener {
	private String cName;
	private String cType;
	private OnaposUI context;
	
	/**
	 * Helper constructor, use if  the collectionType is undefined
	 * 
	 */
	public CreateCollectionListener(String collectionName,OnaposUI context) {
		this(collectionName,"Generic",context);
	}
	
	/**
	 * Listens to the 'Create Collection' button on the NewCollectionFrame dialogue box
	 * @param collectionName the name of the new collection
	 * @param collectionType the type of the new collection
	 * @param context the OnaposUI to add the collection to
	 */
	public CreateCollectionListener(String collectionName,String collectionType,OnaposUI context) {
		cName = collectionName;
		cType = collectionType;
		this.context = context;
	}
	
	/**
	 * Creates the collection and prompts for a space on the disk to save it to
	 * @param arg0 unused
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Collection saveCollection = new Collection(cName,cType);
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		CollectionFile saveFile = new CollectionFile(saveCollection,fc.getSelectedFile());
		saveFile.write();
		context.addCollection(saveCollection);
		context.refreshCollectionList();
	}

}
