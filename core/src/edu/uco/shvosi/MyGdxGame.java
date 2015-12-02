package edu.uco.shvosi;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {

    public StartScreen startScreen;
    public GameScreen gameScreen;
    public StoryScreenOne storyOne;
    public StoryScreenTwo storyTwo;
  
    public int mute = 0;

    @Override
    public void create() {
        startScreen = new StartScreen(this);
        gameScreen = new GameScreen(this);
        storyOne = new StoryScreenOne(this);
        storyTwo = new StoryScreenTwo(this);
        

        setScreen(startScreen);
    }

    @Override
    public void dispose() {
        gameScreen.dispose();
        startScreen.dispose();
    }
}
