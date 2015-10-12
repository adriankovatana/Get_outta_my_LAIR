package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    public Map(Protagonist bernard, String tmxFileName) {
        this.bernard = bernard;
        this.tiledMap = new TmxMapLoader().load(tmxFileName);
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer entityLayer = (TiledMapTileLayer) tiledMap.getLayers().get(1);

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
        entityList = new ArrayList<Entity>();
        entityGrid = new Constants.EntityGridCode[entityLayer.getWidth()][entityLayer.getHeight()];
        for (int x = 0; x < entityLayer.getWidth(); x++) {
            for (int y = 0; y < entityLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = entityLayer.getCell(x, y);
                MapProperties properties = cell.getTile().getProperties();
                if (properties.get("PLAYER") != null) {
                    entityGrid[x][y] = Constants.EntityGridCode.PLAYER;
                    initBernard(x, y);
                } else if (properties.get("ENEMY") != null) {
                    entityGrid[x][y] = Constants.EntityGridCode.ENEMY;
                    if (properties.get("CatLady") != null) {
                        initEnemy(x, y, Constants.EnemyType.CATLADY);
                    } else if (properties.get("Drunk") != null) {
                        initEnemy(x, y, Constants.EnemyType.DRUNK);
                    } else if (properties.get("Wanderer") != null) {
                        initEnemy(x, y, Constants.EnemyType.WANDERER);
                    }
                } else if (properties.get("ITEM") != null) {
                    entityGrid[x][y] = Constants.EntityGridCode.ITEM;
                    if (properties.get("Health") != null) {
                        initItem(x, y, Constants.ItemType.HEALTH);
                    } else if (properties.get("Shield") != null) {
                        initItem(x, y, Constants.ItemType.SHIELD);
                    }
                } else if (properties.get("TRAP") != null) {
                    entityGrid[x][y] = Constants.EntityGridCode.TRAP;
                    if (properties.get("Trap1") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP1);
                    } else if (properties.get("Trap2") != null) {
                        initTrap(x, y, Constants.TrapType.TRAP2);
                    }
                    //else if (properties.get("Trap3") != null) {
                    //  initTrap(x, y, Constants.TrapType.TRAP3);
                    //  }
                } else {
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
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
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
        bernard.clearActions();
        bernard.setCX(cX);
        bernard.setDCX(cX);
        bernard.setCY(cY);
        bernard.setDCY(cY);
        bernard.setX(bernard.getCX() * Constants.TILEDIMENSION);
        bernard.setY(bernard.getCY() * Constants.TILEDIMENSION);
        entityList.add(bernard);
    }

    private void initEnemy(int cX, int cY, Constants.EnemyType enemyType) {
        switch (enemyType) {
            case CATLADY:
                entityList.add(new CatLady(cX, cY));
                break;
            case DRUNK:
                entityList.add(new Drunk(cX, cY));
                break;
            case WANDERER:
                entityList.add(new Wanderer(cX, cY));
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
                entityList.add(new ItemHeart(cX, cY));
                break;
            case SHIELD:
                entityList.add(new ItemShield(cX, cY));
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
                entityList.add(new TrapType1(cX, cY));
                break;
            case TRAP2:
                entityList.add(new TrapType2(cX, cY));
                break;
//            case TRAP3:
//                entityList.add(new TrapType3(cX, cY));
//                break;
            default:
                //ERROR
                Gdx.app.log("ERROR", "Trap type not found. Not added to entity grid.");
                break;
        }
    }

    private void populateMapForTesting() {
        List<Entity> tempList = new ArrayList<Entity>();

        //Add entities to tempList below
		/*	Be aware that entities placed on testmap.tmx will be overriden
         on the mapGrid and will be placed on top of each other. Please
         place them in an empty block
         */
        tempList.add(new TrapType3(2, 2));

        // Populate the cells from the temp list and add to entity list
        for (int i = 0; i < tempList.size(); i++) {
            Entity entity = tempList.get(i);
            this.entityGrid[entity.getCX()][entity.getCY()] = entity.getGridCode();
            entityList.add(entity);
        }
    }

    public boolean bernardCanMove(Constants.Direction direction) {
        if (direction == Constants.Direction.UP) {
            if (bernard.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() + 1] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() + 1] != Constants.EntityGridCode.ENEMY)) {
                bernard.setDCY(bernard.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (bernard.getCY() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX()][bernard.getCY() - 1] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX()][bernard.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX()][bernard.getCY() - 1] != Constants.EntityGridCode.ENEMY)) {
                bernard.setDCY(bernard.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (bernard.getCX() == 0) {
                return false;
            }
            if (mapGrid[bernard.getCX() - 1][bernard.getCY()] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX() - 1][bernard.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() - 1][bernard.getCY()] != Constants.EntityGridCode.ENEMY)) {
                bernard.setDCX(bernard.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (bernard.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[bernard.getCX() + 1][bernard.getCY()] == Constants.MapGridCode.EXIT || (mapGrid[bernard.getCX() + 1][bernard.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[bernard.getCX() + 1][bernard.getCY()] != Constants.EntityGridCode.ENEMY)) {
                bernard.setDCX(bernard.getCX() + 1);
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
                entity.setDCY(entity.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (entity.getCY() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX()][entity.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX()][entity.getCY() - 1] == Constants.EntityGridCode.NONE) {
                entity.setDCY(entity.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (entity.getCX() == 0) {
                return false;
            }
            if (mapGrid[entity.getCX() - 1][entity.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX() - 1][entity.getCY()] == Constants.EntityGridCode.NONE) {
                entity.setDCX(entity.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (entity.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[entity.getCX() + 1][entity.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[entity.getCX() + 1][entity.getCY()] == Constants.EntityGridCode.NONE) {
                entity.setDCX(entity.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    public void moveEntity(Entity entity) {
        entityGrid[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.NONE;
        entityGrid[entity.getDCX()][entity.getDCY()] = entity.getGridCode();
        entity.setCX(entity.getDCX());
        entity.setCY(entity.getDCY());
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
                }
        else if(entity instanceof DamageEntity){
        } else {
                    entityGrid[entity.getCX()][entity.getCY()] = Constants.EntityGridCode.NONE;
                }
    }

    public boolean exitReached() {
        if (mapGrid[bernard.getCX()][bernard.getCY()] == Constants.MapGridCode.EXIT) {
            return true;
        }
        return false;
    }

    public void calculateAITurn(Entity entity) {
        if (entity.getGridCode() == Constants.EntityGridCode.ENEMY) {
            Antagonist enemy = (Antagonist) entity;

            //Change this method to however you want enemies to behave
            enemy.calculateTurn(mapGrid, entityGrid, entityList);
        }
    }

    public void playTurn(Entity entity) {
        if(entity instanceof Protagonist){
            moveEntity(entity);
            entity.performActions();
        }
        else if(entity instanceof Antagonist){
            calculateAITurn(entity);
            moveEntity(entity);
            entity.performActions();
        }
        else
            entity.performActions();
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
