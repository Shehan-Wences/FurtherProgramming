package view;

import model.Registry;
import model.interfaces.Coin;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback {

	private Registry reg;

	public GameEngineCallbackGUI(Registry reg) {
		this.reg = reg;
	}

	@Override
	public void playerCoinUpdate(Player player, Coin coin, GameEngine engine) {

		reg.coinUpdate(coin.getNumber(), player, coin.getFace().toString().substring(0, 1).toUpperCase()
				+ coin.getFace().toString().substring(1).toLowerCase());

	}

	@Override
	public void spinnerCoinUpdate(Coin coin, GameEngine engine) {
		reg.coinUpdate(coin.getNumber(), coin.getFace().toString().substring(0, 1).toUpperCase()
				+ coin.getFace().toString().substring(1).toLowerCase());

	}

	@Override
	public void playerResult(Player player, CoinPair coinPair, GameEngine engine) {
		reg.coinUpdate(coinPair.getCoin1().getNumber(), player,
				coinPair.getCoin1().getFace().toString().substring(0, 1).toUpperCase()
						+ coinPair.getCoin1().getFace().toString().substring(1).toLowerCase());
		reg.coinUpdate(coinPair.getCoin2().getNumber(), player,
				coinPair.getCoin2().getFace().toString().substring(0, 1).toUpperCase()
						+ coinPair.getCoin2().getFace().toString().substring(1).toLowerCase());
		
		
	}

	@Override
	public void spinnerResult(CoinPair coinPair, GameEngine engine) {
		reg.setSpinnerCoinpair(coinPair);
		reg.coinUpdate(coinPair.getCoin1().getNumber(),
				coinPair.getCoin1().getFace().toString().substring(0, 1).toUpperCase()
						+ coinPair.getCoin1().getFace().toString().substring(1).toLowerCase());
		reg.coinUpdate(coinPair.getCoin2().getNumber(),
				coinPair.getCoin2().getFace().toString().substring(0, 1).toUpperCase()
						+ coinPair.getCoin2().getFace().toString().substring(1).toLowerCase());
		reg.winLossCalculation(engine);
		reg.setResults(engine);
		reg.getStatusBar().setStatus(2, "Round Complete");
	}

}
