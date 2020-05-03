package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Registry;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class StatusBarPanel extends JPanel {

	private JLabel status1,status2,status3;
	
	public StatusBarPanel(Registry reg, AppFrame appFrame, GameEngine gameEngine) {

		setLayout(new GridLayout(1, 3));
		status1 = new JLabel("Two Ups", JLabel.CENTER);
		status2 = new JLabel("New Round Started", JLabel.CENTER);
		status3 = new JLabel("Round 1", JLabel.CENTER);
		status1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		status2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		status3.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		
		add(status1);
		add(status2);
		add(status3);
		reg.setStatusBar(this);
		
		revalidate();
		repaint();
	}

	public void setStatus(int statusNo,String text) {
		if(statusNo==1) {
			status1.setText(text);
		}else if(statusNo==2) {
			status2.setText(text);
		}else if(statusNo==3) {
			status3.setText(text);
		}
	}
}
