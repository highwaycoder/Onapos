package onapos;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

public class OnaposUI {
	public static final String DEFAULT_COLLECTION_LOCATION = System.getenv("HOME") + "/.onapos/collections/";
	public static final String FILE_EXTENSION = ".ms";
	private static String collectionLocation;
	private JFrame frame;
	private List<Collection> collections;
	private DefaultListModel collectionSelectorModel;
	private JTable collectionView;
	private DefaultTableModel collectionViewData;
	private JList collectionSelector;
	private boolean addItemPanelExists;
	private JPanel addItemPanel;
	private JTextField searchTextField;
	private JComboBox searchType;
	
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
			private OnaposUI window = new OnaposUI();
			public void run() {
				window.initialize();
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OnaposUI() {
		collections = new ArrayList<Collection>();
	}
	
	/**
	 * Add a collection to the user interface
	 * @param c the collection to add
	 */
	public void addCollection(Collection c) {
		collections.add(c);
		collectionSelectorModel.addElement(c.getName());
		if(collectionSelector!=null) {
			collectionSelector.invalidate();
			collectionSelector.repaint();
		}
		for(String s : c.getProperties().keySet())
			searchType.addItem(s);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		OnaposSplashScreen splash = new OnaposSplashScreen();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new MigLayout());
		frame.setTitle("Onapos - a simple collections manager");
		frame.addWindowListener(new OnaposWindowListener(this));
		
		// by default, the add item panel isn't displayed
		addItemPanelExists = false;
		splash.setProgress(5); // we should be at about 5% by this point
		
		// initialise the menu bar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		splash.setProgress(10);
		
		// initialise the menus
		JMenuItem mntmNewCollection = new JMenuItem("New Collection");
		mntmNewCollection.addActionListener(new NewCollectionListener(this));
		JMenuItem mntmOpenCollection = new JMenuItem("Open Collection");
		mntmOpenCollection.addActionListener(new OpenCollectionListener(this));
		splash.setProgress(15);
		
		// initialise the exit menu item
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// leave this as frame.dispose(), calling exit() directly isn't
				// as sensible as it first seemed.
				frame.dispose();
			}
		});
		
		// initialise the global search box
		Box searchBox = new Box(3);
		searchType = new JComboBox();
		searchTextField = new JTextField(20);
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Item selectedItem = null;
				// first make sure the item actually exists in a collection
				for(Collection c : collections) {
					selectedItem = c.findItem(searchType.toString(), searchTextField.getText());
					if(selectedItem!=null) {
						setSelectedCollection(c);
						break;
					}
				}
				if(selectedItem==null) {
					// TODO: tell the user it couldn't be found somehow
					return;
				}
				boolean found = false;
				int rowIndex = 0;
				int columnIndex = collectionViewData.findColumn(searchType.toString()); 
				while(!found) {
					if(collectionViewData.getValueAt(rowIndex, columnIndex).equals(selectedItem)) {
						collectionView.setRowSelectionInterval(rowIndex,rowIndex);
						found = true;
					}
				}
				
			}
		});
		for(Collection c : collections) {
			for(String s : c.getProperties().keySet()) {
				searchType.addItem(s);
			}
		}
		searchBox.add(searchType);
		searchBox.add(searchTextField);
		searchBox.add(searchButton);
		
		// initialise the collection selector
		JLabel collectionSelectorLabel = new JLabel("Collection:");
		collectionSelectorModel = new DefaultListModel();
		refreshCollectionList();
		collectionSelector = new JList(collectionSelectorModel);
		JPopupMenu collectionMenu = new JPopupMenu();
		JMenuItem deleteCollection = new JMenuItem("Delete Collection");
		collectionMenu.add(deleteCollection);
		deleteCollection.addActionListener(new DeleteCollectionListener(this));
		collectionSelector.addMouseListener(new CollectionSelectorMouseListener(collectionMenu,collectionSelector));
		splash.setProgress(25);
		
		
		// this menu item must be created after the collection selector list, unfortunately
		JMenuItem mntmSaveCollection = new JMenuItem("Save Collection");
		final SaveCollectionListener scl = new SaveCollectionListener();
		mntmSaveCollection.addActionListener(scl);
		splash.setProgress(30);
		
		// initialise the collection viewer
		collectionViewData = new DefaultTableModel();
		collectionView = new JTable(collectionViewData);
		splash.setProgress(40);
		
		// listen for a change in the collection selector and update the table as appropriate
		class CollectionSelectionListener implements ListSelectionListener {
			
			public void valueChanged(ListSelectionEvent arg0) {	
				Collection c;
				if((c = getSelectedCollection()) == null) return; // silent fail (nothing selected)
				scl.setCollection(c);
				populateTable(c);
			}
			
		}
		collectionSelector.addListSelectionListener(new CollectionSelectionListener());
		splash.setProgress(50);

		// initialise the addItemPanel (we don't add it yet though)
		addItemPanel = new JPanel();
		addItemPanel.setLayout(new MigLayout("fillx", "[right]rel[grow,fill]", "[]10[]"));
		splash.setProgress(60);
		
		// setup the menu in the proper order:
		mnFile.add(mntmNewCollection);
		mnFile.add(mntmOpenCollection);
		mnFile.add(mntmSaveCollection);
		mnFile.add(mntmExit);
		splash.setProgress(70);
		
		// load collections here
		try {
			loadCollections();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// finally, pack up the frame and send it to the UI for processing
		frame.add(searchBox,"north");
		frame.add(collectionSelectorLabel);
		frame.add(collectionSelector);
		frame.add(new JScrollPane(collectionView));
		splash.setProgress(80);
		frame.pack();
		splash.setProgress(90);
		frame.setLocationRelativeTo(null);
		splash.setProgress(100); // we're done!
		try {
			// wait for about a quarter of a second for the user to see the splash complete
			Thread.sleep(250);
		} catch (InterruptedException e1) {
			System.exit(-1); // allow the user to force-quit the application while sleeping in case sleep goes wrong
		}
		splash.dispose(); // last thing before displaying the main window is to hide the splash
		frame.setVisible(true);
	}
	
	/**
	 * Create the 'add item' panel (so long as it doesn't already exist)
	 */
	public void addItemPanel() {
		if(addItemPanelExists) {
			if(Onapos.DEBUG_MODE) {
				System.err.println("Warning (inefficiency): called 'addItemPanel()' when one already exists");
			}
			return; // don't do this more than once
		}
		// make frame disappear while we reset it, it looks slightly less odd than the alternative flickering
		frame.setVisible(false);
		JButton addItemButton = new JButton("Add");
		JButton delItemButton = new JButton("Delete");
		
		Map<JLabel,JTextField> itemProperties = new HashMap<JLabel,JTextField>();
		Set<String> properties = getSelectedCollection().getProperties().keySet();
		
		for(String property : properties) {
			itemProperties.put(new JLabel(property), new JTextField(20));
		}
		
		for(Entry<JLabel,JTextField> e : itemProperties.entrySet()) {
			addItemPanel.add(e.getKey(),"");
			addItemPanel.add(e.getValue(),"wrap");
		}
		
		addItemButton.addActionListener(new AddItemListener(this,itemProperties));
		delItemButton.addActionListener(new DelItemListener(this));
		
		
		addItemPanel.add(addItemButton,"skip,width 80px!");
		addItemPanel.add(delItemButton,"");
		frame.add(addItemPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		addItemPanelExists = true;
	}
	
	/**
	 * Gets the currently selected item
	 * @return the currently selected Item or null if no item is selected
	 */
	public Item getSelectedItem() {
		if(collectionView.getSelectedRow()==-1) return null;
		return getSelectedCollection().getItem(
				(Integer) collectionViewData.getValueAt(collectionView.getSelectedRow(), collectionViewData.getColumnCount()-1)
				);
	}
	
	/**
	 * Removes the selected row from the collectionView table,
	 * Note that this doesn't actually remove an item from the collection,
	 * it's just a helper function to clean up the collectionView table
	 * after removing an item.
	 */
	public void removeSelectedRow() {
		if(collectionView.getSelectedRow()==-1) return;
		collectionViewData.removeRow(collectionView.getSelectedRow());
	}
	
	/**
	 * Remove the 'add item' panel from the screen (if no collection is selected)
	 * NOTE: this function is not currently used, and I'm struggling to think of a
	 * reason to keep it - it may be deprecated in a future version, unless I make
	 * use of it.
	 */
	public void delItemPanel() {
		if(addItemPanel==null) {
			// obviously, this has happened because the boolean value hasn't been set properly
			addItemPanelExists = false;
			return; // our work here is done :)
		}
		frame.remove(addItemPanel);
		addItemPanel.removeAll();
		addItemPanelExists = false;
	}
	
	/**
	 * Populate the collectionView JTable with items
	 * @param c the collection with which to populate the table
	 */
	public void populateTable(Collection c) {
		collectionViewData = new DefaultTableModel();
		collectionView.setModel(collectionViewData);
		for(String title : c.getProperties().keySet()) {
			collectionViewData.addColumn(titleCase(title));
		}
		collectionViewData.addColumn("Item UID");
		collectionViewData.fireTableStructureChanged();
		for(Item item : c.getItems()) {
			Vector<Object> row = new Vector<Object>(item.getProperties().values());
			row.add(item.getUID());
			collectionViewData.addRow(row);
		}
		collectionViewData.fireTableDataChanged();
		// don't create an ItemPanel if it already exists
		if(!addItemPanelExists)
			addItemPanel();
	}
	
	/**
	 * Helper function allows me to convert any arbitrary string to title-case representation
	 * @param s the string to convert
	 * @return a converted copy of the string
	 */
	private static String titleCase(String s) {
		StringBuilder rv = new StringBuilder();
		StringTokenizer strtok = new StringTokenizer(s);
		// handle the null error: (should really output a runtime warning here)
		if(s == null) return null;
		while(strtok.hasMoreTokens()) {
			String word = strtok.nextToken();
			String firstLetter = word.substring(0,1);
			String restOfWord = word.substring(1);
			rv.append(firstLetter.toUpperCase() + restOfWord.toLowerCase());
			rv.append(" "); // preserve the tokens that are otherwise stripped
		}
		// hack to remove the last space (probably unnecessary for all intents and purposes):
		rv.setLength(rv.length()-1);
		return rv.toString();
	}
	
	/**
	 * Returns whichever collection is currently selected in the list
	 * FIXME: I'm not happy with this code, it relies on 'collections'
	 * and 'collectionSelector' being synchronised
	 * @return the collection the user has selected in the JList
	 */
	public Collection getSelectedCollection() {
		if(collectionSelector.isSelectionEmpty()) {
			return null; // fail silently if the selection has merely been cleared
		}
		int index = collectionSelector.getSelectedIndex();
		if(index < 0 || index > collections.size()) {
			if(Onapos.DEBUG_MODE) {
				System.err.println("Warning: tried to access collection outside the collection of collections (inception?)");
			}
			return null;
		}
		return collections.get(collectionSelector.getSelectedIndex());
	}
	
	/**
	 * Set the currently selected collection to collection.c
	 * @param c
	 */
	private void setSelectedCollection(Collection c) {
		if(getSelectedCollection().equals(c)) return; // nothing to do here
		collectionSelector.setSelectedValue(c, true); // thank you, Java for this method!
	}
	
	/**
	 * This function refreshes the collection list whenever 'collections' is modified
	 * TODO: deprecate this (there are more efficient ways to keep them in sync)
	 */
	public void refreshCollectionList() {
		collectionSelectorModel.clear();
		for(Collection c : collections) 
			collectionSelectorModel.addElement(c.getName());
		if(collectionSelector!=null) {
			collectionSelector.invalidate();
			collectionSelector.repaint();
		}
	}
	
	/**
	 * For some reason, this particular listener is an inner class - I should move it
	 * into its own file, really.
	 * @author Chris Browne <yoda2031@gmail.com>
	 *
	 */
	private class NewCollectionListener implements ActionListener {
		
		private OnaposUI context;
		
		/**
		 * Creates a listener for the 'new collection' menu item
		 * @param context the OnaposUI that created this listener
		 */
		public NewCollectionListener(OnaposUI context) {
			this.context = context;
		}
		
		/**
		 * We create a new collection frame then, when it's disposed, we reload the collections
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NewCollectionFrame(context);
			// load collections from file
			try {
				loadCollections();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads collections from the disk
	 * @throws IOException because I hate using try/catch inside methods?
	 */
	private void loadCollections() throws IOException {
		File collectionDir = new File(collectionLocation);
		if(!collectionDir.exists()) {
			if(Onapos.DEBUG_MODE) {
				System.err.print("WARNING: collection directory does not exist, trying to create: ");
			}
			if(!collectionDir.mkdirs()) {
				if(Onapos.DEBUG_MODE) {
					System.err.println("failure");
					System.err.println("proceding without existing collections");
				}
			} else if(Onapos.DEBUG_MODE) {
				System.err.println("success");
			}
		}
		File[] collectionFiles = collectionDir.listFiles();
		if(collectionFiles == null) {
			if(Onapos.DEBUG_MODE) {
				System.err.println("WARNING: collection directory could not be read, does not exist");
			}
			return;
		}
		if(collectionFiles.length == 0) {
			if(Onapos.DEBUG_MODE) {
				System.err.println("WARNING: collection directory is empty");
			}
			return;
		}
		for(File collectionFile : collectionFiles) {
			CollectionFile cf = new CollectionFile(collectionFile);
			addCollection(cf.read());
		}
	}
	
	/**
	 * Helper method to save all our collections at once
	 */
	private void saveCollections() {
		for(Collection c : collections) {
			File saveLocation = new File(DEFAULT_COLLECTION_LOCATION + c.getName() + FILE_EXTENSION);
			CollectionFile cf = new CollectionFile(c,saveLocation);
			cf.write();
		}
	}
	
	/**
	 * Clean up and close (auto-saving along the way)
	 * NOTE: DO NOT CALL THIS FROM ANYWHERE OTHER THAN
	 * OnaposWindowListener.windowClosed().
	 * SERIOUSLY.
	 * It's supposed to happen whenever the frame is disposed, and not any other place
	 */
	public void exit() {
		saveCollections();
		System.exit(0);
	}
	
	/**
	 * Deletes a collection from both the disk and the UI
	 * @param selectedCollection the collection to delete
	 */
	public void deleteCollection(Collection selectedCollection) {
		// first delete from disk
		File onDisk = new File(DEFAULT_COLLECTION_LOCATION + selectedCollection.getName() + FILE_EXTENSION);
		if(!onDisk.delete() && Onapos.DEBUG_MODE) {
			System.err.println("Warning: did not delete file (does not exist?): "+onDisk.getAbsolutePath());
		}
		// then from the UI
		collections.remove(selectedCollection);
		if(!collectionSelectorModel.removeElement(selectedCollection.getName()) && Onapos.DEBUG_MODE){
			System.err.println("Warning: Tried to remove collection that didn't exist.");
		}
		if(collectionSelector!=null) {
			collectionSelector.validate();
			collectionSelector.repaint();
		}
		// also remove its properties from searchType
		for(String s : selectedCollection.getProperties().keySet())
			searchType.removeItem(s);
	}
	
}
