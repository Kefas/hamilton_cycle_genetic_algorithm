package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.text.G2TextMeasurer;

import myJgrapht.Hamilton;
import myPkg.AdaptationValues;
import myPkg.ExportImport;
import myPkg.MainLoop;
import myPkg.MyGraph;
import myPkg.ParametersOfEvolution;

import java.awt.FlowLayout;
import javax.swing.JSeparator;

public class DrawPanel extends JPanel {
//	sleep in Miliseconds
	public static int SLEEP =  200;
	
	List<Pair<Integer, Integer>> list;	
	int path[] = null;
	int krisssPath[] = null;
	private AdaptationValues appraisal = null;
	
	class MyTimerTask extends TimerTask{

		@Override
		public void run() {
			updateKrisssCycle();
		}
	}
	private Timer timer;
	private MyTimerTask timerTask;
	private JTextField textField;
	private ChangeListener listener;
	private JSlider slider, slider_2;
	private boolean roundGraph, addVertex;
	private static final int SIZE = 256;
	private int a = SIZE / 2;
	private int b = a;
	private int r = 4 * SIZE / 5;
	private int n;
	private JPanel panel;
	private JTextField textField_2;

	private MyGraph graph;
	protected MainLoop algorithm;

	private boolean flag;

	private int krisssCycleLength;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	protected long hamiltonCycleLength;

