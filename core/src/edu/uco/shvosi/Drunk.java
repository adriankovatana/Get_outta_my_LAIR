package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.List;

public class Drunk extends Antagonist {

    private Animation drunkWalk;
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

    public Drunk(int cX, int cY) {
        super(Constants.EnemyType.DRUNK, TextureLoader.DRUNKTEXTURE, cX, cY);
        drunkWalk = TextureLoader.drunkWalk;

        this.name = "Drunk";
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

    @Override
    public void calculateTurn(Constants.MapGridCode[][] mapGrid, Constants.EntityGridCode[][] entityGrid, List<Entity> entityList) {
        //Random movement
        int random = 0;
        int tries = 0;
        Constants.Direction d = Constants.Direction.NONE;

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
            while (!canMove(d, mapGrid, entityGrid)) {
                if (Math.abs(xdis) < 3 && Math.abs(ydis) < 3) {
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
                if (xdis > 3 || ydis > 3) {
                    if (Math.abs(xdis) > Math.abs(ydis)) {
                        XorY = "X";
                    } else {
                        XorY = "Y";
                    }

                    if ("X".equals(XorY) && xdis >= 0) {
                        d = Constants.Direction.LEFT;
                    }

                    if ("X".equals(XorY) && xdis < 0) {
                        d = Constants.Direction.RIGHT;
                    }
                    if ("Y".equals(XorY) && ydis >= 0) {
                        d = Constants.Direction.DOWN;
                    }

                    if ("Y".equals(XorY) && ydis < 0) {
                        d = Constants.Direction.UP;
                    }
                }//end
                tries++;
                if (tries > 1) {
                    this.setTurnAction(Constants.TurnAction.NONE);
                    this.addAction(this.finishTurn());
                    return;
                }
            }

            this.setTurnAction(Constants.TurnAction.MOVE);
        }//end if active
    }

    @Override
    public void moveAction() {
        super.moveAction();
        moving = true;

    }
}
