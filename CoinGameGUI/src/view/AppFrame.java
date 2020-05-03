package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import model.Registry;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {

	private final GameEngine gameEngine;
	private Registry reg;

	public AppFrame(GameEngine gameEngine, Registry reg) {

		super("Coin Game");
		this.gameEngine = gameEngine;
		this.reg = reg;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new BorderLayout());

		CoinPanel coinPanel = new CoinPanel(this.reg, this, this.gameEngine);
		coinPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		SummaryPanel summaryPanel = new SummaryPanel(this.reg, this, this.gameEngine);
		summaryPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		CoinFunctionsBar coinFuncBar = new CoinFunctionsBar(this.reg, this, this.gameEngine);

		MainMenu MainMenu = new MainMenu(this.reg, this, this.gameEngine);

		StatusBarPanel statusBarPanel = new StatusBarPanel(this.reg, this, this.gameEngine);

		setJMenuBar(MainMenu);
		add(coinFuncBar, BorderLayout.NORTH);
		add(summaryPanel, BorderLayout.WEST);
		add(coinPanel, BorderLayout.CENTER);
		add(statusBarPanel, BorderLayout.SOUTH);
		pack();
		coinFuncBar.setPreferredSize(new Dimension(getWidth(), 40));
		statusBarPanel.setPreferredSize(new Dimension(getWidth(), 30));
		summaryPanel.setPreferredSize(new Dimension(444, 255));

		setMinimumSize(new Dimension(900, 520));
		setBounds(100, 100, 1350, 600);

		setLocationRelativeTo(null);
		revalidate();
		repaint();
	}

}