	/**
	 * Create the panel.
	 */
	public DrawPanel() {

		list = new ArrayList<>();
		roundGraph = false;
		addVertex = true;
		panel = new JPanel();
		timerTask = new MyTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, 0, SLEEP);
		
		

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (addVertex) {
					int x = e.getX();
					int y = e.getY();
					list.add(new Pair<Integer, Integer>(x, y));
					System.out.println("Dzieje sie");
					// revalidate();
					repaint();
				}
			}
		});
		panel.setBackground(Color.WHITE);

		// liczba populacji (napis, slider i textField
		JLabel lblLiczbaPopulacji = new JLabel("Liczba populacji:");
		lblLiczbaPopulacji.setFont(new Font("Tahoma", Font.BOLD, 9));

		slider = new JSlider(100, 3500, 1000);
		slider.setMajorTickSpacing(1400);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("1000");
		textField.setColumns(1);

		ButtonGroup bg1 = new ButtonGroup();

		ButtonGroup bg2 = new ButtonGroup();

		ButtonGroup bg3 = new ButtonGroup();
		
		JPanel panel_2 = new JPanel();

		JPanel panel_3 = new JPanel();

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		
		JPanel panel_6 = new JPanel();
		
		JPanel panel_7 = new JPanel();
		
		JLabel lblReprodukcje = new JLabel("Sukcesja:");
		lblReprodukcje.setBounds(80, 0, 47, 11);
		panel_7.add(lblReprodukcje);
		lblReprodukcje.setHorizontalAlignment(SwingConstants.CENTER);
		lblReprodukcje.setFont(new Font("Tahoma", Font.BOLD, 9));
		final JRadioButton rdbtnRep = new JRadioButton("z po�owicznym zast�powaniem");
		rdbtnRep.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnRep.setBounds(20, 7, 171, 23);
		panel_7.add(rdbtnRep);
		rdbtnRep.setSelected(true);
		bg3.add(rdbtnRep);
		JRadioButton rdbtnRep_1 = new JRadioButton("z ca�kowitym zast�powaniem");
		rdbtnRep_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnRep_1.setBounds(20, 28, 163, 23);
		panel_7.add(rdbtnRep_1);
		bg3.add(rdbtnRep_1);
		panel_6.setLayout(null);

		JLabel lblKrzyowania = new JLabel("Krzy\u017Cowania:");
		lblKrzyowania.setBounds(70, 0, 66, 11);
		panel_6.add(lblKrzyowania);
		lblKrzyowania.setHorizontalAlignment(SwingConstants.CENTER);
		lblKrzyowania.setFont(new Font("Tahoma", Font.BOLD, 9));
		JRadioButton rdbtnKrz_1 = new JRadioButton("jedno punktowe");
		rdbtnKrz_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnKrz_1.setBounds(6, 13, 103, 23);
		panel_6.add(rdbtnKrz_1);
		rdbtnKrz_1.setSelected(true);
		bg2.add(rdbtnKrz_1);
		final JRadioButton rdbtnKrz = new JRadioButton("dwu punktowe");
		rdbtnKrz.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnKrz.setBounds(111, 13, 97, 23);
		panel_6.add(rdbtnKrz);
		bg2.add(rdbtnKrz);
		panel_5.setLayout(null);

		// RadioButtony dla mutacji, krzy�owa� i reprodukcji
		JLabel lblMutacje = new JLabel(" Mutacje:    ");
		lblMutacje.setBounds(77, 5, 58, 11);
		panel_5.add(lblMutacje);
		lblMutacje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMutacje.setFont(new Font("Tahoma", Font.BOLD, 9));
		final JRadioButton rdbtnMut = new JRadioButton("wierzcho�kowa");
		rdbtnMut.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnMut.setBounds(0, 23, 93, 23);
		panel_5.add(rdbtnMut);
		rdbtnMut.setSelected(true);
		bg1.add(rdbtnMut);
		JRadioButton rdbtnMut_1 = new JRadioButton("porz�dkowa");
		rdbtnMut_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnMut_1.setBounds(113, 23, 81, 23);
		panel_5.add(rdbtnMut_1);
		bg1.add(rdbtnMut_1);

		

		textField_2 = new JTextField();
		textField_2.setBounds(78, 22, 45, 20);
		textField_2.setText("1000");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(1);
		panel_4.add(textField_2);

		ButtonGroup bg4 = new ButtonGroup();
				panel_3.setLayout(null);
		
				JLabel lblNewLabel = new JLabel("Metoda ko\u0144czenia:\r\n");
				lblNewLabel.setBounds(59, 0, 92, 11);
				panel_3.add(lblNewLabel);
				lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 9));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		final JRadioButton rdbtnZak = new JRadioButton("limit iteracji");
		rdbtnZak.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnZak.setBounds(6, 14, 80, 23);
		rdbtnZak.setSelected(true);
		bg4.add(rdbtnZak);
		panel_3.add(rdbtnZak);

		final JRadioButton rdbtnZak_1 = new JRadioButton("osi\u0105gni\u0119cie wyp\u0142aszczenia\r\n");
		rdbtnZak_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnZak_1.setBounds(6, 40, 139, 23);
		bg4.add(rdbtnZak_1);
		panel_3.add(rdbtnZak_1);

		final JRadioButton rdbtnZak_2 = new JRadioButton("<html>podobie\u0144stwo do <br /> 'poprawnego rozwi\u0105zania'</html>");
		rdbtnZak_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		rdbtnZak_2.setBounds(6, 85, 145, 35);
		bg4.add(rdbtnZak_2);
		panel_3.add(rdbtnZak_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// ChangeListener dla sliderow
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textField.setText(String.valueOf(slider.getValue()));
			}

		});

		JButton btnWykonaj = new JButton("Start");
		btnWykonaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

