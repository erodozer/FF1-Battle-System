package editor.EnemyEditor;
import graphics.Sprite;
import item.ItemDictionary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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

import actors.Actor;
import actors.Enemy;

import Map.Terrain;
import Map.TileSet;


import editor.ToolKit;

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
	JSpinner[] mpSpinners;
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
	 * Loot
	 */
	JSpinner expSpinner;
	JSpinner gSpinner;
	JComboBox items;
	
	/*
	 * Enemy Sprite
	 */
	SpritePic previewBox;
	
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
		enemyListPane.setSize(200, 343);
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

		JLayeredPane jlp = new JLayeredPane();
		jlp.setSize(new Dimension(420, 210));
		jlp.setLocation(220, 60);
		jlp.setBorder(BorderFactory.createTitledBorder("Stats:"));
		
		statSpinners = new JSpinner[STATS.length];
		
		for (int i = 0; i < STATS.length; i++)
		{
			l = new JLabel(STATS[i] + ": ");
			l.setSize(l.getPreferredSize());
			l.setLocation(10 + (i%2)*210, 20 + 30*(i/2));
			
			//hp can go up to 99999
			JSpinner s = new JSpinner(new SpinnerNumberModel(0, 0, (i == 0)?99999:255, 1));
			s.setSize(100, 24);
			s.setLocation(100 + (i%2)*210, 20 + 30*(i/2));
			statSpinners[i] = s;
			jlp.add(l);
			jlp.add(s);
		}
		
		mpSpinners = new JSpinner[Actor.MPLEVELS];
		l = new JLabel("MP: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 176);
		jlp.add(l);
		for (int i = 0; i < mpSpinners.length; i++)
		{
			JSpinner s = new JSpinner(new SpinnerNumberModel(0, 0, 9, 1));
			s.setSize(44, 24);
			s.setLocation(44 + i*46, 176);
			mpSpinners[i] = s;
			jlp.add(s);	
		}
		add(jlp);
		
		
		/*
		 * Elemental spinners initialization
		 */
		
		jlp = new JLayeredPane();
		jlp.setSize(new Dimension(420, 108));
		jlp.setLocation(220, 268);
		jlp.setBorder(BorderFactory.createTitledBorder("Elemental Resistance: "));
		
		elemSpinners = new JSpinner[ELEM.length];
		
		for (int i = 0; i < ELEM.length; i++)
		{
			l = new JLabel(ELEM[i] + ": ");
			l.setSize(l.getPreferredSize());
			l.setLocation(10 + 210*(i%2), 20 + 30*(i/2));
			
			JSpinner s = new JSpinner(new SpinnerListModel(ELEM_VALUES));
			s.setValue(ELEM_VALUES.get(1));
			s.setSize(100, 24);
			s.setLocation(100 + 210*(i%2), 20 + 30*(i/2));
			elemSpinners[i] = s;
			jlp.add(l);
			jlp.add(s);
		}
		add(jlp);
		
		/*
		 * Loot rewards
		 */
		
		l = new JLabel("Exp: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(430, 10);
		
		expSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
		expSpinner.setSize(100, 24);
		expSpinner.setLocation(430, 32);
		add(l);
		add(expSpinner);
		
		l = new JLabel("G: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(540, 10);
		
		gSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
		gSpinner.setSize(100, 24);
		gSpinner.setLocation(540, 32);
		add(l);
		add(gSpinner);
		
		l = new JLabel("Item Drop: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 232);
		
		items = new JComboBox(new DefaultComboBoxModel(new Vector<String>(ItemDictionary.map.keySet())));
		items.addItem(null);
		items.setSelectedItem(null);
		items.setSize(230, 24);
		items.setLocation(650, 250);
		add(l);
		add(items);
		
		/*
		 * Enemy Sprite preview
		 */
		
		l = new JLabel("Sprite Preview: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 10);
		
		previewBox = new SpritePic();
		previewBox.setSize(230,200);
		JScrollPane p = new JScrollPane(previewBox);
		p.setSize(230, 200);
		p.setLocation(650, 32);
		add(l);
		add(p);
		
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
			elemSpinners[i].setValue(ELEM_VALUES.get(e.getElementalResistance(i)));
		
		//load loot values
		activeEnemy = e;
		
		previewBox.load(e.getSprite());
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
				JOptionPane.showMessageDialog(this, "Could not make the directory for the enemy.\nReasons for this could be:\n" +
						"1) You are running the editor on a read-only partition.\n2) There is already an enemy/directory with that name", "Error", JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(this, "Could not rename the enemy.  Data has not been saved.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		
		//save to file
		try {
			File f = new File(location+nameField.getText()+"/enemy.ini");
			f.delete();					//deletes the old file
			f.createNewFile();			//saves data to new file
			Ini ini = new Ini(f);
			
			//saving main
			ini.add("enemy", "exp", expSpinner);
			ini.add("enemy", "g", gSpinner);
			
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
			
			String mp = "";
			for (int i = 0; i < mpSpinners.length; i++)
			{
				mp += mpSpinners[i].getValue();
				if (i < mpSpinners.length-1)
					mp+="/";
			}
			ini.add("distribution", "mp", mp);
			
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
	
	class SpritePic extends JComponent
	{
		Sprite preview;
		Image dbImage;			//image buffer
		
		/**
		 * Loads a new sprite to preview
		 * @param s		path to the sprite's file
		 */
		public void load(String s)
		{
			this.load(new Sprite(s));
		}
		
		public void load(Sprite s)
		{
			preview = s;
			preview.scale(2.0, 2.0);	//auto scale 2x
			
			//ONLY RESCALE IF LARGER THAN THE PREVIEW AREA
			if (preview.getScaledWidth() > this.getWidth() ||
				preview.getScaledHeight() > this.getHeight())
			{
				//scale the preview to fit inside the preview area
				double width = preview.getWidth();
				double height = preview.getHeight();
				
				if (width > height)
				{
					height *= this.getWidth()/width;
					width *= this.getWidth()/width;
				}
				else
				{
					width *= this.getHeight()/height;
					height *= this.getHeight()/height;
				}
				
				preview.scale((int)width, (int)height);
			}
			dbImage = null;
			repaint();
		}
		
		/**
		 * Gets the previewed sprite
		 * @return
		 */
		public Sprite getSprite()
		{
			return preview;
		}
		
		/**
		 * Paints the sprite
		 */
		@Override
		public void paint(Graphics g)
		{
			if (dbImage == null)
			{
				dbImage = createImage(getWidth(), getHeight());
				if (preview != null)
					preview.paint(dbImage.getGraphics());
			}
			
			g.drawImage(dbImage, 0, 0, null);
		}
	}
}
