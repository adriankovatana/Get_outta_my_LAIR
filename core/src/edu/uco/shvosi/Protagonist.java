package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.ArrayList;
import java.util.List;

public class Protagonist extends Entity implements Observable {
	
	private int health;
	private int maxHealth;
	private Constants.TurnAction turnAction;
        private Constants.Direction direction;
    private List<Observer> observers;

    public Protagonist(int cX, int cY) {
        super(Constants.EntityGridCode.PLAYER, TextureLoader.BERNARDTEXTURE, cX, cY);
		this.maxHealth = 100;
		this.health = this.maxHealth;
		this.turnAction = Constants.TurnAction.NONE;
                this.direction = Constants.Direction.NONE;
		this.observers = new ArrayList();
                
                this.name = "Bernard";
    }
	
	@Override
	public void performActions() {
		this.setTurnFinished(false);
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

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
		
		if (this.isDead()) {
            // Draw death animation
        }
    }
	
	@Override
	public void performDeath() {
		this.addAction(sequence(deathAnimation(), finishTurn()));
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public void takeDamage(int damage){
		this.health -= damage;
		if(this.health <= 0){
			this.health = 0;
			this.setDead(true);
		}
	}
	
	public void heal(int value){
		this.health += value;
		if(this.health >= maxHealth){
			this.health = maxHealth;
		}
	}
	
	public Constants.TurnAction getTurnAction(){
		return this.turnAction;
	}
	
	public void setTurnAction(Constants.TurnAction turnAction){
		this.turnAction = turnAction;
	}
        
        public Constants.Direction getDirection() {
            return this.direction;
        }
        
        public void setDirection(Constants.Direction direction) {
            this.direction = direction;
        }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.observerUpdate(this);
        }
    }

    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }
    
    public void removeAllObservers() {
        this.observers.clear();
    }

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(sequence(moveAction, finishTurn()));
    }

    public void attackAction() {
        //Do Stuffs
    }
	
	public Action deathAnimation() {
		return new Action() {
			@Override
			public boolean act(float delta){
                            //Temp death
                            return true;
				/*if (this.deathAnimation.isAnimationFinished()) {
					return true;
				}*/
				//return false;
			}
		};
	}
	
	public Action finishTurn() {
		return new Action() {
			@Override
			public boolean act(float delta){
				Protagonist.this.setTurnFinished(true);
				Protagonist.this.turnAction = Constants.TurnAction.NONE;
				return true;
			}
		};
	}
}