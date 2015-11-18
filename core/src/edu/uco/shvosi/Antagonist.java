package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Disposable;
import java.util.List;

public class Antagonist extends Entity {

    protected int health;
    protected int maxHealth;
    protected Constants.EnemyType enemyType;
    protected boolean moving;
    protected Animation walkAnimation;
    protected float elapsedWalk;
    protected Animation attackAnimation;
    protected float elapsedAttack;
    protected Animation deathAnimation;
    protected float elapsedDeath;
    protected int damage;
    protected TextureRegion healthbarBackground;
    protected TextureRegion healthbarFill;
    protected int xpValue;
    protected int range;
    protected boolean ignite;
    protected boolean frostBite;
    protected int frostBiteCounter = 0;
    protected int igniteCounter = 0;
    protected ParticleEffect igniteParticle;
    protected Animation frostBiteAnimation;
    protected float elapsedFrostBite;
    protected int bluesCount;

    public Antagonist(Constants.EnemyType enemyType, Texture texture, int cX, int cY) {
        super(Constants.EntityGridCode.ENEMY, texture, cX, cY);
        this.maxHealth = 50;
        this.health = this.maxHealth;
        this.enemyType = enemyType;
        this.damage = 0;
        this.turnAction = Constants.TurnAction.NONE;
        this.moving = false;
        this.elapsedWalk = 0f;
        this.elapsedAttack = 0f;
        this.healthbarBackground = new TextureRegion(TextureLoader.HPBARBACKGROUND);
        this.healthbarFill = new TextureRegion(TextureLoader.HPBARFILL);
        this.deathAnimation = TextureLoader.death;
        this.xpValue = 0;
        this.ignite = false;
        this.frostBite = false;
        igniteParticle = new ParticleEffect();
        igniteParticle.load(Gdx.files.internal("ignite.p"), Gdx.files.internal(""));
        this.frostBiteAnimation = TextureLoader.frost;
        this.elapsedFrostBite = 0f;
        this.name = "Antagonist";
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        batch.draw(healthbarBackground, this.getX() + 10, this.getY());
        batch.draw(healthbarFill, this.getX() + 11, this.getY() + 1, healthbarFill.getRegionWidth() * ((float) health / (float) maxHealth), healthbarFill.getRegionHeight());
        if (this.isDead()) {
            elapsedDeath += Gdx.graphics.getDeltaTime();
            batch.draw(deathAnimation.getKeyFrame(elapsedDeath), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
        }

        if (ignite == true) {
            igniteParticle.start();
            igniteParticle.getEmitters().first().setPosition(this.getX() + 50, this.getY() + 15);
            igniteParticle.draw(batch, Gdx.graphics.getDeltaTime());
            igniteParticle.allowCompletion();
        }

        if (frostBite == true) {
            elapsedFrostBite += Gdx.graphics.getDeltaTime();
            batch.draw(frostBiteAnimation.getKeyFrame(elapsedFrostBite), this.getX() - 20, this.getY() - 20, Constants.TILEDIMENSION + 40, Constants.TILEDIMENSION + 40);
        }
    }

    @Override
    public void performActions() {
        //this.setTurnFinished(false);
        switch (this.turnAction) {
            case MOVE:
                moveAction();
                break;
            case ATTACK:
                attackAction();
                break;
            default:
                this.setTurnFinished(true);
                break;
        }
    }

    public void setBoundingBox(int size) {
        Rectangle box = new Rectangle(getCX(), getCY(), size, size);
    }

    public int getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        if (frostBite == true) {
            this.health -= damage + damage / 5;
        } else {
            this.health -= damage;
        }
        if (this.health <= 0) {
            this.health = 0;
            this.setDead(true);
        }
    }

    public void heal(int value) {
        this.health += value;
        if (this.health >= maxHealth) {
            this.health = maxHealth;
        }
    }

    public Constants.EnemyType getEnemyType() {
        return this.enemyType;
    }