//				System.out.println("Wykonaj button size:" + list.size());
				
				if (list.size() < 150) {
					addVertex = false;
					double adjacencyMatrix[][] = new double[list.size()][list
							.size()];
					for (int i = 0; i < list.size() - 1; i++)
						for (int j = i + 1; j < list.size(); j++)
							adjacencyMatrix[i][j] = distance(list.get(i),
									list.get(j));
	
					Hamilton hamilton = new Hamilton(adjacencyMatrix);
					List<Integer> result = hamilton.execute();
//					System.out.println(result);
					hamiltonCycleLength = 0L;
					int cycle[] = new int[result.size()];
					for (int i = 0; i < result.size(); i++){
						if(i != result.size()-1)
							hamiltonCycleLength += distance(list.get(result.get(i)), list.get(result.get(i+1)));
						cycle[i] = result.get(i);
					}
					hamiltonCycleLength += distance(list.get(result.get(0)), list.get(result.get(result.size()-1)));
					System.out.println("Cykl wyliczony z algotymu z biblioteki: " + hamiltonCycleLength);
					drawPath(cycle);
				}

				// algorithms settings

				ParametersOfEvolution params = new ParametersOfEvolution();
				params.setSizeOfPopulation(Integer.parseInt(textField.getText()));
				params.setNumberOfIterations(Integer.parseInt(textField_1
						.getText()));
				params.setMaxAmountOfAncestors(Integer.parseInt(textField_5.getText()));
				params.setSimilarityToAncestors(Double.parseDouble(textField_3.getText()));
				params.setSimiliarityInTest(Double.parseDouble(textField_4.getText()));
				params.setCorrectResult(hamiltonCycleLength);
				
				if (rdbtnMut.isSelected())
					params.setMethodOfMutation(2);
				else
					params.setMethodOfMutation(1);

				if (rdbtnKrz.isSelected())
					params.setMethodOfCrossing(2);
				else
					params.setMethodOfCrossing(1);

				if (rdbtnRep.isSelected())
					params.setMethodOfBreeding(2);
				else
					params.setMethodOfBreeding(1);
				
				if(rdbtnZak.isSelected())
					params.setMethodToFinish(1);
				if(rdbtnZak_1.isSelected())
					params.setMethodToFinish(2);
				if(rdbtnZak_2.isSelected())
					params.setMethodToFinish(3);


				if (list.size() > 0) {
					ExportImport.newExportToFile(toIntTable(list), "temp.txt");
					MyGraph graph = new MyGraph("temp.txt");

					appraisal = new AdaptationValues();

//					System.out.println("Uruchamiam algorytm z paramterami:");
//					System.out.println("Liczba populacji:"
//							+ textField.getText());
//					System.out.println("Liczba iteracji:"
//							+ textField_1.getText());

					algorithm = new MainLoop(graph, params, appraisal);
					algorithm.mainFunction();
					algorithm.start();
					
					
					
				}	
			}
		});

		JButton btnGrafNaKole = new JButton("Graf na kole");
		btnGrafNaKole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				roundGraph = !roundGraph;
			}
		});

		JPanel panel_1 = new JPanel();

		JLabel lblZestawy = new JLabel("Zestawy ");
		lblZestawy.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblZestawy.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnNewButton = new JButton("20 rozproszony");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();

				int tab[][] = ExportImport.newImportFromFile("zestaw20_p.txt");

				for (int i = 0; i < tab.length; i++)
					list.add(new Pair(tab[i][0], tab[i][1]));

				repaint();
			}
		});

		JButton btnGeneruj = new JButton("Generuj");
		btnGeneruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
				graph = new MyGraph(Integer.parseInt(textField_2.getText()),
						panel.getX() + 10, panel.getY() + 10, panel.getWidth(),
						panel.getHeight());
				fromIntTable(graph.getTableOfCoordinates());
			}
		});

		JButton btnWyczy = new JButton("Reset");
		btnWyczy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				roundGraph = false;
				addVertex = true;
				if (list != null)
					list.clear();
			}
		});
		
		
		
				JButton btnWczytajZPliku = new JButton("Wczytaj z pliku");
				btnWczytajZPliku.setFont(new Font("Tahoma", Font.PLAIN, 9));
				btnWczytajZPliku.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String path;
						JFileChooser fileChooser = new JFileChooser();
						int ret = fileChooser.showOpenDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							path = selectedFile.getAbsolutePath();
							clear();
							int tab[][] = new ExportImport().newImportFromFile(path);

							for (int i = 0; i < tab.length; i++)
								list.add(new Pair(tab[i][0], tab[i][1]));

							repaint();

						}

					}
				});
		
				JButton btnZapiszDoPliku = new JButton("Zapisz do pliku");
				btnZapiszDoPliku.setFont(new Font("Tahoma", Font.PLAIN, 9));
				btnZapiszDoPliku.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String path;
						int tab[][] = new int[list.size()][2];
						JFileChooser fileChooser = new JFileChooser();
						int ret = fileChooser.showOpenDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) {
							File selectedFile = fileChooser.getSelectedFile();
							path = selectedFile.getAbsolutePath();
							// clear();

							for (int i = 0; i < list.size(); i++) {
								tab[i][0] = list.get(i).getX();
								tab[i][1] = list.get(i).getY();
							}
							ExportImport.newExportToFile(tab, path);

							repaint();

						}

					}
				});
		
		JSeparator separator = new JSeparator();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(116)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 692, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblLiczbaPopulacji)
											.addGap(61))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
											.addGap(80))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(panel_5, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
											.addComponent(slider, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(panel_6, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
											.addComponent(panel_7, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 197, Short.MAX_VALUE)
											.addComponent(btnWczytajZPliku)
											.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
											.addComponent(panel_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
											.addComponent(panel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnGrafNaKole)
												.addGap(18)
												.addComponent(btnGeneruj, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))))))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(604)
							.addComponent(btnWyczy)
							.addGap(45)
							.addComponent(btnWykonaj)
							.addGap(42))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnZapiszDoPliku)
							.addGap(23))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addGap(71))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblZestawy)
							.addGap(107))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblLiczbaPopulacji, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnGrafNaKole)
								.addComponent(btnGeneruj))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblZestawy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)
							.addGap(44)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnZapiszDoPliku)
								.addComponent(btnWczytajZPliku))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnWykonaj)
								.addComponent(btnWyczy)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 737, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setHonorsVisibility(false);
		panel.setLayout(null);
		
		textField_1 = new JTextField("10000");
		textField_1.setBounds(157, 15, 45, 20);
		panel_3.add(textField_1);
		textField_1.setColumns(10);
		
		textField_3 = new JTextField("0.98");
		textField_3.setBounds(157, 41, 45, 20);
		panel_3.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField("0.98");
		textField_4.setBounds(157, 91, 45, 20);
		panel_3.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblIloRozwaanychPrzodkw = new JLabel("Ilo\u015B\u0107 rozwa\u017Canych przodk\u00F3w:");
		lblIloRozwaanychPrzodkw.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblIloRozwaanychPrzodkw.setBounds(10, 70, 141, 14);
		panel_3.add(lblIloRozwaanychPrzodkw);
		
		textField_5 = new JTextField("1000");
		textField_5.setBounds(157, 67, 45, 20);
		panel_3.add(textField_5);
		textField_5.setColumns(10);
		
				JLabel lblLiczbaWierzchokw = new JLabel(
						"Liczba wierzcho\u0142k\u00F3w:");
				lblLiczbaWierzchokw.setBounds(58, 0, 104, 11);
				panel_4.add(lblLiczbaWierzchokw);
				lblLiczbaWierzchokw.setHorizontalAlignment(SwingConstants.CENTER);
				lblLiczbaWierzchokw.setFont(new Font("Tahoma", Font.BOLD, 9));
				
						slider_2 = new JSlider(100, 10000, 1000);
						slider_2.setBounds(0, 47, 200, 45);
						panel_4.add(slider_2);
						slider_2.setMajorTickSpacing(4900);
						slider_2.setPaintTicks(true);
						slider_2.setPaintLabels(true);
						slider_2.addChangeListener(new ChangeListener() {
							@Override
							public void stateChanged(ChangeEvent e) {
								textField_2.setText(String.valueOf(slider_2.getValue()));
							}

						});
		panel_7.setLayout(null);
		
				

		JButton button = new JButton("20");
		button.setBounds(31, 5, 61, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();

				int tab[][] = ExportImport.newImportFromFile("zestaw20.txt");

				for (int i = 0; i < tab.length; i++)
					list.add(new Pair(tab[i][0], tab[i][1]));

				repaint();
			}
		});
		panel_1.setLayout(null);
		panel_1.add(button);

		JButton button_1 = new JButton("50");
		button_1.setBounds(130, 5, 61, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();

				int tab[][] = ExportImport.newImportFromFile("zestaw50.txt");

				for (int i = 0; i < tab.length; i++)
					list.add(new Pair(tab[i][0], tab[i][1]));

				repaint();
			}
		});
		panel_1.add(button_1);
		setLayout(groupLayout);

	}

	public void updateKrisssCycle() {
		
		if( algorithm != null && !algorithm.isExitPressed()){
			krisssPath = appraisal.getTheBestCycle();
			krisssCycleLength = 0;
			repaint();
			if(krisssPath != null){
				for(int i=0; i<krisssPath.length-1;i++)
					krisssCycleLength += distance(list.get(krisssPath[i]), list.get(krisssPath[i+1]));
				krisssCycleLength += distance(list.get(krisssPath[0]), list.get(krisssPath[krisssPath.length-1]));
				flag = true;
			}
		}
		else if(flag){
			System.out.println("Dlugosc cyklu krissa: " + krisssCycleLength);
			flag = false;
		}
		
	}

	protected int[][] toIntTable(List<Pair<Integer, Integer>> list2) {
		int tab[][] = new int[list2.size()][2];
		for (int i = 0; i < list2.size(); i++) {
			tab[i][0] = list2.get(i).getX();
			tab[i][1] = list2.get(i).getY();
		}
		return tab;
	}

	void fromIntTable(int tab[][]) {
		if (list != null)
			list.clear();

		for (int i = 0; i < tab.length; i++)
			list.add(new Pair(tab[i][0], tab[i][1]));

	}

	protected double distance(Pair<Integer, Integer> pair,
			Pair<Integer, Integer> pair2) {

		return Math.sqrt(Math.pow(pair.getX() - pair2.getX(), 2)
				+ Math.pow(pair.getY() - pair2.getY(), 2));
	}

	public void paint(Graphics g) {
		revalidate();
		repaint();
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		if (list.size() > 0 && list.size() < 150) {
			for (int i = 0; i < list.size(); i++) {
				g.setColor(Color.red);
				g.fillOval(list.get(i).getX(), list.get(i).getY(), 10, 10);
				g.drawString(Integer.toString(i), list.get(i).getX() + 5, list
						.get(i).getY() - 5);
				// System.out.println(tabX[i]);
			}

			if (roundGraph == true) {
				g.setColor(Color.white);
				g.fillRect(panel.getX(), panel.getY(), panel.getWidth(),
						panel.getHeight());
				
				for (int i = 0; i < list.size(); i++) {

					
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
					list.get(i).setX(x - r2 - 100);
					list.get(i).setY(y - r2);
					g.drawString(Integer.toString(i), list.get(i).getX() + 5,
							list.get(i).getY() - 5);
				}

				// roundGraph = false;
			}
			
			if (krisssPath != null && list.size() == krisssPath.length) {
				
				g.setColor(Color.GREEN);
				g2d.setStroke(new BasicStroke(2));
				
				for (int i = 0; i < krisssPath.length - 1; i++) {

					g2d.drawLine(list.get(krisssPath[i]).getX() + 5,
							list.get(krisssPath[i]).getY() + 5,
							list.get(krisssPath[i + 1]).getX() + 5,
							list.get(krisssPath[i + 1]).getY() + 5);
				}

				g.drawLine(
						list.get(krisssPath[krisssPath.length - 1]).getX() + 5,
						list.get(krisssPath[krisssPath.length - 1]).getY() + 5,
						list.get(krisssPath[0]).getX() + 5, list.get(krisssPath[0])
								.getY() + 5);
			}

			if (path != null && list.size() == path.length) {
				g.setColor(Color.RED);
				g2d.setStroke(new BasicStroke(1));
				for (int i = 0; i < path.length - 1; i++) {

					g.drawLine(list.get(path[i]).getX() + 5, list.get(path[i])
							.getY() + 5, list.get(path[i + 1]).getX() + 5, list
							.get(path[i + 1]).getY() + 5);
				}

				g.drawLine(list.get(path[path.length - 1]).getX() + 5, list
						.get(path[path.length - 1]).getY() + 5,
						list.get(path[0]).getX() + 5,
						list.get(path[0]).getY() + 5);
			}

		
		}
	}

	public void clear() {
		if (list != null) {
			roundGraph = false;
			list.clear();
			addVertex = true;
			path = null;
			krisssPath = null;
		}
	}

	public void drawPath(int tab[]) {
		path = tab;
		repaint();
	}
}
