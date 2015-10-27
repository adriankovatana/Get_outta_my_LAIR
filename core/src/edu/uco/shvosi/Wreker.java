package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class Wreker extends Antagonist {
    
    private Animation wrekerWalk;
    private Animation wrekerAttack;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX;
    private int bernardY;
    private String XorY;
    private int xdis;
    private int ydis;
    private int damage = 50;
    private boolean active = false;
    private int [] directions;
    private boolean moved = false;
    

    public Wreker(int cX, int cY) {
        super(Constants.EnemyType.WREKER, TextureLoader.WREKERTEXTURE, cX, cY);
        this.name = "Wreker";
        wrekerWalk = TextureLoader.wrekerWalk;
        wrekerAttack = TextureLoader.wrekerAttack;

        this.setBoundingBox(120);
        this.damage = damage;

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
            if(xdis >=0)
            {
                flip = true;
            }
            else
            {
                flip = false;
            }
            if(Math.abs(xdis) >1 ||Math.abs(ydis) >1){    
                if (flip) {
                    temp = wrekerWalk.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(wrekerWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (wrekerWalk.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }
            if(Math.abs(xdis) <=1 && Math.abs(ydis) <=1){    
                if (flip) {
                    temp = wrekerAttack.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                } else {
                    batch.draw(wrekerAttack.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                }
                if (wrekerAttack.isAnimationFinished(elapsedTime)) {
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
                        for(int i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX-1][this.cY-i]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.DOWN;
                            }
                            if(mapGrid[this.cX-1][this.cY+i]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.UP;
                            }
                            else
                            {
                                active = false;
                            }
                        }
                    }
                }//end try to go left
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
                        for(int i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX+1][this.cY-i]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.DOWN;
                            }
                            if(mapGrid[this.cX+1][this.cY+i]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.UP;
                            }
                            else
                            {
                                active = false;
                            }
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
                        for(int i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX+i][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.RIGHT;
                            }
                            if(mapGrid[this.cX-i][this.cY-1]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.LEFT;
                            }
                            else
                            {
                                active = false;
                            }
                        }
                    }//end try go down
                }
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
                        for(int i =0; i < 5; i++)//try to go around large obstacle
                        {
                            if(mapGrid[this.cX+i][this.cY+1]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.RIGHT;
                            }
                            if(mapGrid[this.cX-i][this.cY+1]== Constants.MapGridCode.FLOOR)
                            {
                                d = Constants.Direction.LEFT;
                            }
                            else
                            {
                                active = false;
                            }
                        }
                    }//end try go down
                }
                        this.setTurnAction(Constants.TurnAction.MOVE);
                }//end move to one spot away
             }//end if active
//                if(this.canMove(Constants.Direction.UP_LEFT,mapGrid,entityGrid))
//                {
//                    if((Math.abs((this.cX - 1)-bernardX) < xdis)||(Math.abs(this.cY +1)<ydis))
//                    {
//                        d = Constants.Direction.UP_LEFT;
//                    }
//                    else
//                    {
//                        directions[0] = 1;
//                    }
//                        
//                        
//                }
            
//            while (!canMove(d, mapGrid, entityGrid)) {
//        
//                if(Math.abs(xdis) > Math.abs(ydis))
//                {
//                    XorY="X";
//                }
//                else
//                {
//                    XorY="Y";
//                }
//        
//                if("X".equals(XorY) && xdis >= 0)
//                {
//                    d = Constants.Direction.LEFT;
//                }
//        
//            if("X".equals(XorY) && xdis < 0)
//            {
//                d = Constants.Direction.RIGHT;
//            }
//            if("Y".equals(XorY) && ydis >= 0)
//            {
//                d = Constants.Direction.DOWN;
//            }
//        
//            if("Y".equals(XorY) && ydis < 0)
//            {
//                d = Constants.Direction.UP;
//            }
//        
//            tries++;
//            if(tries > 5){
//                this.setTurnAction(Constants.TurnAction.NONE);
//                this.addAction(this.finishTurn());
//                return;
//            }
//        }//end while
   
//end if active
//        else if (active && (xdis <= 1 && ydis <= 1))
//        {
//           this.setTurnAction(Constants.TurnAction.ATTACK);
//
//        }
    }
    
    @Override
    public void collision(Entity entity) {
    }
}
