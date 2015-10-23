package edu.uco.shvosi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Entity extends Image {

    protected int cX; // Coordinate X
    protected int cY; // Coordinate Y
    protected int pCX; // Previous Coordinate X
    protected int pCY; // Previous Coordinate Y
    protected boolean dead; // Life state
    protected boolean turnFinished; // Used to check if all actions are finished
    protected Constants.EntityGridCode gridCode; //NONE if not on grid, has type otherwise
    protected TextureRegion textureRegion;
    protected Constants.TurnAction turnAction;

    protected String name;

    public Entity(Constants.EntityGridCode gridCode, Texture texture, int cX, int cY) {
        this.cX = cX;
        this.cY = cY;
        this.pCX = this.cX;
        this.pCY = this.cY;
        this.setPosition(this.cX * Constants.TILEDIMENSION,
                this.cY * Constants.TILEDIMENSION);
        this.dead = false;
        this.turnFinished = false;
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        this.gridCode = gridCode;
        textureRegion = new TextureRegion(texture);
        this.turnAction = Constants.TurnAction.NONE;
    }

    public String getName() {
        return this.name;
    }

    public int getCX() {
        return this.cX;
    }

    public void setCX(int cX) {
        this.cX = cX;
    }

    public int getCY() {
        return this.cY;
    }

    public void setCY(int cY) {
        this.cY = cY;
    }

    public int getPCX() {
        return this.pCX;
    }

    public void setPCX(int pCX) {
        this.pCX = pCX;
    }

    public int getPCY() {
        return this.pCY;
    }

    public void setPCY(int pCY) {
        this.pCY = pCY;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead(boolean b) {
        this.dead = b;
    }

    public boolean turnFinished() {
        return this.turnFinished;
    }

    public void setTurnFinished(boolean b) {
        this.turnFinished = b;
    }

    public Constants.TurnAction getTurnAction() {
        return this.turnAction;
    }

    public void setTurnAction(Constants.TurnAction turnAction) {
        this.turnAction = turnAction;
    }

    public Constants.EntityGridCode getGridCode() {
        return this.gridCode;
    }

    public void flipTexture(Constants.Direction direction) {
        switch (direction) {
            case LEFT:
                if (!textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                break;
            case RIGHT:
                if (textureRegion.isFlipX()) {
                    textureRegion.flip(true, false);
                }
                break;
            default:
                //Do nothing
                break;
        }
    }

    public void performActions() {
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, this.getX(), this.getY());
    }

    public void performDeath() {
        //Add death actions then remove itself from the stage
        this.turnFinished = true;
        this.remove();
    }

    public void collision(Entity entity) {
    }
}
