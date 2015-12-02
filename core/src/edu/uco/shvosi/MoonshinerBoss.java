/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.graphics.g2d.Batch;
import edu.uco.shvosi.Constants.EntityGridCode;
import edu.uco.shvosi.Constants.MapGridCode;
import edu.uco.shvosi.Constants.TurnAction;
import java.util.List;

/**
 *
 * @author Cody McGuire
 */
public class MoonshinerBoss extends Antagonist {

    private float elapsedTime;
    private int bernardX;
    private int bernardY;
    private int[][] blocked;
    private int blockedCount;
    private int[] nextCell;

    public MoonshinerBoss(int cX, int cY) {
        super(Constants.EnemyType.BOSS, TextureLoader.MOONSHINERTEXTURE, cX, cY);

        this.name = "MoonshinerBoss";
        super.attackAnimation = TextureLoader.moonshinerAttack;
        super.walkAnimation = TextureLoader.moonshinerWalk;

        super.health = 200;
        super.maxHealth = 200;
        super.xpValue = 500;
        blocked = null;
        blockedCount = 0;

    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);

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
            for (int i = 0; i < mapGrid[0].length; i++) {
                for (int j = 0; j < mapGrid.length; j++) {
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
        System.out.println("Bernard: " + bernardX + ", " + bernardY + " Moonshiner: " + this.getCX() + ", " + this.getCY());
        nextCell = AStar.test(0, mapGrid[0].length, mapGrid.length, bernardX, bernardY, this.cX, this.cY, blocked);

        blocked = null;
        blockedCount = 0;

        if (nextCell[0] == bernardX && nextCell[1] == bernardY) {

        } else {
            this.cX = nextCell[0];
            this.cY = nextCell[1];

            this.setTurnAction(TurnAction.MOVE);
        }

    }
}
