package model;

import model.interfaces.Coin;
import model.interfaces.CoinPair;

public class CoinPairImpl implements CoinPair {

	private Coin coin1, coin2;

	public CoinPairImpl() {
		this.coin1 = new CoinImpl(1);
		this.coin2 = new CoinImpl(2);
	}

	@Override
	public Coin getCoin1() {
		return coin1;
	}

	@Override
	public Coin getCoin2() {
		return coin2;
	}

	@Override
	public boolean equals(CoinPair coinPair) {
		return coin1.equals(coinPair.getCoin1()) && coin2.equals(coinPair.getCoin2());
	}

	@Override
	public boolean equals(Object coinPair) {
		if (this != null && coinPair != null) {
			return coinPair instanceof CoinPair ? equals((CoinPair) coinPair) : false;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		String details = coin1 + ", " + coin2;
		return details;
	}

	@Override
	public int hashCode() {
		return coin1.hashCode() + coin2.hashCode();
	}
}