    public void attackAction() {
        //Do Stuffs
    }

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(sequence(moveAction, finishTurn()));
        this.moving = true;
    }

    public Action attackAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                if (Antagonist.this.attackAnimation.isAnimationFinished(delta)) {
                    return true;
                }
                return false;
            }
        };
    }

    public Action movingAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                if (Antagonist.this.walkAnimation.isAnimationFinished(delta)) {
                    return true;
                }
                return false;
            }
        };
    }

    public Action finishTurn() {
        if (ignite == true) {
            igniteCounter--;
            this.takeDamage(this.health / 10);
            Gdx.app.log("Ignite Counter", String.valueOf(igniteCounter));
//            Gdx.app.log("Health", String.valueOf(this.health));
            if (igniteCounter <= 0) {
                ignite = false;
                igniteParticle.reset();
            }
        }
        if (frostBite == true) {
            frostBiteCounter--;
            Gdx.app.log("FrostBite Counter", String.valueOf(frostBiteCounter));
            if (frostBiteCounter <= 0) {
                frostBite = false;
            }
        }

        return new Action() {
            @Override
            public boolean act(float delta) {
                Antagonist.this.setTurnFinished(true);
                Antagonist.this.turnAction = Constants.TurnAction.NONE;
                Antagonist.this.moving = false;
                return true;
            }
        };
    }

    public void calculateTurn(Constants.MapGridCode[][] mapGrid,
            Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
    }

    public DamageEntity[] getDamageEntities() {
        //Return damage entities to the engine to be placed
        return null;
    }

    protected boolean canMove(Constants.Direction direction, Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid) {
        if (direction == Constants.Direction.UP) {
            if (this.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() + 1] == Constants.EntityGridCode.NONE) {
                this.setCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (this.getCY() == 0) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() - 1] == Constants.EntityGridCode.NONE) {
                this.setCY(this.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (this.getCX() == 0) {
                return false;
            }
            if (mapGrid[this.getCX() - 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() - 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (this.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[this.getCX() + 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.UP_RIGHT) {
            if (mapGrid[this.getCX() + 1][this.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()+ 1] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() + 1);
                this.setCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN_RIGHT) {
            if (mapGrid[this.getCX() + 1][this.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()- 1] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() + 1);
                this.setCY(this.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.UP_LEFT) {
            if (mapGrid[this.getCX() - 1][this.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() - 1][this.getCY() + 1] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() - 1);
                this.setCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN_LEFT) {
            if (mapGrid[this.getCX() - 1][this.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()- 1][this.getCY()-1] == Constants.EntityGridCode.NONE) {
                this.setCX(this.getCX() - 1);
                this.setCY(this.getCY() - 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }

    @Override
    public void performDeath() {
        this.addAction(deathAnimation());
    }

    public Action deathAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                //Temp death
                if (deathAnimation.isAnimationFinished(elapsedDeath)) {
                    Antagonist.this.remove();
                    Antagonist.this.setTurnFinished(true);
                    Antagonist.this.turnAction = Constants.TurnAction.NONE;
                    return true;
                }
                return false;
            }
        };
    }

    int getXpValue() {
        return xpValue;
    }

     public void setIgnite(boolean ignite) {
        this.ignite = ignite;
    }

    public void setIgniteCounter(int igniteCounter) {
        this.igniteCounter = igniteCounter;
    }

    public void setFrost(boolean frostBite) {
        this.frostBite = frostBite;
    }

    public void setFrostBiteCounter(int frostBiteCounter) {
        this.frostBiteCounter = frostBiteCounter;
    }

//    public int getBluesCount(List<Entity> entityList) {
//        int blueX;
//        int blueY;
//        for (int i = 0; i < entityList.size(); i++) {
//            if (entityList.get(i).getName().equals("Blues")) {
//                blueX = entityList.get(i).getCX();
//                blueY = entityList.get(i).getCY();
//                if(Math.abs(this.getCX() - blueX) <=3 && Math.abs(this.getCY() - blueY) <= 3){
//                     bluesCount += 10;
//                }                
//            }
//        }
//        return bluesCount;
//    }

    @Override
    public void dispose() {
        igniteParticle.dispose();
    }
}
