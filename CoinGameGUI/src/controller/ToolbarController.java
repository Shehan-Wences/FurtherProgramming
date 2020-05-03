package controller;

import java.awt.event.*;
import javax.swing.JOptionPane;
import model.Registry;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.AppFrame;

public class ToolbarController implements ActionListener {

	private AppFrame appFrame;
	private GameEngine gameEngine;
	private Registry reg;

	public ToolbarController(Registry reg, AppFrame appFrame, GameEngine gameEngine) {
		this.appFrame = appFrame;
		this.gameEngine = gameEngine;
		this.reg = reg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Spin")) {
			reg.getStatusBar().setStatus(2, reg.getCurrentPlayer().getPlayerName() + " is Spinning..");
			new Thread() {
				@Override
				public void run() {
					reg.setSpinStatus("done");
					reg.setSpinnerStatus("disabled");
					gameEngine.spinPlayer(reg.getCurrentPlayer(), 100, 1000, 100, 50, 500, 50);
					reg.setSpinnerStatus("ready");
				}
			}.start();

		} else if (e.getActionCommand().equals("Player Box")) {

			for (Player player : gameEngine.getAllPlayers()) {
				if (player.getPlayerName() == reg.getToolBar().getPlayerListComboBox().getSelectedItem()) {
					reg.setCurrentPlayer(player);
				}
			}
			reg.getMainMenu().setDisplay(reg.getSpinnerStatus(), reg.getPlayerSpinStatus());
			reg.getToolBar().setDisplay(reg.getSpinnerStatus(), reg.getPlayerSpinStatus());
			if (reg.getCurrentPlayer().getResult() != null) {
				reg.coinUpdate(reg.getCurrentPlayer().getResult().getCoin1().getNumber(), reg.getCurrentPlayer(),
						reg.getCurrentPlayer().getResult().getCoin1().getFace().toString().substring(0, 1).toUpperCase()
								+ reg.getCurrentPlayer().getResult().getCoin1().getFace().toString().substring(1)
										.toLowerCase());
				reg.coinUpdate(reg.getCurrentPlayer().getResult().getCoin2().getNumber(), reg.getCurrentPlayer(),
						reg.getCurrentPlayer().getResult().getCoin2().getFace().toString().substring(0, 1).toUpperCase()
								+ reg.getCurrentPlayer().getResult().getCoin2().getFace().toString().substring(1)
										.toLowerCase());
			} else {
				reg.getCoinPanel().setEmptyCoin();
			}
		} else if (e.getActionCommand().equals("Spinner Spin")) {

			String[] options = { "Yes", "Cancel" };

			if (reg.notSpunPlayers() > 0) {
				int n = JOptionPane.showOptionDialog(appFrame,
						"There are player(s) that has place bets and not spun.\n Do you want to Spin them first ?",
						"Spinner Spin?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[0]);
				if (n == 0) {
					reg.setSpinStatus("done");
					reg.setSpinnerStatus("disabled");
					reg.getStatusBar().setStatus(2, "Players are Spinning..");

					new Thread() {
						@Override
						public void run() {
							for (Player player : gameEngine.getAllPlayers()) {
								if (player.getResult() == null && player.getBet() != 0) {
									reg.setCurrentPlayer(player);
									reg.setSpinStatus("done");
									reg.getToolBar().getPlayerListComboBox().setSelectedItem(player.getPlayerName());
									gameEngine.spinPlayer(player, 100, 1000, 100, 50, 500, 50);
								} else if (player.getBet() == 0) {
									player.resetBet();
									reg.getTable().setValueAt(player.getBetType(),
											reg.rowNumber(player.getPlayerName()), 2);
								}
							}

							JOptionPane.showMessageDialog(appFrame, "Spinners going to spin now");
							reg.getStatusBar().setStatus(2, "Spinner is Spinning..");
							reg.getToolBar().getPlayerListComboBox().setVisible(false);
							reg.getToolBar().getSpinnerSpin().setEnabled(false);
							reg.getToolBar().getPlayerListComboBox().setEnabled(false);
							reg.setSpinnerStatus("done");

							gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
						}
					}.start();

				}
			} else {
				reg.getToolBar().getPlayerListComboBox().setVisible(false);
				for (Player player : gameEngine.getAllPlayers()) {
					if (player.getResult() == null) {
						player.resetBet();
					}
				}
				reg.getStatusBar().setStatus(2, "Spinner is Spinning..");
				reg.getToolBar().getSpinnerSpin().setEnabled(false);
				reg.getToolBar().getPlayerListComboBox().setEnabled(false);
				new Thread() {
					@Override
					public void run() {

						gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
						reg.setSpinnerStatus("done");
					}
				}.start();
			}

		}
	}

}
