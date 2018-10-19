package galkon;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ListDataListener, ListSelectionListener, PropertyChangeListener, ItemListener {

	public static JFrame frame;
	public static JList list;
	public static DefaultListModel model;
	public static JScrollPane listPane;
	public static JTable table;
	public static JScrollPane tablePane;
	public static JRadioButton byName;
	public static JButton add;
	public static JButton remove;
	public static BufferedImage image;
	public static JLabel imageLabel;
	public int tableHeight;

	public UserInterface() {
		try {
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} catch (Exception e2) {
			}
		}
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Sprite Packer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		buildInterface();
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setSize(550, 340);
		frame.setMinimumSize(frame.getSize());
		frame.setLocationRelativeTo(null);
	}

	public void buildInterface() {
		model = new DefaultListModel();
		model.addListDataListener(this);
		list = new JList(model);
		list.addListSelectionListener(this);
		listPane = new JScrollPane(list);
		listPane.setBounds(5, 5, 120, 300);
		listPane.setVisible(true);
		frame.add(listPane);
		String fieldNames[] = { "Variable", "Value" };
		String dataValues[][] = {
			{ "name", "" },
			{ "drawOffsetX", "" },
			{ "drawOffsetY", "" },
			{ "width", ""},
			{ "height", "" }
		};
		table = new JTable(dataValues, fieldNames);
		table.addPropertyChangeListener(this);
		table.setSelectionMode(0);
		table.setTransferHandler(null);
		tablePane = new JScrollPane(table);
		tableHeight = (table.getRowCount() * 19) + 19;
		tablePane.setBounds(130, 5, 300, tableHeight);
		tablePane.setVisible(true);
		drawButtons();
		frame.add(tablePane);
		frame.pack();
		SpriteLoader.buildSpriteList();
	}

	public void drawButtons() {
		String[] names = { "Write cache" };
		for (int index = 0; index < names.length; index++) {
			JButton button = new JButton(names[index]);
			button.addActionListener(e -> FileController.buildCache());
			button.setBounds(435, 5 + (25 * index), 105, 22);
			frame.add(button);
		}
	}

	public void updateProperties(int index) {
		if (SpriteLoader.grabImage(index) != null) {
			if (imageLabel != null) {
				frame.remove(imageLabel);
			}
			image = SpriteLoader.grabImage(index);
			imageLabel = new JLabel(new ImageIcon(image));
			imageLabel.setBounds(130, tableHeight + 10, image.getWidth(), image.getHeight());
			frame.setSize((140 + image.getWidth()) > 550 ? (140 + image.getWidth()) : 550, (tableHeight + 50) + image.getHeight() > 340 ? (tableHeight + 45) + image.getHeight() : 340);
			frame.add(imageLabel);
			frame.repaint();
		} else {
			frame.remove(imageLabel);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		int index = 0;
		if (table.getSelectedColumn() == 1) {
			if (table.getSelectedRow() == index) {
				SpriteLoader.setName(list.getSelectedIndex(), table.getValueAt(index, 1).toString());
			}
			index++;
			if (table.getSelectedRow() == index) {
				SpriteLoader.setOffsetX(list.getSelectedIndex(), Integer.parseInt(table.getValueAt(index, 1).toString()));
			}
			index++;
			if (table.getSelectedRow() == index) {
				SpriteLoader.setOffsetY(list.getSelectedIndex(), Integer.parseInt(table.getValueAt(index, 1).toString()));
			}
			index++;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int index = 0;
		if (list.getSelectedIndex() != -1) {
			SpriteLoader.grabImage(list.getSelectedIndex());
			table.setValueAt(SpriteLoader.getName(list.getSelectedIndex()), index++, 1);
			table.setValueAt(Integer.toString(SpriteLoader.getOffsetX(list.getSelectedIndex())), index++, 1);
			table.setValueAt(Integer.toString(SpriteLoader.getOffsetY(list.getSelectedIndex())), index++, 1);
			table.setValueAt(Integer.toString(SpriteLoader.getWidth(list.getSelectedIndex())), index++, 1);
			table.setValueAt(Integer.toString(SpriteLoader.getHeight(list.getSelectedIndex())), index++, 1);
			updateProperties(list.getSelectedIndex());
		}
	}

	@Override
	public void contentsChanged(ListDataEvent arg0) {}

	@Override
	public void intervalAdded(ListDataEvent arg0) {}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {}

}