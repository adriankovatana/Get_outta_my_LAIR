package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StoryScreenOne implements Screen {

    private MyGdxGame game;

    private SpriteBatch batch;
    private Sprite splash;
    private Texture splashT;
    private Image skipBut;
    private Texture skipT;
    private Image nextBut;
    private Texture nextT;

    private Stage stage;

    Sound intro = Gdx.audio.newSound(Gdx.files.internal("music/Brazen.mp3"));
    Sound music = Gdx.audio.newSound(Gdx.files.internal("music/MellowDarkness.mp3"));

    public StoryScreenOne(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splash.draw(batch);
        batch.end();
        stage.draw();

        /*if (Gdx.input.isTouched()) {
         intro.stop();
         music.loop(Constants.MASTERVOLUME);
         game.setScreen(game.gameScreen);
         }*/
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
        splashT = new Texture(Gdx.files.internal("storyOne.png"));
        splash = new Sprite(splashT, 1024, 576);
        splash.setPosition(0, 0);
        skipT = new Texture(Gdx.files.internal("skipButton.png"));
        skipBut = new Image(skipT);
        skipBut.setPosition(850, 25);
        skipBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                intro.stop();
                music.loop(Constants.MASTERVOLUME);
                game.setScreen(game.gameScreen);
                return true;
            }
        });
        nextT = new Texture(Gdx.files.internal("nextButton.png"));
        nextBut = new Image(nextT);
        nextBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                intro.stop();
                music.loop(Constants.MASTERVOLUME);
                game.setScreen(game.storyTwo);
                return true;
            }
        });
        nextBut.setPosition(850, 110);

        stage = new Stage();

        stage.addActor(skipBut);
        stage.addActor(nextBut);

        Gdx.input.setInputProcessor(stage);
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
        skipT.dispose();
        nextT.dispose();
    }

}
