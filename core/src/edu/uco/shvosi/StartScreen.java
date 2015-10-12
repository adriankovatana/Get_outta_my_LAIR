package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen{
	
	private MyGdxGame game;
        
	private SpriteBatch batch;
	private Sprite splash;
	private Texture splashT;
	private Sprite startBut;
	private Texture startT;
	private Sprite quitBut;
	private Texture quitT;
        
        Sound intro = Gdx.audio.newSound(Gdx.files.internal("music/Brazen.mp3"));
        Sound music = Gdx.audio.newSound(Gdx.files.internal("music/MellowDarkness.mp3"));


	public StartScreen (MyGdxGame game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                
		batch.begin();
		splash.draw(batch);
		startBut.draw(batch);
		quitBut.draw(batch);
		batch.end();
		
		if (Gdx.input.isTouched()) {
                        intro.stop();
                        music.loop(Constants.MASTERVOLUME);
			game.setScreen(game.gameScreen);
		}
	}
 

   @Override
	public void resize(int width, int height) {
	}


   @Override
	public void show() {
		 // called when this screen is set as the screen with game.setScreen();
            music.stop();
            intro.play(Constants.MASTERVOLUME);
		batch = new SpriteBatch();
		splashT = new Texture(Gdx.files.internal("splash.png"));
		splash = new Sprite(splashT, 800, 450);
		splash.setPosition(0, 0);
		startT = new Texture(Gdx.files.internal("startButtonS.png"));
		startBut = new Sprite(startT, 100, 50);
		startBut.setPosition(550, 15);
		quitT = new Texture(Gdx.files.internal("quitButton.png"));
		quitBut = new Sprite(quitT, 100, 50);
		quitBut.setPosition(650, 15);
	}


   @Override
	public void hide() {
		 // called when current screen changes from this to a different screen
		 dispose();
	}


   @Override
	public void pause() {
	}


   @Override
	public void resume() {
	}


   @Override
	public void dispose() {
			// never called automatically
		//batch.dispose();
		splashT.dispose();
		startT.dispose();
		quitT.dispose();
	}
    
}


