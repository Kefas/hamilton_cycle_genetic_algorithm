package GUI;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import myPkg.*;


public class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static MyFrame frame;
	private Main main;
	
	public MyFrame(){
		main = new Main();
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

}
