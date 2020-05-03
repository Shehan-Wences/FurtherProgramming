package model;

import model.enumeration.CoinFace;
import model.interfaces.Coin;

public class CoinImpl implements Coin {

	private int number;
	private CoinFace coinFace;

	public CoinImpl(int number) {
		this.number = number;
		// getting random number between 0/1 and using that value to get Coinface enum
		this.coinFace = CoinFace.values()[((int) (Math.random() * 2)) % CoinFace.values().length];
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public CoinFace getFace() {
		return this.coinFace;
	}

	@Override
	public void flip() {
		// getting the value of current coinface and adding 1 and making sure it doesnt
		// exceed enum length
		// getting the enum of the new value
		this.coinFace = CoinFace.values()[((this.coinFace.ordinal() + 1)) % CoinFace.values().length];
	}

	@Override
	public boolean equals(Coin coin) {
		return coinFace.equals(coin.getFace()) && number == getNumber();
	}

	@Override
	public boolean equals(Object coin) {
		if (this != null && coin != null) {
			return coin instanceof Coin ? equals((Coin) coin) : false;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		// coinface is converted to string and making the 1st letter capital by using
		// substring
		String details = "Coin " + getNumber() + ": " + getFace().toString().substring(0, 1).toUpperCase()
				+ getFace().toString().substring(1).toLowerCase();
		return details;
	}

	@Override
	public int hashCode() {
		return coinFace.hashCode();
	}

}
