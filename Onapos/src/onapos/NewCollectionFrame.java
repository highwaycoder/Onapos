package onapos;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class NewCollectionFrame {
	private JFrame frame;
	
	public NewCollectionFrame(OnaposUI context) {
		frame = new JFrame();
		frame.setBounds(150, 150, 350, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new MigLayout());

		JTextField collectionNameField = new JTextField(20);
		JTextField collectionTypeField = new JTextField(20);
		
		JLabel collectionNameLabel = new JLabel("Collection Name:");
		collectionNameLabel.setLabelFor(collectionNameField);
		
		JLabel collectionTypeLabel = new JLabel("Collection Type:");
		collectionTypeLabel.setLabelFor(collectionTypeField);
		
		JPanel labelPanel = new JPanel(new GridLayout(0,1));
		labelPanel.add(collectionNameLabel);
		labelPanel.add(collectionTypeLabel);
		
		JPanel fieldPanel = new JPanel(new GridLayout(0,1));
		fieldPanel.add(collectionNameField);
		fieldPanel.add(collectionTypeField);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new CreateCollectionListener(collectionNameField.getText(),collectionTypeField.getText(),context));
		frame.add(labelPanel);
		frame.add(fieldPanel);
		frame.add(createButton);
		frame.pack();
	}
	
}
