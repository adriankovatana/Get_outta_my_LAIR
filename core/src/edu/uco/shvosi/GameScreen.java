package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private MyGdxGame game;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Map map;
    private Stage stage;
    private String healthpoints;
    private Skin skin;
    private Label healthLabel;
    public static Inventory invent;
    private TextureLoader textureLoader = new TextureLoader();

    private int level = 0;
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

        /* == INPUT == */
        /* Bernard Controls */
        if (!roundStarted && activeEntity instanceof Protagonist) {

            //Movement
            if (Gdx.input.isKeyJustPressed(Keys.W) && map.bernardCanMove(Constants.Direction.UP)) {
            //entityTurnInProg = true;
                //this.playTurn = true;
                roundStarted = true;
                map.bernard.notifyObservers();
                map.bernard.setDirection(Constants.Direction.UP);
                map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                Gdx.app.log("MOVING", "UP");
            } else if (Gdx.input.isKeyJustPressed(Keys.S) && map.bernardCanMove(Constants.Direction.DOWN)) {
            //entityTurnInProg = true;
                //this.playTurn = true;
                roundStarted = true;
                map.bernard.notifyObservers();
                map.bernard.setDirection(Constants.Direction.DOWN);
                map.bernard.setTurnAction(Constants.TurnAction.MOVE);
                Gdx.app.log("MOVING", "DOWN");
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
                }
            }

        //Use Item
//        if (Gdx.input.isKeyJustPressed(Keys.Q)) {
//            map.bernard.useItem();
//        }
//
            //Skills
            if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
                roundStarted = true;
                map.bernard.setSkill(Protagonist.SkillName.REDLASERSKILL);
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
                map.bernard.setSkill(Protagonist.SkillName.SKILLONE);
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
                map.bernard.setSkill(Protagonist.SkillName.SKILLTWO);
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
                map.bernard.setSkill(Protagonist.SkillName.DETECTION);
                map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                map.bernard.setActiveSkill();
                map.bernard.attackAction();
        }

        if (Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
                roundStarted = true;
                map.bernard.setSkill(Protagonist.SkillName.BARRIERSKILL);
                map.bernard.setTurnAction(Constants.TurnAction.ATTACK);
                map.bernard.setActiveSkill();
                map.bernard.attackAction();
        }
//        if (map.bernard.getBarrierLimit() == 0) {
//            map.bernard.setHeal(true);
//        }
        }

        /* -- END INPUT -- */
        /* == UPDATE == */
        //Check if dead
        for (int i = 0; i < map.getEntityList().size(); i++) {
            Entity aggressor = map.getEntityList().get(i);

            if (aggressor.isDead()) {
                aggressor.performDeath();
                map.removeEntityFromGrid(aggressor);
                map.getEntityList().remove(i);
            }
        }

        // -- Play the turn --
        //Sets the next active entity when it is done with its turn
        if (roundStarted && activeEntity.turnFinished) {
            activeEntity = map.getEntityList().get(++entityTurn % map.getEntityList().size());

            //If we have reached bernard, the turn is over and set all turnfinished to false;
            if (activeEntity instanceof Protagonist) {
                roundStarted = false;
                for (int i = 0; i < map.getEntityList().size(); i++) {
                    map.getEntityList().get(i).setTurnFinished(false);
                }
//                for (int i = 0; i < map.miscEntityList.size(); i++) {
//                    map.miscEntityList.get(i).setTurnFinished(false);
//                }
            }
        }

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

        //Check collision
//        for (int i = 0; i < map.miscEntityList.size(); i++) {
//            map.miscEntityList.get(i).collision(activeEntity);
//        }
        //Add actions to the activeEntity if it just started its turn!
        if (roundStarted && !activeEntity.hasActions()) {
            map.playTurn(activeEntity);
            //Gdx.app.log("TURN", activeEntity.getName());
        }

        if (map.exitReached()) {
            //Load the next level
            activeEntity.clearActions();
            activeEntity = bernard;
            entityTurn = 0;
            roundStarted = false;
            stage.clear();
            map.dispose();
            initNewLevel();
        }

