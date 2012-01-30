package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class CreateCollectionListener implements ActionListener {
	private String cName;
	private String cType;
	
	public CreateCollectionListener(String collectionName,String collectionType) {
		cName = collectionName;
		cType = collectionType;
	}
	
	public CreateCollectionListener(String collectionName,String collectionType,String collectionLocation) {
		cName = collectionName;
		cType = collectionType;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Collection saveCollection = new Collection(cName,cType);
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		CollectionFile saveFile = new CollectionFile(saveCollection,fc.getSelectedFile());
		saveFile.write();
	}

}
