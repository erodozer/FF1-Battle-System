package editor.SpriteCreator;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import editor.ToolKit;
import engine.Sprite;

import actors.Enemy;

/**
 * NewMapDialog
 * @author nhydock
 *
 *	Little window for creating a new map
 */
public class SpriteLayerDialog extends JDialog implements ActionListener {

	private JButton okButton;			//end dialog
	
	private JComboBox categoryList;		//combo box that lists the different categories
	private JComboBox elementList;		//combo box that lists the different item in the category
	private DefaultComboBoxModel elements;

	private SpritePic previewBox;		//previews the layer going to be added
	
	private SpriteCreatorGUI parent;	//parent gui
	
	private int index = -1;
	
	/**
	 * Constructs the dialog window
	 * @param p		Parent GUI
	 */
	public SpriteLayerDialog(SpriteCreatorGUI p)
	{
		parent = p;
		setLayout(null);
		
		JLabel l;
		
		/*
		 * Initialize category list
		 */
		
		l = new JLabel("Category");
		l.setLocation(10, 10);
		l.setSize(200, 24);
		
		categoryList = new JComboBox(ToolKit.spriteCategories);
		categoryList.setSize(200, 24);
		categoryList.setLocation(10, 36);
		categoryList.addActionListener(this);
		
		add(l);
		add(categoryList);
		
		/*
		 * Initialize element list
		 */
		
		l = new JLabel("Element");
		l.setLocation(10, 64);
		l.setSize(200, 24);
		
		elements = new DefaultComboBoxModel(ToolKit.spriteElements[0]);
		elementList = new JComboBox(elements);
		elementList.setSize(200, 24);
		elementList.setLocation(10, 92);
		elementList.addActionListener(this);
		
		add(l);
		add(elementList);
		
		
		/*
		 * Initialize preview
		 */
		
		l = new JLabel("Preview");
		l.setLocation(232, 10);
		l.setSize(200, 24);
		
		previewBox = new SpritePic();
		previewBox.setSize(150,150);
		previewBox.setLocation(232, 32);
		previewImage();
		add(previewBox);
		
		/*
		 * Initialize buttons
		 */
		
		okButton = new JButton("OK");
		okButton.setSize(200, 24);
		okButton.setLocation(10, 128);
		okButton.addActionListener(this);
		
		add(l);
		add(okButton);
		
		setSize(392,200);
		setVisible(true);
		setModal(true);
		setResizable(false);
	}
	
	public SpriteLayerDialog(SpriteCreatorGUI p, int i)
	{
		this(p);
		index = i;
		Layer l = p.layers.get(i);
		categoryList.setSelectedIndex(l.getCategory());

		previewImage();
	}

	/**
	 * Accepts button clicking
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == categoryList)
		{
			updateElements();
		}
		if (event.getSource() == elementList)
		{
			previewImage();
		}
		if (event.getSource() == okButton)
		{
			Layer l = new Layer(categoryList.getSelectedIndex(), elementList.getSelectedIndex());
			if (index == -1)
				parent.layers.add(l);
			else
				parent.layers.set(index, l);
			parent.layerList.setListData(parent.layers);
			parent.layerListPane.setViewportView(parent.layerList);
			dispose();
		}
	}
	
	/**
	 * Updates the elements list
	 */
	public void updateElements()
	{
		elements = new DefaultComboBoxModel(ToolKit.spriteElements[categoryList.getSelectedIndex()]);
		elementList.setModel(elements);
		elementList.setSelectedIndex(-1);
		previewImage();
		repaint();
	}
	
	/**
	 * Changes the preview image
	 */
	public void previewImage()
	{
		previewBox.load("editor/spriteCreator/"+(String)categoryList.getSelectedItem()+"/"+(String)elementList.getSelectedItem());
		repaint();
	}
	
	
	class SpritePic extends JComponent
	{
		Sprite preview;
		Image dbImage;			//image buffer
		
		public SpritePic()
		{
			this.setSize(150, 150);
		}
		
		/**
		 * Loads a new sprite to preview
		 * @param s		path to the sprite's file
		 */
		public void load(String s)
		{
			preview = new Sprite(s, 2, 4);
			
			//scale the preview to fit inside the preview area
			/*
			double width = preview.getWidth();
			double height = preview.getHeight();
			
			if (width > height)
			{
				height = (height/width)*(width/this.getWidth());
				width = width/this.getWidth();
			}
			else
			{
				width = (width/height)*(height/this.getHeight());
				height = height/this.getHeight();
			}
			preview.scale((int)width, (int)height);
			*/
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
				preview.paint(dbImage.getGraphics());
			}
			
			g.drawImage(dbImage, 0, 0, null);
		}
	}
}
