package editor.SpriteCreator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import javax.swing.JScrollPane;
import javax.swing.JSlider;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import editor.MapEditor.MapGrid;
import graphics.Sprite;


import actors.Enemy;

import Map.Map;
import Map.NPC;

/**
 * EnemyEditorGUI
 * @author nhydock
 *
 *	Simple GUI for creating and editing enemies
 *	Most of the values are easily manipulated using spinners
 */
public class SpriteCreatorGUI extends JPanel implements ActionListener, ChangeListener{
	
	SpritePic previewBox;
	
	/*
	 * Buttons
	 */
	JButton newButton;
	JButton saveButton;
	JButton savePNGButton;
	JButton loadButton;
	JButton AddButton;			//add new layer
	JButton RemButton;			//remove layer
	JButton EdtButton;			//edit layer properties
	JButton SftUpButton;		//shifts a layer up the list
	JButton SftDnButton;		//shifts a layer down the list
	
	
	/*
	 * Directions
	 */
	JSlider direction;
	JSlider walkCycle;
	
	/*
	 * Layer switcher
	 */
	JList layerList;
	JScrollPane layerListPane;
	
	/*
	 * Fields
	 */
	JTextField nameField;
	
	/*
	 * Sprite Layers
	 */
	final Vector<Layer> layers = new Vector<Layer>();
	
	/*
	 * Other
	 */
	Enemy activeEnemy;		//the currently active enemy for editing
	
	Font font = new Font("Arial", 1, 32);

	private JScrollPane previewPane;
	
