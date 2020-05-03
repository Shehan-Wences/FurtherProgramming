package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Registry;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class SummaryPanel extends JPanel {

	private JFrame appFrame;

	public SummaryPanel(Registry reg, AppFrame appFrame, GameEngine gameEngine) {
		this.appFrame = appFrame;

		setLayout(new GridLayout(1, 1));

		DefaultTableModel table = new DefaultTableModel();
		JTable jtable = new JTable(table);

		table.addColumn("Player Name");
		table.addColumn("Points");
		table.addColumn("Bet Type");
		table.addColumn("Bet");
		table.addColumn("Win/Loss");

		reg.setTable(table);

		add(new JScrollPane(jtable));

		jtable.setEnabled(false);
		jtable.getTableHeader().setReorderingAllowed(false);

		revalidate();
		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);
		setPreferredSize(
				new Dimension((appFrame.getContentPane().getWidth()) / 3, (appFrame.getContentPane().getHeight()) / 3));
		revalidate();
		repaint();
	}

}
