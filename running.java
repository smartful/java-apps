package running;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;

public class Fenetre extends JFrame{

	private JPanel panVitesse = new JPanel();
	private JLabel labDistance = new JLabel("Distance (en m)");
	private JLabel labVitesse = new JLabel("Vitesse (en km/h)");
	private JLabel labTemps = new JLabel("Temps ");
	private JLabel labHeure = new JLabel("h");
	private JLabel labMinute = new JLabel("min");
	private JLabel labSeconde = new JLabel("s");
	private JLabel labResultVitesse = new JLabel("");
	private JLabel labError = new JLabel("");
	private JButton raz = new JButton("RAZ");
	private SpinnerNumberModel modelHeure = new SpinnerNumberModel(0, //Valeur initiale
															0, //min
															99, //max
															1); //step
	private SpinnerNumberModel modelMin = new SpinnerNumberModel(0, //Valeur initiale
															0, //min
															59, //max
															1); //step
	private SpinnerNumberModel modelSec = new SpinnerNumberModel(0, //Valeur initiale
															0, //min
															59, //max
															1); //step
	private JSpinner spinHeure = new JSpinner(modelHeure);
	private JSpinner spinMin = new JSpinner(modelMin);
	private JSpinner spinSeconde = new JSpinner(modelSec);
	private JTextField textDistance = new JTextField();
	
	public Fenetre() {
		this.setTitle("Running");
		this.setSize(400, 300);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("icon_smartful.png").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//Mise à zéro
		raz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				textDistance.setText("");
				spinHeure.setValue(0);
				spinMin.setValue(0);
				spinSeconde.setValue(0);
				labError.setText("");
				labResultVitesse.setText("");
			}
		});
		
		//Mise en forme de la fenêtre de calcul de la vitesse
		fenVitesse();
		
		this.getContentPane().add(panVitesse, BorderLayout.CENTER);
		this.getContentPane().add(raz, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public void fenVitesse(){
		JPanel panChoixTemps = new JPanel();
		JPanel panChoixDistance = new JPanel();
		JPanel panResultVitesse = new JPanel();
		
		//Mise en forme de l'entrée de la distance
		labDistance.setHorizontalAlignment(JLabel.CENTER);
		textDistance.setPreferredSize(new Dimension(150,30));
		JPanel panTextDistance = new JPanel();
		panTextDistance.add(textDistance);
		panChoixDistance.setLayout(new GridLayout(1,2,5,5));
		panChoixDistance.add(labDistance);
		panChoixDistance.add(panTextDistance);
		
		//Mise en forme de l'entrée du temps
		panChoixTemps.setLayout(new GridLayout(1,7,5,5));
		panChoixTemps.add(labTemps);
		panChoixTemps.add(spinHeure);
		panChoixTemps.add(labHeure);
		panChoixTemps.add(spinMin);
		panChoixTemps.add(labMinute);
		panChoixTemps.add(spinSeconde);
		panChoixTemps.add(labSeconde);
		
		//Création du bouton de calcul
		JPanel panCalcul = new JPanel();
		JButton calculVitesseBouton = new JButton("Calcul");
		calculVitesseBouton.setPreferredSize(new Dimension(200,30));
		calculVitesseBouton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String str = "";
				str = calculVitesse(textDistance.getText(),(int)spinHeure.getValue(),
						(int)spinMin.getValue(),(int)spinSeconde.getValue());
				labResultVitesse.setText(str);
			}
		});
		panCalcul.add(calculVitesseBouton);
		
		//Affichage du résultat
		labVitesse.setHorizontalAlignment(JLabel.CENTER);
		Font police = new Font("Arial",Font.TYPE1_FONT,12);
		labVitesse.setFont(police);
		panResultVitesse.setLayout(new GridLayout(1,2,5,5));
		panResultVitesse.add(labVitesse);
		panResultVitesse.add(labResultVitesse);
		
		//Affichage d'une erreur
		labError.setHorizontalAlignment(JLabel.CENTER);
		Font policeError = new Font("Arial", Font.TYPE1_FONT, 14);
		labError.setFont(policeError);
		labError.setForeground(Color.red);
		
		//Aggrégation des différents Panels dans le Panel panVitesse
		panVitesse.setLayout(new GridLayout(5,1,10,10));
		panVitesse.add(panChoixDistance);
		panVitesse.add(panChoixTemps);
		panVitesse.add(panCalcul);
		panVitesse.add(labError);
		panVitesse.add(panResultVitesse);
	}
	
	public String calculVitesse(String distance, int heure, int min, int sec){
		String strVitesse = " ";
		int dis =0, temps = 0;
		double vit = 0;
		
		//Vérification des données d'entrées et conversion de la distance en int 
		if(distance.isEmpty()) distance = "0";
		
		try{
			dis = Integer.valueOf(distance).intValue();
		} catch(NumberFormatException e){
			labError.setText("Erreur dans la saisie");
		}
		
		//Calcul
		temps = heure*3600 + min*60 + sec; //On commence par traduire le temps en seconde
		vit = (double)dis / (double)temps; // On a la vitesse en mêtre par seconde
		vit = 3.6 * vit; // On a la vitesse en km par heure
		
		strVitesse = " " +  arrondi(vit,2) ;
		
		return strVitesse;
	}
	
	public double arrondi(double nombre, int precision){
		double arrondi;
		double temp;
		temp = nombre * Math.pow(10,precision);
		temp += 0.5; 
		temp = Math.floor(temp);
		arrondi = temp / Math.pow(10,precision);
		
		return arrondi;
	}

	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
	}

}