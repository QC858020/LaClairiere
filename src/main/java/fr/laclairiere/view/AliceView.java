/**
 * 
 */
package fr.laclairiere.view;

import java.util.Observable;

/**
 * @author Hugo &  changed by QC
 *
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fr.laclairiere.controller.AliceController;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;
import fr.laclairiere.mvc.model.Model;
/**
 * This is the view to display Alice's user stories.
 * @author HugoPopo & QC & Xiaxt
 *
 */
public class AliceView extends View{
	private boolean connected = false;
	private User user = null;
	
	private JPanel container = new JPanel();
	
	private JButton logIn = new JButton("Se connecter");
	private JButton logOut = new JButton("Deconnexion");
	private JButton newQuestions = new JButton("les nouvelles questions");
	private JButton myBadges = new JButton("mes Badges et autres utilisateurs");
	private JButton myAnswers = new JButton("les questions auxquelles j'ai repondu");
		
	private JTextField userIdScanner = new JTextField();
	
	private AliceController controller;

	public AliceView(String label, Model model, final AliceController controller) {
		super(label, model);
		this.controller = controller;
		this.setSize(700,350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.container.setLayout(new BorderLayout());
		
		// Parameters panel
		JPanel paramPanel = new JPanel();
		paramPanel.setLayout(new CardLayout());
		this.userIdScanner.setSize(400, 20);
		paramPanel.add(this.userIdScanner);
		
		
		// Buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(3));
		this.logIn.setSize(250, 12);
		this.logOut.setSize(250, 12);
		this.newQuestions.setSize(250, 12);
		this.myAnswers.setSize(250, 12);
		this.myBadges.setSize(250, 12);
		logIn.setEnabled(true);
		logOut.setEnabled(false);
		buttonPanel.add(this.logIn);
		buttonPanel.add(this.logOut);
		buttonPanel.add(this.newQuestions);
		buttonPanel.add(this.myBadges);
		buttonPanel.add(this.myAnswers);
		
		
		// Results panel
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout());
		resultDisplay.setLineWrap(true);
		resultDisplay.setWrapStyleWord(true);
		this.resultDisplay.setEditable(false);
		this.scroll.setPreferredSize(new Dimension(200, 200));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		resultPanel.add(this.scroll);
		
		this.container.add(paramPanel, BorderLayout.NORTH);
		this.container.add(buttonPanel, BorderLayout.CENTER);
		this.container.add(resultPanel, BorderLayout.SOUTH);
		this.setContentPane(container);
		
		this.logIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String param = userIdScanner.getText();
				if (param.matches("[0-9]+"))
					(controller).userVerify(param);
				else {
					resultDisplay.setText("The user id is not a number");
					userIdScanner.setText("");
				}
			}
		});
		
		this.logOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				userIdScanner.setText("Please log in.");
				connected = false;
				logIn.setEnabled(true);
				logOut.setEnabled(false);
				user = null;
			}
		});
		
		this.myAnswers.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//String param = userIdScanner.getText();
				if (user !=null) {
					ArrayList<String> params = new ArrayList<String>();
					params.add(((Integer)user.getUserId()).toString());
					// System.out.println("param : "+((Integer)user.getUserId()).toString());
					resultDisplay.setText(resultDisplay.getText()+"\n\nMes réponses :\n");
					(controller).printAllOfMyAnswers(params);
				}
				else
					resultDisplay.setText("Please log in");
			}
		});
		
		this.newQuestions.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//String param = userIdScanner.getText();
				if (user != null) {
					ArrayList<String> params = new ArrayList<String>();
					params.add(((Integer)user.getUserId()).toString());
					// System.out.println("param : "+((Integer)user.getUserId()).toString());
					resultDisplay.setText(resultDisplay.getText()+"\n\nNouvelles questions :\n");
					(controller).myQuestions(params);
				}
				else
					resultDisplay.setText("Please log in");
			}
		});
		
		this.myBadges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String param = userIdScanner.getText();
				if (user != null) {
					ArrayList<String> params = new ArrayList<String>();
					params.add(((Integer)user.getUserId()).toString());
					//System.out.println("param : "+((Integer)user.getUserId()).toString());
					resultDisplay.setText(resultDisplay.getText()+"\n\nMes badges :\n");
					(controller).myBadges(params);
				}
				else
					resultDisplay.setText("Please log in");
			}
		});
	}

	public void update(Observable arg0, Object arg1) {
		if (((ArrayList<Item>)arg1) == null) {
			this.resultDisplay.setText("Wrong User Id!");
		}
		else {
			logIn.setEnabled(false);
			logOut.setEnabled(true);
			connected = true;
			if (user == null)
				try{
					user = (User)((ArrayList<Item>)arg1).get(0);
					this.resultDisplay.setText(((ArrayList<Item>)arg1).toString());
					userIdScanner.setText("Log in success");
				}
				catch(Exception e){
					this.resultDisplay.setText("Une erreur est survenue");
					userIdScanner.setText("Log in failure");
				}
			else {
				for(Item i : (ArrayList<Item>)arg1){
					this.resultDisplay.setText(this.resultDisplay.getText()+i.toString());
				}
			}
			
		}	
	}

}
