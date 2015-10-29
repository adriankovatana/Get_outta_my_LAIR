
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class Suffragette extends Antagonist {
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int begX; 
    private int begY;
    private Constants.Direction direction;
    private int moveCount = 0;

    private boolean active = false;
    int distance;
    

    public Suffragette(int cX, int cY, Constants.Direction d, int distance) {
        super(Constants.EnemyType.SUFFRAGETTE, TextureLoader.SUFFERTETEXTURE, cX, cY);
        this.name = "Suffragette";
        this.walkAnimation = TextureLoader.suffragetteWalk;
        this.begX = cX;
        this.begY = cY;
        this.direction = d;
        this.damage = 0;
        this.health = 1000;
        this.maxHealth = this.health;
        this.distance = distance;
    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
        //MeleeAttack meleeAttach = new MeleeAttack(bernardX, bernardY, damage);
        this.addAction(this.finishTurn());
        
    }
    
        @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        
                         
            elapsedTime += Gdx.graphics.getDeltaTime();
            if(direction == Constants.Direction.LEFT)
            {
                flip = true;
            }
            else
            {
                flip = false;
            }
                
                if (flip) {
                    temp = this.walkAnimation.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(this.walkAnimation.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (this.walkAnimation.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            
         
        
    }

    
    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
                
        Constants.Direction d = Constants.Direction.NONE;


            if(this.getHealth() < 1000)
            {
                active = true;
            }
        
            if (active)
             {
                while (moveCount < distance)
                {
                    d = this.direction;
                    moveCount ++;
                    this.setTurnAction(Constants.TurnAction.MOVE);
                }
                reverse();
                moveCount = 0;
                this.setTurnAction(Constants.TurnAction.MOVE);
             }//end if active
    }
    
    public void reverse()
    {
        if(direction == Constants.Direction.DOWN)
        {
            direction = Constants.Direction.UP;
        }
        if(direction == Constants.Direction.UP)
        {
            direction = Constants.Direction.DOWN;
        }   
        if(direction == Constants.Direction.RIGHT)
        {
            direction = Constants.Direction.LEFT;
        }
        if(direction == Constants.Direction.LEFT)
        {
            direction = Constants.Direction.RIGHT;
        }
    }
    
    
    @Override
    public void collision(Entity entity) {
    }
}

    


