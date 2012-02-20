package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CreateCollectionListener implements ActionListener {
	private JTextField cNameField;
	private JTextField cTypeField;
	private Map<JComboBox,JTextField> fields;
	private OnaposUI context;
	private JFrame newCollectionFrame;
	
	/**
	 * Helper constructor, use if  the collectionType is undefined
	 * 
	 */
	public CreateCollectionListener(JTextField collectionName,Map<JComboBox,JTextField> fields,OnaposUI context,JFrame f) {
		this(collectionName,new JTextField("Generic"),fields,context,f);
	}
	
	/**
	 * Listens to the 'Create Collection' button on the NewCollectionFrame dialogue box
	 * @param collectionName the name of the new collection
	 * @param collectionType the type of the new collection
	 * @param context the OnaposUI to add the collection to
	 */
	public CreateCollectionListener(JTextField collectionName,JTextField collectionType,Map<JComboBox,JTextField> fields,OnaposUI context,JFrame f) {
		cNameField = collectionName;
		cTypeField = collectionType;
		this.fields = fields;
		this.context = context;
		newCollectionFrame = f;
	}
	
	/**
	 * Creates the collection and prompts for a space on the disk to save it to
	 * @param arg0 unused
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Collection saveCollection = new Collection(cNameField.getText(),cTypeField.getText());
		for(Entry<JComboBox,JTextField> e : fields.entrySet()) {
			PropertyType type = (PropertyType) e.getKey().getSelectedItem();
			String name = e.getValue().getText();
			saveCollection.addProperty(name, type);
		}
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(null);
		CollectionFile saveFile = new CollectionFile(saveCollection,fc.getSelectedFile());
		
		saveFile.write();
		context.addCollection(saveCollection);
		context.refreshCollectionList();
		newCollectionFrame.dispose();
	}

}
