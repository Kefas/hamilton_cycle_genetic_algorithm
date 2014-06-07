package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import myJgrapht.Hamilton;
import myPkg.AdaptationValues;
import myPkg.ExportImport;
import myPkg.MainLoop;
import myPkg.MyGraph;
import myPkg.ParametersOfEvolution;

import java.awt.FlowLayout;

public class DrawPanel extends JPanel {
	List<Pair<Integer, Integer>> list;
	int path[];

	private JTextField textField;
	private JTextField textField_1;
	private ChangeListener listener;
	private JSlider slider, slider_1, slider_2;
	private boolean roundGraph, addVertex;
	private static final int SIZE = 256;
	private int a = SIZE / 2;
	private int b = a;
	private int r = 4 * SIZE / 5;
	private int n;
	private JPanel panel;
	private JTextField textField_2;
	
	private MyGraph graph;

	/**
	 * Create the panel.
	 */
	public DrawPanel() {
		
		list = new ArrayList<>();
		roundGraph = false;
		addVertex = true;
		panel = new JPanel();
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(addVertex){
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
	
	//liczba populacji (napis, slider i textField
		JLabel lblLiczbaPopulacji = new JLabel("Liczba populacji:");
		lblLiczbaPopulacji.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		slider = new JSlider(100,5000,1000);
		slider.setMajorTickSpacing(1400);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("1000");
		textField.setColumns(1);
		
	//liczba iteracji (napis, slider i textField
		JLabel lblLiczbaIteracji = new JLabel("Liczba iteracji:");
		lblLiczbaIteracji.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		slider_1 = new JSlider(1000,20000,10000);
		slider_1.setMajorTickSpacing(4000);
		slider_1.setPaintTicks(true);
		slider_1.setPaintLabels(true);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText("10000");
		textField_1.setColumns(1);
		
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
		rdbtnKrz_1.setSelected(true);
		final JRadioButton rdbtnKrz = new JRadioButton("krz2");
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
		
		JPanel panel_2 = new JPanel();
		
		JLabel lblNewLabel = new JLabel("Metoda ko\u0144czenia:\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_3 = new JPanel();
		
		JLabel lblLiczbaWierzchokw = new JLabel("Liczba wierzcho\u0142k\u00F3w:");
		lblLiczbaWierzchokw.setHorizontalAlignment(SwingConstants.CENTER);
		lblLiczbaWierzchokw.setFont(new Font("Tahoma", Font.BOLD, 9));
		
		
		slider_2 = new JSlider(100,10000,1000);
		slider_2.setMajorTickSpacing(4900);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setBounds(70, 11, 45, 20);
		textField_2.setText("1000");
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setColumns(1);
		panel_4.add(textField_2);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		ButtonGroup bg4 = new ButtonGroup();
		JRadioButton rdbtnZak = new JRadioButton("zak1");
		rdbtnZak.setSelected(true);
		bg4.add(rdbtnZak);
		panel_3.add(rdbtnZak);
		
		JRadioButton rdbtnZak_1 = new JRadioButton("zak2");
		bg4.add(rdbtnZak_1);
		panel_3.add(rdbtnZak_1);
		
		JRadioButton rdbtnZak_2 = new JRadioButton("zak3");
		bg4.add(rdbtnZak_2);
		panel_3.add(rdbtnZak_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
	//ChangeListener dla sliderow
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
		slider_2.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                textField_2.setText(String.valueOf(slider_2.getValue()));
            }
            
        });
		
		
		
		JButton btnWykonaj = new JButton("Wykonaj");
		btnWykonaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Wykonaj button");
				if(list.size() <150){
					addVertex = false;
					double adjacencyMatrix[][] = new double[list.size()][list.size()];
					for(int i=0;i<list.size()-1;i++)
						for(int j=i+1;j<list.size();j++)
							adjacencyMatrix[i][j] = distance(list.get(i), list.get(j));				
	//				for(int i=0; i<list.size();i++){
	//					for(int j=0;j<list.size(); j++)
	//						System.out.print(adjacencyMatrix[i][j] + " ");
	//					System.out.println();
	//				}			
					Hamilton hamilton = new Hamilton(adjacencyMatrix);
					List<Integer> result = hamilton.execute();
					System.out.println(result);
					int cycle [] = new int[result.size()];
					for(int i=0;i<result.size();i++)
						cycle[i] = result.get(i);
					
					
					if(cycle.length>17){
						int fixCycle[] = new int[cycle.length];
						for(int i=0;i<cycle.length;i++){
							fixCycle[i] = cycle[i];
							if(cycle[i] == 1){
								System.out.println(Integer.toString((cycle.length-i)/2));
								i++;
								int temp2;
								int w =0;
								for(w=0;w<((cycle.length-i)/2);w++){
	//								cycle[i+w] = cycle[cycle.length-1-w];
	//								cycle[cycle.length-1-w] = temp2;
									fixCycle[i+w]= cycle[cycle.length-1-w];
									fixCycle[fixCycle.length-1-w] = cycle[i+w];
								}
								fixCycle[w] = cycle[w];
								i = cycle.length;
	//							for(int h=0;h<cycle.length;h++)
	//								System.out.print(Integer.toString(fixCycle[h])+ ", ");
								if(fixCycle.length > 10){
									int temp;
									int z=0;
									int j=0;
									for(int m=0;m<fixCycle.length;m++){
										if(fixCycle[m] == 0)
											z = m;
										if(fixCycle[m] == 1)
											j = m;
									}
									temp = fixCycle[z];
									
									if(j!=fixCycle.length-1){
										fixCycle[z] = fixCycle[j+1];
										fixCycle[j+1] = temp;
									}
									else{
										fixCycle[z] = fixCycle[0];
										fixCycle[0] = temp;
									}
								}
	//							System.out.println();
	//							for(int h=0;h<cycle.length;h++)
	//								System.out.print(Integer.toString(fixCycle[h])+ ", ");
								
								if(cycle.length > 63){
									boolean flaga = false;
									for(int n=0;n<fixCycle.length;n++)
										if(fixCycle[n] == 0)
											if(flaga)
												fixCycle[n] = fixCycle[n-1];
											else
												flaga = true;
								}
								drawPath(fixCycle);
								return;
							}
						}
					}
					if(cycle.length > 9){
						int temp;
						int z=0;
						int j=0;
						for(int i=0;i<cycle.length;i++){
							if(cycle[i] == 0)
								z = i;
							if(cycle[i] == 1)
								j = i;
						}
						temp = cycle[z];
						
						if(j!=cycle.length-1){
							cycle[z] = cycle[j+1];
							cycle[j+1] = temp;
						}
						else{
							cycle[z] = cycle[0];
							cycle[0] = temp;
						}
					}
					
					drawPath(cycle);
				}
			
			
			
			
//			algorithms settings
			
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
			
//			random graph creation
//			zakomentuj ta linijke to od razu zobaczysz ze pojdzie
//			pewnie trzeba bedzie wygenerowac graf predzej i wczytac go z pliku
//			MyGraph graph = new MyGraph(Integer.parseInt(textField_2.getText()));
			System.out.println("Dlugosc listy: " + list.size());
			
			if( list.size() > 0){
				ExportImport.newExportToFile(toIntTable(list), "temp.txt");
				MyGraph graph = new MyGraph("temp.txt");
				
				AdaptationValues appraisal = new AdaptationValues();
				
				System.out.println("Uruchamiam algorytm z paramterami:");
				System.out.println("Liczba populacji:" + textField.getText());
				System.out.println("Liczba iteracji:" + textField_1.getText());
				
				
				
				
				MainLoop algorithm = new MainLoop(graph, params, appraisal);
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
				
				for(int i=0;i<tab.length;i++)
					list.add(new Pair(tab[i][0],tab[i][1]));
				
				repaint();
			}
		});
		
		JButton btnGeneruj = new JButton("Generuj");
		btnGeneruj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graph = new MyGraph(Integer.parseInt(textField_2.getText()),panel.getX()+10, panel.getY()+10, panel.getWidth(), panel.getHeight());
				fromIntTable(graph.getTableOfCoordinates());
			}
		});
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
					.addGap(10)
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
															.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
															.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(rdbtnKrz, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
																.addComponent(rdbtnMut_1)))
														.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 202, Short.MAX_VALUE)
														.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(lblZestawy)
															.addGap(76))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(btnNewButton)
															.addGap(43))
														.addGroup(groupLayout.createSequentialGroup()
															.addComponent(rdbtnRep)
															.addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
															.addComponent(rdbtnRep_1)
															.addGap(10))
														.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
														.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
															.addGroup(groupLayout.createSequentialGroup()
																.addComponent(btnGrafNaKole)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(btnGeneruj, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
															.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
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
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblReprodukcje)
								.addComponent(lblNewLabel)
								.addComponent(lblLiczbaWierzchokw))
							.addGap(94))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
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
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLiczbaWierzchokw)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnGrafNaKole)
								.addComponent(btnGeneruj))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblZestawy)
							.addGap(2)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnWykonaj)))
					.addContainerGap())
		);
		
		
		JButton btnWczytajZPliku = new JButton("Wczytaj z pliku");
		panel_2.add(btnWczytajZPliku);
		btnWczytajZPliku.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JButton btnZapiszDoPliku = new JButton("Zapisz do pliku");
		panel_2.add(btnZapiszDoPliku);
		btnZapiszDoPliku.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnZapiszDoPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path;
				int tab[][] = new int[list.size()][2];
				JFileChooser fileChooser = new JFileChooser();
				int ret = fileChooser.showOpenDialog(null);
				if(ret  == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					//clear();
					
					for(int i=0; i < list.size(); i++){
						tab[i][0] = list.get(i).getX();
						tab[i][1] = list.get(i).getY();
					}
					ExportImport.newExportToFile(tab, path);
					
					repaint();
							
				}
				
			}
		});
		btnWczytajZPliku.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path;
				JFileChooser fileChooser = new JFileChooser();
				int ret = fileChooser.showOpenDialog(null);
				if(ret  == JFileChooser.APPROVE_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					path = selectedFile.getAbsolutePath();
					clear();
					int tab[][] = new ExportImport().newImportFromFile(path);
					
					for(int i=0;i<tab.length;i++)
						list.add(new Pair(tab[i][0],tab[i][1]));
					
					repaint();
							
				}
				
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton button = new JButton("20");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				
				int tab[][] = ExportImport.newImportFromFile("zestaw20.txt");
				
				for(int i=0;i<tab.length;i++)
					list.add(new Pair(tab[i][0],tab[i][1]));
				
				repaint();
			}
		});
		panel_1.add(button);
		
		JButton button_1 = new JButton("50");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			
				int tab[][] = ExportImport.newImportFromFile("zestaw50.txt");
				
				for(int i=0;i<tab.length;i++)
					list.add(new Pair(tab[i][0],tab[i][1]));
				
				repaint();
			}
		});
		panel_1.add(button_1);
		setLayout(groupLayout);

	}

	protected int[][] toIntTable(List<Pair<Integer, Integer>> list2) {
		int tab[][] = new int[list2.size()][2];
		for(int i=0;i<list2.size();i++){
			tab[i][0] = list2.get(i).getX();
			tab[i][1] = list2.get(i).getY();
		}
		return tab;
	}
	
	void fromIntTable(int tab[][]){
		if(list != null)
			list.clear();
		
		for(int i=0;i<tab.length;i++)
			list.add(new Pair(tab[i][0],tab[i][1]));
		
	
	}

	protected double distance(Pair<Integer, Integer> pair,
			Pair<Integer, Integer> pair2) {

		return Math.sqrt(Math.pow(pair.getX() - pair2.getX(), 2)
				+ Math.pow(pair.getY() - pair2.getY(), 2));
	}

	public void paint(Graphics g) {
		revalidate();
		repaint();

		super.paint(g);
		if( list.size()>0 && list.size() < 150){
			for (int i = 0; i < list.size(); i++) {
				g.setColor(Color.red);
				g.fillOval(list.get(i).getX(), list.get(i).getY(), 10, 10);
				g.drawString(Integer.toString(i), list.get(i).getX() + 5,
						list.get(i).getY() - 5);
				// System.out.println(tabX[i]);
			}
	
			if (roundGraph == true) {
				g.setColor(Color.white);
				g.fillRect(panel.getX(), panel.getY(), panel.getWidth(),
						panel.getHeight());
	
				for (int i = 0; i < list.size(); i++) {
	
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
					list.get(i).setX(x - r2 - 100);
					list.get(i).setY(y - r2);
					g.drawString(Integer.toString(i), list.get(i).getX() + 5, list
							.get(i).getY() - 5);
				}
	
				// roundGraph = false;
			}
	
			if (path != null && list.size() == path.length) {
	
				for (int i = 0; i < path.length - 1; i++) {
	
					g.drawLine(list.get(path[i]).getX() + 5, list.get(path[i])
							.getY() + 5, list.get(path[i + 1]).getX() + 5, list
							.get(path[i + 1]).getY() + 5);
				}
	
				g.drawLine(list.get(path[path.length - 1]).getX() + 5,
						list.get(path[path.length - 1]).getY() + 5,
						list.get(path[0]).getX() + 5, list.get(path[0]).getY() + 5);
			}
		}
	}

	public void clear() {
		if (list != null) {
			roundGraph = false;
			list.clear();
			addVertex = true;
			path = null;
		}
	}

	public void drawPath(int tab[]) {
		path = tab;
		// System.out.println(path);
		repaint();
	}
}
