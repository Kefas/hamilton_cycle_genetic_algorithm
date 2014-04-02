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

import myPkg.MyGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class MyFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static MyFrame frame;
	static MyGraph main;
	JMenuBar menuBar;
	JMenu op1, op2, op3;
	JMenuItem genGraph, about, finish;
	JDesktopPane desktop;
	private GenFrame ramka;
	private static Graph g;
	public MyFrame(){
		main = new MyGraph();
		
		menuBar = new JMenuBar();
		op1 = new JMenu("Opcja1");
		op2 = new JMenu("Opcja2");
		op3 = new JMenu("Pomoc");
		
		genGraph = new JMenuItem("Generuj graf");
		about = new JMenuItem("O programie");
		finish = new JMenuItem("Zako�cz");
		
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
			ramka = new GenFrame();
			ramka.setVisible(true);
		}
			
	}

	static void setGraph(int v){
		main.makeGraph(v);
		main.printGraph(main.getGraph());
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	public static Graph paintGraph() {
	    Graph<Integer, String> g = new UndirectedSparseMultigraph<Integer, String>();

	    for(int i=0; i< main.getGraph().size()-1;i++){
	    	g.addVertex((Integer)i);
	    	for(int j=0; j< main.getGraph().get(i).getList().size(); j++){
	    		g.addEdge("Edge-"+i+"-"+j, i, main.getGraph().get(i).getList().get(j).getL());
	    	}
	    }
	    return g;
	  }
	
	static void paintG(){
		g = paintGraph();
	    VisualizationViewer<Integer,String> vv = 
	     new VisualizationViewer<Integer,String>(new FRLayout(g),
	    		 new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-100,Toolkit.getDefaultToolkit().getScreenSize().height-100));
	    frame.getContentPane().add(vv);
	    
	    
	    //"interakcja" grafu z myszk� (oddalanie, przesuwanie grafu)
	    DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
	    gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	    vv.setGraphMouse(gm);
	 
	}
	
	
}
