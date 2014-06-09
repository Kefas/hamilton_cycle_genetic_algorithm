package GUI;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class InfoPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public InfoPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("WSKAZ\u00D3WKI DOTYCZ\u0104CE OBS\u0141UGI PROGRAMU");
		lblNewLabel.setBounds(205, 5, 390, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel);
		
		JLabel lblMenurysujGraf = new JLabel("Menu \"Rysuj graf\" umo\u017Cliwia wygenerowanie grafu automatycznie, wczytanie go z pliku oraz ustawienie wierzcho\u0142k\u00F3w grafu r\u0119cznie. \r\n\r\n\r\n");
		lblMenurysujGraf.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMenurysujGraf.setVerticalAlignment(SwingConstants.TOP);
		lblMenurysujGraf.setBounds(10, 49, 780, 20);
		add(lblMenurysujGraf);
		
		JLabel lblUwagaRysowanieGrafu = new JLabel("Uwaga- rysowanie grafu na lewym (bia\u0142ym) panelu jest mo\u017Cliwe jedynie dla grafu o liczbie wierzcho\u0142k\u00F3w < 150.");
		lblUwagaRysowanieGrafu.setForeground(Color.BLACK);
		lblUwagaRysowanieGrafu.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblUwagaRysowanieGrafu.setBounds(10, 68, 735, 14);
		add(lblUwagaRysowanieGrafu);
		
		JLabel lblOpcjePaneluPrawego = new JLabel("OPCJE PANELU PRAWEGO:");
		lblOpcjePaneluPrawego.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblOpcjePaneluPrawego.setBounds(10, 104, 131, 14);
		add(lblOpcjePaneluPrawego);
		
		JLabel lblMoliweJest = new JLabel("- mo\u017Cliwe jest ustawienie utworzonego grafu na kole (button \"Graf na okr\u0119gu\")");
		lblMoliweJest.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMoliweJest.setBounds(10, 124, 377, 14);
		add(lblMoliweJest);
		
		JLabel lblNewLabel_1 = new JLabel("- mo\u017Cliwe jest wygenerowanie grafu o zadanej liczbie wierzcho\u0142k\u00F3w za pomoc\u0105 przycisku \"Generuj\"");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1.setBounds(10, 140, 467, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("- dost\u0119pne s\u0105 trzy pokazowe zestawy danych");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(10, 156, 443, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("- po zako\u0144czeniu dzia\u0142ania algorytmu nale\u017Cy zamkn\u0105\u0107 okno wykresu, po czym mo\u017Cna przeprowadza\u0107 dalsze dzia\u0142ania");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_3.setBounds(10, 172, 621, 14);
		add(lblNewLabel_3);

	}
}
