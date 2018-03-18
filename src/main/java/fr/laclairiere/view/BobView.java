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
import fr.laclairiere.controller.BobController;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.model.item.User;
import fr.laclairiere.mvc.model.Model;
/**
 * This is the view to display Bob's user stories.
 * @author HugoPopo & changed by QC
 *
 */
public class BobView extends View{

	private boolean connected = false;
	private User user = null;

	private JPanel container = new JPanel();

	// Add button here.
	private JButton logIn = new JButton("Se connecter");
	private JButton logOut = new JButton("Deconnexion");
	private JButton followTopics = new JButton("Follow topics");
	private JButton findQuestions = new JButton("Find Questions");

	private JTextField userIdScanner = new JTextField();

	private BobController controller;

	public BobView(String label, Model model, final BobController controller) {
		super(label, model);
		this.controller = controller;
		this.setSize(700,300);
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
		this.followTopics.setSize(250, 12);
		logIn.setEnabled(true);
		logOut.setEnabled(false);
		// Add buttons to buttonPanel here
		buttonPanel.add(this.logIn);
		buttonPanel.add(this.logOut);
		buttonPanel.add(this.followTopics);
		buttonPanel.add(this.findQuestions);


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
		this.followTopics.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String param = String.valueOf(user.getUserId());
				ArrayList<String> params = new ArrayList<String>();
				params.add(param);
				//System.out.println("param : "+param);
				controller.newQuestionsToFollow(params);
			}

		});
		this.findQuestions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String param = String.valueOf(user.getUserId());
				ArrayList<String> params = new ArrayList<String>();
				params.add(param);
				//System.out.println("param : "+param);
				controller.newQuestionsToFind(params);
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
