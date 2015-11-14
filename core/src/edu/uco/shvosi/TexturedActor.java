package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TexturedActor extends Actor{
    public TextureRegion textureRegion;
    
    public TexturedActor(Texture texture, float x, float y){
        super();
        this.textureRegion = new TextureRegion(texture);
        this.setPosition(x, y);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(textureRegion, this.getX(), this.getY());
    }
}