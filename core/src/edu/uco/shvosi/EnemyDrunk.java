package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class EnemyDrunk extends Antagonist {

    private Animation drunkWalk;
    private Animation drunkAttack;
    private Animation bottleFall;
    private boolean moving = false;
    private boolean flip = false;
    private float elapsedTime;
    private TextureRegion temp;
    private int bernardX;
    private int bernardY;
    private String XorY;
    private int xdis;
    private int ydis;
    private boolean active = false;
    private DamageEntity bottleDamage;
    private BottleSkill bottleSkill;
    private int bottleX;
    private int bottleY;
        
    public EnemyDrunk(int cX, int cY) {
        super(Constants.EnemyType.DRUNK, TextureLoader.DRUNKTEXTURE, cX, cY);
        drunkWalk = TextureLoader.drunkWalk;
        drunkAttack = TextureLoader.drunkAttack;
        bottleFall = TextureLoader.bottleSkill;
        this.walkAnimation = TextureLoader.drunkWalk;
        this.attackAnimation = TextureLoader.drunkAttack;
        health = 30;
        maxHealth = 30;
        range = 2;
        this.damage = 10;
        BottleSkill bottle = new BottleSkill();
        bottleDamage = new DamageEntity(0,0,this.damage);
        bottleDamage.setDamage(damage);
        this.attacking = false;
        this.name = "Drunk";
        
    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
        int random;
        random = (int) (Math.random() * 51);
        switch (random % 32) {
//            case 1:
//                bottleDamage.setCX(this.cX -range);
//                bottleDamage.setCY(this.cY + range);
//                bottleX = -200;
//                bottleY= 200;
//                break;
//            case 2:
//                bottleDamage.setCX(this.cX - range/range);
//                bottleDamage.setCY(this.cY + range);
//                bottleX = -100;
//                bottleY= 200;
//                break;
//            case 3:
//                bottleDamage.setCX(this.cX);
//                bottleDamage.setCY(this.cY + range);
//                bottleX = 0;
//                bottleY= 200;
//                break;
//            case 4:
//                bottleDamage.setCX(this.cX + range/range);
//                bottleDamage.setCY(this.cY + range);
//                bottleX = 100;
//                bottleY= 200;
//                break;
//            case 5:
//                bottleDamage.setCX(this.cX + range);
//                bottleDamage.setCY(this.cY + range);
//                bottleX = 200;
//                bottleY= 200;                
//                break;
//            case 6:
//                bottleDamage.setCX(this.cX + range);
//                bottleDamage.setCY(this.cY + range/range);           
//                bottleX = 200;
//                bottleY= 100;
//                break;
//            case 7:
//                bottleDamage.setCX(this.cX + range);
//                bottleDamage.setCY(this.cY);           
//                bottleX = 200;
//                bottleY= 0;
//                break;
//            case 8:
//                bottleDamage.setCX(this.cX + range);
//                bottleDamage.setCY(this.cY + range/range);           
//                bottleX = 200;
//                bottleY= 100;
//                break;
//            case 9:
//                bottleDamage.setCX(this.cX + range);
//                bottleDamage.setCY(this.cY - range);
//                bottleX = 200;
//                bottleY= -200;
//                break;
//            case 10:
//                bottleDamage.setCX(this.cX + range/range);
//                bottleDamage.setCY(this.cY - range);           
//                bottleX = 100;
//                bottleY= -200;                break;
//            case 11:
//                bottleDamage.setCX(this.cX);
//                bottleDamage.setCY(this.cY - range);           
//                bottleX = 0;
//                bottleY= -200;
//                break;
//            case 12:
//                bottleDamage.setCX(this.cX - range/range);
//                bottleDamage.setCY(this.cY - range);           
//                bottleX = -100;
//                bottleY= -200;
//                break;
//            case 13:
//                bottleDamage.setCX(this.cX -range);
//                bottleDamage.setCY(this.cY - range);
//                bottleX = -200;
//                bottleY= -200;
//                break;
//            case 14:
//                bottleDamage.setCX(this.cX - range);
//                bottleDamage.setCY(this.cY + range/range);           
//                bottleX = -200;
//                bottleY= 100;
//                break;
//            case 15:
//                bottleDamage.setCX(this.cX - range);
//                bottleDamage.setCY(this.cY);           
//                bottleX = -200;
//                bottleY= 0;
//                break;
//            case 16:
//                bottleDamage.setCX(this.cX - range);
//                bottleDamage.setCY(this.cY + range/range);
//                bottleX = -200;
//                bottleY= 100;
//                break;   
            case 1:
                bottleDamage.setCX(this.cX -range/range);
                bottleDamage.setCY(this.cY + range/range);
                bottleX = -100;
                bottleY= 100;
                break;
            case 2:
                bottleDamage.setCX(this.cX );
                bottleDamage.setCY(this.cY + range/range);
                bottleX = 0;
                bottleY= 100;
                break;
            case 3:
                bottleDamage.setCX(this.cX + range/range);
                bottleDamage.setCY(this.cY + range/range);
                bottleX = 100;
                bottleY= 100;
                break;
            case 4:
                bottleDamage.setCX(this.cX + range/range);
                bottleDamage.setCY(this.cY );
                bottleX = 100;
                bottleY= 0;
                break;
            case 5:
                bottleDamage.setCX(this.cX + range/range);
                bottleDamage.setCY(this.cY - range/range);
                bottleX = 100;
                bottleY= -100;                
                break;
            case 6:
                bottleDamage.setCX(this.cX);
                bottleDamage.setCY(this.cY - range/range);           
                bottleX = 0;
                bottleY= -100;
                break;
            case 7:
                bottleDamage.setCX(this.cX - range/range);
                bottleDamage.setCY(this.cY - range/range);           
                bottleX = -100;
                bottleY= -100;
                break;
            case 8:
                bottleDamage.setCX(this.cX - range/range);
                bottleDamage.setCY(this.cY);           
                bottleX = -100;
                bottleY= 0;
                break;                
            default:
                bottleDamage.setCX(this.bernardX);
                bottleDamage.setCY(this.bernardY); 
                bottleX = -(xdis*100);
                bottleY = -(ydis*100);
                break;                
        }//end switch
        bottleDamage.setDead(false);
        Map.miscEntityList.add(bottleDamage);
        this.addAction(this.finishTurn());
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);

        elapsedTime += Gdx.graphics.getDeltaTime();        
 
        if(!attacking){
            if (flip) {
                temp = drunkWalk.getKeyFrame(elapsedTime);
                temp.flip(true, false);
                batch.draw(temp, this.getX(), getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                temp.flip(true, false);
            } else {
                batch.draw(drunkWalk.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            }
            if (drunkWalk.isAnimationFinished(elapsedTime)) {
                moving = false;
                elapsedTime = 0f;
            }
        }
                
        if(attacking){
            batch.draw(drunkAttack.getKeyFrame(elapsedTime), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            
            //batch.draw(TextureLoader.hammerDownSkill.getKeyFrame(elapsedTime), bernardX, bernardY, Constants.TILEDIMENSION, Constants.TILEDIMENSION);

            if (drunkAttack.isAnimationFinished(elapsedTime)) {
                TextureLoader.bottleSkill.setFrameDuration(0.15f);
                batch.draw(bottleFall.getKeyFrame(elapsedTime),this.getX() + bottleX, this.getY() + bottleY, Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            }
            if(bottleFall.isAnimationFinished(elapsedTime)){
                moving = false;
                attacking = false;
                elapsedTime = 0f;
            }
        }
        

    }

    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        
        int random = 0;
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;
        
//        getBluesCount(entityList);
//        damage = damage + bluesCount;

        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER) {
                bernardX = entityList.get(i).getCX();
                bernardY = entityList.get(i).getCY();
                break;
            }
        }

        xdis = this.getCX() - bernardX;
        ydis = this.getCY() - bernardY;
        if (Math.abs(xdis) < 5 && Math.abs(ydis)  < 5) {
            active = true;
        }
        if (active) {
            //System.out.println(Math.abs(xdis) + " " + Math.abs(ydis));
            if (Math.abs(xdis) < range && Math.abs(ydis) < range) {
                while (!canMove(d, mapGrid, entityGrid)) {
                    random = (int) (Math.random() * entityGrid.length);
                    switch (random % 16) {
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
                        case 4:
                            d = Constants.Direction.RIGHT;
                            flip = false;
                            break;
			default:
                            //this.setTurnAction(Constants.TurnAction.ATTACK);
                            tries = 5;
                            break;
                    }
                      
                    tries++;
                    if(tries > 5){
                        this.setTurnAction(Constants.TurnAction.ATTACK);
                        attacking = true;
                        return;
                    }
                }//end while
            if(tries <= 5)
            {
                this.setTurnAction(Constants.TurnAction.MOVE);
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
            }//end out of range
        }//end if active
    }

    @Override
    public void moveAction() {
        super.moveAction();
        moving = true;

    }
}
