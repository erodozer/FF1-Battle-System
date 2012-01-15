package editor.MapEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import editor.ToolKit;

/**
 * NewMapDialog
 * @author nhydock
 *
 *	Little window for creating a new map
 */
public class LoadMapDialog extends JDialog implements ActionListener {

	JButton okButton;			//end dialog
	
	JComboBox mapList;			//map list combo box
	MapEditorGUI parent;		//parent gui
	
	/**
	 * Constructs the dialog window
	 * @param p		Parent GUI
	 */
	public LoadMapDialog(MapEditorGUI p)
	{
		parent = p;
		setLayout(null);
		
		JLabel l = new JLabel("Select the map to load");
		l.setLocation(10, 16);
		l.setSize(200, 24);
		JLabel l2 = new JLabel("x");
		mapList = new JComboBox(ToolKit.maps);
		mapList.setLocation(10, 48);
		mapList.setSize(230, 24);
		
		okButton = new JButton("OK");
		okButton.setSize(100, 32);
		okButton.setLocation(75, 74);
		
		okButton.addActionListener(this);
		
		add(l);
		add(mapList);
		add(okButton);
		
		setSize(250,148);
		setVisible(true);
		setModal(true);
		setResizable(false);
	}

	/**
	 * Accepts button clicking
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == okButton)
		{
			try {
				parent.load((String)mapList.getSelectedItem());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Sorry, The map could not be properly loaded");
				e.printStackTrace();
			}
			dispose();
			
		}
	}
	
}
