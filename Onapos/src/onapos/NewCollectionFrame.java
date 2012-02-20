package onapos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class NewCollectionFrame {
	private JFrame frame;
	
	/**
	 * A dialogue box for creating new collections
	 * @param context the OnaposUI that created this NewCollectionFrame
	 */
	public NewCollectionFrame(OnaposUI context) {
		frame = new JFrame();
		frame.setBounds(150, 150, 500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new MigLayout("wrap", "[grow][][]", ""));

		JTextField collectionNameField = new JTextField(20);
		JTextField collectionTypeField = new JTextField(20);
		
		JLabel collectionNameLabel = new JLabel("Collection Name:");
		collectionNameLabel.setLabelFor(collectionNameField);
		
		JLabel collectionTypeLabel = new JLabel("Collection Type:");
		collectionTypeLabel.setLabelFor(collectionTypeField);
		
		Map<JComboBox,JTextField> fields = new HashMap<JComboBox,JTextField>();
		
		JButton addField = new JButton("Add property");
		addField.addActionListener(new AddFieldListener(fields));
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new CreateCollectionListener(collectionNameField.getText(),collectionTypeField.getText(),fields,context,frame));
		
		
		frame.add(collectionNameLabel,"");
		frame.add(collectionNameField,"");
		frame.add(collectionTypeLabel,"skip");
		frame.add(collectionTypeField);
		
		frame.add(new JLabel("Property Type"),"skip");
		frame.add(new JLabel("Property Name"));
		// create one box to start with (bit of a hack, but who cares?)
		addField.getActionListeners()[0].actionPerformed(null); 
		
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(addField);
		buttonBox.add(createButton);
		buttonBox.add(Box.createHorizontalGlue());
		frame.add(buttonBox,"south");
		frame.pack();
	}
	
	
	private class AddFieldListener implements ActionListener  {

		private Map<JComboBox,JTextField> fields;
		
		public AddFieldListener(Map<JComboBox,JTextField> f) {
			fields = f;
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox t = new JComboBox(PropertyType.values());
			JTextField n = new JTextField(20);
			fields.put(t, n);
			frame.add(t,"skip");
			frame.add(n);
			frame.pack();
		}
		
	}
}