	public SpriteCreatorGUI()
	{
		setLayout(null);
		
		JLabel l;
		
		/*
		 * Preview box
		 */
		
		previewBox = new SpritePic(this);
		previewPane = new JScrollPane(previewBox);
		previewPane.setLocation(220, 10);
		previewPane.setSize(420, 365);
		previewPane.getViewport().setBackground(Color.GRAY);
		
		add(previewPane);
		
		/*
		 * Initialize fields 
		 */
		l = new JLabel("Name: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(10, 10);
		nameField = new JTextField("");
		nameField.setSize(200, 24);
		nameField.setLocation(10, 32);
		
		add(l);
		add(nameField);
		
		l = new JLabel("Layers: ");
		l.setSize(200,16);
		l.setLocation(10,64);
		layerList = new JList(layers);
		layerListPane = new JScrollPane(layerList);
		layerListPane.setSize(200, 228);
		layerListPane.setLocation(10, 92);
		
		add(l);
		add(layerListPane);
		refreshList();
		
		/*
		 * Initialize Direction handling
		 */
		l = new JLabel("Direction: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 10);
		direction = new JSlider(0, Map.DIRECTIONS);
		direction.setPaintTicks(true);
		direction.setPaintLabels(true);
		direction.setSnapToTicks(true);
		direction.setSize(230, 36);
		direction.setLocation(650, 32);
		direction.addChangeListener(this);
		add(direction);
		add(l);
		
		/*
		 * Initialize Walk Cycle handling
		 */
		l = new JLabel("Walk Cycle Frame: ");
		l.setSize(l.getPreferredSize());
		l.setLocation(650, 72);
		walkCycle = new JSlider(0, NPC.WALKCYCLE);
		walkCycle.setPaintTicks(true);
		walkCycle.setPaintLabels(true);
		walkCycle.setSnapToTicks(true);
		walkCycle.setSize(230, 36);
		walkCycle.setLocation(650, 92);
		walkCycle.addChangeListener(this);
		add(walkCycle);
		add(l);

		
		/*
		 * Initialize Buttons
		 */
		RemButton = new JButton("-");
		RemButton.setSize(48,24);
		RemButton.setLocation(210-RemButton.getWidth(), 350);
		
		AddButton = new JButton("+");
		AddButton.setSize(48,24);
		AddButton.setLocation(RemButton.getX()-AddButton.getWidth(), 350);
		
		EdtButton = new JButton("Edit");
		EdtButton.setSize(100,24);
		EdtButton.setLocation(10, 350);
		
		AddButton.addActionListener(this);
		RemButton.addActionListener(this);
		EdtButton.addActionListener(this);
		
		add(AddButton);
		add(RemButton);
		add(EdtButton);
		
		SftUpButton = new JButton("\u2191");
		SftUpButton.setSize(100,24);
		SftUpButton.setLocation(10, 320);
		
		SftDnButton = new JButton("\u2193");
		SftDnButton.setSize(100,24);
		SftDnButton.setLocation(110, 320);
		
		SftUpButton.addActionListener(this);
		SftDnButton.addActionListener(this);
		
		add(SftUpButton);
		add(SftDnButton);
		
		int[] buttonSize = {230, 24};
		newButton = new JButton("New");
		newButton.setSize(buttonSize[0], buttonSize[1]);
		newButton.setLocation(650, 260);
		saveButton = new JButton("Save");
		saveButton.setSize(buttonSize[0], buttonSize[1]);
		saveButton.setLocation(650, 290);
		savePNGButton = new JButton("Save as .PNG");
		savePNGButton.setSize(buttonSize[0], buttonSize[1]);
		savePNGButton.setLocation(650, 320);
		loadButton = new JButton("Load");
		loadButton.setSize(buttonSize[0], buttonSize[1]);
		loadButton.setLocation(650, 350);
		
		newButton.addActionListener(this);
		saveButton.addActionListener(this);
		savePNGButton.addActionListener(this);
		loadButton.addActionListener(this);
		
		add(newButton);
		add(saveButton);
		add(savePNGButton);
		add(loadButton);

	}

	
	/**
	 * Handles most input
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();		//source of the action
		
		//start creating a new sprite
		if (source == newButton)
		{
			layers.removeAllElements();
			refreshList();
		}
		else if (source == savePNGButton)
		{
			BufferedImage b = previewBox.save();
			try {
			    // retrieve image
			    BufferedImage bi = previewBox.save();
			    File outputfile = new File("data/actors/npcs/"+nameField.getText()+".png");
			    outputfile.createNewFile();
			    ImageIO.write(bi, "png", outputfile);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "The image was not able to save.\n" +
							"Possible reasons for this could be \n" +
							"1) The filesystem is read-only\n" +
							"2) There is already a file with that name in the npcs folder"
							, "An error has occurred", JOptionPane.ERROR_MESSAGE);
			}

		}
		//add a new layer
		if (source == AddButton)
		{
			new SpriteLayerDialog(this);
		}
		//edit the currently selected layer
		else if (source == EdtButton)
		{
			int index = layerList.getSelectedIndex();
			if (index == -1)
				new SpriteLayerDialog(this);
			else
				new SpriteLayerDialog(this, index);
		}
		//removes the currently selected layer
		else if (source == RemButton)
		{
			layers.remove(layerList.getSelectedIndex());
			refreshList();
		}
		//shift the currently selected layer up one
		else if (source == SftUpButton)
		{
			int i = layerList.getSelectedIndex();
			if (i > 0)
			{
				layers.add(i-1, layers.remove(i));
				refreshList();
				layerList.setSelectedIndex(i-1);
			}
		}
		//shift the currently selected layer down one
		else if (source == SftDnButton)
		{
			int i = layerList.getSelectedIndex();
			if (i < layers.size()-1)
			{
				layers.add(i+1, layers.remove(i));
				refreshList();
				layerList.setSelectedIndex(i+1);
			}
		}
	}
	
	public void refreshList()
	{
		layerList.setListData(layers);
		layerListPane.setViewportView(layerList);
		previewBox.update();
		repaint();
	}

	/**
	 * Handles slider input
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource() == direction)
		{
			if (direction.getValue() == 0)
				previewBox.setDirection(-1);
			else
				previewBox.setDirection(direction.getValue());
		}
		if (arg0.getSource() == walkCycle)
		{
			if (walkCycle.getValue() == 0)
				previewBox.setStep(-1);
			else
				previewBox.setStep(walkCycle.getValue());
		}
	}

	/**
	 * SpritePic (SpriteCreatorGUI)
	 * @author nhydock
	 *
	 *	Shows a preview of the complete spriteset
	 */
	class SpritePic extends JComponent //implements Scrollable
	{
		int width = 1;
		int height = 1;
		int step = 1;
		int dir = 1;
		BufferedImage dbImage;			//image buffer
		
		SpriteCreatorGUI parent;
		
		public SpritePic(SpriteCreatorGUI p)
		{
			super();
			parent = p;
			dbImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
			this.setSize(width, height);
		}
		
		/**
		 * Loads a new sprite to preview
		 * @param s		path to the sprite's file
		 */
		public void update()
		{
			if (parent.layers.size() < 1)
				return;
			
			Layer l = parent.layers.get(0);
			width = (int)l.getWidth();
			height = (int)l.getHeight();
			
			for (int i = 1; i < parent.layers.size(); i++)
			{				
				l = parent.layers.get(i);
				width = Math.max(width, (int)l.getWidth());
				height = Math.max(height, (int)l.getHeight());
			}
			
			this.setSize(width*2, height*2);
			dbImage = new BufferedImage(width*((step==-1)?NPC.WALKCYCLE:1), height*((dir==-1)?Map.DIRECTIONS:1), BufferedImage.TYPE_4BYTE_ABGR);
			repaint();
		}

		
		public void setDirection(int i)
		{
			dir = i;
			update();
		}
		
		public void setStep(int i)
		{
			step = i;
			update();
		}
		
		/**
		 * Paints the sprite
		 */
		@Override
		public void paint(Graphics g)
		{
			Graphics g2 = dbImage.getGraphics();
			
			g2.setColor(new Color(25,155,105));
			g2.fillRect(0, 0, dbImage.getWidth(), dbImage.getHeight());
			
			Layer l;
			for (int i = 0; i < parent.layers.size(); i++)
			{
				l = parent.layers.get(i);
				if (dir == -1)
					for (int y = 1; y <= Map.DIRECTIONS; y++)
					{
						if (step == -1)
							for (int x = 1; x <= NPC.WALKCYCLE; x++)
							{
								l.setFrame(x, y);
								l.setX((x-1)*width);
								l.setY((y-1)*height);
								l.paint(g2);
							}
						else
						{
							l.setFrame(step, y);
							l.setX(0);
							l.setY((y-1)*height);
							l.paint(g2);	
						}
					}
				else if (step == -1)
					for (int x = 1; x <= NPC.WALKCYCLE; x++)
					{
						l.setFrame(x, dir);
						l.setX((x-1)*width);
						l.setY(0);
						l.paint(g2);
					}
				else
				{
					l.setFrame(step, dir);
					l.setX(0);
					l.setY(0);
					l.paint(g2);	
				}
			}
			
			g.drawImage(dbImage, 0, 0, dbImage.getWidth()*2, dbImage.getHeight()*2, null);
		}
		/*
		@Override
		public Dimension getPreferredScrollableViewportSize() {
			return new Dimension(width, height);
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle arg0, int arg1,
				int arg2) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean getScrollableTracksViewportHeight() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean getScrollableTracksViewportWidth() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			return 0;
		}
		*/
	
		public BufferedImage save()
		{
			BufferedImage b;
			
			b = new BufferedImage(width*NPC.WALKCYCLE, height*Map.DIRECTIONS, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g2 = b.getGraphics();
			
			Layer l;
			for (int i = 0; i < parent.layers.size(); i++)
			{
				l = parent.layers.get(i);
				for (int y = 1; y <= Map.DIRECTIONS; y++)
					for (int x = 1; x <= NPC.WALKCYCLE; x++)
						{
							l.setFrame(x, y);
							l.setX((x-1)*width);
							l.setY((y-1)*height);
							l.paint(g2);
						}
				l.paint(g2);
			}
			repaint();
			
			return b;
		}
	}
	
	
}
