package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {

	Map<String, Player> playerList = new HashMap<>();
	Collection<GameEngineCallback> callbacks = new HashSet<>();

	@Override
	public void spinPlayer(Player player, int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2,
			int finalDelay2, int delayIncrement2) throws IllegalArgumentException {

		if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0 || initialDelay2 < 0 || finalDelay2 < 0
				|| delayIncrement2 < 0 || finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2
				|| delayIncrement1 > (finalDelay1 - initialDelay1) || delayIncrement2 > (finalDelay2 - initialDelay2)) {

			throw new IllegalArgumentException();
		} else {
			
			CoinPair pairOfCoins = new CoinPairImpl();

			Thread one = new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						int delayCurrent = initialDelay1;

						while (finalDelay1 > delayCurrent) {
							if (initialDelay1 < delayCurrent) {
								pairOfCoins.getCoin1().flip();

							}
							Thread.sleep(delayCurrent);

							for (GameEngineCallback cb : callbacks) {
								cb.playerCoinUpdate(player, pairOfCoins.getCoin1(), GameEngineImpl.this);

							}
							delayCurrent = delayCurrent + delayIncrement1;
							player.setResult(pairOfCoins);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});

			Thread two = new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						int delayCurrent = initialDelay2;

						while (finalDelay2 > delayCurrent) {
							if (initialDelay2 < delayCurrent) {
								pairOfCoins.getCoin2().flip();

							}
							Thread.sleep(delayCurrent);

							for (GameEngineCallback cb : callbacks) {
								cb.playerCoinUpdate(player, pairOfCoins.getCoin2(), GameEngineImpl.this);

							}
							delayCurrent = delayCurrent + delayIncrement2;
							player.setResult(pairOfCoins);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});

			one.start();
			two.start();

			try {
				one.join();
				two.join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			for (GameEngineCallback cb : callbacks) {
				cb.playerResult(player, pairOfCoins, this);
			}

		}
	}

	@Override
	public void spinSpinner(int initialDelay1, int finalDelay1, int delayIncrement1, int initialDelay2, int finalDelay2,
			int delayIncrement2) throws IllegalArgumentException {
		if (initialDelay1 < 0 || finalDelay1 < 0 || delayIncrement1 < 0 || initialDelay2 < 0 || finalDelay2 < 0
				|| delayIncrement2 < 0 || finalDelay1 < initialDelay1 || finalDelay2 < initialDelay2
				|| delayIncrement1 > (finalDelay1 - initialDelay1) || delayIncrement2 > (finalDelay2 - initialDelay2)) {

			throw new IllegalArgumentException();
		} else {

			
			CoinPair pairOfCoins = new CoinPairImpl();

			Thread one = new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						int delayCurrent = initialDelay1;

						while (finalDelay1 > delayCurrent) {
							if (initialDelay1 < delayCurrent) {
								pairOfCoins.getCoin1().flip();

							}
							Thread.sleep(delayCurrent);

							for (GameEngineCallback cb : callbacks) {
								cb.spinnerCoinUpdate(pairOfCoins.getCoin1(), GameEngineImpl.this);
							}
							delayCurrent = delayCurrent + delayIncrement1;

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});

			Thread two = new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						int delayCurrent = initialDelay2;

						while (finalDelay2 > delayCurrent) {
							if (initialDelay2 < delayCurrent) {
								pairOfCoins.getCoin2().flip();

							}
							Thread.sleep(delayCurrent);

							for (GameEngineCallback cb : callbacks) {
								cb.spinnerCoinUpdate(pairOfCoins.getCoin2(), GameEngineImpl.this);
							}
							delayCurrent = delayCurrent + delayIncrement2;

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			});
			one.start();
			two.start();

			try {
				one.join();
				two.join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			applyBetResults(pairOfCoins);
			for (GameEngineCallback cb : callbacks) {
				cb.spinnerResult(pairOfCoins, this);
			}
		}

	}

	@Override
	public void applyBetResults(CoinPair spinnerResult) {
		// iterating through player list and applying winloss
		for (Player player : getAllPlayers()) {
			player.getBetType().applyWinLoss(player, spinnerResult);

		}

	}

	@Override
	public void addPlayer(Player player) {
		playerList.put(player.getPlayerId(), player);
	}

	@Override
	public Player getPlayer(String id) {
		return playerList.get(id);
	}

	@Override
	public boolean removePlayer(Player player) {
		if (playerList.remove(player.getPlayerId()) == null) {
			return false;
		} else {
			playerList.remove(player.getPlayerId());
			return true;
		}

	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacks.add(gameEngineCallback);

	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		return callbacks.remove(gameEngineCallback);
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return playerList.values();
	}

	@Override
	public boolean placeBet(Player player, int bet, BetType betType) {
		if (player.setBet(bet)) {
			player.setBetType(betType);
			return true;
		} else {
			return false;
		}

	}

}
