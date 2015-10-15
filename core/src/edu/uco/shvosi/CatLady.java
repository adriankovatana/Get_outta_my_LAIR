package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.List;

public class CatLady extends Antagonist {
    
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX; 
    private int bernardY; 
    private String XorY; 
    private int xdis; 
    private int ydis; 
    private boolean active = false;

    public CatLady(int cX, int cY) {
        super(Constants.EnemyType.CATLADY, TextureLoader.CATLADYTEXTURE, cX, cY);
        this.walkAnimation = TextureLoader.catLadyWalk;
        
        this.name = "CatLady";

    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
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
                batch.draw(this.walkAnimation.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION , Constants.TILEDIMENSION);
            }
            if (this.walkAnimation.isAnimationFinished(elapsedTime)) {
                moving = false;
                elapsedTime = 0f;
            }
        
    }

    
    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int random = 0;
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
        
        if(xdis < 5 && ydis < 5)
        {
        active = true;
        }
        
        if (active && (xdis < 2 || ydis < 2))
        {
        
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
            d = Constants.Direction.RIGHT;
        }
        
        if("X".equals(XorY) && xdis < 0)
        {
            d = Constants.Direction.LEFT;
        }
        if("Y".equals(XorY) && ydis >= 0)
        {
            d = Constants.Direction.UP;
        }
        if("Y".equals(XorY) && ydis < 0)
        {
            d = Constants.Direction.DOWN;
        }
        
                    tries++;
            if(tries > 5){
                this.setTurnAction(Constants.TurnAction.NONE);
                this.addAction(this.finishTurn());
                return;
            }
        }

         this.setTurnAction(Constants.TurnAction.MOVE);
        }//end if active
        else if (active && (xdis >= 2 || ydis >= 2))
        {
           this.setTurnAction(Constants.TurnAction.ATTACK);

        }
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
