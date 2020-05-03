package view;

import java.awt.GridLayout;

import javax.swing.*;

import controller.ToolbarController;
import model.Registry;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class CoinFunctionsBar extends JToolBar {

	private GameEngine gameEngine;
	private JButton playerSpin, spinnerSpin;
	private JComboBox<String> comboBox;

	public CoinFunctionsBar(Registry reg, AppFrame appFrame, GameEngine gameEngine) {

		this.gameEngine = gameEngine;

		setLayout(new GridLayout(1, 3));

		playerSpin = new JButton("SPIN");
		playerSpin.setBorder(BorderFactory.createRaisedBevelBorder());
		playerSpin.setActionCommand("Spin");
		playerSpin.addActionListener(new ToolbarController(reg, appFrame, gameEngine));

		spinnerSpin = new JButton("SPINNER SPIN");
		spinnerSpin.setBorder(BorderFactory.createRaisedBevelBorder());
		spinnerSpin.setActionCommand("Spinner Spin");
		spinnerSpin.addActionListener(new ToolbarController(reg, appFrame, gameEngine));

		comboBox = new JComboBox<>();
		add(playerSpin);
		add(comboBox);
		add(spinnerSpin);

		comboBox.setActionCommand("Player Box");
		comboBox.addActionListener(new ToolbarController(reg, appFrame, gameEngine));

		playerSpin.setEnabled(false);
		spinnerSpin.setEnabled(false);
		comboBox.setEnabled(false);

		reg.setToolBar(this);

		setFloatable(false);
		revalidate();
		repaint();
	}

	public void setDisplay(String spinnerStatus, String playerSpinStatus) {

		if (spinnerStatus == "ready") {
			spinnerSpin.setEnabled(true);

		} else if (spinnerStatus == "disabled") {
			spinnerSpin.setEnabled(false);

		} else if (spinnerStatus == "done") {
			spinnerSpin.setEnabled(false);

		}

		if (playerSpinStatus == "ready" && spinnerStatus != "done") {
			playerSpin.setEnabled(true);

		} else if (playerSpinStatus == "bet" && spinnerStatus != "done") {
			playerSpin.setEnabled(false);

		} else if (playerSpinStatus == "done" && spinnerStatus != "done") {
			playerSpin.setEnabled(false);

		} else {

		}

	}

	public void playerListPopulate() {

		comboBox.setEnabled(true);
		comboBox.removeAllItems();
		for (Player player : gameEngine.getAllPlayers()) {
			comboBox.addItem(player.getPlayerName());

		}
	
	}
	// Player Combo Box reference
	public JComboBox<String> getPlayerListComboBox() {
		return this.comboBox;
	}

	public JButton getPlayerSpin() {
		return playerSpin;
	}
	public JButton getSpinnerSpin() {
		return spinnerSpin;
	}
	
}
