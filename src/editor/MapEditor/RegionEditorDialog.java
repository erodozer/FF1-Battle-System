package editor.MapEditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

import editor.ToolKit;

import scenes.WorldScene.WorldSystem.Terrain;

/**
 * NewMapDialog
 * @author nhydock
 *
 *	Little window for creating a new map
 */
public class RegionEditorDialog extends JDialog implements ActionListener {

	String[] terrains;			//available terrains to choose from
	JComboBox terrain;			//drop down list of the terrains
	
	JButton okButton;			//done with the terrain
	JButton cancelButton;		//
	
	JButton fAddButton;			//add region
	JButton fRemButton;			//remove region
	JButton fEdtButton;			//edit region properties
	
	MapEditorGUI parent;		//parent gui
	
	Vector<String> formations = new Vector<String>();
	JList fList;			//list of formations
	JScrollPane fPane;		//displays list of formations
	
	
	int index = -1;
	/**
	 * Constructs the dialog window
	 * @param p		Parent GUI
	 */
	public RegionEditorDialog(MapEditorGUI p)
	{
		parent = p;
		setLayout(null);
		
		/*
		 * Initialize Terrain selector
		 */
		JLabel l = new JLabel("Terrains");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 10);
		
		terrain = new JComboBox(ToolKit.terrains);
		terrain.setSize(200, 24);
		terrain.setLocation(10, 32);
		
		add(l);
		add(terrain);
		
		/*
		 * Initialize formation list
		 */
		JLabel l2 = new JLabel("Formations");
		l2.setSize(l2.getPreferredSize());
		l2.setLocation(10, 68);
		
		fList = new JList(formations);
		fPane = new JScrollPane(fList);
		fPane.setSize(200, 230);
		fPane.setLocation(10, 92);
		
		add(l2);
		add(fPane);
		
		/*
		 * Initialize buttons
		 */
		okButton = new JButton("OK");
		okButton.setSize(80, 24);
		okButton.setLocation(20, 380);
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.setSize(80, 24);
		cancelButton.setLocation(120, 380);
		cancelButton.addActionListener(this);
		
		add(okButton);
		add(cancelButton);
		
		fRemButton = new JButton("-");
		fRemButton.setSize(fRemButton.getPreferredSize());
		fRemButton.setLocation(210-fRemButton.getWidth(), 335);
		
		fAddButton = new JButton("+");
		fAddButton.setSize(fAddButton.getPreferredSize());
		fAddButton.setLocation(fRemButton.getX()-fAddButton.getWidth(), 335);
		
		fEdtButton = new JButton("Edit");
		fEdtButton.setSize(100,24);
		fEdtButton.setLocation(10, 335);
		
		fAddButton.addActionListener(this);
		fRemButton.addActionListener(this);
		fEdtButton.addActionListener(this);
		
		add(fAddButton);
		add(fRemButton);
		add(fEdtButton);
		
		/*
		 * Initialize dialog window
		 */
		setSize(230,450);
		setVisible(true);
		setModal(true);
		setResizable(false);
		setTitle("Region Editor");
		setLocationRelativeTo(parent);
	}
	
	public RegionEditorDialog(MapEditorGUI p, int i)
	{
		this(p);
		index = i;
		try {
			load(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accepts button clicking
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == okButton)
		{
			Region r = new Region(null);
			r.background = (String)terrain.getSelectedItem();
			if (index == -1)
				parent.regions.add(r);
			else
				parent.regions.set(index, r);
			parent.regionList.setListData(parent.regions);
			parent.regionPane.setViewportView(parent.regionList);
			dispose();
		}
		else if (event.getSource() == cancelButton)
			dispose();
	}
	
	
	public void load(int i) throws Exception 
	{
		Preferences p = new IniPreferences(new Ini(new File("data/maps/"+parent.name+"/map.ini")));
		Region r = new Region(p.node("Region"+index));
		terrain.setSelectedItem(r.background);
		
	}
	
}
