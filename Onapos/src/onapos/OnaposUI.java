package onapos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.miginfocom.swing.MigLayout;

public class OnaposUI {
	public static final String DEFAULT_COLLECTION_LOCATION = "/usr/share/onapos/collections/";
	private static String collectionLocation;
	private JFrame frame;
	private List<Collection> collections = new ArrayList<Collection>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length < 2) {
			collectionLocation = DEFAULT_COLLECTION_LOCATION;
		} else {
			collectionLocation = args[1];
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OnaposUI window = new OnaposUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OnaposUI() {
		try {
			loadCollections();
		} catch(IOException e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new MigLayout());
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewCollection = new JMenuItem("New Collection");
		mnFile.add(mntmNewCollection);
		mntmNewCollection.addActionListener(new NewCollectionListener());
		JMenuItem mntmOpenCollection = new JMenuItem("Open Collection");
		mnFile.add(mntmOpenCollection);
		
		JMenuItem mntmSaveCollection = new JMenuItem("Save Collection");
		mnFile.add(mntmSaveCollection);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				// FIXME: makes it exit faster? (danger, will robinson!)
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JLabel collectionSelectorLabel = new JLabel("Collection:");
		
		ArrayList<String> collectionNames = new ArrayList<String>();
		for(Collection c : collections) {
			collectionNames.add(c.getName());
		}
		JList collectionSelector = new JList(collectionNames.toArray());
		
		frame.add(collectionSelectorLabel);
		frame.add(collectionSelector);
	}
	
	private class NewCollectionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NewCollectionFrame();
			// load collections from file
			try {
				loadCollections();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadCollections() throws IOException {
		File collectionDir = new File(collectionLocation);
		File[] collectionFiles = collectionDir.listFiles();
		if(collectionFiles == null) {
			System.err.println("WARNING: collection directory could not be read, does not exist");
			return;
		}
		if(collectionFiles.length == 0) {
			System.err.println("WARNING: collection directory is empty");
			return;
		}
		for(File collectionFile : collectionFiles) {
			CollectionFile cf = new CollectionFile(collectionFile);
			collections.add(cf.read());
		}
	}
	
}
