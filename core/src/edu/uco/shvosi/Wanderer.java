package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class Wanderer extends Antagonist {
    
    private Animation wanderWalk;
    private Animation wanderAttack;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX;
    private int bernardY;
    private String XorY;
    private int xdis;
    private int ydis;
    private int damage = 5;
    private boolean active = false;
    private DamageEntity melee;
    private boolean moved = false;
    

    public Wanderer(int cX, int cY) {
        super(Constants.EnemyType.WANDERER, TextureLoader.WANDERTEXTURE, cX, cY);
        wanderWalk = TextureLoader.wanderWalk;
        wanderAttack = TextureLoader.wanderAttack;
        this.damage = damage;
        melee = new DamageEntity(0,0,this.damage);
        
        this.name = "Wanderer";
        
        this.xpValue = 100;

    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
        if(!flip)
        {
           melee.setCX(this.cX + 1);
           melee.setCY(this.cY);
        }
        else
        {
           melee.setCX(this.cX - 1);
           melee.setCY(this.cY);
        }

        melee.setDead(false);
        Map.miscEntityList.add(melee);
        this.addAction(this.finishTurn());
        
    }
    
        @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        
                         
            elapsedTime += Gdx.graphics.getDeltaTime();
            if(xdis >=0)
            {
                flip = true;
            }
            else
            {
                flip = false;
            }
            if(Math.abs(xdis) >1 || Math.abs(ydis) >1){    
                if (flip) {
                    temp = wanderWalk.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(wanderWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (wanderWalk.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }
            if(Math.abs(xdis) <=1 && Math.abs(ydis) <=1){    
                if (flip) {
                    temp = wanderAttack.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(wanderAttack.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (wanderAttack.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }
        
    }

    
    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;

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
            
            //if bernard is less than 5 spaces away I become active
            if(Math.abs(xdis) < 5 && Math.abs(ydis) < 5)
            {
                active = true;
            }
        
            if (active)//active charater can move and attack
             {
                if(Math.abs(xdis)> 1 || Math.abs(ydis)> 1)//moves to one spot awway
                {
                    if(Math.abs(xdis) > Math.abs(ydis))
                    {
                        XorY="X";
                    }
                    else
                    {
                        XorY="Y";
                    }
                if("X".equals(XorY) && xdis > 1)//need to go left
                {
                    if(this.canMove(Constants.Direction.LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.LEFT;//try to go left
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.UP_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_LEFT;//if something in the way try to go around
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.DOWN_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_LEFT;//try to go around
                        moved = true;
                    }
                    if(!moved)
                    {
                        int i;
                        int k;
                        for(i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX-1][this.cY-i]== Constants.MapGridCode.FLOOR)
                            {
                               // d = Constants.Direction.DOWN;
                                break;
                            }
                        }
                        for (k = 0; k < 5; k++ )
                            if(mapGrid[this.cX-1][this.cY+k]== Constants.MapGridCode.FLOOR)
                            {
                                //d = Constants.Direction.UP;
                                break;
                            }
                        if(i<k)
                        {
                            d = Constants.Direction.UP;
                        }
                        else
                        {
                            d = Constants.Direction.DOWN;
                        }
                        }
                    }
                //end try to go left
                if("X".equals(XorY) && xdis < 1)//need to go right
                {
                    if(this.canMove(Constants.Direction.RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.RIGHT;//try right
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.UP_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_RIGHT;//go around
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.DOWN_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_RIGHT;//go around
                        moved = true;
                    }
                    if(!moved)
                    {
                        int i;
                        int k;
                        for(i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX-1][this.cY-i]== Constants.MapGridCode.FLOOR)
                            {
                               // d = Constants.Direction.DOWN;
                                break;
                            }
                        }
                        for (k = 0; k < 5; k++ )
                            if(mapGrid[this.cX-1][this.cY+k]== Constants.MapGridCode.FLOOR)
                            {
                                //d = Constants.Direction.UP;
                                break;
                            }
                        if(i<k)
                        {
                            d = Constants.Direction.UP;
                        }
                        else
                        {
                            d = Constants.Direction.DOWN;
                        }
                        }
                }//end go right
                if("Y".equals(XorY) && ydis > 1)//need to go down
                {
                    if(this.canMove(Constants.Direction.DOWN,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN;//try down
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.DOWN_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_LEFT;//go around
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.DOWN_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_RIGHT;//go aroud
                        moved = true;
                    }
                    if(!moved)
                    {
                        int i;
                        int k;
                        for(i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX-i][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                               // d = Constants.Direction.DOWN;
                                break;
                            }
                        }
                        for (k = 0; k < 5; k++ )
                            if(mapGrid[this.cX+k][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                                //d = Constants.Direction.UP;
                                break;
                            }
                        if(i<k)
                        {
                            d = Constants.Direction.RIGHT;
                        }
                        else
                        {
                            d = Constants.Direction.LEFT;
                        }
                        }
                }//end down
                if("Y".equals(XorY) && ydis < 1)//need to go up
                {
                    if(this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP;//try up
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.UP_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_LEFT;//go around
                        moved = true;
                    }
                    else if (this.canMove(Constants.Direction.UP_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_RIGHT;//go around
                        moved = true;
                    }
                    if(!moved)
 {
                        int i;
                        int k;
                        for(i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX-i][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                               // d = Constants.Direction.DOWN;
                                break;
                            }
                        }
                        for (k = 0; k < 5; k++ )
                            if(mapGrid[this.cX+k][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                                //d = Constants.Direction.UP;
                                break;
                            }
                        if(i<k)
                        {
                            d = Constants.Direction.RIGHT;
                        }
                        else
                        {
                            d = Constants.Direction.LEFT;
                        }
                        }
                }//end up
                        this.setTurnAction(Constants.TurnAction.MOVE);
                }//end move to one spot away
                if(Math.abs(xdis) <=1 && Math.abs(ydis) <=1)
                {
                   this.setTurnAction(Constants.TurnAction.ATTACK);

                }                            
                   
            }//end if active
             }//end function

}
