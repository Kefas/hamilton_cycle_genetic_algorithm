package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GenFrame extends JFrame {
	private int h,w;
	private JPanel sliderPanel;
	private JTextField textField;
	private ChangeListener listener;
	   
	static final int FPS_MIN = 0;
	static final int FPS_MAX = 30;
	static final int FPS_INIT = 15; 
	
	
		GenFrame(){
			System.out.println("GenFrame");
			w=500;
			h=300;
			sliderPanel = new JPanel();
			sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
			// common listener for all sliders
		      listener = new ChangeListener()
		         {
		            public void stateChanged(ChangeEvent event)
		            {
		               // update text field when the slider value changes
		               JSlider source = (JSlider) event.getSource();
		               textField.setText("" + source.getValue());
		            }
		         };
		         
		         JSlider slider = new JSlider(FPS_MIN, FPS_MAX, FPS_INIT);
		        
		         slider.setPaintTicks(true);
		         slider.setPaintLabels(true);
		         slider.setMajorTickSpacing(10);
		         slider.setMinorTickSpacing(1);
		         slider.setPreferredSize(new Dimension(300, 60));
		         textField = new JTextField("15", 3);
		        
		         addSlider("Liczba Wierzcho³ków:  ",slider, textField);
		         add(sliderPanel, BorderLayout.CENTER);
		        
		         //add(textField, BorderLayout.SOUTH);
		         
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setTitle("Generuj graf");
				setResizable(false);
				setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-(w/2),Toolkit.getDefaultToolkit().getScreenSize().height/2-(h/2));
				setSize(w,h);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			}
		});
		}


	public void addSlider(String description,JSlider s,  JTextField t)
	{
	   s.addChangeListener(listener);
	   JPanel panel = new JPanel();
	   panel.add(new JLabel(description));
	   panel.add(s);
	   panel.add(t);
	  
	   sliderPanel.add(panel);
	}
}
