
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
    private int goToX;
    private int goToY;
    private int targetX;
    private int targetY;
    private Constants.Direction direction;
    private Constants.Direction oposite;
    private boolean arrived = false;
    private boolean active = false;
    private boolean thisWay = true;
    private int distance;
    

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
        if (direction == Constants.Direction.DOWN)
        {
            goToX = cX;
            goToY = cY - distance;
            oposite = Constants.Direction.UP;
        }
        if (direction == Constants.Direction.UP)
        {
            goToX = cX;
            goToY = cY + distance;
            oposite = Constants.Direction.DOWN;
        }
        if (direction == Constants.Direction.RIGHT)
        {
            goToX = cX + distance;
            goToY = cY;
           oposite = Constants.Direction.LEFT;
        }
        if (direction == Constants.Direction.LEFT)
        {
            goToX = cX - distance;
            goToY = cY;
            oposite = Constants.Direction.RIGHT;
        }
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


            if(this.health < 1000)
            {
                active = true;
            }
            if(active){
                if(thisWay)//set target
                {
                    targetX = goToX;
                    targetY = goToY;
                }   
                else
                {
                    targetX = begX;
                    targetY = begY;
                }
                if(this.canMove(direction,mapGrid,entityGrid))
                    {
                        d = direction;
                        this.setTurnAction(Constants.TurnAction.MOVE);
                    }
                if(cX == goToX && cY == goToY)
                {
                    thisWay = false;//going that way now
                    reverse();//reverses direction
                }
                if(cX == begX && cY == begY)
                {
                    thisWay = true;
                    reverse();
                    active = false;
                    health = 1000;
                }
                 
                                
               // this.setTurnAction(Constants.TurnAction.MOVE);
             }//end if active
    }
    
    public void reverse(){
        if (direction == Constants.Direction.DOWN)
        {
            direction = Constants.Direction.UP;
        }
        if (direction == Constants.Direction.UP)
        {
            direction = Constants.Direction.DOWN;
        }
        if (direction == Constants.Direction.RIGHT)
        {
           direction = Constants.Direction.LEFT;
        }
        if (direction == Constants.Direction.LEFT)
        {
            direction = Constants.Direction.RIGHT;
        }
    
    }
    
    @Override
    public void collision(Entity entity) {
    }
}

    


