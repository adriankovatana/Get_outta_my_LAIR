package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class EnemyBlues extends Antagonist {
    
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
    private int enemyX;
    private int enemyY;
    private DamageEntity blueDamage;
    
    
    public EnemyBlues(int cX, int cY) {
        super(Constants.EnemyType.BLUES, TextureLoader.BLUESTEXTURE, cX, cY);
        this.name = "Blues";
        this.walkAnimation = TextureLoader.blueWalk;
        BluesSkill bluesSkill = new BluesSkill();
        this.range = 3;
        this.damage = 1;
        blueDamage = new DamageEntity(0,0,this.damage);
        this.xpValue = 25;

    }

    @Override
    public void attackAction() {
        blueDamage.setCX(bernardX);
        blueDamage.setCY(bernardY);
        blueDamage.setDamage(this.damage);
        blueDamage.setDead(false);
        Map.miscEntityList.add(blueDamage);
        this.addAction(this.finishTurn());
    }
    
    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
                
                                 
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

        int random = 0;
        Constants.Direction d = Constants.Direction.NONE;

        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER) {
                bernardX = entityList.get(i).getCX();
                bernardY = entityList.get(i).getCY();
                break;
            }
        }
        damage = 1;
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).getGridCode() == Constants.EntityGridCode.ENEMY) {
                enemyX = entityList.get(i).getCX();
                enemyY = entityList.get(i).getCY();
                if(Math.abs(this.getCX() - enemyX) < range && Math.abs(this.getCY() - enemyY) < range)
                   {damage = damage * 2;}
            }
        }

        xdis = this.getCX() - bernardX;
        ydis = this.getCY() - bernardY;
        if (Math.abs(xdis) < 5 && Math.abs(ydis)  < 5) {
            active = true;
        }
        if (active) {
            if (Math.abs(xdis) <= range && Math.abs(ydis) <= range) {
                if(this.damage == 2){
                    int tries = 0;
                    while (tries < 5) {
                    random = (int) (Math.random() * entityGrid.length);
                    switch (random % 4) {
                        case 1:
                            if(this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                                {d = Constants.Direction.UP;}
                            break;
                        case 2:
                            if(this.canMove(Constants.Direction.DOWN,mapGrid,entityGrid))
                                {d = Constants.Direction.DOWN;}
                            break;
                        case 3:
                            if(this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                                {d = Constants.Direction.LEFT;}
                                flip = true;
                            break;
                        case 4:
                            if(this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                                {d = Constants.Direction.RIGHT;}
                                flip = false;
                            break;
                        default:
                            d = Constants.Direction.NONE;                           
                    }//end switch
                    tries ++;
                }//end while
                this.setTurnAction(Constants.TurnAction.MOVE);
                }//end damage < 2
                else{
                    this.setTurnAction(Constants.TurnAction.ATTACK);
                    }
            }//end in range
            else if (Math.abs(xdis) > range || Math.abs(ydis) > range) {

                int distanceDown = 0;
                int distanceUp = 0;
                int distanceRight = 0;
                int distanceLeft = 0;
                
                if(Math.abs(xdis) > Math.abs(ydis))
                {
                    XorY="X";
                }
                else
                {
                   XorY="Y";
                }
                    
                if("X".equals(XorY) && xdis > range)//need to go left
                {
                    for(distanceDown=0; distanceDown < 5; distanceDown++)//get shortest distance around verticle obstacle
                    {
                        if(mapGrid[this.cX-1][this.cY-distanceDown]== Constants.MapGridCode.FLOOR)
                       {
                            break;
                        }
                    }
                    for (distanceUp = 0; distanceUp < 5; distanceUp++ )
                    {
                        if(mapGrid[this.cX-1][this.cY+distanceUp]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                    }
                    if(this.canMove(Constants.Direction.LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.LEFT;//try to go left
                    }
                    else if (this.canMove(Constants.Direction.UP_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_LEFT;//if something in the way try to go around
                    }
                    else if (this.canMove(Constants.Direction.DOWN_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_LEFT;//try to go around
                    }
                    else if(distanceDown>=distanceUp)
                    {
                        if (this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                        {       
                            d = Constants.Direction.UP;
                        }
                    }
                    else if (distanceDown<distanceUp)
                    {
                        if (this.canMove(Constants.Direction.DOWN,mapGrid,entityGrid))
                        {       
                            d = Constants.Direction.DOWN;
                        }
                    }
                }//end try to go left
                    
                if("X".equals(XorY) && xdis < range)//need to go right
                {   
                    for(distanceDown=0; distanceDown < 5; distanceDown++)//get shortest distance around verticle obstacle
                    {
                        if(mapGrid[this.cX+1][this.cY-distanceDown]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                }
                for (distanceUp = 0; distanceUp < 5; distanceUp++ )
                {
                        if(mapGrid[this.cX+1][this.cY+distanceUp]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                }
                if(this.canMove(Constants.Direction.RIGHT,mapGrid,entityGrid))
                {
                    d = Constants.Direction.RIGHT;//try right
                }
                else if (this.canMove(Constants.Direction.UP_RIGHT,mapGrid,entityGrid))
                {
                    d = Constants.Direction.UP_RIGHT;//go around
                }
                else if (this.canMove(Constants.Direction.DOWN_RIGHT,mapGrid,entityGrid))
                {
                    d = Constants.Direction.DOWN_RIGHT;//go around
                }
                else if(distanceDown>=distanceUp)
                {
                    if (this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                    {       
                         d = Constants.Direction.UP;
                    }
                }
                else if (distanceDown<distanceUp)
                {
                    if (this.canMove(Constants.Direction.DOWN,mapGrid,entityGrid))
                    {       
                        d = Constants.Direction.DOWN;
                    }
                }
                     
                }//end go right
                if("Y".equals(XorY) && ydis > range)//need to go down
                 {
                    for(distanceRight=0; distanceRight < 5; distanceRight++)//get shortest distance around verticle obstacle
                    {
                        if(mapGrid[this.cX+distanceRight][this.cY-1]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                    }
                    for (distanceLeft = 0; distanceLeft < 5; distanceLeft++ )
                    {
                        if(mapGrid[this.cX-distanceLeft][this.cY-1]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                    }
                    if(this.canMove(Constants.Direction.DOWN,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN;//try down
                    }
                    else if (this.canMove(Constants.Direction.DOWN_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_LEFT;//go around
                    }
                    else if (this.canMove(Constants.Direction.DOWN_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.DOWN_RIGHT;//go aroud
                    }
                    else if(distanceLeft>=distanceRight)
                        {
                            if (this.canMove(Constants.Direction.RIGHT,mapGrid,entityGrid))
                            {       
                                d = Constants.Direction.RIGHT;
                            }
                        }
                    else if (distanceLeft<distanceRight)
                        {
                            if (this.canMove(Constants.Direction.LEFT,mapGrid,entityGrid))
                            {       
                                d = Constants.Direction.LEFT;
                            }
                        }
                        
                }//end down
                if("Y".equals(XorY) && ydis < range)//need to go up
                {
                    for(distanceRight=0; distanceRight < 5; distanceRight++)//get shortest distance around verticle obstacle
                    {
                        if(mapGrid[this.cX+distanceRight][this.cY+1]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                    }
                    for (distanceLeft = 0; distanceLeft < 5; distanceLeft++ )
                    {
                        if(mapGrid[this.cX-distanceLeft][this.cY+1]== Constants.MapGridCode.FLOOR)
                        {
                            break;
                        }
                    }                    
                    if(this.canMove(Constants.Direction.UP,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP;//try up
                    }
                    else if (this.canMove(Constants.Direction.UP_LEFT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_LEFT;//go around
                    }
                    else if (this.canMove(Constants.Direction.UP_RIGHT,mapGrid,entityGrid))
                    {
                        d = Constants.Direction.UP_RIGHT;//go around
                    }
                    else if(distanceLeft>=distanceRight)
                        {
                            if (this.canMove(Constants.Direction.RIGHT,mapGrid,entityGrid))
                            {       
                                d = Constants.Direction.RIGHT;
                            }
                        }
                    else if (distanceLeft<distanceRight)
                        {
                            if (this.canMove(Constants.Direction.LEFT,mapGrid,entityGrid))
                            {       
                                d = Constants.Direction.LEFT;
                            }
                        }
                       
                }//end up
                this.setTurnAction(Constants.TurnAction.MOVE);
            }
        }//end if active
    }
    
    @Override
    public void dispose() {
        super.dispose();
        //bluesSkill.dispose();
    }
}