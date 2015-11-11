package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.ArrayList;
import java.util.List;

public class Map {

    private TiledMap tiledMap;
    private TiledMapRenderer renderer;
    private Constants.MapGridCode[][] mapGrid;
    private Constants.EntityGridCode[][] entityGrid;
    private List<Entity> entityList;
    private int tileDimension;
    private int width;
    private int height;
    public Protagonist bernard;
    private OrthographicCamera cameraMiniMap;
    private OrthographicCamera cameraFullMap;
    private TiledMapTileLayer thickFogLayer;
    private TiledMapTileLayer thinFogLayer;
    private int[] mapIndex = {0};
    private int[] fogIndex = {2,3};

    public static List<Entity> miscEntityList;

    public Map(Protagonist bernard, String tmxFileName) {
        this.bernard = bernard;
        this.tiledMap = new TmxMapLoader().load(tmxFileName);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer entityLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        thinFogLayer = (TiledMapTileLayer) tiledMap.getLayers().get(2);
        thickFogLayer = (TiledMapTileLayer) tiledMap.getLayers().get(3);

        //Setup mapGrid
        mapGrid = new Constants.MapGridCode[mapLayer.getWidth()][mapLayer.getHeight()];
        for (int x = 0; x < mapLayer.getWidth(); x++) {
            for (int y = 0; y < mapLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = mapLayer.getCell(x, y);
                MapProperties properties = cell.getTile().getProperties();
                if (properties.get("WALL") != null) {
                    mapGrid[x][y] = Constants.MapGridCode.WALL;
                } else if (properties.get("STRUCTURE") != null) {
                    mapGrid[x][y] = Constants.MapGridCode.STRUCTURE;
                } else if (properties.get("EXIT") != null) {
                    mapGrid[x][y] = Constants.MapGridCode.EXIT;
                } else {
                    mapGrid[x][y] = Constants.MapGridCode.FLOOR;
                }

                //To animate a cell tile, see below
                //cell.setTile(new AnimatedTiledMapTile(0.5f,waterTiles));
            }
        }

        //Setup entityGrid
        miscEntityList = new ArrayList<Entity>();
        entityList = new ArrayList<Entity>();
        entityGrid = new Constants.EntityGridCode[entityLayer.getWidth()][entityLayer.getHeight()];
        for (int x = 0; x < entityLayer.getWidth(); x++) {
            for (int y = 0; y < entityLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = entityLayer.getCell(x, y);
                if (cell == null) {
                    entityGrid[x][y] = Constants.EntityGridCode.NONE;
                    continue;
                }
                MapProperties properties = cell.getTile().getProperties();
                if (properties.get("PLAYER") != null) {
                    entityGrid[x][y] = Constants.EntityGridCode.PLAYER;
                    initBernard(x, y);
                } else if (properties.get("ENEMY") != null) {
                    if (properties.get("CatLady") != null) {
                        initEnemy(x, y, Constants.EnemyType.CATLADY);
                    } else if (properties.get("CatAttack") != null) {
                        initEnemy(x, y, Constants.EnemyType.CATATTACK);
                    } else if (properties.get("Drunk") != null) {
                        initEnemy(x, y, Constants.EnemyType.DRUNK);
                    } else if (properties.get("Wanderer") != null) {
                        initEnemy(x, y, Constants.EnemyType.WANDERER);
                    } else if (properties.get("Blues") != null) {
                        initEnemy(x, y, Constants.EnemyType.BLUES);
                    } else if (properties.get("Wreker") != null) {
                        initEnemy(x, y, Constants.EnemyType.WREKER);
                    } else if (properties.get("Hammer") != null) {
                        initEnemy(x, y, Constants.EnemyType.HAMMER);
                    } else if (properties.get("Suffragette") != null) {
                        initEnemy(x, y, Constants.EnemyType.SUFFRAGETTE);
                    } else if (properties.get("GreyGate") != null) {
                        if (properties.get("Left") != null) {
                            initGreyGate(x, y, Constants.GateType.LEFT);
                        } else if (properties.get("Right") != null) {
                            initGreyGate(x, y, Constants.GateType.RIGHT);
                        } else {
                            Gdx.app.log("MAP CREATION", "ENEMY type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                            entityGrid[x][y] = Constants.EntityGridCode.NONE;
                            continue;
                        }
                    } else if (properties.get("RedGate") != null) {
                        if (properties.get("Left") != null) {
                            initRedGate(x, y, Constants.GateType.LEFT);
                        } else if (properties.get("Right") != null) {
                            initRedGate(x, y, Constants.GateType.RIGHT);
                        } else {
                            Gdx.app.log("MAP CREATION", "ENEMY type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                            entityGrid[x][y] = Constants.EntityGridCode.NONE;
                            continue;
                        }
                    } else {
                        Gdx.app.log("MAP CREATION", "ENEMY type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                        entityGrid[x][y] = Constants.EntityGridCode.NONE;
                        continue;
                    }
                    entityGrid[x][y] = Constants.EntityGridCode.ENEMY;
                } else if (properties.get("ITEM") != null) {
                    if (properties.get("Health") != null) {
                        initItem(x, y, Constants.ItemType.HEALTH);
                    } else if (properties.get("Shield") != null) {
                        initItem(x, y, Constants.ItemType.SHIELD);
                    } else if (properties.get("Whistle") != null) {
                        initItem(x, y, Constants.ItemType.WHISTLE);
                    } else if (properties.get("RedKey") != null) {
                        initItem(x, y, Constants.ItemType.REDKEY);
                    } else if (properties.get("GreyKey") != null) {
                        initItem(x, y, Constants.ItemType.GREYKEY);
                    } else {
                        Gdx.app.log("MAP CREATION", "ITEM type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                        entityGrid[x][y] = Constants.EntityGridCode.NONE;
                        continue;
                    }
                    entityGrid[x][y] = Constants.EntityGridCode.ITEM;
                } else if (properties.get("TRAP") != null) {
                    if (properties.get("Trap1") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP1);
                    } else if (properties.get("Trap2") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP2);
                    } else if (properties.get("Trap3") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP3);
                    } else if (properties.get("Trap4") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP4);
                    } else if (properties.get("Trap5") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP5);
                    } else if (properties.get("SlideTile") != null) {
                        if (properties.get("Up") != null) {
                            initArrowTrap(x, y, Constants.ArrowType.UP);
                        } else if (properties.get("Down") != null) {
                            initArrowTrap(x, y, Constants.ArrowType.DOWN);
                        } else if (properties.get("Left") != null) {
                            initArrowTrap(x, y, Constants.ArrowType.LEFT);
                        } else if (properties.get("Right") != null) {
                            initArrowTrap(x, y, Constants.ArrowType.RIGHT);
                        } else {
                            Gdx.app.log("MAP CREATION", "ARROW type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                            entityGrid[x][y] = Constants.EntityGridCode.NONE;
                            continue;
                        }
                    } else if (properties.get("Blocker") != null) {
                        initTrap(x, y, Constants.TrapType.BLOCKER);
                    } else {
                        Gdx.app.log("MAP CREATION", "TRAP type at entityLayer(" + x + ")(" + y + ") is unknown. Creation skipped.");
                        entityGrid[x][y] = Constants.EntityGridCode.NONE;
                        continue;
                    }
                    entityGrid[x][y] = Constants.EntityGridCode.TRAP;
                } else {
                    //    Gdx.app.log("MAP CREATION", "Unknown property at entityLayer("+x+")("+y+"). Creation skipped.");
                    entityGrid[x][y] = Constants.EntityGridCode.NONE;
                }

                //To animate a cell tile, see below
                //cell.setTile(new AnimatedTiledMapTile(0.5f,waterTiles));
            }
        }
        // Populate Entity Grid and List for testing
        populateMapForTesting();

        this.tileDimension = Constants.TILEDIMENSION;
        this.width = mapGrid.length * tileDimension;
        this.height = mapGrid[0].length * tileDimension;

        this.cameraFullMap = new OrthographicCamera(this.width, this.height);
        this.cameraFullMap.zoom = 1.1f;
        this.cameraFullMap.translate(this.width / 2, this.height / 2);
        this.cameraFullMap.update();

        this.cameraMiniMap = new OrthographicCamera(this.width, this.height);
        this.cameraMiniMap.zoom = 4f;
        this.cameraMiniMap.translate(-this.width * 0.99f, this.height * 1.98f);
        this.cameraMiniMap.update();
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render(mapIndex);
    }

