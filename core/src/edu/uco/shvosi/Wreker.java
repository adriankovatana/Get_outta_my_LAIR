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
            if(xdis >1 || ydis >1){    
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
            if(xdis <=1 && ydis <=1){    
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
        
            if (active)
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
}
