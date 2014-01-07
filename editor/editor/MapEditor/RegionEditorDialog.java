package editor.MapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import map.Terrain;

import editor.ToolKit;


/**
 * NewMapDialog
 * @author nhydock
 *
 *	Little window for creating a new map
 */
public class RegionEditorDialog extends JDialog implements ActionListener {

	String name = "plains";		//region name
	JTextField nameField;		//region naming field
	
	JSpinner eRateSpinner;		//spinner for encounter rate
	
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
		 * Initialize naming field
		 */
		JLabel l = new JLabel("Name");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 10);
		
		nameField = new JTextField("plains");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		add(l);
		add(nameField);
		
		/*
		 * Initialize encounter spinner
		 */
		l = new JLabel("Encounter rate");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 64);
		
		eRateSpinner = new JSpinner(new SpinnerNumberModel(1,1,20,1));
		eRateSpinner.setSize(eRateSpinner.getPreferredSize());
		eRateSpinner.setLocation(210-eRateSpinner.getWidth(), 62);
		
		add(l);
		add(eRateSpinner);
		
		/*
		 * Initialize Terrain selector
		 */
		l = new JLabel("Terrain");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 96);
		
		terrain = new JComboBox(ToolKit.terrains);
		terrain.setSize(200, 24);
		terrain.setLocation(10, 120);
		
		add(l);
		add(terrain);
		
		/*
		 * Initialize formation list
		 */
		l = new JLabel("Formations");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 156);
		
		fList = new JList(formations);
		fPane = new JScrollPane(fList);
		fPane.setSize(200, 160);
		fPane.setLocation(10, 172);
		
		add(l);
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
	
	public RegionEditorDialog(MapEditorGUI p, int i, Terrain r)
	{
		this(p);
		index = i;
		nameField.setText(r.getName());
		terrain.setSelectedItem(r.getBackground().getName());
		eRateSpinner.setValue(r.getStraightRate());
		formations = r.getFormations();
		fList.setListData(formations);
		fPane.setViewportView(fList);
	}

	/**
	 * Accepts button clicking
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		//close dialog and save changes to the region
		if (event.getSource() == okButton)
		{
			Terrain r = new Terrain(null);
			r.setBackground((String)terrain.getSelectedItem());
			r.setName(nameField.getText());
			r.setRate((Integer)eRateSpinner.getValue());
			r.setFormations(formations);
			if (index == -1)
				parent.regions.add(r);
			else
				parent.regions.set(index, r);
			parent.regionList.setListData(parent.regions);
			parent.regionPane.setViewportView(parent.regionList);
			dispose();
		}
		
		//close dialog without saving changes
		if (event.getSource() == cancelButton)
			dispose();
		
		if (fList.getSelectedIndex() != -1)
		{
			// edit a formation
			if (event.getSource() == fEdtButton)
				new FormationDialog(this, fList.getSelectedIndex());
			// remove a formation
			else if (event.getSource() == fRemButton) {
				formations.remove(fList.getSelectedIndex());
				fList.setListData(formations);
				fPane.setViewportView(fList);
			}
		}
		// create a new formation
		if (event.getSource() == fAddButton)
			new FormationDialog(this);
				

	}
	
}
