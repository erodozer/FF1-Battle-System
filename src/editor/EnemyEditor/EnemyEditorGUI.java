package editor.EnemyEditor;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.InvalidFileFormatException;

import actors.Enemy;

import Map.Terrain;


import editor.ToolKit;
import engine.TileSet;

/**
 * EnemyEditorGUI
 * @author nhydock
 *
 *	Simple GUI for creating and editing enemies
 *	Most of the values are easily manipulated using spinners
 */
public class EnemyEditorGUI extends JPanel implements ActionListener, MouseListener{
	
	public static String[] AVAILABLEENEMIES = new String[0];
	
	/*
	 * Buttons
	 */
	JButton newButton;
	JButton saveButton;
	JButton restoreButton;
	
	/*
	 * Enemy switcher
	 */
	JList enemies;
	JScrollPane enemyListPane;
	
	/*
	 * Fields
	 */
	JTextField nameField;
	
	/*
	 * Enemy Stats
	 */
	final String[] STATS = {"HP", "STR", "DEF", "VIT", "INT", "SPD", "LUCK", "EVADE", "RESIST", "Hit %"};
	JSpinner[] statSpinners;
	
	/*
	 * Enemy elemental properties
	 */
	final String[] ELEM = {"FIRE", "FREZ", "ELEC", "DEMI", "LGHT"};
	final Vector<String> ELEM_VALUES = new Vector(){
		{
			this.add("Weak");
			this.add("Neutral");
			this.add("Strong");
		}
	};
	
	JSpinner[] elemSpinners;
	
	/*
	 * Loot spinners
	 */
	JSpinner expSpinner;
	JSpinner gSpinner;
	
	/*
	 * Enemy Sprite
	 */
	
	
	/*
	 * Other
	 */
	Enemy activeEnemy;		//the currently active enemy for editing
	
	Font font = new Font("Arial", 1, 32);
	
