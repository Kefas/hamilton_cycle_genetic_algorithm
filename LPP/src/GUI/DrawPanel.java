package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawPanel extends JPanel implements MouseListener {

	List<Pair<Integer, Integer>> list;
	int path[];

	public DrawPanel(int w, int h) {
		// pressed = false;
		this.setPreferredSize(new Dimension(w, h));
		this.setBackground(Color.white);
		list = new ArrayList<>();
		addMouseListener(this);
	}

	public void paint(Graphics g) {
		// if(pressed == true){
		System.out.println("WUT");
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int) (Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth() * 0.8), (int) (Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() * 0.8));
		for (int i = 0; i < list.size(); i++) {
			g.setColor(Color.red);
			g.fillOval(list.get(i).getX(), list.get(i).getY(), 10, 10);
			// System.out.println(tabX[i]);
		}

		if (path != null) {
			for (int i = 0; i < path.length - 1; i++) {
				g.drawLine(list.get(path[i]).getX() + 5, list.get(path[i])
						.getY() + 5, list.get(path[i + 1]).getX() + 5, list
						.get(path[i + 1]).getY() + 5);
			}
			g.drawLine(list.get(path.length - 1).getX() + 5,
					list.get(path.length - 1).getY() + 5,
					list.get(0).getX() + 5, list.get(0).getY() + 5);
		}
		// pressed = false;
		// }
	}

	public void drawPath(int tab[]) {
		path = tab;
		// System.out.println(path);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// pressed = true;
		// int x = e.getX();
		// int y = e.getY();
		// tabX[count] = x;
		// tabY[count] = y;
		int x = e.getX();
		int y = e.getY();
		list.add(new Pair<Integer, Integer>(x, y));
		System.out.println("Dzieje sie");
		// revalidate();
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
