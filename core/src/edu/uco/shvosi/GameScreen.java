package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    public MyGdxGame game;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    public Map map;
    private Stage stage;
    private String healthpoints;
    private Label healthLabel;
    public static Inventory invent;
    public static int turnCount;
    private TextureLoader textureLoader = new TextureLoader();
    private boolean paused = false;
    private boolean showFullMap = false;

    PauseScreen pauseScreen = new PauseScreen(this);

    public static int level = 0;
    //private boolean turnsFinished = true;
    //private boolean playTurn = false;
    private Protagonist bernard;

    private Entity activeEntity;
    //private boolean entityTurnInProg;
    private int entityTurn;

    private boolean roundStarted;
    private Label turnLabel;

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            if(Gdx.input.isKeyJustPressed(Keys.M)){
                paused = true;
                showFullMap = true;
            }
            
            //Check for game over
            if (map.getEntityList().isEmpty()
                    || (map.bernard.isDead() && map.bernard.turnFinished)) {
                if (roundStarted || !map.getEntityList().isEmpty()) {
                    entityTurn = 0;
                    roundStarted = false;
                    stage.clear();
                    turnLabel.setText("Game Over");
                    turnLabel.setX(map.bernard.getX());
                    turnLabel.setY(map.bernard.getY() + 50);
                    healthLabel.setX(map.bernard.getX() - 65);
                    healthLabel.setY(map.bernard.getY());
                    healthLabel.setText("Press 'Esc' to go to main menu...");
                    stage.addActor(turnLabel);
                    stage.addActor(healthLabel);
                    map.getEntityList().clear();
                }
                if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
                    map.dispose();
                    level = 0;
                    game.setScreen(game.startScreen);
                }
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                stage.draw();
                return;
            }

            /* == INPUT == */
            /* Bernard Controls */
            if (!roundStarted && activeEntity instanceof Protagonist) {
                if (map.bernard.getTransform() == true) {
                    roundStarted = true;
                    map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                    String c = String.valueOf(map.bernard.getTransformCounter());
                    Gdx.app.log("Lose Turn Counter", c);
                    map.playTurn(activeEntity);
                } else {
                    //Movement
                    if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Constants.Direction.UP)) {
                    //entityTurnInProg = true;
                        //this.playTurn = true;
                        roundStarted = true;
                        map.bernard.notifyObservers();
                        map.bernard.setDirection(Constants.Direction.UP);
                        map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                        Gdx.app.log("MOVING", "UP");
                        map.playTurn(activeEntity);
                    } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Constants.Direction.DOWN)) {
                    //entityTurnInProg = true;
                        //this.playTurn = true;
                        roundStarted = true;
                        map.bernard.notifyObservers();
                        map.bernard.setDirection(Constants.Direction.DOWN);
                        map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                        Gdx.app.log("MOVING", "DOWN");
                        map.playTurn(activeEntity);
                    } else if (Gdx.input.isKeyJustPressed(Keys.A)) {
                        if (map.bernard.getDirection() != Constants.Direction.LEFT) {
                            map.bernard.flipTexture(Constants.Direction.LEFT);
                            map.bernard.setDirection(Constants.Direction.LEFT);
                        }
                        if (map.bernardCanMove(Constants.Direction.LEFT)) {
                        //entityTurnInProg = true;
                            //this.playTurn = true;
                            roundStarted = true;
                            map.bernard.notifyObservers();
                            map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                            Gdx.app.log("MOVING", "LEFT");
                            map.playTurn(activeEntity);
                        }
                    } else if (Gdx.input.isKeyJustPressed(Keys.D)) {
                        if (map.bernard.getDirection() != Constants.Direction.RIGHT) {
                            map.bernard.flipTexture(Constants.Direction.RIGHT);
                            map.bernard.setDirection(Constants.Direction.RIGHT);
                        }

                        if (map.bernardCanMove(Constants.Direction.RIGHT)) {
                        //entityTurnInProg = true;
                            //this.playTurn = true;
                            roundStarted = true;
                            map.bernard.notifyObservers();
                            map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                            Gdx.app.log("MOVING", "RIGHT");
                            map.playTurn(activeEntity);
                        }
                    }

                    //Use Item
                    if (Gdx.input.isKeyJustPressed(Keys.Q)) {
                        //map.bernard.useItem();
                    }

                    //Mute
                    if (Gdx.input.isKeyJustPressed(Keys.M)) {
                        map.bernard.mute = 1;
                    }

                    //Skills
                    if (Gdx.input.isKeyJustPressed(Keys.SPACE) && map.bernard.redLaserCooldown <= 0) {
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.REDLASERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                            //stage.addActor(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
                    //entityTurnInProg = true;
                        //this.playTurn = true;
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.SKILLONE);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                            //stage.addActor(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.SKILLTWO);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                            //stage.addActor(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
                        roundStarted = true;
                        map.bernard.setExecuteDetection(true);
                        map.bernard.setSkill(Constants.SkillName.DETECTION);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_4) && map.bernard.barrierCooldown <= 0 && !map.bernard.getExecuteBarrier()) {
                        roundStarted = true;
                        map.bernard.setBarrierLimit(3);
                        map.bernard.setExecuteBarrier(true);
                        map.bernard.setSkill(Constants.SkillName.BARRIERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_5) && map.bernard.lightningInfusionCooldown <= 0 && !map.bernard.getExecuteLightBarrier()) {
                        roundStarted = true;
                        map.bernard.setLightBarrierLimit(3);
                        map.bernard.setExecuteLightBarrier(true);
                        map.bernard.setSkill(Constants.SkillName.LIGHTBARRIERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_6)) {
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.FREEZINGSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_7)) {
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.LASERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.I)) {
                        this.pause();
                    }
                }
            }

            /* -- END INPUT -- */
            /* == UPDATE == */
            //Check map exit
            if (map.exitReached()) {
                //Load the next level
                entityTurn = 0;
                roundStarted = false;
                stage.clear();
                map.dispose();
                initNewLevel();
                return;
            }

            //Check if dead
            for (int i = 0; i < map.getEntityList().size(); i++) {
                Entity aggressor = map.getEntityList().get(i);

                if (aggressor.isDead()) {
                    if (activeEntity instanceof Protagonist && aggressor instanceof Antagonist) {
                        Antagonist ant;
                        ant = (Antagonist) aggressor;

                        map.bernard.addExp(ant.getXpValue());
                    }
                    aggressor.performDeath();
                    map.removeEntityFromGrid(aggressor);
                    map.getEntityList().remove(i);
                }
            }

        // -- Play the turn --
            //Sets the next active entity when it is done with its turn
            if (roundStarted && activeEntity.turnFinished) {
                activeEntity = map.getEntityList().get(++entityTurn % map.getEntityList().size());

            //Gdx.app.log(activeEntity.getName(), activeEntity.cX + "' " + activeEntity.cY);
                //If we have reached bernard, the turn is over and set all turnfinished to false;
                if (activeEntity instanceof Protagonist) {
                    roundStarted = false;
                    for (int i = 0; i < map.getEntityList().size(); i++) {
                        map.getEntityList().get(i).setTurnFinished(false);
                        if (i == map.getEntityList().size() - 1) {
                            turnCount++;
                        }
                    }
//                for (int i = 0; i < map.miscEntityList.size(); i++) {
//                    map.miscEntityList.get(i).setTurnFinished(false);
//                }
                }
            }

            //Collision
            for (int i = 0; i < map.miscEntityList.size(); i++) {
                Entity aggressor = map.miscEntityList.get(i);

                if (aggressor.isDead()) {
                    aggressor.performDeath();
                    map.removeEntityFromGrid(aggressor);
                    map.miscEntityList.remove(i);
                } else {
                    for (int j = 0; j < map.getEntityList().size(); j++) {
                        aggressor.collision(map.getEntityList().get(j));
                    }
                }
            }

            //Add actions to the activeEntity if it just started its turn!
            if (roundStarted && !activeEntity.hasActions() && !(activeEntity instanceof Protagonist)) {
                map.playTurn(activeEntity);
                //Gdx.app.log("TURN", activeEntity.getName());
            }

            //temporary health display
            healthpoints = "HP: " + map.bernard.getHealth();
            healthLabel.setX(map.bernard.getX() - 5);
            healthLabel.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2);
            healthLabel.setText("Level: " + map.bernard.getLevel() + "    " + healthpoints);

            //temporary inventory display
            invent.setX(map.bernard.getX() - Constants.SCREENWIDTH / 2 + map.bernard.getWidth() * 0.75f);
            invent.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2 - map.bernard.getHeight() / 2);        
            
            //temporary turn display
            String temp = "";
            if (roundStarted) {
                temp = "Turn Underway";
            }
            turnLabel.setX(map.bernard.getX());
            turnLabel.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2 - 10);
            turnLabel.setText(temp);


            /* -- END UPDATE -- */

            /* == RENDER == */
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//            batch.setProjectionMatrix(camera.combined);
//            batch.begin();
//            map.render(camera);
//            batch.end();
            stage.act(Gdx.graphics.getDeltaTime());
            map.render(camera);
            stage.draw();
            map.renderMiniMap();

            centerCameraOn(map.bernard);
            camera.update();
            /* -- END RENDER -- */
        } else {
            if(showFullMap){
                if(Gdx.input.isKeyJustPressed(Keys.M)){
                    paused = false;
                    showFullMap = false;
                }
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                //batch.setProjectionMatrix(camera.combined);
                batch.begin();
                map.renderFullMap();
                batch.end();
            } else {
        //        invent = new Inventory(TextureLoader.INVENTORYTEXTURE, 5, 0);
                pauseScreen.render(delta);
            }
        }
    }

    public void centerCameraOn(Entity entity) {
        camera.position.x = entity.getX() + entity.getWidth() / 2;
        camera.position.y = entity.getY() + entity.getHeight() / 2;
    }

    public void initNewLevel() {
        //Test Level
        if (level == 0) {
            map = new Map(this.bernard, "maps/testmap.tmx");
            level = 1;
        } else if (level == 1) {
            map = new Map(this.bernard, "colemap/colemap.tmx");
            level = 2;
        } else if (level == 2) {
            map = new Map(this.bernard, "colemap/colemap.tmx");
            //           map = new Map(this.bernard, "maps/testmap2.tmx");
            level = 0;
        }
        map.bernard.removeAllObservers();
        map.bernard.setCurrentMap(map.getMapGrid());
        activeEntity = bernard;

        initStage();
    }

    public void initStage() {
        // Add the entities to the stage
        for (int i = map.miscEntityList.size() - 1; i > -1; i--) {
            stage.addActor(map.miscEntityList.get(i));
        }
        for (int i = map.getEntityList().size() - 1; i > -1; i--) {
            stage.addActor(map.getEntityList().get(i));
        }
        //Health Display
        stage.addActor(healthLabel);
        stage.addActor(invent);
        stage.addActor(turnLabel);
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();

        batch = new SpriteBatch();

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        invent = new Inventory(TextureLoader.INVENTORYTEXTURE, 5, 0);
        healthLabel = new Label("HP: ", TextureLoader.SKIN);
        bernard = new Protagonist(0, 0);
        activeEntity = bernard;
        entityTurn = 0;
        roundStarted = false;
        turnLabel = new Label("", TextureLoader.SKIN);

        initNewLevel();

        map.bernard.getLevelUpDialog().show(stage);
        map.bernard.getLevelUpDialog().setVisible(false);
        Gdx.input.setInputProcessor(stage);

        turnCount = 0;
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
        paused = true;
        pauseScreen.show();
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
        pauseScreen.setInvX(175);
        pauseScreen.setInvY(375);
        stage.addActor(invent);
        paused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // never called automatically
        //batch.dispose();
        textureLoader.dispose();
        stage.dispose();
    }
}
