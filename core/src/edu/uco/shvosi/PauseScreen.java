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
    private Image[] itemI = new Image[10];
    private Texture[] itemT = new Texture[10];
    
    private Stage stage;
    private Label healthLabel;
    private Label damageLabel;
    private Label levelLabel;
    private int[] items = new int[10];
    private int index = 0;
    private int invX = 175;
    private int invY = 375;


    public PauseScreen(GameScreen s) {
        screen = s;
    }


    @Override
    public void render(float delta) {
   //     this.show();
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.invent.setX(25);
        screen.invent.setY(475);
        stage.addActor(screen.invent);
        
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
        
        healthLabel.setX(295);
        healthLabel.setY(115);
        healthLabel.setText(health);
        damageLabel.setX(645);
        damageLabel.setY(115);
        damageLabel.setText(damage);
        levelLabel.setX(495);
        levelLabel.setY(115);
        levelLabel.setText(level);
        
        startT = new Texture(Gdx.files.internal("buttons/resumebutton.png"));
        startBut = new Image(startT);
        startBut.setPosition(350, 15);
        startBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screen.resume();
                return true;
            }
        });
        menuT = new Texture(Gdx.files.internal("buttons/menubutton.png"));
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
        menuBut.setPosition(480, 15);
        
        quitT = new Texture(Gdx.files.internal("buttons/quitbutton.png"));
        quitBut = new Image(quitT);
        quitBut.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        quitBut.setPosition(610, 15);
        
        stage = new Stage();
        
        items = Protagonist.getInventory();
        
        for (int i : items){
            if (index == 5){
                invX = 175;
                invY = 225;       
            }
            
            if (i == 0){
 //               Gdx.app.log("ITEM", "0");
                break;
            }
            else if (i == 1){
//                Gdx.app.log("ITEM", "1");
                itemT[index] = TextureLoader.SHIELDTEXTURE;
                itemI[index] = new Image(itemT[index]);
                itemI[index].addListener(new ClickListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Protagonist.setActive(1);
                        return true;
                    }
                });
                itemI[index].setPosition(invX, invY);
                invX = invX + 150;
                stage.addActor(itemI[index]);   
            }
             else if (i == 2){
//                Gdx.app.log("ITEM", "2");
                itemT[index] = TextureLoader.WHISTLETEXTURE;
                itemI[index] = new Image(itemT[index]);
                itemI[index].addListener(new ClickListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Protagonist.setActive(2);
                        return true;
                    }
                });
                    itemI[index].setPosition(invX, invY);

                invX = invX + 150;
                stage.addActor(itemI[index]);   
            }
             else if (i == 3){
 //               Gdx.app.log("ITEM", "3");
                itemT[index] = TextureLoader.GREYKEYTEXTURE;
                itemI[index] = new Image(itemT[index]);
                itemI[index].addListener(new ClickListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Protagonist.setActive(3);
                        return true;
                    }
                });
                itemI[index].setPosition(invX, invY);
                invX = invX + 150;
                stage.addActor(itemI[index]);   
            }
             else if(i == 4){
 //               Gdx.app.log("ITEM", "4");
                itemT[index] = TextureLoader.REDKEYTEXTURE;
                itemI[index] = new Image(itemT[index]);
                itemI[index].addListener(new ClickListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Protagonist.setActive(4);
                        return true;
                    }
                });

                itemI[index].setPosition(invX, invY);

                invX = invX + 150;
                stage.addActor(itemI[index]);   
            }
            else {
                Gdx.app.log("BROKEN", ""); 
            }
            index++;
        }
        
        index = 0;
        

        stage.addActor(startBut);
        stage.addActor(quitBut);
        stage.addActor(menuBut);
        stage.addActor(healthLabel);
        stage.addActor(damageLabel);
        stage.addActor(levelLabel);
 //       stage.addActor(invent);

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
    
    public void setInvX(int x){
        invX = x;
    }
    public void setInvY(int y){
        invY = y;
    }

}

