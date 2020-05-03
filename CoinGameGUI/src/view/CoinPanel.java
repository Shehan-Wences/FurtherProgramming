package view;


import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.Registry;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class CoinPanel extends JPanel {

	private JFrame appFrame;

	private BufferedImage coinOne;
	private BufferedImage coinTwo;

	public CoinPanel(Registry reg, AppFrame appFrame, GameEngine gameEngine) {

		this.appFrame = appFrame;
		setLayout(new GridLayout(1,1));
		setEmptyCoin();
		reg.setCoinPanel(this);
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		int x = appFrame.getContentPane().getWidth() - getWidth();
		int y = appFrame.getContentPane().getHeight()-50;
		int diameter = Math.min(x, y);

		g.drawImage(coinOne, 0, 0, diameter, diameter, this);
		g.drawImage(coinTwo, diameter, 0, diameter, diameter, this);
		revalidate();
		repaint();

	}

	public void setCoinOne(BufferedImage image) {
		this.coinOne = image;
		revalidate();
		repaint();
	}

	public void setCoinTwo(BufferedImage image) {
		this.coinTwo = image;
		revalidate();
		repaint();
	}

	public void setEmptyCoin() {
		try {
			this.coinOne = ImageIO.read(new File("img/none.png"));
			this.coinTwo = ImageIO.read(new File("img/none.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		revalidate();
		repaint();
	}
}