	public EnemyEditorGUI()
	{
		setLayout(null);
		
		/*
		 * Initialize fields 
		 */
		JLabel l = new JLabel("Enemies: ");
		l.setSize(200,16);
		l.setLocation(10,10);
		enemies = new JList(AVAILABLEENEMIES);
		enemies.addMouseListener(this);
		enemyListPane = new JScrollPane(enemies);
		enemyListPane.setSize(200, 340);
		enemyListPane.setLocation(10, 32);
		
		add(l);
		add(enemyListPane);
		refreshList();
		
		l = new JLabel("Name: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(220, 10);
		nameField = new JTextField("");
		nameField.setSize(200, 24);
		nameField.setLocation(220, 32);
		
		add(l);
		add(nameField);
		
		
		/*
		 * Stat spinner initialization
		 */
		l = new JLabel("Stats: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(450, 10);
		
		add(l);

		statSpinners = new JSpinner[STATS.length];
		
		for (int i = 0; i < STATS.length; i++)
		{
			l = new JLabel(STATS[i] + ": ");
			l.setSize(l.getPreferredSize());
			l.setLocation(450, 32 + (28*i));
			
			JSpinner s = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
			s.setSize(100, 24);
			s.setLocation(530, 32 + (28*i));
			statSpinners[i] = s;
			add(l);
			add(s);
		}
		
		
		/*
		 * Elemental spinners initialization
		 */
		
		l = new JLabel("Elemental Resistance: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 10);
		
		add(l);

		elemSpinners = new JSpinner[ELEM.length];
		
		for (int i = 0; i < ELEM.length; i++)
		{
			l = new JLabel(ELEM[i] + ": ");
			l.setSize(l.getPreferredSize());
			l.setLocation(650, 32 + (28*i));
			
			JSpinner s = new JSpinner(new SpinnerListModel(ELEM_VALUES));
			s.setValue(ELEM_VALUES.get(1));
			s.setSize(100, 24);
			s.setLocation(730, 32 + (28*i));
			elemSpinners[i] = s;
			add(l);
			add(s);
		}
		
		/*
		 * Initialize Buttons
		 */
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
		
		add(newButton);
		add(saveButton);
		add(restoreButton);

	}

	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newButton)
		{
			enemies.setSelectedValue(null, false);
			activeEnemy = null;
			reset();
		}
		else if (event.getSource() == saveButton)
		{
			saveEnemy();
		}
	}

	/**
	 * Loads an enemies values into the editor's fields and makes it the active enemy
	 * @param e
	 */
	public void loadEnemy(Enemy e)
	{
		//load name
		nameField.setText(e.getName());
		
		//load stats
		statSpinners[0].setValue(e.getMaxHP());
		statSpinners[1].setValue(e.getStr());
		statSpinners[2].setValue(e.getDef());
		statSpinners[3].setValue(e.getVit());
		statSpinners[4].setValue(e.getInt());
		statSpinners[5].setValue(e.getSpd());
		statSpinners[6].setValue(e.getLuck());
		statSpinners[7].setValue(e.getEvd());
		statSpinners[8].setValue(e.getMDef());
		statSpinners[9].setValue(e.getAcc());
		
		//load elemental resistance
		for (int i = 0; i < ELEM_VALUES.size(); i++)
			elemSpinners[0].setValue(ELEM_VALUES.get(e.getElementalResistance(i)));
		
		//load loot values
		
		activeEnemy = e;
	}
	
	/**
	 * Sets all fields to their default values
	 */
	public void reset()
	{
		nameField.setText("");
		for (JSpinner s : statSpinners)
			s.setValue(0);
				
		for (JSpinner s : elemSpinners)
			s.setValue(ELEM_VALUES.get(1));
	}
	
	/**
	 * Saves the editor's values to the enemy ini
	 */
	public void saveEnemy()
	{
		String location = "data/actors/enemies/";
		
		//making a new enemy
		if (activeEnemy == null)
		{
			if (!new File(location+nameField.getText()).mkdir())
			{
				JOptionPane.showMessageDialog(this, null, "Could not make the directory for the enemy.\nReasons for this could be: " +
						"1) You are running the editor on a read-only partition.\n2) There is already an enemy with that name", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		//saving over an old enemy
		else if (!nameField.getText().equals(activeEnemy.getName()))
		{
			File old = new File(location+activeEnemy.getName());
			//if a directory already exists for the active enemy, rename it to the new name
			if (old.exists())
			{
				if (!new File(location+activeEnemy.getName()).renameTo(new File(location+nameField.getText())))
				{
					JOptionPane.showMessageDialog(this, null, "Could not rename the enemy.  Data has not been saved.", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		
		//save to file
		try {
			File f = new File(location+nameField.getText()+"/enemy.ini");
			Ini ini = new Ini(f);
			
			//saving main
			ini.add("main", "exp", expSpinner);
			ini.add("main", "g", gSpinner);
			
			//saving stat distribution
			ini.add("distribution", "hp", statSpinners[0].getValue());
			ini.add("distribution", "str", statSpinners[1].getValue());
			ini.add("distribution", "def", statSpinners[2].getValue());
			ini.add("distribution", "spd", statSpinners[3].getValue());
			ini.add("distribution", "evd", statSpinners[4].getValue());
			ini.add("distribution", "vit", statSpinners[5].getValue());
			ini.add("distribution", "int", statSpinners[6].getValue());
			ini.add("distribution", "mdef", statSpinners[7].getValue());
			ini.add("distribution", "acc", statSpinners[8].getValue());
			
			
			//saving elemental distribution
			ini.add("elemental", "fire", ELEM_VALUES.indexOf(elemSpinners[0].getValue()));
			ini.add("elemental", "frez", ELEM_VALUES.indexOf(elemSpinners[1].getValue()));
			ini.add("elemental", "elec", ELEM_VALUES.indexOf(elemSpinners[2].getValue()));
			ini.add("elemental", "lght", ELEM_VALUES.indexOf(elemSpinners[3].getValue()));
			ini.add("elemental", "dark", ELEM_VALUES.indexOf(elemSpinners[4].getValue()));
			
			ini.store(f);
			
			//refresh the list of names after saving
			loadEnemy(new Enemy(nameField.getText()));
			refreshList();	
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void refreshList()
	{
		AVAILABLEENEMIES = new ArrayList<String>()
		{
			{
				for (String s : new File("data/actors/enemies").list())
					if (new File("data/actors/enemies/" + s + "/enemy.ini").exists())
						this.add(s);
				System.out.println(this);
			}
		}.toArray(new String[]{});
		
		enemies.setListData(AVAILABLEENEMIES);
		if (activeEnemy != null)
			enemies.setSelectedValue(activeEnemy.getName(), true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == enemies)
			loadEnemy(new Enemy((String)enemies.getSelectedValue()));
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
