package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;

public class GameUI implements Disposable{
    private GameScreen screen;
    private TexturedActor hud;
    private TexturedActor health;
    private BitmapFont healthText;
    
    public GameUI(GameScreen screen){
        this.screen = screen;
        hud = new TexturedActor(TextureLoader.HUD,0,0);
        health = new TexturedActor(TextureLoader.HEALTHPOOL,74,0){
            @Override
            public void draw(Batch batch, float parentAlpha){
                batch.draw(health.textureRegion.getTexture(), health.getX(), health.getY(),
                health.textureRegion.getRegionWidth(), 
                health.textureRegion.getRegionHeight()*GameUI.this.screen.map.bernard.getHealthPercentage(),
                health.textureRegion.getU(),health.textureRegion.getV(),health.textureRegion.getU2(),
                health.textureRegion.getV2()*GameUI.this.screen.map.bernard.getHealthPercentage());
            }
        };
        healthText = new BitmapFont();
        healthText.setColor(Color.WHITE);
    }
    
    public void draw(Batch batch, float alpha){
        health.draw(batch, alpha);
        healthText.draw(batch, screen.map.bernard.getHealth()+" / "+screen.map.bernard.getMaxHealth(), 115, 65);
        
        //Skill Icons
        
        hud.draw(batch, alpha);
        screen.invent.draw(batch, alpha);
    }

    @Override
    public void dispose() {
        healthText.dispose();
    }
}