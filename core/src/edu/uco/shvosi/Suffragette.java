
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
    private int bernardX;
    private int bernardY;
    private String XorY;
    private int xdis;
    private int ydis;
    private int begX; 
    private int begY;
    private boolean active = false;
    

    public Suffragette(int cX, int cY) {
        super(Constants.EnemyType.SUFFRAGETTE, TextureLoader.SUFFERTETEXTURE, cX, cY);
        this.walkAnimation = TextureLoader.suffragetteWalk;
        this.begX = cX;
        this.begY = cY;
        this.setBoundingBox(120);
        this.damage = 0;
        this.health = 1000;

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
        //Random movement
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;

            for(int i = 0; i < entityList.size(); i++)
            {
                if(entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER){
                    bernardX = entityList.get(i).getCX();
                    bernardY = entityList.get(i).getCY();
                    break;
                }
            }
          
            xdis = this.getCX() - bernardX;
            ydis = this.getCY() - bernardY;
            if(this.getHealth() < 1000)
            {
                active = true;
            }
        
            if (active)
             {
             if(xdis > 1 && ydis >1){    
            
            while (!canMove(d, mapGrid, entityGrid)) {
        
                if(Math.abs(xdis) > Math.abs(ydis))
                {
                    XorY="X";
                }
                else
                {
                    XorY="Y";
                }
        
                if("X".equals(XorY) && xdis >= 0)
                {
                    d = Constants.Direction.LEFT;
                }
        
            if("X".equals(XorY) && xdis < 0)
            {
                d = Constants.Direction.RIGHT;
            }
            if("Y".equals(XorY) && ydis >= 0)
            {
                d = Constants.Direction.DOWN;
            }
        
            if("Y".equals(XorY) && ydis < 0)
            {
                d = Constants.Direction.UP;
            }
        
            tries++;
            if(tries > 5){
                this.setTurnAction(Constants.TurnAction.NONE);
                this.addAction(this.finishTurn());
                return;
            }
        }//end while         
            
             }//end if > 1
               else{
                 int backX;
                 int backY;
                 
                 backX = this.getCX() - this.begX;
                 backY = this.getCY() - this.begY;
                 
                while (!canMove(d, mapGrid, entityGrid)) {
        
                if(Math.abs(backX) > Math.abs(backY))
                {
                    XorY="X";
                }
                else
                {
                    XorY="Y";
                }
        
                if("X".equals(XorY) && backX >= 0)
                {
                    d = Constants.Direction.LEFT;
                }
        
            if("X".equals(XorY) && backY < 0)
            {
                d = Constants.Direction.RIGHT;
            }
            if("Y".equals(XorY) && backY >= 0)
            {
                d = Constants.Direction.DOWN;
            }
        
            if("Y".equals(XorY) && backY < 0)
            {
                d = Constants.Direction.UP;
            }
        
            tries++;
            if(tries > 5){
                this.setTurnAction(Constants.TurnAction.NONE);
                this.addAction(this.finishTurn());
                return;
            }
        }//end while 
                 
                    
             }
   
        this.setTurnAction(Constants.TurnAction.MOVE);
             }//end if active
//        else if (active && (xdis <= 1 && ydis <= 1))
//        {
//           this.setTurnAction(Constants.TurnAction.ATTACK);
//
//        }
    }
    
    @Override
    public void collision(Entity entity) {
    }


    private boolean canMove(Constants.Direction direction, Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid) {
        if (direction == Constants.Direction.UP) {
            if (this.getCY() == mapGrid[0].length - 1) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() + 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() + 1] == Constants.EntityGridCode.NONE) {
                this.setDCY(this.getCY() + 1);
                return true;
            }
        } else if (direction == Constants.Direction.DOWN) {
            if (this.getCY() == 0) {
                return false;
            }
            if (mapGrid[this.getCX()][this.getCY() - 1] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX()][this.getCY() - 1] == Constants.EntityGridCode.NONE) {
                this.setDCY(this.getCY() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.LEFT) {
            if (this.getCX() == 0) {
                return false;
            }
            if (mapGrid[this.getCX() - 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() - 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setDCX(this.getCX() - 1);
                return true;
            }
        } else if (direction == Constants.Direction.RIGHT) {
            if (this.getCX() == mapGrid.length - 1) {
                return false;
            }
            if (mapGrid[this.getCX() + 1][this.getCY()] == Constants.MapGridCode.FLOOR
                    && entityGrid[this.getCX() + 1][this.getCY()] == Constants.EntityGridCode.NONE) {
                this.setDCX(this.getCX() + 1);
                return true;
            }
        } else {
            // No direction
        }
        return false;
    }
}

    


