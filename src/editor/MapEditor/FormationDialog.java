package editor.MapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import actors.Enemy;

import editor.ToolKit;

/**
 * NewMapDialog
 * @author nhydock
 *
 *	Little window for creating a new map
 */
public class FormationDialog extends JDialog implements ActionListener {

	private JButton okButton;			//end dialog
	private JButton addButton;			//add enemy
	private JButton remButton;			//remove enemy
	
	private JComboBox enemyList;		//enemy list combo box
	private Vector<String> formation;	//assembled formation
	private JList formationList;		//assembled formation as a JList
	private JScrollPane formationPane;	//pane to display formation
	
	private RegionEditorDialog parent;	//parent gui
	private int index = -1;
	
	/**
	 * Constructs the dialog window
	 * @param p		Parent GUI
	 */
	public FormationDialog(RegionEditorDialog p)
	{
		parent = p;
		setLayout(null);
		
		/*
		 * Initialize formation list
		 */
		JLabel l = new JLabel("Formation");
		l.setLocation(10, 10);
		l.setSize(200, 24);
		
		formation = new Vector<String>();
		formationList = new JList(formation);
		formationPane = new JScrollPane(formationList);
		formationPane.setSize(200, 190);
		formationPane.setLocation(10, 32);
		add(l);
		add(formationPane);
		
		/*
		 * Initialize enemy combo box
		 */
		l = new JLabel("Enemies");
		l.setLocation(220, 10);
		l.setSize(200, 24);
		
		enemyList = new JComboBox(Enemy.AVAILABLEENEMIES);
		enemyList.setLocation(220, 32);
		enemyList.setSize(200, 24);
		
		add(l);
		add(enemyList);
		
		
		/*
		 * Initialize buttons
		 */
		addButton = new JButton("Add Enemy");
		addButton.setSize(200, 24);
		addButton.setLocation(220, 64);
		addButton.addActionListener(this);
		
		remButton = new JButton("Remove selected");
		remButton.setSize(200, 24);
		remButton.setLocation(220, 96);
		remButton.addActionListener(this);
		
		okButton = new JButton("OK");
		okButton.setSize(200, 24);
		okButton.setLocation(220, 128);
		okButton.addActionListener(this);
		
		add(l);
		add(addButton);
		add(remButton);
		add(okButton);
		
		setSize(430,260);
		setVisible(true);
		setModal(true);
		setResizable(false);
	}
	
	public FormationDialog(RegionEditorDialog p, int i)
	{
		this(p);
		index = i;
		formation = new Vector<String>();
		String[] e = parent.formations.get(i).split(",");
		for (int x = 0; x < e.length; x++)
			formation.add(e[x]);
		formationList.setListData(formation);
		formationPane.setViewportView(formationList);
	}

	/**
	 * Accepts button clicking
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == okButton)
		{
			String f = "";
			
			for (int i = 0; i < formation.size()-1; i++)
				f += formation.get(i) + ",";
			f += formation.get(formation.size()-1);
			
			if (index == -1)
				parent.formations.add(f);
			else
				parent.formations.set(index, f);
			parent.fList.setListData(parent.formations);
			parent.fPane.setViewportView(parent.fList);
			dispose();
		}
		else if (event.getSource() == addButton)
		{
			formation.add((String)enemyList.getSelectedItem());
			formationList.setListData(formation);
			formationPane.setViewportView(formationList);
		}
		else if (event.getSource() == remButton)
		{
			formation.remove(formationList.getSelectedIndex());
			formationList.setListData(formation);
			formationPane.setViewportView(formationList);
		}
	}
	
}
