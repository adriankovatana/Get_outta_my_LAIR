package edu.uco.shvosi;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

    public StartScreen startScreen;
    public GameScreen gameScreen;
    public int mute = 0;

    @Override
    public void create() {
        startScreen = new StartScreen(this);
        gameScreen = new GameScreen(this);

        setScreen(startScreen);
    }

    @Override
    public void dispose() {
        startScreen.dispose();
        gameScreen.dispose();
    }
}
