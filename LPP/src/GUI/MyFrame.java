package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


public class MyFrame extends JFrame implements ActionListener {

	static MyFrame frame;

	private JMenuBar menuBar;
	private JMenu op1, op2, op3;
	private JMenuItem drawGraph,about, finish;
	private JDesktopPane desktop;
	private DrawPanel p;
	private GenPanel gen;
	
	public MyFrame() {
		p = new DrawPanel();
		gen = new GenPanel();
		
		menuBar = new JMenuBar();
		op1 = new JMenu("Graf...");
		op3 = new JMenu("Pomoc");
		
	
		drawGraph = new JMenuItem("Rysuj graf");
		about = new JMenuItem("O programie");
		finish = new JMenuItem("Zakończ");
	
		setJMenuBar(menuBar);
		menuBar.add(op1);
		menuBar.add(op3);
		op1.add(drawGraph);
		op1.addSeparator();
		op1.add(finish);
		op3.add(about);
		
		finish.addActionListener(this);
		drawGraph.addActionListener(this);
	}
	/**
	 * Launch the application.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == drawGraph){
			getContentPane().removeAll();
			p.clear();
			getContentPane().add(p);
			revalidate();
			//repaint();
		}	
		
		if(o == finish){
			dispose();
		}
		
	}
	
	public static void main(String[] args) {
		frame = new MyFrame();	
		frame.setBackground(Color.gray);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle("Travelling salesman problem - Badania Operacyjne 2014");
				frame.setResizable(false);
				frame.setLocation(0, 0);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}	
		});
	}
}
		
