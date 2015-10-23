package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class Blues extends Antagonist {
    
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX; 
    private int bernardY; 
    private String XorY; 
    private int xdis; 
    private int ydis; 
    private boolean active = false;
    private BluesSkill bluesSkill;
    
    public Blues(int cX, int cY) {
        super(Constants.EnemyType.BLUES, TextureLoader.BLUESTEXTURE, cX, cY);
        this.name = "Blues";
        this.walkAnimation = TextureLoader.blueWalk;
        BluesSkill bluesSkill = new BluesSkill();
    }

    @Override
    public void attackAction() {
        this.addAction(this.finishTurn());
    }
    
    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
                
                int random = 0;
                random = (int) (Math.random() * 10);
                random = random % 2;
                                
                elapsedTime += Gdx.graphics.getDeltaTime();
           //if(random == 0)
           //{
                if (flip) {
                    temp = this.walkAnimation.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    batch.draw(temp, this.getX(),getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                    temp.flip(true, false);
                    
                } else {
                    batch.draw(this.walkAnimation.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION , Constants.TILEDIMENSION);
                }
                TextureLoader.blueSkill.setFrameDuration(0.5f);
                
                
                //bluesSkill.draw(batch, alpha, this);
                
                //batch.draw(this.bluesSkill.animation.getKeyFrame(elapsedTime), this.cX, this.cY, Constants.TILEDIMENSION*3 , Constants.TILEDIMENSION*3);
                batch.draw(TextureLoader.blueSkill.getKeyFrame(elapsedTime), this.getX()-150, this.getY()-150, Constants.TILEDIMENSION*4 , Constants.TILEDIMENSION*4);


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
        if(active)
        {
            while (!canMove(d, mapGrid, entityGrid)) {
                if(xdis < 3 && ydis < 3)
                {
                    random = (int) (Math.random() * entityGrid.length);
                    
                    switch (random % 4) {
                        case 1:
                            d = Constants.Direction.UP;
                            break;
                        case 2:
                            d = Constants.Direction.DOWN;
                            break;
                        case 3:
                            d = Constants.Direction.LEFT;
                            flip = true;
                            break;
                        default:
                            d = Constants.Direction.RIGHT;
                            flip = false;
                            break;
                    }
                }//end
                if(xdis >= 3 || ydis >= 3)
                {
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
                }//end
                tries++;
                if(tries > 5){
                    this.setTurnAction(Constants.TurnAction.NONE);
                    this.addAction(this.finishTurn());
                    return;
            }
        }

        this.setTurnAction(Constants.TurnAction.MOVE);
        }//end if active
    }
}