/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.uco.shvosi.Constants.EntityGridCode;
import edu.uco.shvosi.Constants.MapGridCode;
import edu.uco.shvosi.Constants.TurnAction;
import java.util.List;

/**
 *
 * @author Cody McGuire
 */
public class BossMoonshiner extends Antagonist {

    private float elapsedTime;
    private int bernardX;
    private int bernardY;
    private int[][] blocked;
    private int blockedCount;
    private int[] nextCell;
    private DamageEntity bottleAttack;
    private float elapsedBottle;
    private boolean attacking;
    private TextureRegion temp;
    private boolean flip;
    private int xdis;
    private int ydis;

    public BossMoonshiner(int cX, int cY) {
        super(Constants.EnemyType.BOSS, TextureLoader.MOONSHINERTEXTURE, cX, cY);

        this.name = "MoonshinerBoss";
        super.attackAnimation = TextureLoader.moonshinerAttack;
        super.walkAnimation = TextureLoader.moonshinerWalk;

        super.health = 200;
        super.maxHealth = 200;
        super.xpValue = 500;
        this.damage = 7;
        elapsedBottle = 0f;
        blocked = null;
        blockedCount = 0;
        bottleAttack = new DamageEntity(0, 0, damage);
        attacking = false;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (attacking) {
            batch.draw(TextureLoader.bottleSkill.getKeyFrame(elapsedBottle), bottleAttack.getX(), bottleAttack.getY());
//            batch.draw(TextureLoader.bottleSkill.getKeyFrame(elapsedBottle), bottleAttack.getX() - Constants.TILEDIMENSION, bottleAttack.getY());
//            batch.draw(TextureLoader.bottleSkill.getKeyFrame(elapsedBottle), bottleAttack.getX() + Constants.TILEDIMENSION, bottleAttack.getY());
//            batch.draw(TextureLoader.bottleSkill.getKeyFrame(elapsedBottle), bottleAttack.getX(), bottleAttack.getY() - Constants.TILEDIMENSION);
//            batch.draw(TextureLoader.bottleSkill.getKeyFrame(elapsedBottle), bottleAttack.getX(), bottleAttack.getY() + Constants.TILEDIMENSION);
            elapsedBottle += Gdx.graphics.getDeltaTime();
            if (TextureLoader.bottleSkill.isAnimationFinished(elapsedBottle * 2)) {
                elapsedBottle = 0f;
                attacking = false;
            }
        }
        
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
                    temp = walkAnimation.getKeyFrame(elapsedTime);
                    temp.flip(true, false);
                    super.textureRegion = temp;
                    temp.flip(true, false);
                } else {
                    super.textureRegion = walkAnimation.getKeyFrame(elapsedTime);
                }
                if (walkAnimation.isAnimationFinished(elapsedTime)) {
                    moving = false;
                    elapsedTime = 0f;
                }
            }

    }

    @Override
    public void attackAction() {
        //Do Attack Stuffs?
        attacking = true;
        bottleAttack.setCX(bernardX);
        bottleAttack.setCY(bernardY);
        bottleAttack.setX(bernardX * Constants.TILEDIMENSION);
        bottleAttack.setY(bernardY * Constants.TILEDIMENSION);
        bottleAttack.setDead(false);
        Map.miscEntityList.add(bottleAttack);
        this.addAction(this.finishTurn());
    }

    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {

        for (int i = 0; i < entityList.size(); i++)//get bernards location
        {
            if (entityList.get(i).getGridCode() == Constants.EntityGridCode.PLAYER) {
                bernardX = entityList.get(i).getCX();
                bernardY = entityList.get(i).getCY();
                break;
            }
        }

        if (blocked == null) {
            blocked = new int[mapGrid[0].length * mapGrid.length][2];
            for (int i = 0; i < mapGrid[0].length - 1; i++) {
                for (int j = 0; j < mapGrid.length - 1; j++) {
                    if (mapGrid[i][j] != MapGridCode.FLOOR) {
                        blocked[blockedCount][0] = i;
                        blocked[blockedCount][1] = j;
                        blockedCount++;
                    }
                }
            }

            for (Entity e : entityList) {
                if (!(e instanceof Protagonist) && e != this) {
                    blocked[blockedCount][0] = e.getCX();
                    blocked[blockedCount][1] = e.getCY();
                    blockedCount++;
                }
            }
        }
        nextCell = AStar.test(0, mapGrid[0].length, mapGrid.length, bernardX, bernardY, this.cX, this.cY, blocked);
        xdis = bernardX - this.cX;
        ydis = bernardY - this.cY;
        blocked = null;
        blockedCount = 0;

        if ((nextCell[0] == bernardX && nextCell[1] == bernardY) || (Math.abs(xdis) < 3 && Math.abs(ydis) < 3)) {
            this.setTurnAction(TurnAction.ATTACK);

        } else {
            this.cX = nextCell[0];
            this.cY = nextCell[1];

            this.setTurnAction(TurnAction.MOVE);
        }

    }
}
