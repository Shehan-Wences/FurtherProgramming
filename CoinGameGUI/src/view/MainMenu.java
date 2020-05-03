package view;

import javax.swing.*;
import controller.MenuBarController;
import model.Registry;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class MainMenu extends JMenuBar {

	private JMenuItem newRoundItem, exitMenuItem, viewPlayerMenuItem, addPlayerMenuItem, removePlayerMenuItem,
			placeBetMenuItem, cancelBetMenuItem, viewSpinnerMenuItem;
	private Registry reg;

	public MainMenu(Registry reg, AppFrame appFrame, GameEngine gameEngine) {

		this.reg = reg;
		JMenu gameMenu = new JMenu("Game");

		newRoundItem = new JMenuItem("New Round");
		newRoundItem.setActionCommand("New Round");
		newRoundItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		gameMenu.add(newRoundItem);
		newRoundItem.setEnabled(false);

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");
		exitMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		gameMenu.add(exitMenuItem);

		JMenu playerMenu = new JMenu("Player");

		viewPlayerMenuItem = new JMenuItem("View Player Results");
		viewPlayerMenuItem.setActionCommand("View Player Results");
		viewPlayerMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		playerMenu.add(viewPlayerMenuItem);
		viewPlayerMenuItem.setEnabled(false);

		addPlayerMenuItem = new JMenuItem("Add Player");
		addPlayerMenuItem.setActionCommand("Add Player");
		addPlayerMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		playerMenu.add(addPlayerMenuItem);

		removePlayerMenuItem = new JMenuItem("Remove Player");
		removePlayerMenuItem.setActionCommand("Remove Player");
		removePlayerMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		playerMenu.add(removePlayerMenuItem);
		removePlayerMenuItem.setEnabled(false);

		placeBetMenuItem = new JMenuItem("Place Bet");
		placeBetMenuItem.setActionCommand("Place Bet");
		placeBetMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		playerMenu.add(placeBetMenuItem);
		placeBetMenuItem.setEnabled(false);

		cancelBetMenuItem = new JMenuItem("Cancel Bet");
		cancelBetMenuItem.setActionCommand("Cancel Bet");
		cancelBetMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		playerMenu.add(cancelBetMenuItem);
		cancelBetMenuItem.setEnabled(false);

		JMenu spinnerMenu = new JMenu("Spinner");

		viewSpinnerMenuItem = new JMenuItem("Spinner View Results");
		viewSpinnerMenuItem.setActionCommand("Spinner View Results");
		viewSpinnerMenuItem.addActionListener(new MenuBarController(reg, appFrame, gameEngine));
		spinnerMenu.add(viewSpinnerMenuItem);
		viewSpinnerMenuItem.setEnabled(false);

		reg.setMainMenu(this);
		add(gameMenu);
		add(playerMenu);
		add(spinnerMenu);

		revalidate();
		repaint();
	}

	public void setDisplay(String spinnerStatus, String playerSpinStatus) {

		if (spinnerStatus == "ready") {
			viewSpinnerMenuItem.setEnabled(false);
			viewPlayerMenuItem.setEnabled(false);
		} else if (spinnerStatus == "disabled") {
			viewSpinnerMenuItem.setEnabled(false);
			viewPlayerMenuItem.setEnabled(false);
			newRoundItem.setEnabled(false);
		} else if (spinnerStatus == "done") {
			viewSpinnerMenuItem.setEnabled(true);
			viewPlayerMenuItem.setEnabled(true);
			newRoundItem.setEnabled(true);
		}

		if (playerSpinStatus == "ready" && spinnerStatus != "done") {
			addPlayerMenuItem.setEnabled(true);
			removePlayerMenuItem.setEnabled(true);
			placeBetMenuItem.setEnabled(false);
			cancelBetMenuItem.setEnabled(true);

		} else if (playerSpinStatus == "bet" && spinnerStatus != "done") {
			addPlayerMenuItem.setEnabled(true);
			removePlayerMenuItem.setEnabled(true);
			placeBetMenuItem.setEnabled(true);
			cancelBetMenuItem.setEnabled(false);

		} else if (playerSpinStatus == "done" && spinnerStatus != "done") {
			addPlayerMenuItem.setEnabled(false);
			removePlayerMenuItem.setEnabled(false);
			placeBetMenuItem.setEnabled(false);
			cancelBetMenuItem.setEnabled(false);
		}
		if (reg.getAllPlayerSpinStatus().containsValue("done")) {
			addPlayerMenuItem.setEnabled(false);
			removePlayerMenuItem.setEnabled(false);
		}
	}
	public JMenuItem getAddPlayer() {
		return this.addPlayerMenuItem;
	}
}
