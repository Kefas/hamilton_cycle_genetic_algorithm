package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
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

import myPkg.AdaptationValues;
import myPkg.MainLoop;
import myPkg.MyGraph;
import myPkg.ParametersOfEvolution;

public class GenPanel extends JPanel {

	List<Pair<Integer, Integer>> list;
	int path[];

	private JTextField textField;
	private JTextField textField_1;
	private ChangeListener listener;
	private JSlider slider, slider_1, slider_2;
	private boolean roundGraph;
	private static final int SIZE = 256;
	private int a = SIZE / 2;
	private int b = a;
	private int r = 4 * SIZE / 5;
	private int n;
	private JPanel panel;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public GenPanel() {

		list = new ArrayList<>();
		roundGraph = false;

		panel = new JPanel();

		panel.setBackground(Color.WHITE);

		// liczba populacji (napis, slider i textField)
		JLabel lblLiczbaPopulacji = new JLabel("Liczba populacji:");
		lblLiczbaPopulacji.setFont(new Font("Tahoma", Font.BOLD, 9));

		slider = new JSlider(100, 5000, 1000);
		slider.setMajorTickSpacing(1400);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("1000");
		textField.setColumns(1);

		// liczba iteracji (napis, slider i textField)
		JLabel lblLiczbaIteracji = new JLabel("Liczba iteracji:");
		lblLiczbaIteracji.setFont(new Font("Tahoma", Font.BOLD, 9));

		slider_1 = new JSlider(1000, 20000, 10000);
		slider_1.setMajorTickSpacing(4000);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("10000");
		textField_1.setColumns(1);

		// liczba wierzcholkow (napis, slider i textField)
		JLabel lblLiczbaWierzchokw = new JLabel(
				"Liczba wierzcho\u0142k\u00F3w:");
		lblLiczbaWierzchokw.setFont(new Font("Tahoma", Font.BOLD, 9));

		slider_2 = new JSlider(100, 100000, 50000);
		slider_2.setMajorTickSpacing(49900);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_2.setText("50000");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(1);

		// RadioButtony dla mutacji, krzy�owa� i reprodukcji
		JLabel lblMutacje = new JLabel("Mutacje:");
		lblMutacje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMutacje.setFont(new Font("Tahoma", Font.BOLD, 9));

		ButtonGroup bg1 = new ButtonGroup();

		final JRadioButton rdbtnMut = new JRadioButton("mut1");
		rdbtnMut.setSelected(true);
		JRadioButton rdbtnMut_1 = new JRadioButton("mut2");
		bg1.add(rdbtnMut);
		bg1.add(rdbtnMut_1);

		JLabel lblKrzyowania = new JLabel("Krzy\u017Cowania:");
		lblKrzyowania.setHorizontalAlignment(SwingConstants.CENTER);
		lblKrzyowania.setFont(new Font("Tahoma", Font.BOLD, 9));

		ButtonGroup bg2 = new ButtonGroup();
		JRadioButton rdbtnKrz_1 = new JRadioButton("krz1");
		final JRadioButton rdbtnKrz = new JRadioButton("krz2");
		rdbtnKrz_1.setSelected(true);
		

		bg2.add(rdbtnKrz);
		bg2.add(rdbtnKrz_1);

		JLabel lblReprodukcje = new JLabel("  Reprodukcje:");
		lblReprodukcje.setHorizontalAlignment(SwingConstants.CENTER);
		lblReprodukcje.setFont(new Font("Tahoma", Font.BOLD, 9));

		ButtonGroup bg3 = new ButtonGroup();
		final JRadioButton rdbtnRep = new JRadioButton("rep1");
		rdbtnRep.setSelected(true);

		JRadioButton rdbtnRep_1 = new JRadioButton("rep2");
		bg3.add(rdbtnRep);
		bg3.add(rdbtnRep_1);

		// ChangeListener dla sliderow

		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textField.setText(String.valueOf(slider.getValue()));
			}

		});
		slider_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textField_1.setText(String.valueOf(slider_1.getValue()));
			}

		});
		
		slider_2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textField_2.setText(String.valueOf(slider_2.getValue()));
			}

		});

		
		JButton btnWykonaj = new JButton("Wykonaj");
		btnWykonaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				parameters settings
				ParametersOfEvolution params = new ParametersOfEvolution();
				params.setSizeOfPopulation(Integer.parseInt(textField.getText()));
				params.setNumberOfIterations(Integer.parseInt(textField_1.getText()));
								
				if(rdbtnMut.isSelected())
					params.setMethodOfMutation(2);
				else
					params.setMethodOfMutation(1);
				
				if( rdbtnKrz.isSelected())
					params.setMethodOfCrossing(2);
				else
					params.setMethodOfCrossing(1);
				
				if( rdbtnRep.isSelected())
					params.setMethodOfBreeding(2);
				else
					params.setMethodOfBreeding(1);
				
//				random graph creation
//				zakomentuj ta linijke to od razu zobaczysz ze pojdzie
//				pewnie trzeba bedzie wygenerowac graf predzej i wczytac go z pliku
				MyGraph graph = new MyGraph(Integer.parseInt(textField_2.getText()));

				AdaptationValues appraisal = new AdaptationValues();
				
				System.out.println("Uruchamiam algorytm z paramterami:");
				System.out.println("Liczba populacji:" + textField.getText());
				System.out.println("Liczba iteracji:" + textField_1.getText());
				System.out.println("Liczba wierzołków:" + textField_2.getText());
				
				MainLoop algorithm = new MainLoop(graph, params, appraisal);
				algorithm.mainFunction();
				algorithm.start();
				
			}
		});
		
		JButton btnWczytajZPliku = new JButton("Wczytaj z pliku");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addGroup(groupLayout.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(slider_1, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
														.addGroup(groupLayout.createSequentialGroup()
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(rdbtnMut, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
																.addComponent(rdbtnKrz_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
															.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(rdbtnKrz, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
																.addComponent(rdbtnMut_1)))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(rdbtnRep)
															.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
															.addComponent(rdbtnRep_1)
															.addGap(10))
														.addComponent(slider_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
														.addComponent(slider, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
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
									.addComponent(btnWykonaj)))
							.addGap(32))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblReprodukcje)
							.addGap(94))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblLiczbaWierzchokw)
							.addGap(78))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(112))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnWczytajZPliku)
							.addGap(80))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
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
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblLiczbaWierzchokw)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(btnWczytajZPliku)
							.addPreferredGap(ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
							.addComponent(btnWykonaj)))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

	public void paint(Graphics g) {
		revalidate();
		repaint();

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

		if (roundGraph == true) {

			for (int i = 0; i < list.size(); i++) {

				g.setColor(Color.white);
				g.fillRect(list.get(i).getX(), list.get(i).getY(), 15, 15);

				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);

				a = getWidth() / 2;
				b = getHeight() / 2;
				int m = Math.min(a, b);
				r = 4 * m / 5;
				int r2 = Math.abs(m - r) / 2;

				double t = 2 * Math.PI * i / list.size();
				int x = (int) Math.round(a + r * Math.cos(t));
				int y = (int) Math.round(b + r * Math.sin(t));
				g2d.setColor(Color.red);
				g2d.fillOval(x - r2 - 100, y - r2, 10, 10);

			}

			// roundGraph = false;
		}
	}
}
