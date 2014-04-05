package GUI;

import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

public class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyPanel(JRadioButton l1, JRadioButton l2, JRadioButton l3, JRadioButton l4, JRadioButton l5){
		ButtonGroup group = new ButtonGroup();
		group.add(l1);
		group.add(l2);
		group.add(l3);
		group.add(l4);
		group.add(l5);
		add(l1);
		l2.setSelected(true);
		add(l2);
		add(l3);
		add(l4);
		add(l5);
		setLayout(new FlowLayout());
	}
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		
		
	}

}
