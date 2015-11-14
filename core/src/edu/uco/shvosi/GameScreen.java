package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    public MyGdxGame game;

    private SpriteBatch batch;
    private SpriteBatch uibatch;
    private OrthographicCamera camera;
    private Vector3 mousePosition;
    public Map map;
    private Stage stage;
    private String healthpoints;
    private Label healthLabel;
    public static Inventory invent;
    public static int turnCount;
    private TextureLoader textureLoader = new TextureLoader();
    private boolean paused = false;
    private boolean help = false;
    private boolean showFullMap = false;
    private boolean targetToggle = true;

    private PauseScreen pauseScreen;
    private HelpScreen helpScreen;
    private GameUI gameUI;

    public static int level = 0;
    private Protagonist bernard;

    private Entity activeEntity;
    private int entityTurn;

    private boolean roundStarted;
    private Label turnLabel;

    public GameScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        if (!paused) {
            if (Gdx.input.isKeyJustPressed(Keys.M)) {
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

                    //Bernard Coordinates Check
//                    if (Gdx.input.isKeyJustPressed(Keys.C)) {
//                        Gdx.app.log("Bernards X Coordinates", String.valueOf(bernard.getX()));
//                        Gdx.app.log("Bernards Y Coordinates", String.valueOf(bernard.getY()));
//                        Gdx.app.log("Mouse Position", String.valueOf(mousePosition));
//                    }
                    //Toggle Target Square
                    if (Gdx.input.isKeyJustPressed(Keys.T)) {
                        targetToggle ^= true;
                    }

                    //Mouse Direction
                    if (mousePosition.x > map.bernard.getX() + Constants.TILEDIMENSION && mousePosition.y < map.bernard.getY() + Constants.TILEDIMENSION && mousePosition.y > map.bernard.getY()) {
                        map.bernard.setDirection(Constants.Direction.RIGHT);
                        map.bernard.flipTexture(Constants.Direction.RIGHT);
//                        Gdx.app.log("Direction", "Right");
                    } else if (mousePosition.x < map.bernard.getX() && mousePosition.y < map.bernard.getY() + Constants.TILEDIMENSION && mousePosition.y > map.bernard.getY()) {
                        map.bernard.setDirection(Constants.Direction.LEFT);
                        map.bernard.flipTexture(Constants.Direction.LEFT);
//                        Gdx.app.log("Direction", "Left");
                    } else if (mousePosition.y > map.bernard.getY() + Constants.TILEDIMENSION && mousePosition.x < map.bernard.getX() + Constants.TILEDIMENSION && mousePosition.x > map.bernard.getX()) {
                        map.bernard.setDirection(Constants.Direction.UP);
//                        Gdx.app.log("Direction", "Up");
                    } else if (mousePosition.y < map.bernard.getY() && mousePosition.x < map.bernard.getX() + Constants.TILEDIMENSION && mousePosition.x > map.bernard.getX()) {
                        map.bernard.setDirection(Constants.Direction.DOWN);
//                        Gdx.app.log("Direction", "Down");
                    }

                    //Movement
                    if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Constants.Direction.UP)) {
                        //entityTurnInProg = true;
                        //this.playTurn = true;
                        roundStarted = true;
                        map.bernard.setDirection(Constants.Direction.UP);
                        map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                        Gdx.app.log("MOVING", "UP");
                        map.playTurn(activeEntity);
                    } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Constants.Direction.DOWN)) {
                        //entityTurnInProg = true;
                        //this.playTurn = true;
                        roundStarted = true;
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
                            map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                            Gdx.app.log("MOVING", "RIGHT");
                            map.playTurn(activeEntity);
                        }
                    }

                    //Use Item
                    if (Gdx.input.isKeyJustPressed(Keys.Q)) {
                        map.bernard.useItem();
                    }

                    //Skills
                    if (Gdx.input.isKeyJustPressed(Keys.SPACE) && map.bernard.redLaserCooldown <= 0 && map.bernard.getLevel() >= Constants.REDLASERREQ) {
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

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_1) && map.bernard.getLevel() >= Constants.SKILLONEREQ) {
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

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_2) && map.bernard.getLevel() >= Constants.SKILLTWOREQ) {
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

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_3) && map.bernard.getLevel() >= Constants.DETECTIONREQ) {
                        roundStarted = true;
                        map.bernard.setExecuteDetection(true);
                        map.bernard.setSkill(Constants.SkillName.DETECTION);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_4) && map.bernard.barrierCooldown <= 0 && !map.bernard.getExecuteBarrier() && map.bernard.getLevel() >= Constants.BARRIERREQ) {
                        roundStarted = true;
                        map.bernard.setBarrierLimit(3);
                        map.bernard.setExecuteBarrier(true);
                        map.bernard.setSkill(Constants.SkillName.BARRIERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_5) && map.bernard.lightningInfusionCooldown <= 0 && !map.bernard.getExecuteLightBarrier() && map.bernard.getLevel() >= Constants.LIGHTBARRIERREQ) {
                        roundStarted = true;
                        map.bernard.setLightBarrierLimit(3);
                        map.bernard.setExecuteLightBarrier(true);
                        map.bernard.setSkill(Constants.SkillName.LIGHTBARRIERSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_6) && map.bernard.freezingCooldown <= 0 && map.bernard.getLevel() >= Constants.FREEZINGREQ) {
                        roundStarted = true;
                        map.bernard.setSkill(Constants.SkillName.FREEZINGSKILL);
                        map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                        map.bernard.setActiveSkill();
                        map.bernard.attackAction();
                        for (DamageEntity d : map.bernard.getActiveSkill().damageEntities) {
                            map.miscEntityList.add(d);
                        }
                    }

                    if (Gdx.input.isKeyJustPressed(Keys.NUM_7) && map.bernard.fusionCooldown <= 0 && map.bernard.getLevel() >= Constants.LASERREQ) {
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
                        this.setHelp(false);
                        this.pause();
                    }
                    if (Gdx.input.isKeyJustPressed(Keys.H)) {
                        this.setHelp(true);
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
                    aggressor.dispose();
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
                    aggressor.dispose();
                    map.miscEntityList.remove(i);
                } else {
                    for (int j = 0; j < map.getEntityList().size(); j++) {
                        aggressor.collision(map.getEntityList().get(j));
                    }
                }
                if (bernard.getSliding()) {
                    map.removeFogAroundBernard();
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
            healthLabel.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2 - 100);
            healthLabel.setText("Level: " + map.bernard.getLevel() + "    " + healthpoints);

            //temporary inventory display
