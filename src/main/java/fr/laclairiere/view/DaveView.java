package fr.laclairiere.view;

import java.util.Observable;

/**
 * @author Hugo
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
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fr.laclairiere.controller.DaveController;
import fr.laclairiere.model.item.Item;
import fr.laclairiere.mvc.model.Model;
/**
 * This is the view to display Dave's user stories.
 * @author HugoPopo
 *
 */
public class DaveView extends View{

	private JPanel container = new JPanel();

	private JButton topTag = new JButton("top tag");
	private JButton topSeveralTags = new JButton("top several tags");
	private JButton topContributors = new JButton("top contributors");

	private JTextField tagScanner = new JTextField();

	private DaveController controller;

	public DaveView(String label, Model model, final DaveController controller) {
		super(label, model);
		this.controller = controller;
		this.setSize(700,300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.container.setLayout(new BorderLayout());

		// Parameters panel
		JPanel paramPanel = new JPanel();
		paramPanel.setLayout(new CardLayout());
		this.tagScanner.setSize(400, 20);
		paramPanel.add(this.tagScanner);


		// Buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(3));
		this.topTag.setSize(250, 12);
		this.topSeveralTags.setSize(250, 12);
		buttonPanel.add(this.topTag);
		buttonPanel.add(this.topSeveralTags);
		buttonPanel.add(this.topContributors);


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

		this.topTag.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String param = tagScanner.getText();
				resultDisplay.setText(resultDisplay.getText()+'\n'+"Top tag : "+param+'\n');
				ArrayList<String> params = new ArrayList<String>();
				params.add(param);
				((DaveController)controller).topTag(params);				
			}
		});
		
		this.topSeveralTags.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				String[] param = tagScanner.getText().split(" ");
				resultDisplay.setText(resultDisplay.getText()+'\n'+"Top des tags : ");
				ArrayList<String> params=new ArrayList<String>();  
				for(int i=0;i<param.length;i++){
					resultDisplay.setText(resultDisplay.getText()+param[i]+" ");
					params.add(param[i]);  
				}
				resultDisplay.setText(resultDisplay.getText()+'\n');
				((DaveController)controller).topSeveralTags((params));
			}
		});
		
		this.topContributors.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String param = tagScanner.getText();
				resultDisplay.setText(resultDisplay.getText()+'\n'+"Top contributeurs du tag : "+param+'\n');
				ArrayList<String> params = new ArrayList<String>();
				params.add(param);
				((DaveController)controller).topContributors(params);				
			}
		});
	}

	public void update(Observable arg0, Object arg1) {
		for(Item i : (ArrayList<Item>)arg1){
			this.resultDisplay.setText(this.resultDisplay.getText()+i.toString());
		}
		
	}

}
