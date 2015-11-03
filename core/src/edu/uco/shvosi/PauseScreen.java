package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen implements Screen {

    private GameScreen screen;
    private SpriteBatch batch;
    private Sprite splash;
    private Texture splashT;
    private Image startBut;
    private Texture startT;
    private Image menuBut;
    private Texture menuT;
    private Image quitBut;
    private Texture quitT;

    private Stage stage;
    private Label healthLabel;
    private Label damageLabel;
    private Label levelLabel;


    public PauseScreen(GameScreen screen) {
        this.screen = screen;
    }

    @Override
    public void render(float delta) {
        this.show();
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        splash.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
        batch = new SpriteBatch();
        splashT = new Texture(Gdx.files.internal("pause.png"));
        splash = new Sprite(splashT, 1024, 576);
        splash.setPosition(0, 0);
        
        damageLabel = new Label("", TextureLoader.SKIN);
        healthLabel = new Label("", TextureLoader.SKIN);
        levelLabel = new Label("", TextureLoader.SKIN);
        
        String health = "Health: " + Integer.toString(screen.map.bernard.getHealth()) + "/" + Integer.toString(screen.map.bernard.getMaxHealth());
        String damage = "Damage: " + Float.toString(screen.map.bernard.getDamage());
        String level = "Level: " + Integer.toString(screen.map.bernard.getLevel());
        
        healthLabel.setX(350);
        healthLabel.setY(100);
        healthLabel.setText(health);
        damageLabel.setX(550);
        damageLabel.setY(100);
        damageLabel.setText(damage);
        levelLabel.setX(475);
        levelLabel.setY(150);
        levelLabel.setText(level);
        
        startT = new Texture(Gdx.files.internal("resumeButton.png"));
        startBut = new Image(startT);
        startBut.setPosition(350, 15);
        startBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screen.resume();
                return true;
            }
        });
        menuT = new Texture(Gdx.files.internal("menuButton.png"));
        menuBut = new Image(menuT);
        menuBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screen.map.dispose();
                screen.level = 0;
                screen.game.setScreen(screen.game.startScreen);
                screen.resume();
                return true;
            }
        });
        menuBut.setPosition(450, 15);
        
        quitT = new Texture(Gdx.files.internal("quitButton.png"));
        quitBut = new Image(quitT);
        quitBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        quitBut.setPosition(550, 15);
        
        stage = new Stage();

        stage.addActor(startBut);
        stage.addActor(quitBut);
        stage.addActor(menuBut);
        stage.addActor(healthLabel);
        stage.addActor(damageLabel);
        stage.addActor(levelLabel);

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
        splashT.dispose();
        startT.dispose();
        quitT.dispose();
    }

}

