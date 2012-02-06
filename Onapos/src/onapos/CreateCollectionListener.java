package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

public class CreateCollectionListener implements ActionListener {
	private String cName;
	private String cType;
	private OnaposUI context;
	
	public CreateCollectionListener(String collectionName,OnaposUI context) {
		this(collectionName,"Generic",context);
	}
	
	public CreateCollectionListener(String collectionName,String collectionType,OnaposUI context) {
		cName = collectionName;
		cType = collectionType;
		this.context = context;
	}
	
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
