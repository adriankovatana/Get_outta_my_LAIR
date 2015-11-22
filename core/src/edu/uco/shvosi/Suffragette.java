
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
    private int xdis; 
    private int ydis;
    private int bernardX;
    private int bernardY;
//    private int targetX;
//    private int targetY;
    private Constants.Direction direction;
    private Constants.Direction reverse;
    private boolean directionSet = false;
    private boolean active = false;
//    private boolean thisWay = true;
 //   private int distance;
    

    public Suffragette(int cX, int cY) {
        super(Constants.EnemyType.SUFFRAGETTE, TextureLoader.SUFFERTETEXTURE, cX, cY);
        this.name = "Suffragette";
        this.walkAnimation = TextureLoader.suffragetteWalk;
        //this.begX = cX;
        //this.begY = cY;
        //this.direction = d;
        this.damage = 0;
        this.health = 1000;
        this.maxHealth = this.health;
        //this.distance = distance;

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
                    direction = Constants.Direction.LEFT;
                    }
                    if(xdis > 0)
                    {
                        direction = Constants.Direction.RIGHT;
                    }
                    if(ydis < 0)
                    {
                        direction = Constants.Direction.UP;
                    }
                    if(ydis > 0)
                    {
                        direction = Constants.Direction.DOWN;
                    }                
                                
                    if (direction == Constants.Direction.DOWN)
                    {
                        reverse = Constants.Direction.UP;
                    }
                    if (direction == Constants.Direction.UP)
                    {
                        reverse = Constants.Direction.DOWN;
                    }
                    if (direction == Constants.Direction.RIGHT)
                    {
                        reverse = Constants.Direction.LEFT;
                    }
                    if (direction == Constants.Direction.LEFT)
                    {
                        reverse = Constants.Direction.RIGHT;
                    }
                    directionSet = true;
                }//end direction not set
                
                if(this.canMove(direction, mapGrid, entityGrid))
                {
                    this.setTurnAction(Constants.TurnAction.MOVE);
                }
                else if(this.canMove(reverse, mapGrid, entityGrid))
                {
                    this.setTurnAction(Constants.TurnAction.MOVE);
                }
                else
                {
                    this.setTurnAction(Constants.TurnAction.NONE);
                }
                              
               // this.setTurnAction(Constants.TurnAction.MOVE);
            }//end if active
    }
    
//    public void reverse(){
//        if (direction == Constants.Direction.DOWN)
//        {
//            direction = Constants.Direction.UP;
//        }
//        if (direction == Constants.Direction.UP)
//        {
//            direction = Constants.Direction.DOWN;
//        }
//        if (direction == Constants.Direction.RIGHT)
//        {
//           direction = Constants.Direction.LEFT;
//        }
//        if (direction == Constants.Direction.LEFT)
//        {
//            direction = Constants.Direction.RIGHT;
//        }
//    
//    }
    
    @Override
    public void collision(Entity entity) {
    }
}

    