//        if(!entityTurnInProg && activeEntity.turnFinished && !activeEntity.hasActions()){
//            // Add Actions for AI
//            if(!(activeEntity instanceof Protagonist)){
//                entityTurnInProg = true;
//            }
//        }
//        
//        if(entityTurnInProg && activeEntity.turnFinished && !activeEntity.hasActions()){
//            entityTurnInProg = false;
//            activeEntity = map.getEntityList().get(++entityTurn % map.getEntityList().size());
//        }
//        if (this.playTurn) {
//            this.playTurn = false;
//
//            // Temporary back to start screen if bernard is dead
//            if (map.bernard.isDead()) {
//                game.setScreen(game.startScreen);
//            }
//            
//            //Main Turn Loop
//            for (int i = 1; i < map.getEntityList().size(); i++) {
//                map.collision(map.bernard, map.getEntityList().get(i));
//            }
//            map.playTurn(map.bernard);
//            
//            for (int i = 1; i < map.getEntityList().size(); i++) {
//                Entity aggressor = map.getEntityList().get(i);
//
////                //Check if the aggressor is dead, do death stuff and go to next iteration if so
////                if (aggressor.isDead()) {
////                    aggressor.performDeath();
////                    map.removeEntityFromGrid(aggressor);
////                    map.getEntityList().remove(i);
////                    continue;
////                }
//
//                //Check aggressor's collision with all other entities
//                for (int j = 0; j < map.getEntityList().size(); j++) {
//                    if (i != j) {
//
//                        map.collision(aggressor, map.getEntityList().get(j));
//
//                    }
//                }
//
//                //Play aggressor's turn
//                map.playTurn(aggressor);
//            }
//            
//            if (map.exitReached()) {
//                //Load the next level
//                map.bernard.turnFinished = true;
//                map.bernard.clearActions();
//                stage.clear();
//                map.dispose();
//                initNewLevel();
//            }
//        }
//        //Check if all actions for the turn is finished
//        for (int i = 0; i < map.getEntityList().size(); i++) {
//            if (!map.getEntityList().get(i).turnFinished()) {
//                //Gdx.app.log(map.getEntityList().get(i).getName(), "Turn not finished...");
//                this.turnsFinished = false;
//                break;
//            }
//            this.turnsFinished = true;
//        }
        //temporary health display
        healthpoints = "HP: " + map.bernard.getHealth();
        healthLabel.setX(map.bernard.getX() + 25);
        healthLabel.setY(map.bernard.getY() + 250);
        healthLabel.setText(healthpoints);

        //temporary inventory display
        invent.setX(map.bernard.getX() - 325);
        invent.setY(map.bernard.getY() + 175);

        //temporary turn display
        String temp = "";
        if (roundStarted) {
            temp = "Turn Underway";
        }
        turnLabel.setX(map.bernard.getX() + 10);
        turnLabel.setY(map.bernard.getY() + 240);
        turnLabel.setText(temp);


        /* -- END UPDATE -- */

        /* == RENDER == */
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(camera);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        centerCameraOn(map.bernard);
        camera.update();
        /* -- END RENDER -- */
    }

    public void centerCameraOn(Entity entity) {
        camera.position.x = entity.getX() + entity.getWidth() / 2;
        camera.position.y = entity.getY() + entity.getHeight() / 2;
    }

    public void initNewLevel() {
        //Test Level
        if (level == 0) {
            map = new Map(this.bernard, "maps/testmap.tmx");
            map.bernard.removeAllObservers();
            level = 1;
        } else if (level == 1) {
            map = new Map(this.bernard, "maps/testmap2.tmx");
            map.bernard.removeAllObservers();
            level = 0;
        }

        initStage();
    }

    public void initStage() {
        // Add the entities to the stage
        for (int i = map.getEntityList().size() - 1; i > -1; i--) {
            stage.addActor(map.getEntityList().get(i));
        }
        for (int i = map.miscEntityList.size() - 1; i > -1; i--) {
            stage.addActor(map.miscEntityList.get(i));
        }

        //Health Display
        stage.addActor(healthLabel);
        stage.addActor(invent);
        stage.addActor(turnLabel);
        //bernard.clearActions();
    }

    @Override
    public void show() {
        // called when this screen is set as the screen with game.setScreen();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        //Initialize Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        FitViewport fv = new FitViewport(Constants.SCREENWIDTH, Constants.SCREENHEIGHT, camera);
        stage = new Stage(fv, batch);
        invent = new Inventory(TextureLoader.INVENTORYTEXTURE, 5, 0);
        healthLabel = new Label("HP: ", skin);
        bernard = new Protagonist(0, 0);
        activeEntity = bernard;
        //entityTurnInProg = false;
        entityTurn = 0;
        roundStarted = false;
        turnLabel = new Label("", skin);

        initNewLevel();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // never called automatically
        //batch.dispose();
        stage.dispose();
        skin.dispose();
    }
}
