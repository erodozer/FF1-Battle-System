package editor.ItemEditor;

import item.Item;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.ini4j.Ini;

import spell.Spell;

/**
 * SpellEditorGUI
 * @author nhydock
 *
 *	Simple GUI for creating and editing spells
 *	Special feature is the equation builder and tester for spell
 */
public class ItemEditorGUI extends JPanel implements ActionListener, MouseListener{
	
	/*
	 * Buttons
	 */
	JButton newButton;
	JButton saveButton;
	JButton restoreButton;
	JButton testButton;
	
	/*
	 * Item switcher
	 */
	JList itemList;
	JScrollPane itemListPane;
	
	/*
	 * Fields
	 */
	JTextField nameField;		//holds the name
	JComboBox spell;			//list of spells that can be used for the item
	JTextField value;			//Holds either an equation or a constant value for determining the effects of the spell
	
	/*
	 * Type Switcher
	 */
	JLayeredPane typePane;
	int[] EQUIPMENTTYPES = {Item.WEAPON_TYPE, Item.ARMOR_TYPE, Item.ACCESSORY_TYPE};
	String[] EQUIPMENTLABELS = {"Weapon", "Armor", "Accessory"};
	ButtonGroup types;
	JRadioButton[] equipmentTypeButtons;
	JCheckBox equippable;
	
	Font font = new Font("Arial", 1, 32);


	private Vector<String> AVAILABLEITEMS;
	
	public ItemEditorGUI()
	{
		setLayout(null);
		
		JLayeredPane jlp;
		JLabel l;
		
		/*
		 * Initialize fields 
		 */
		l = new JLabel("Items: ");
		l.setSize(200,16);
		l.setLocation(10,10);
		itemList = new JList();
		itemList.addMouseListener(this);
		itemListPane = new JScrollPane(itemList);
		itemListPane.setSize(200, 343);
		itemListPane.setLocation(10, 32);
		
		refreshList();
		
		add(l);
		add(itemListPane);
		
		l = new JLabel("Name: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(220, 10);
		nameField = new JTextField("");
		nameField.setSize(200, 24);
		nameField.setLocation(220, 32);
		
		add(l);
		add(nameField);
				
		l = new JLabel("Battle Command: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 10);
		
		spell = new JComboBox<String>(Spell.AVAILABLESPELLS);
		spell.setSize(230, 24);
		spell.setLocation(650, 32);
		spell.addItem("");
		spell.setSelectedItem("");
		add(l);
		add(spell);
		
		/*
		 * Value Type button group
		 */
		typePane = new JLayeredPane();
		typePane.setSize(new Dimension(400, 64));
		typePane.setLocation(220, 64);
		typePane.setBorder(BorderFactory.createTitledBorder("Equipment Type: "));
		types = new ButtonGroup();
		equipmentTypeButtons = new JRadioButton[EQUIPMENTTYPES.length];
		for (int i = 0; i < EQUIPMENTTYPES.length; i++)
		{
			JRadioButton jb = new JRadioButton(EQUIPMENTLABELS[i]);
			jb.setSize(jb.getPreferredSize());
			jb.setLocation(40+(100*i), 24);
			equipmentTypeButtons[i] = jb;
			types.add(jb);
			typePane.add(jb);
		}
		equipmentTypeButtons[0].setSelected(true);
		
		add(typePane);
		
		equippable = new JCheckBox("Is Equippable");
		equippable.setSize(equippable.getPreferredSize());
		equippable.setLocation(typePane.getX() + 150, typePane.getY()-3);
		equippable.addActionListener(this);
		enablePane(typePane, equippable.isSelected());
		add(equippable);
		
		
		/*
		 * Initialize Buttons
		 */
		testButton = new JButton("Test");
		testButton.setSize(230, 32);
		testButton.setLocation(650, 215);
		
		int[] buttonSize = {230, 24};
		newButton = new JButton("New");
		newButton.setSize(buttonSize[0], buttonSize[1]);
		newButton.setLocation(650, 290);
		saveButton = new JButton("Save");
		saveButton.setSize(buttonSize[0], buttonSize[1]);
		saveButton.setLocation(650, 320);
		restoreButton = new JButton("Restore");
		restoreButton.setSize(buttonSize[0], buttonSize[1]);
		restoreButton.setLocation(650, 350);
		
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		restoreButton.addActionListener(this);
		
		add(testButton);
		add(newButton);
		add(saveButton);
		add(restoreButton);

		validate();
	}

	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newButton)
		{
			reset();
		}
		else if (event.getSource() == saveButton)
		{
			saveItem();
		}
		else if (event.getSource() == equippable)
		{
			enablePane(typePane, equippable.isSelected());
		}
	}

	/**
	 * Sets all the components of a pane to be enabled or disabled in one shot
	 * @param jp	pane of components to lock
	 * @param lock	whether to enable them or not
	 */
	public void enablePane(JLayeredPane jp, boolean lock)
	{
		for (Component j : jp.getComponents())
			j.setEnabled(lock);
	}
	
	/**
	 * Loads an item's values into the editor's fields and makes it the active item
	 * @param e
	 */
	public void loadItem(Item item)
	{
		//load name
		nameField.setText(item.getName());
		equippable.setSelected(item.isEquipment());
		enablePane(typePane, equippable.isSelected());
		for (int i = 0; i < EQUIPMENTTYPES.length; i++)
		{
			boolean selected = false;
			if (i == item.getEquipmentType())
				selected = true;
			equipmentTypeButtons[i].setSelected(selected);
		}
	}
	
	/**
	 * Sets all fields to their default values
	 */
	public void reset()
	{
		nameField.setText("");
				
	}
	
	/**
	 * Saves the editor's values to the enemy ini
	 */
	public void saveItem()
	{
		String location = "data/actors/enemies/";
		
		//save to file
		try {
			File f = new File(location+nameField.getText()+"/enemy.ini");
			f.delete();					//deletes the old file
			f.createNewFile();			//saves data to new file
			Ini ini = new Ini(f);
			
			
			ini.store(f);
			
			//refresh the list of names after saving
			loadItem(Item.loadItem(nameField.getText()));
			refreshList();	
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void refreshList()
	{
		AVAILABLEITEMS = new Vector<String>()
		{
			{
				String[] s = new File("data/items").list(new FilenameFilter() {
		            @Override
					public boolean accept(File f, String s) {
		            	return (new File("data/items/"+s+"/item.ini").exists());
		              }
		            });
				for (String str : s)
					add(str);
			}
		};
		
		itemList.setListData(AVAILABLEITEMS);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == itemList)
			loadItem(Item.loadItem((String)itemList.getSelectedValue()));
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {}


	@Override
	public void mouseExited(MouseEvent arg0) {}


	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
