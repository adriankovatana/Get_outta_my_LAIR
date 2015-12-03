
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class EnemySuffragette extends Antagonist {
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int xdis; 
    private int ydis;
    private int bernardX;
    private int bernardY;
    private int begX;
    private int begY;
    private Constants.Direction direction;
    private Constants.Direction reverse;
    private boolean directionSet;
    private boolean active;
    private boolean hasMoved;
 //   private int distance;
    

    public EnemySuffragette(int cX, int cY) {
        super(Constants.EnemyType.SUFFRAGETTE, TextureLoader.SUFFERTETEXTURE, cX, cY);
        this.name = "Suffragette";
        this.walkAnimation = TextureLoader.suffragetteWalk;
        this.begX = cX;
        this.begY = cY;
        //this.direction = d;
        this.damage = 0;
        this.health = 1000;
        this.maxHealth = this.health;
        //this.distance = distance;
        directionSet = false;
        active = false;
        hasMoved = false;

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
                
        //Constants.Direction d = Constants.Direction.NONE;


            if(this.health < 1000)
            {
                active = true;
            }
            if(active){
                if(!directionSet){   
                    for(int i = 0; i < entityList.size(); i++)//get bernards location
                    {
                        if(entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER){
                            bernardX = entityList.get(i).getCX();
                            bernardY = entityList.get(i).getCY();
                            break;
                        }
                    }
          
                    xdis = this.getCX() - bernardX;//get dis between me and bernard on x axis
                    ydis = this.getCY() - bernardY;//get dis between me and bernard on y axis
                
                    if(xdis < 0)
                    {
                    direction = Constants.Direction.RIGHT;
                    }
                    if(xdis > 0)
                    {
                        direction = Constants.Direction.LEFT;
                    }
                    if(ydis < 0)
                    {
                        direction = Constants.Direction.UP;
                    }
                    if(ydis > 0)
                    {
                        direction = Constants.Direction.DOWN;
                    }                
//                                
//                    if (direction == Constants.Direction.DOWN)
//                    {
//                        reverse = Constants.Direction.UP;
//                    }
//                    if (direction == Constants.Direction.UP)
//                    {
//                        reverse = Constants.Direction.DOWN;
//                   }
//                    if (direction == Constants.Direction.LEFT)
//                    {
//                        reverse = Constants.Direction.RIGHT;
//                    }     }
//                    if (direction == Constants.Direction.RIGHT)
//                    {
//                        reverse = Constants.Direction.LEFT;
//                    }
//                    if (direction == Constants.Direction.LEFT)
//                    {
//                        reverse = Constants.Direction.RIGHT;
//                    }
                    directionSet = true;
                }//end direction not set
                
                if(this.canMove(direction, mapGrid, entityGrid))
                {
                    this.setTurnAction(Constants.TurnAction.MOVE);
                    hasMoved = true;
                }
                else if(this.canMove(reverse(direction), mapGrid, entityGrid))
                {
                    this.setTurnAction(Constants.TurnAction.MOVE);
                    hasMoved = true;
                }
                else
                {
                    this.setTurnAction(Constants.TurnAction.NONE);
                }
                if(hasMoved && begX == cX && begY == cY)
                {
                    health = 1000;
                    active = false;
                    directionSet = false;
                    hasMoved = false;
                }
                              
               // this.setTurnAction(Constants.TurnAction.MOVE);
            }//end if active
    }
    
    public Constants.Direction reverse(Constants.Direction direction){
        if (direction == Constants.Direction.DOWN)
        {
            this.direction = Constants.Direction.UP;
        }
        else if (direction == Constants.Direction.UP)
        {
            this.direction = Constants.Direction.DOWN;
        }
        else if (direction == Constants.Direction.RIGHT)
        {
           this.direction = Constants.Direction.LEFT;
        }
        else if (direction == Constants.Direction.LEFT)
        {
            this.direction = Constants.Direction.RIGHT;
        }
        return this.direction;    
    }
    
    @Override
    public void collision(Entity entity) {
    }
}

    