//            invent.setX(map.bernard.getX() - Constants.SCREENWIDTH / 2 + map.bernard.getWidth() * 0.75f);
//            invent.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2 - map.bernard.getHeight() / 2);
            //temporary turn display
            String temp = "";
            if (roundStarted) {
                temp = "Turn Underway";
            }
            turnLabel.setX(map.bernard.getX());
            turnLabel.setY(map.bernard.getY() + Constants.SCREENHEIGHT / 2 - 110);
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
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            //Target Square
            if (targetToggle == true) {
                if (map.bernard.getDirection() == Constants.Direction.UP) {
                    batch.draw(TextureLoader.TARGETSQUARE, map.bernard.getX() - 5, map.bernard.getY() + Constants.TILEDIMENSION - 3, Constants.TILEDIMENSION + 10, Constants.TILEDIMENSION + 10);
                } else if (map.bernard.getDirection() == Constants.Direction.DOWN) {
                    batch.draw(TextureLoader.TARGETSQUARE, map.bernard.getX() - 5, map.bernard.getY() - Constants.TILEDIMENSION - 3, Constants.TILEDIMENSION + 10, Constants.TILEDIMENSION + 10);
                } else if (map.bernard.getDirection() == Constants.Direction.LEFT) {
                    batch.draw(TextureLoader.TARGETSQUARE, map.bernard.getX() - Constants.TILEDIMENSION - 5, map.bernard.getY() - 3, Constants.TILEDIMENSION + 10, Constants.TILEDIMENSION + 10);
                } else if (map.bernard.getDirection() == Constants.Direction.RIGHT) {
                    batch.draw(TextureLoader.TARGETSQUARE, map.bernard.getX() + Constants.TILEDIMENSION - 5, map.bernard.getY() - 3, Constants.TILEDIMENSION + 10, Constants.TILEDIMENSION + 10);
                }
            }

            batch.end();

            stage.draw();
            map.renderFog();
            map.renderMiniMap();

            uibatch.begin();
            gameUI.draw(uibatch, delta);
            uibatch.end();

            centerCameraOn(map.bernard);
            camera.update();

            //Separate mouse coordinates from the screen to the actual game map
            mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePosition);

            /* -- END RENDER -- */
        } else {
            if (showFullMap) {
                if (Gdx.input.isKeyJustPressed(Keys.M)) {
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
                if (help) {
                    helpScreen.render(delta);
                } else {
                    pauseScreen.render(delta);
                }
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
            map = new Map(this.bernard, "maps/testmap2.tmx");
            level = 2;
        } else if (level == 2) {
            map = new Map(this.bernard, "colemap/colemap.tmx");
            level = 0;
        }
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
        //       stage.addActor(invent);
        stage.addActor(turnLabel);

        map.bernard.getLevelUpDialog().show(stage);
        map.bernard.getLevelUpDialog().setVisible(false);
        map.bernard.getNewSkillDialog().show(stage);
        map.bernard.getNewSkillDialog().setVisible(false);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();

        batch = new SpriteBatch();
        uibatch = new SpriteBatch();
        pauseScreen = new PauseScreen(this);
        helpScreen = new HelpScreen(this);

        mousePosition = new Vector3();

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        invent = new Inventory(TextureLoader.INVENTORYTEXTURE, 0, 0);
        invent.setPosition(658, 0);
        healthLabel = new Label("HP: ", TextureLoader.SKIN);
        bernard = new Protagonist(0, 0);
        activeEntity = bernard;
        entityTurn = 0;
        roundStarted = false;
        turnLabel = new Label("", TextureLoader.SKIN);

        initNewLevel();

        map.bernard.getLevelUpDialog().show(stage);
        map.bernard.getLevelUpDialog().setVisible(false);
        map.bernard.getNewSkillDialog().show(stage);
        map.bernard.getNewSkillDialog().setVisible(false);
        Gdx.input.setInputProcessor(stage);

        turnCount = 0;
        gameUI = new GameUI(this);
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
        paused = true;
        if (help) {
            helpScreen.show();
        } else {
            pauseScreen.show();
        }
    }

    public void setHelp(boolean a) {
        help = a;
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
        pauseScreen.setInvX(175);
        pauseScreen.setInvY(375);
        invent.setPosition(658, 0);
        paused = false;
        help = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // never called automatically
        if(batch != null){
            map.dispose();
            helpScreen.dispose();
            pauseScreen.dispose();
            gameUI.dispose();
            stage.dispose();
            uibatch.dispose();
            batch.dispose();
        }
        textureLoader.dispose();
            
    }
}
