package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
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
    protected int damage;

    public Antagonist(Constants.EnemyType enemyType, Texture texture, int cX, int cY) {
        super(Constants.EntityGridCode.ENEMY, texture, cX, cY);
        this.maxHealth = 1;
        this.health = this.maxHealth;
        this.enemyType = enemyType;
        this.damage = 0;
        this.turnAction = Constants.TurnAction.NONE;
        this.moving = false;
        this.elapsedWalk = 0f;
        this.elapsedAttack = 0f;
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
    
    public void setBoundingBox(int size)
    {
        Rectangle box = new Rectangle(getCX(), getCY(), size, size);
    }

    public int getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
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
}
