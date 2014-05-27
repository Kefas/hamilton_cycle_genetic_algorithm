package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class My2Panel extends JPanel {
	List<Pair<Integer, Integer>> list;
	int path[];
	
	private JTextField textField;
	private JTextField textField_1;
	private ChangeListener listener;
	private JSlider slider, slider_1 ;

	/**
	 * Create the panel.
	 */
	public My2Panel() {

		list = new ArrayList<>();
	
		
		final JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				list.add(new Pair<Integer, Integer>(x, y));
				System.out.println("Dzieje sie");
				// revalidate();
				repaint();

			}
		});
		panel.setBackground(Color.WHITE);
		
		slider = new JSlider(0,5000,2500);
		slider.setMajorTickSpacing(2500);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		
		JLabel lblLiczbaPopulacji = new JLabel("Liczba populacji:");
		lblLiczbaPopulacji.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		JLabel lblLiczbaIteracji = new JLabel("Liczba iteracji:");
		lblLiczbaIteracji.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		slider_1 = new JSlider(0,10000,5000);
		slider_1.setMajorTickSpacing(5000);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		
		
		JLabel lblMutacje = new JLabel("Mutacje:");
		lblMutacje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMutacje.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		JRadioButton rdbtnMut_1 = new JRadioButton("mut2");
		
		JLabel lblKrzyowania = new JLabel("Krzy\u017Cowania:");
		lblKrzyowania.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		JRadioButton rdbtnKrz = new JRadioButton("krz2");
		
		JRadioButton rdbtnKrz_1 = new JRadioButton("krz1");
		
		JLabel lblReprodukcje = new JLabel("  Reprodukcje:");
		lblReprodukcje.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		JRadioButton rdbtnMut = new JRadioButton("mut1");
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("2500");
		textField.setColumns(1);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("5000");
		textField_1.setColumns(1);
		

		slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText(String.valueOf(slider.getValue()));
            }
            
        });
		slider_1.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                textField_1.setText(String.valueOf(slider_1.getValue()));
            }
            
        });
		
		
		
		JRadioButton rdbtnRep = new JRadioButton("rep1");
		
		JRadioButton rdbtnRep_1 = new JRadioButton("rep2");
		
		JButton btnWykonaj = new JButton("Wykonaj");
        
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(slider_1, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
												.addComponent(slider, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(rdbtnMut, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
														.addComponent(rdbtnKrz_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
													.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(rdbtnKrz, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
														.addComponent(rdbtnMut_1)))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(rdbtnRep)
													.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
													.addComponent(rdbtnRep_1)
													.addGap(10)))
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(79)
											.addComponent(lblMutacje)))
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLiczbaIteracji)
									.addGap(69))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addGap(82))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLiczbaPopulacji)
									.addGap(61))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addGap(80))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(82)
							.addComponent(lblKrzyowania, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(78)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnWykonaj)
								.addComponent(lblReprodukcje))))
					.addGap(32))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(lblLiczbaPopulacji, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLiczbaIteracji, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMutacje)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnMut)
								.addComponent(rdbtnMut_1))
							.addGap(13)
							.addComponent(lblKrzyowania, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnKrz_1)
								.addComponent(rdbtnKrz))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblReprodukcje, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnRep)
								.addComponent(rdbtnRep_1))
							.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
							.addComponent(btnWykonaj)))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}
	
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		System.out.println("PAINT");
//		g.setColor(Color.red);
//		g.fillRect(40, 40, 120, 120);
//		
//	}
	public void paint(Graphics g){
		super.paint(g);
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
	}
	
}
