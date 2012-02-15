package onapos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;

public class OnaposUI {
	public static final String DEFAULT_COLLECTION_LOCATION = "/usr/share/onapos/collections/";
	private static String collectionLocation;
	private JFrame frame;
	private List<Collection> collections;
	private DefaultListModel collectionSelectorModel;
	private JTable collectionView;
	private DefaultTableModel collectionViewData;
	
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
		collections = new ArrayList<Collection>();
		try {
			loadCollections();
		} catch(IOException e) {
			e.printStackTrace();
		}
		initialize();
	}
	
	public void addCollection(Collection c) {
		collections.add(c);
		
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
		mntmNewCollection.addActionListener(new NewCollectionListener(this));
		JMenuItem mntmOpenCollection = new JMenuItem("Open Collection");
		mntmOpenCollection.addActionListener(new OpenCollectionListener(this));
		
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				// FIXME: makes it exit faster? (danger, will robinson!)
				System.exit(0);
			}
		});
		
		JLabel collectionSelectorLabel = new JLabel("Collection:");
		collectionSelectorModel = new DefaultListModel();
		refreshCollectionList();
		final JList collectionSelector = new JList(collectionSelectorModel);
		
		// this menu item must be created after the collection selector list
		JMenuItem mntmSaveCollection = new JMenuItem("Save Collection");
		final SaveCollectionListener scl = new SaveCollectionListener();
		mntmSaveCollection.addActionListener(scl);
		
		
		collectionViewData = new DefaultTableModel();
		collectionView = new JTable(collectionViewData);
		
		class CollectionSelectionListener implements ListSelectionListener {
			
			public void valueChanged(ListSelectionEvent arg0) {	
				Collection c;
				if((c = getSelectedCollection(collectionSelector)) == null) return; // silent fail (nothing selected)
				scl.setCollection(c);
				populateTable(c);
			}
			
		}
		
		collectionSelector.addListSelectionListener(new CollectionSelectionListener());
		
		
		// setup the menu in the proper order:
		mnFile.add(mntmNewCollection);
		mnFile.add(mntmOpenCollection);
		mnFile.add(mntmSaveCollection);
		mnFile.add(mntmExit);

		
		frame.add(collectionSelectorLabel);
		frame.add(collectionSelector);
		frame.add(collectionView);
	}
	
	public void populateTable(Collection c) {
		TableColumn[] cols = new TableColumn[c.getProperties().values().size()];
		collectionViewData = new DefaultTableModel();
		int i=0;
		for(Property p : c.getProperties().values()) {
			cols[i] = new TableColumn();
			cols[i].setHeaderValue(p.toString());
			collectionViewData.addColumn(cols[i]);
			collectionView.addColumn(cols[i]);
			i++;
		}
		collectionView.setModel(collectionViewData);
		for(Item item : c.getItems()) {
			collectionViewData.addRow(item.getProperties().values().toArray());
		}
		collectionViewData.fireTableDataChanged();
	}
	
	// FIXME: I'm not happy with the below code, it relies on collections and collectionSelector being sync
	public Collection getSelectedCollection(JList collectionSelector) {
		if(collectionSelector.isSelectionEmpty()) {
			return null; // fail silently if the selection has merely been cleared
		}
		int index = collectionSelector.getSelectedIndex();
		if(index < 0 || index > collections.size()) {
			System.err.println("Warning: tried to access collection outside the collection of collections (inception?)");
			return null;
		}
		return collections.get(collectionSelector.getSelectedIndex());
	}
	
	public void refreshCollectionList() {
		collectionSelectorModel.removeAllElements();
		for(Collection c : collections) 
			collectionSelectorModel.addElement(c.getName());
	}
	
	private class NewCollectionListener implements ActionListener {
		
		private OnaposUI context;
		
		public NewCollectionListener(OnaposUI context) {
			this.context = context;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NewCollectionFrame(context);
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