    public void renderFog() {
        renderer.render(fogIndex);
    }

    public void renderFullMap() {
        renderer.setView(this.cameraFullMap);
        renderer.render();
    }

    public void renderMiniMap() {
        renderer.setView(this.cameraMiniMap);
        renderer.render();
    }

    private void removeThickFog(int cX, int cY) {
        if(cX >= mapGrid.length || cY >= mapGrid[0].length || cX < 0 || cY < 0)
            return;
        if (thickFogLayer.getCell(cX, cY).getTile() != null) {
            thickFogLayer.getCell(cX, cY).setTile(null);
        }
    }
    
    private void removeThinFog(int cX, int cY) {
        if(cX >= mapGrid.length || cY >= mapGrid[0].length || cX < 0 || cY < 0)
            return;
        if (thinFogLayer.getCell(cX, cY).getTile() != null) {
            thinFogLayer.getCell(cX, cY).setTile(null);
        }
    }
    
    public void removeFogAroundBernard(){
        int x = -bernard.sightRadius;
        int y = bernard.sightRadius;
        for (int i = 0; i < 1+bernard.sightRadius*2; i++) {
            y = bernard.sightRadius;
            for (int j = 0; j < 1+bernard.sightRadius*2; j++) {
                if(Math.round(Vector2.dst(0, 0, x, y)) <= bernard.sightRadius)
                    removeThickFog(bernard.getCX() + x, bernard.getCY() + y);
                if(Math.round(Vector2.dst(0, 0, x, y)) <= bernard.sightRadius-1)
                    removeThinFog(bernard.getCX() + x, bernard.getCY() + y);
                y--;
            }
            x++;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Constants.MapGridCode[][] getMapGrid() {
        return this.mapGrid;
    }

    public Constants.EntityGridCode[][] getEntityGrid() {
        return this.entityGrid;
    }

    public List<Entity> getEntityList() {
        return this.entityList;
    }

    private void initBernard(int cX, int cY) {
        bernard.seqAction = new SequenceAction();
        bernard.clearActions();
        bernard.setCX(cX);
        bernard.setCY(cY);
        bernard.setPCX(cX);
        bernard.setPCY(cY);
        bernard.setX(bernard.getCX() * Constants.TILEDIMENSION);
        bernard.setY(bernard.getCY() * Constants.TILEDIMENSION);
        entityList.add(bernard);
        //Remove fog
        this.removeFogAroundBernard();
    }

    private void initEnemy(int cX, int cY, Constants.EnemyType enemyType) {
        switch (enemyType) {
            case CATLADY:
                entityList.add(new CatLady(cX, cY));
                break;
            case CATATTACK:
                entityList.add(new CatAttack(cX, cY));
                break;
            case DRUNK:
                entityList.add(new Drunk(cX, cY));
                break;
            case WANDERER:
                entityList.add(new Wanderer(cX, cY));
                break;
            case BLUES:
                entityList.add(new Blues(cX, cY));
                break;
            case WREKER:
                entityList.add(new Wreker(cX, cY));
                break;
            case HAMMER:
                entityList.add(new Hammer(cX, cY));
                break;
            case SUFFRAGETTE:
                entityList.add(new Suffragette(cX, cY, Constants.Direction.NONE, 0));
                break;
            case GREYGATE:
                break;
            case REDGATE:
                break;
            default:
                //ERROR
                Gdx.app.log("ERROR", "Enemy type not found. Not added to entity grid.");
                break;
        }
    }

    private void initItem(int cX, int cY, Constants.ItemType itemType) {
        switch (itemType) {
            case HEALTH:
                miscEntityList.add(new ItemHeart(cX, cY));
                break;
            case SHIELD:
                miscEntityList.add(new ItemShield(cX, cY));
                break;
            case WHISTLE:
                miscEntityList.add(new ItemWhistle(cX, cY));
                break;
            case REDKEY:
                miscEntityList.add(new RedKey(cX, cY));
                break;
            case GREYKEY:
                miscEntityList.add(new GreyKey(cX, cY));
                break;
            default:
                //ERROR
                Gdx.app.log("ERROR", "Item type not found. Not added to entity grid.");
                break;
        }
    }

    private void initTrap(int cX, int cY, Constants.TrapType trapType) {
        switch (trapType) {
            case TRAP1:
                miscEntityList.add(new TrapType1(cX, cY));
                break;
            case TRAP2:
                miscEntityList.add(new TrapType2(cX, cY));
                break;
            case TRAP3:
                miscEntityList.add(new TrapType3(cX, cY));
                break;
            case TRAP4:
                miscEntityList.add(new TrapType4(cX, cY));
                break;
            case TRAP5:
                miscEntityList.add(new TrapType5(cX, cY));
                break;
            case BLOCKER:
                miscEntityList.add(new Blocker(cX, cY));
                break;
            default:
                //ERROR
                Gdx.app.log("ERROR", "Trap type not found. Not added to entity grid.");
                break;
        }
    }

    private void initArrowTrap(int cX, int cY, Constants.ArrowType arrowType) {
        switch (arrowType) {
            case UP:
                miscEntityList.add(new SlideTile(cX, cY, Constants.Direction.UP, TextureLoader.SLIDEUP));
                break;
            case DOWN:
                miscEntityList.add(new SlideTile(cX, cY, Constants.Direction.DOWN, TextureLoader.SLIDEDOWN));
                break;
            case LEFT:
                miscEntityList.add(new SlideTile(cX, cY, Constants.Direction.LEFT, TextureLoader.SLIDELEFT));
                break;
            case RIGHT:
                miscEntityList.add(new SlideTile(cX, cY, Constants.Direction.RIGHT, TextureLoader.SLIDERIGHT));
                break;

            default:
                //ERROR
                Gdx.app.log("ERROR", "Arrow type not found. Not added to entity grid.");
                break;
        }
    }

    private void initGreyGate(int cX, int cY, Constants.GateType gateType) {
        switch (gateType) {
            case LEFT:
                entityList.add(new GreyGate(cX, cY, Constants.Direction.LEFT));
                break;
            case RIGHT:
                entityList.add(new GreyGate(cX, cY, Constants.Direction.RIGHT));
                break;

            default:
                //ERROR
                Gdx.app.log("ERROR", "Gate type not found. Not added to entity grid.");
                break;
        }
    }

    private void initRedGate(int cX, int cY, Constants.GateType gateType) {
        switch (gateType) {
            case LEFT:
                entityList.add(new RedGate(cX, cY, Constants.Direction.LEFT));
                break;
            case RIGHT:
                entityList.add(new RedGate(cX, cY, Constants.Direction.RIGHT));
                break;

            default:
                //ERROR
                Gdx.app.log("ERROR", "Gate type not found. Not added to entity grid.");
                break;
        }
    }

    private void populateMapForTesting() {
        List<Entity> tempAntagList = new ArrayList<Entity>();
        List<Entity> tempMiscList = new ArrayList<Entity>();

        /*  ADDING ENTITIES NOT ON TILE SHEETS TO THE MAP
         INSTRCTIONS BELOW
        
         - Add antagonists to tempAntagList
         - Add items,traps, or non moving entities to tempMiscList
         - testmap 1
         - [1][7] - [17][10] empty testing area
         - [9][2] Bernard position
         - testmap 2 - 20x20 map, fully empty
         - [1][1] - [18][17] empty testing area
         - [1][17] Bernard position
         - Be aware that entities placed on any map.tmx will hold priority
         at its location on the entityGrid. Entities created here will be ignored
         if placed on the same tile as an entity from any map.tmx. Please
         place them in an empty block
         - An error message will appear if it is not created for the above reason
         */
        //Add for testmap 1
        if (GameScreen.level == 0) {
            Gdx.app.log("Map", "Adding entities for map level=0");
            tempAntagList.add(new CatLady(9, 21));
            tempAntagList.add(new CatAttack(9, 22));
            tempAntagList.add(new CatAttack(9, 20));
            tempAntagList.add(new CatAttack(8, 21));
            tempAntagList.add(new CatAttack(10, 21));
            tempAntagList.add(new CatAttack(8, 22));
            tempAntagList.add(new CatAttack(10, 20));

            tempAntagList.add(new Wanderer(9, 4));

           // tempAntagList.add(new Suffragette(5, 7, Constants.Direction.UP, 2));
            tempAntagList.add(new Wreker(9,3));
            tempAntagList.add(new Blues(17,3));

        } //Add for testmap 2
        else if (GameScreen.level == 1) {
            Gdx.app.log("Map", "Adding entities for map level=1");
        } //Add for Cole's Map
        else if (GameScreen.level == 2) {
            Gdx.app.log("Map", "Adding entities for map level=2");
        } else {
            Gdx.app.log("Map", "Level out of scope");
            return;
        }

        // Populate the cells from tempAntagList and add to entity list
        for (int i = 0; i < tempAntagList.size(); i++) {
            Entity entity = tempAntagList.get(i);
            if (this.entityGrid[entity.getCX()][entity.getCY()] == Constants.EntityGridCode.NONE) {
                this.entityGrid[entity.getCX()][entity.getCY()] = entity.getGridCode();
                entityList.add(entity);
            } else {
                Gdx.app.log("ENTITY PLACEMENT ERROR", "[" + entity.getName()
                        + "] could not be placed at (" + entity.getCX() + "," + entity.getCY()
                        + ") because another entity exist there. Please choose a different coordinate.");
            }

        }
        // Populate the cells from tempMiscList and add to entity list
        for (int i = 0; i < tempMiscList.size(); i++) {
            Entity entity = tempMiscList.get(i);
            if (this.entityGrid[entity.getCX()][entity.getCY()] == Constants.EntityGridCode.NONE) {
                this.entityGrid[entity.getCX()][entity.getCY()] = entity.getGridCode();
                miscEntityList.add(entity);
            } else {
                Gdx.app.log("ENTITY PLACEMENT ERROR", "[" + entity.getName()
                        + "] could not be placed at (" + entity.getCX() + "," + entity.getCY()
                        + ") because another entity exist there. Please choose a different coordinate.");
            }
        }
    }

    public boolean bernardCanMove(Constants.Direction direction) {
        if (direction == Constants.Direction.UP) {
            if (bernard.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() + 1] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() + 1] != Constants.EntityGridCode.ENEMY)) {
                bernard.setCY(bernard.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (bernard.getCY() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() - 1] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() - 1] != Constants.EntityGridCode.ENEMY)) {
                bernard.setCY(bernard.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (bernard.getCX() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX() - 1][bernard.getCY()] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX() - 1][bernard.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() - 1][bernard.getCY()] != Constants.EntityGridCode.ENEMY)) {
                bernard.setCX(bernard.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (bernard.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX() + 1][bernard.getCY()] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX() + 1][bernard.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() + 1][bernard.getCY()] != Constants.EntityGridCode.ENEMY)) {
                bernard.setCX(bernard.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    public boolean enemyCanMove(Entity entity, Constants.Direction direction) {
        if (direction == Constants.Direction.UP) {
            if (entity.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[entity.getCX()][entity.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX()][entity.getCY() + 1] == Constants.EntityGridCode.NONE) {
                entity.setCY(entity.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (entity.getCY() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX()][entity.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX()][entity.getCY() - 1] == Constants.EntityGridCode.NONE) {
                entity.setCY(entity.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (entity.getCX() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX() - 1][entity.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX() - 1][entity.getCY()] == Constants.EntityGridCode.NONE) {
                entity.setCX(entity.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (entity.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[entity.getCX() + 1][entity.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX() + 1][entity.getCY()] == Constants.EntityGridCode.NONE) {
                entity.setCX(entity.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    public void moveEntity(Entity entity) {
        entityGrid[entity.getPCX()][entity.getPCY()] = Constants.EntityGridCode.NONE;
        entityGrid[entity.getCX()][entity.getCY()] = entity.getGridCode();
        entity.setPCX(entity.getCX());
        entity.setPCY(entity.getCY());
    }

    public boolean collision(Entity aggressor, Entity receiver) {
        if (aggressor.getCX() == receiver.getCX()
                && aggressor.getCY() == receiver.getCY()) {
            aggressor.collision(receiver);
            return true;
        }
        return false;
    }

    public boolean isAlive(Entity entity) {
        if (entity.isDead()) {
            //entityGrid[entity.getCX()][entity.getCY()] = EntityGridCode.EMPTY;
            Gdx.app.log("ENTITY", "DEAD");
            return false;
        }
        return true;
    }

    public void removeEntityFromGrid(Entity entity) {
        if (entity.getGridCode() == Constants.EntityGridCode.ITEM || entity.getGridCode() == Constants.EntityGridCode.TRAP) {
            entityGrid[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.PLAYER;
        } else if (entity instanceof DamageEntity) {
        } else {
            entityGrid[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.NONE;
        }
    }

    public boolean exitReached() {
        if (mapGrid[bernard.getCX()][bernard.getCY()] == Constants.MapGridCode.EXIT
                && bernard.turnFinished) {
            return true;
        }
        return false;
    }

    public void calculateAITurn(Entity entity) {
        if (entity.getGridCode() == Constants.EntityGridCode.ENEMY) {
            Antagonist enemy = (Antagonist) entity;

            //Change this method to however you want enemies to behave
            enemy.calculateTurn(mapGrid, entityGrid, entityList);

            if (enemy.getTurnAction() == Constants.TurnAction.MOVE) {
                moveEntity(enemy);
            } else if (enemy.getTurnAction() == Constants.TurnAction.ATTACK && enemy.getDamageEntities() != null) {
                for (int i = 0; i < enemy.getDamageEntities().length; i++) {
                    this.miscEntityList.add(enemy.getDamageEntities()[i]);
                    //Change melee skill and use like bernard instead Adrian ya nooblet
                }
            }
        }
    }

    public void playTurn(Entity entity) {
        if (entity instanceof Protagonist) {
            if (entity.getTurnAction() == Constants.TurnAction.MOVE) {
                moveEntity(entity);
                this.removeFogAroundBernard();
            }
            entity.performActions();
        } else if (entity instanceof Antagonist) {
            calculateAITurn(entity);
            entity.performActions();
        } else {
            entity.performActions();
        }
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
