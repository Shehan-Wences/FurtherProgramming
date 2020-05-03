package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.CoinFunctionsBar;
import view.CoinPanel;
import view.MainMenu;
import view.StatusBarPanel;

public class Registry {

	private CoinPair spinnerCoinpair;
	private DefaultTableModel table;
	
	// This Hashmap is used to store status of each individual player which helps
	// enabling disabling MenuItems and Toolbar Buttons
	private HashMap<Player, String> spinStatus;
	
	// This hashmap is used to calculate winLoss
	private HashMap<Player, Integer> winLoss;
	private StatusBarPanel statusBar;
	private MainMenu mainMenu;
	private CoinFunctionsBar toolBar;
	private CoinPanel coinPanel;

	// This is used to store status of spinner which helps
	// enabling disabling MenuItems and Toolbar Buttons
	private String spinnerStatus;
	private Player currentPlayer;
	private int roundNo;
	private int playerNumber;

	public Registry() {
		this.spinnerStatus = "disabled";
		this.spinStatus = new HashMap<Player, String>();
		this.winLoss = new HashMap<Player, Integer>();
		this.roundNo = 1;
		this.playerNumber = 1;
	}

	public StatusBarPanel getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(StatusBarPanel statusBar) {
		this.statusBar = statusBar;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public CoinFunctionsBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(CoinFunctionsBar toolBar) {
		this.toolBar = toolBar;
	}

	public CoinPanel getCoinPanel() {
		return coinPanel;
	}

	public void setCoinPanel(CoinPanel coinPanel) {
		this.coinPanel = coinPanel;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public String getPlayerSpinStatus() {
		return spinStatus.get(currentPlayer);
	}

	public String getSpinnerStatus() {
		return this.spinnerStatus;
	}

	public HashMap<Player, String> getAllPlayerSpinStatus() {
		return spinStatus;
	}

	public void setSpinnerCoinpair(CoinPair spinnerCoinpair) {
		this.spinnerCoinpair = spinnerCoinpair;
	}

	public CoinPair getSpinnerCoinpair() {
		return spinnerCoinpair;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public int getRoundNo() {
		return this.roundNo;
	}

	public void setRoundNo(int roundNo) {
		this.roundNo = roundNo;
	}

	public void setOldPointsBalance(Player player, int pointsBalance) {
		winLoss.put(player, pointsBalance);
	}

	public int getoldPointsBalance(Player player) {
		return winLoss.get(player);
	}

	public void winLossCalculation(GameEngine game) {
		for (Player player : game.getAllPlayers()) {
			winLoss.put(player, player.getPoints() - getoldPointsBalance(player));
		}
	}

	public void setTable(DefaultTableModel table) {
		this.table = table;
	}

	public DefaultTableModel getTable() {
		return this.table;
	}

	// coin image returning method
	public BufferedImage getImage(String text) {
		try {
			if (text.equals("Heads")) {
				BufferedImage heads;
				heads = ImageIO.read(new File("img/heads.png"));
				return heads;

			} else if (text.equals("Tails")) {
				BufferedImage tails = ImageIO.read(new File("img/tails.png"));
				return tails;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// getting the number of players that has not spun but placed bets
	public int notSpunPlayers() {
		int number = 0;
		for (String value : spinStatus.values()) {
			if (value.equals("ready")) {
				number++;
			}
		}
		return number;
	}

	// return the row number of a player
	public int rowNumber(String playerName) {
		for (int a = 0; a < table.getRowCount(); a++) {

			if (table.getValueAt(a, 0).equals(playerName)) {
				return a;
			}

		}
		return -1;
	}

	// coinupdate for spinner
	public void coinUpdate(int number, String text) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (number == 1) {
					coinPanel.setCoinOne(getImage(text));
				}
				if (number == 2) {
					coinPanel.setCoinTwo(getImage(text));
				}
			}
		});

	}
	//Jtable results setting
	public void setResults(GameEngine gameEngine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				for (Player player : gameEngine.getAllPlayers()) {
					getTable().setValueAt(player.getPoints(), rowNumber(player.getPlayerName()), 1);
					getTable().setValueAt(getoldPointsBalance(player), rowNumber(player.getPlayerName()), 4);
				}
			}
		});

	}

	// coinupdate for players
	public void coinUpdate(int number, Player player, String text) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (currentPlayer.equals(player) && number == 1) {
					coinPanel.setCoinOne(getImage(text));
				}
				if (currentPlayer.equals(player) && number == 2) {
					coinPanel.setCoinTwo(getImage(text));
				}
			}
		});

	}

	// Spinner Spin button enabling
	public void setSpinnerStatus(String spinnerStatus) {
		this.spinnerStatus = spinnerStatus;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainMenu.setDisplay(spinnerStatus, spinStatus.get(currentPlayer));
				toolBar.setDisplay(spinnerStatus, spinStatus.get(currentPlayer));
			}
		});

	}

	// individual player current state is stored
	public void setSpinStatus(String stat) {
		spinStatus.put(currentPlayer, stat);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainMenu.setDisplay(spinnerStatus, stat);
				toolBar.setDisplay(spinnerStatus, stat);
			}
		});

	}

	public void removePlayer(Player player) {
		spinStatus.remove(player);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainMenu.setDisplay(spinnerStatus, spinStatus.get(player));
				toolBar.setDisplay(spinnerStatus, spinStatus.get(player));
			}
		});
	}
}
