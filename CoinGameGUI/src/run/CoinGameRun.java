package run;


import javax.swing.SwingUtilities;
import model.GameEngineImpl;
import model.Registry;
import model.interfaces.GameEngine;
import validate.Validator;
import view.AppFrame;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;


public class CoinGameRun {


	
	public static void main(String[] args) {
		
		GameEngine gameEngine = new GameEngineImpl();
		Validator.validate(true);
		Registry reg=new Registry();
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());
		gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(reg));
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new AppFrame(gameEngine,reg);
			}
		});

	}

}
