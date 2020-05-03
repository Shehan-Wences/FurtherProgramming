package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Registry;
import model.SimplePlayer;
import model.enumeration.BetType;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.AppFrame;

public class MenuBarController implements ActionListener {

	private AppFrame appFrame;
	private GameEngine gameEngine;
	private Registry reg;

	public MenuBarController(Registry reg, AppFrame appFrame, GameEngine gameEngine) {
		this.appFrame = appFrame;
		this.gameEngine = gameEngine;
		this.reg = reg;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit")) {
			System.exit(0);

		} else if (e.getActionCommand().equals("Add Player")) {
			addPlayer();
		} else if (e.getActionCommand().equals("Place Bet")) {
			placeBet();
		} else if (e.getActionCommand().equals("View Player Results")) {
			reg.getStatusBar().setStatus(2, "Player Results");
			reg.getToolBar().getPlayerListComboBox().setVisible(true);
			reg.getToolBar().getPlayerListComboBox().setEnabled(true);
			// Checking if their is a result for the player if there is displaying them
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

		} else if (e.getActionCommand().equals("Spinner View Results")) {
			reg.getStatusBar().setStatus(2, "Spinner Results");
			reg.getToolBar().getPlayerListComboBox().setVisible(false);
			reg.getToolBar().getPlayerListComboBox().setEnabled(false);
			// Checking if their is a result for the spinner if there is displaying them
			if (reg.getSpinnerCoinpair() != null) {
				reg.coinUpdate(reg.getSpinnerCoinpair().getCoin1().getNumber(),
						reg.getSpinnerCoinpair().getCoin1().getFace().toString().substring(0, 1).toUpperCase()
								+ reg.getSpinnerCoinpair().getCoin1().getFace().toString().substring(1).toLowerCase());
				reg.coinUpdate(reg.getSpinnerCoinpair().getCoin2().getNumber(),
						reg.getSpinnerCoinpair().getCoin2().getFace().toString().substring(0, 1).toUpperCase()
								+ reg.getSpinnerCoinpair().getCoin2().getFace().toString().substring(1).toLowerCase());
			} else {
				reg.getCoinPanel().setEmptyCoin();
			}

		} else if (e.getActionCommand().equals("Remove Player")) {
			reg.getStatusBar().setStatus(2, "Player : " + reg.getCurrentPlayer().getPlayerName() + " Removed");
			reg.getTable().removeRow(reg.rowNumber(reg.getCurrentPlayer().getPlayerName()));
			gameEngine.removePlayer(reg.getCurrentPlayer());
			reg.removePlayer(reg.getCurrentPlayer());
			reg.getToolBar().playerListPopulate();
			for (Player player : gameEngine.getAllPlayers()) {
				if (player.getPlayerName() == reg.getToolBar().getPlayerListComboBox().getSelectedItem()) {
					reg.setCurrentPlayer(player);
				}
			}

			reg.getMainMenu().setDisplay(reg.getSpinnerStatus(), reg.getPlayerSpinStatus());
			reg.getToolBar().setDisplay(reg.getSpinnerStatus(), reg.getPlayerSpinStatus());

		} else if (e.getActionCommand().equals("Cancel Bet")) {
			reg.getTable().setValueAt("", reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 2);
			reg.getTable().setValueAt("", reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 3);
			reg.setSpinStatus("bet");
			reg.getCurrentPlayer().resetBet();
			reg.getToolBar().getSpinnerSpin().setEnabled(false);
			reg.getTable().setValueAt(reg.getCurrentPlayer().getBetType(),
					reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 2);
			reg.getTable().setValueAt(reg.getCurrentPlayer().getBet(),
					reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 3);
			reg.getStatusBar().setStatus(2, "Player : " + reg.getCurrentPlayer().getPlayerName() + " Canceled the Bet");

		} else if (e.getActionCommand().equals("New Round")) {

			reg.setSpinnerStatus("disabled");
			reg.getStatusBar().setStatus(2, "New Round Started");
			for (Player player : gameEngine.getAllPlayers()) {
				if (player.getPoints() < 1) {
					JOptionPane.showMessageDialog(appFrame,
							"Player : " + player.getPlayerName() + " will be removed from the game [No Points Left]",
							"Alert", JOptionPane.INFORMATION_MESSAGE);
					gameEngine.removePlayer(player);
					reg.getTable().removeRow(reg.rowNumber(player.getPlayerName()));
					reg.removePlayer(player);
					reg.getToolBar().playerListPopulate();
				}
			}

			for (Player player : gameEngine.getAllPlayers()) {
				player.resetBet();
				player.setResult(null);
				reg.setCurrentPlayer(player);
				reg.setSpinStatus("bet");
				reg.getTable().setValueAt("NO_BET", reg.rowNumber(player.getPlayerName()), 2);
				reg.getTable().setValueAt("", reg.rowNumber(player.getPlayerName()), 3);
				reg.setOldPointsBalance(player, player.getPoints());

			}
			reg.getMainMenu().getAddPlayer().setEnabled(true);
			reg.setRoundNo(reg.getRoundNo() + 1);
			reg.getStatusBar().setStatus(3, "Round " + reg.getRoundNo());
			reg.getToolBar().playerListPopulate();
			reg.getToolBar().getPlayerListComboBox().setEnabled(true);
			reg.getToolBar().getPlayerListComboBox().setVisible(true);
		}

	}

	public void addPlayer() {
		String points = "";
		String playerName = "";
		boolean status = true;
		// validating stuff before storing(different methods used to validate can be
		// found at the end of this class)
		playerName = JOptionPane.showInputDialog(appFrame, "Please Input Player Name :", "Player Name",
				JOptionPane.PLAIN_MESSAGE);
		if (playerName != null) {
			if (validateName(playerName.trim())) {
				if (nameDuplication(playerName.trim())) {

					points = JOptionPane.showInputDialog(appFrame, "Please Input Points Amount (Max:9999) :",
							"Amount of Points", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(appFrame, "Invalid Name, Name should contain Letters and spaces only",
							"Alert", JOptionPane.ERROR_MESSAGE);
					status = false;
				}

			} else {
				JOptionPane.showMessageDialog(appFrame, "Invalid Name, Name should contain Letters and spaces only",
						"Alert", JOptionPane.ERROR_MESSAGE);
				status = false;
			}
		}
		if (points != null && status != false && playerName != null) {
			if (validatePoints(points)) {
				if (Integer.parseInt(points) > 0) {
					int pointsInt = Integer.parseInt(points);
					String stringName = playerName.trim();

					Thread add = new Thread(new Runnable() {

						@Override
						public void run() {

							gameEngine.addPlayer(new SimplePlayer(reg.getPlayerNumber() + "", stringName, pointsInt));

							reg.setCurrentPlayer(gameEngine.getPlayer(reg.getPlayerNumber() + ""));
							reg.setSpinStatus("bet");
							reg.setPlayerNumber(reg.getPlayerNumber() + 1);
							reg.setOldPointsBalance(reg.getCurrentPlayer(), pointsInt);

						}
					});
					add.start();
					try {
						add.join();
					} catch (InterruptedException e1) {

						e1.printStackTrace();
					}

					reg.getToolBar().playerListPopulate();
					reg.setSpinStatus(reg.getPlayerSpinStatus());
					reg.getStatusBar().setStatus(2, "Player : " + stringName + " Added");
					reg.getTable().addRow(new Object[] { stringName, pointsInt, "NO_BET", "0", "0" });

				} else {
					JOptionPane.showMessageDialog(appFrame,
							"Invalid Points Amount Entered,Note that Minimum amount is 1", "Alert",
							JOptionPane.ERROR_MESSAGE);
					status = false;
				}
			} else {
				JOptionPane.showMessageDialog(appFrame,
						"Invalid Points Amount Entered,Note that Maximum amount is 9999", "Alert",
						JOptionPane.ERROR_MESSAGE);

			}
		}
	}

	public void placeBet() {
		boolean status = true;
		String points = "";
		String betType = "";
		String[] choices = { "COIN1", "COIN2", "BOTH" };

		// This section does input validation for bets-nullcheck,maximum amount and
		// above zero check
		points = JOptionPane.showInputDialog(appFrame, "Please Bet Amount (Max:9999) :", "Amount of Points",
				JOptionPane.PLAIN_MESSAGE);
		if (points != null) {
			if (validatePoints(points)) {
				if (Integer.parseInt(points) > 0) {
					betType = (String) JOptionPane.showInputDialog(appFrame, "Choose Bet Type...",
							"Select a Bet Type :", JOptionPane.PLAIN_MESSAGE, null, choices, choices[1]);
				} else {
					JOptionPane.showMessageDialog(appFrame,
							"Invalid Points Amount Entered,Note that Minimum amount is 1", "Alert",
							JOptionPane.ERROR_MESSAGE);
					status = false;
				}
			} else {
				JOptionPane.showMessageDialog(appFrame,
						"Invalid Points Amount Entered,Note that Maximum amount is 9999", "Alert",
						JOptionPane.ERROR_MESSAGE);
				status = false;
			}
		}

		if (betType != null && points != null && status != false) {

			int a = 3;
			if (betType.equals("COIN1")) {
				a = 0;
			} else if (betType.equals("COIN2")) {
				a = 1;
			} else if (betType.equals("BOTH")) {
				a = 2;
			}
			int pointsInt = Integer.parseInt(points);
			int betNo = a;

			if (gameEngine.placeBet(reg.getCurrentPlayer(), pointsInt, BetType.values()[betNo])) {

				// if bet is successfull changing MenuItem status
				reg.setSpinnerStatus("ready");
				reg.setSpinStatus("ready");
				reg.getTable().setValueAt(reg.getCurrentPlayer().getBetType(),
						reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 2);
				reg.getTable().setValueAt(reg.getCurrentPlayer().getBet(),
						reg.rowNumber(reg.getCurrentPlayer().getPlayerName()), 3);
				reg.getStatusBar().setStatus(2, "Player : " + reg.getCurrentPlayer().getPlayerName() + " Placed a Bet");
				boolean autoSpin = true;
				for (Player player : gameEngine.getAllPlayers()) {
					if (player.getBet() == 0) {
						autoSpin = false;
					}
				}
				// auto spin if all bets are placed
				if (autoSpin == true) {
					reg.setSpinnerStatus("disabled");
					JOptionPane.showMessageDialog(appFrame, "Players Will Auto Spin Now");
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
								}
							}

							JOptionPane.showMessageDialog(appFrame, "Spinners going to spin now");
							reg.getToolBar().getPlayerListComboBox().setVisible(false);

							reg.getToolBar().getPlayerListComboBox().setEnabled(false);

							gameEngine.spinSpinner(100, 1000, 100, 50, 500, 50);
							reg.setSpinnerStatus("done");
						}
					}.start();

				}
			} else {
				JOptionPane.showMessageDialog(appFrame, "Not Enough Points To Set BET", "Alert",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public boolean validateName(String name) {
		String regex = "^[a-zA-Z\\s]+";
		return name.matches(regex);

	}

	public boolean nameDuplication(String name) {
		for (Player player : gameEngine.getAllPlayers()) {
			if (player.getPlayerName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public boolean validatePoints(String points) {
		String regex = "^[0-9]{1,4}";
		return points.matches(regex);

	}
}
