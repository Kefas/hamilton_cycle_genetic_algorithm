package GUI;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import myPkg.Main;


public class MyFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static MyFrame frame;
	static Main main;
	JMenuBar menuBar;
	JMenu op1, op2, op3;
	JMenuItem genGraph, about, finish;
	JDesktopPane desktop;
	
	public MyFrame(){
		main = new Main();
		
		menuBar = new JMenuBar();
		op1 = new JMenu("Opcja1");
		op2 = new JMenu("Opcja2");
		op3 = new JMenu("Pomoc");
		
		genGraph = new JMenuItem("Generuj graf");
		about = new JMenuItem("O programie");
		finish = new JMenuItem("Zakoñcz");
		
		setJMenuBar(menuBar);
		menuBar.add(op1);
		menuBar.add(op2);
		menuBar.add(op3);
		op1.add(genGraph);
		op1.addSeparator();
		op1.add(finish);
		op3.add(about);
		
		finish.addActionListener(this);
		genGraph.addActionListener(this);
	}
	
	public static void main(String[] args) {
		frame = new MyFrame();
		frame.setBackground(Color.gray);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setTitle("Longest simple path problem - Badania Operacyjne 2014");
				frame.setResizable(false);
				frame.setLocation(0, 0);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == finish)
			dispose();
		if(source == genGraph){
			GenFrame ramka = new GenFrame();
			ramka.setVisible(true);
		}
			
	}

	static void getGraph(int v){
		main.makeGraph(v);
		main.printGraph(main.getGraph());
	}
}
