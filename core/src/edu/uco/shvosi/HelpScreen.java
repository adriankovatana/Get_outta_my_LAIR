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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HelpScreen implements Screen {

    private GameScreen screen;
    private SpriteBatch batch;
    private Sprite splash;
    private Image startBut;
    private Texture startT;
    private Texture pageone;
    private Texture pagetwo;
    private Texture pagethree;
    private int page = 1;
    private Stage stage;

    public HelpScreen(GameScreen s) {
        screen = s;
    }

    @Override
    public void render(float delta) {        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);   
        stage.addActor(startBut);
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
        batch = new SpriteBatch();
        pageone = new Texture(Gdx.files.internal("help.png"));
        pagetwo = new Texture(Gdx.files.internal("controls.png"));
        pagethree = new Texture(Gdx.files.internal("items.png"));
        splash = new Sprite(pageone, 1024, 576);
        splash.setPosition(0, 0);

        startT = new Texture(Gdx.files.internal("buttons/okaybutton.png"));
        startBut = new Image(startT);
        startBut.setPosition(480, 15);
        startBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (page == 1){
                    splash.setTexture(pagetwo);
                    page = 2;
                }
                else if (page == 2){
                    splash.setTexture(pagethree);
                    page = 3;
                }
                else{
                    page = 1;
                    screen.resume();
                }
                return true;
            }
        });

        stage = new Stage();
        //stage.addActor(startBut);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
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
        if(batch != null){
            pageone.dispose();
            pagetwo.dispose();
            pagethree.dispose();
            startT.dispose();
            stage.dispose();
            batch.dispose();
        }
    }

}


