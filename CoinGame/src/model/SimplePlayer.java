package model;

import model.enumeration.BetType;
import model.interfaces.CoinPair;
import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String playerId;
	private String playerName;
	private int points;
	private int bet;
	private BetType betType;
	private CoinPair coinPair;

	public SimplePlayer(String playerId, String playerName, int initialPoints) {
		this.playerId = playerId;
		this.playerName = playerName;
		this.points = initialPoints;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String getPlayerId() {
		return this.playerId;
	}

	@Override
	public boolean setBet(int bet) {
		if (bet > 0 && this.points >= bet) {
			this.bet = bet;
			return true;
		} else {
			resetBet();
			return false;
		}

	}

	@Override
	public int getBet() {
		return this.bet;
	}

	@Override
	public void setBetType(BetType betType) {
		this.betType = (BetType) betType;
	}

	@Override
	public BetType getBetType() {
		return this.betType;
	}

	@Override
	public void resetBet() {
		this.bet = 0;
		this.betType = BetType.NO_BET;

	}

	@Override
	public CoinPair getResult() {
		return this.coinPair;
	}

	@Override
	public void setResult(CoinPair coinPair) {
		this.coinPair=coinPair;

	}

	@Override
	   public  String toString() {
		
		String details="Player: id="+this.playerId+", name="+this.playerName+", bet="+this.bet+", betType="+this.betType+
				", points="+this.points+", RESULT .. "+this.coinPair+"";
		return details;
	}
}
