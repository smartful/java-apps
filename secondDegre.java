/******************************************************************************************
 * Programme : Equation d'ordre 2
 * But : Résoudre une équation du second degré
 * Auteur : SMARTFUL (i.e. Rémi RODRIGUES)
 * Date : 17/03/2015
 * Mise à jour : 08/04/2015
 * 				 Corrections dans la fonction calcul
 */

package secondDegre;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class SecondDegre extends JFrame{

	private JLabel labA, labB, labC;
	private JLabel equation = new JLabel("Ax^2 + Bx + C = 0");
	private JLabel resultLabel = new JLabel();
	private JLabel errorCoef = new JLabel();
	private String resultat = "Les racines s'affichent ici";
	private JTextField a, b, c;
	private JPanel content = new JPanel();
	private JButton resoudre = new JButton("Resoudre");
	
	public SecondDegre() {
		this.setTitle("Equation d'ordre 2");
		this.setIconImage(new ImageIcon("icon_smartful.png").getImage());
		this.setSize(400,390);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.miseEnForme();
		
		this.setVisible(true);
	}
	
	public void miseEnForme(){
		//Mise en forme de la saisie des coefficients
		JPanel panCoef = new JPanel();
		panCoef.setBorder(BorderFactory.createTitledBorder("Coefficients"));
		panCoef.setPreferredSize(new Dimension(360,150));
		panCoef.setLayout(new GridLayout(3,2,10,10));
		labA = new JLabel("A");
		labA.setHorizontalAlignment(JLabel.CENTER);
		a = new JTextField();
		a.setPreferredSize(new Dimension(250,30));
		labB = new JLabel("B");
		labB.setHorizontalAlignment(JLabel.CENTER);
		b = new JTextField();
		b.setPreferredSize(new Dimension(250,30));
		labC = new JLabel("C");
		labC.setHorizontalAlignment(JLabel.CENTER);
		c = new JTextField();
		c.setPreferredSize(new Dimension(250,30));
		panCoef.add(labA);
		panCoef.add(a);
		panCoef.add(labB);
		panCoef.add(b);
		panCoef.add(labC);
		panCoef.add(c);
		
		//On lance le calcul si on appui sur le bouton
		resoudre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				resultat = "";
				resultat = calcul(a.getText(),b.getText(),c.getText());
				resultLabel.setText(resultat);
			}
		});
		//Mise en forme du bouton de résolution
		JPanel panBouton = new JPanel();
		panBouton.add(resoudre);
		
		//Mise en forme du label de l'équation de second ordre
		JPanel panEqu = new JPanel();
		panEqu.setBorder(BorderFactory.createTitledBorder("equation"));
		panEqu.setPreferredSize(new Dimension(360,50));
		panEqu.add(equation);
		
		
		//Mise en forme du champs de résultat
		JPanel panResult = new JPanel();
		panResult.setBorder(BorderFactory.createTitledBorder("racines"));
		panResult.setPreferredSize(new Dimension (360,70));
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		Font police = new Font("Arial",Font.BOLD,18); 
		resultLabel.setFont(police);
		panResult.add(resultLabel);
		
		//Mise en forme du champs des erreurs de saisie
		errorCoef.setHorizontalAlignment(JLabel.CENTER);
		Font police2 = new Font("Arial",Font.TYPE1_FONT,14);
		errorCoef.setFont(police2);
		errorCoef.setForeground(Color.red);
		
		//Mise en forme de tous les panels dans le content
		content.add(panCoef);
		content.add(panEqu);
		content.add(panResult);
		content.add(errorCoef);
		
		this.getContentPane().add(panBouton,BorderLayout.SOUTH);
		this.getContentPane().add(content,BorderLayout.CENTER);
	}
	
	public String calcul(String paramA, String paramB, String paramC){
		String str = "";
		double a = 0, b = 0, c = 0;
		double delta;
	
		//Conversion des String en double
		if(paramA.isEmpty()) paramA = "0";
		if(paramB.isEmpty()) paramB = "0";
		if(paramC.isEmpty()) paramC = "0";
		
		try{
			a = Double.valueOf(paramA).doubleValue();
			b = Double.valueOf(paramB).doubleValue();
			c = Double.valueOf(paramC).doubleValue();
			errorCoef.setText("");
		}catch(NumberFormatException e){
			errorCoef.setText("Erreur dans la saisie des coefficients");
		}
		
		//Calcul
		if(a == 0){
			if(b == 0){
				if(c == 0){
					str = "Tous les coefficients sont à zéros";
				}
				else{
					str = "Pas de solution dans R";
				}
				
			}	
			else{
				double x = -b/c;
				str = "x0 = " + arrondi(x,3);
			}
			
		}
		else{
			delta = (b*b) - (4*a*c);
			if(delta < 0){
				str = "Pas de solution dans R";
			}
			else if(delta == 0){
				double x = -b/(2*a);
				str = "x0 = " + arrondi(x,3);
			}
			else{
				double x1 = (-b - Math.sqrt(delta))/(2*a);
				double x2 = (-b + Math.sqrt(delta))/(2*a);
				str = "x1 = " + arrondi(x1,3) +"      -----      x2 = " + arrondi(x2,3); 
			}
		}
		return str;
	}
	
	public double arrondi(double nombre, int precision){
		double arrondi;
		double temp;
		temp = nombre * Math.pow(10,precision);
		temp += 0.5; //On rajoute 0.5 pour ajouter +1 si le dernier digit est supérieur ou égale à 5
		temp = Math.floor(temp);
		arrondi = temp / Math.pow(10, precision);
		
		return arrondi;
	}

	public static void main(String[] args) {
		SecondDegre fen = new SecondDegre();
	}

}