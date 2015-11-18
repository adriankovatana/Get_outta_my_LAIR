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
    private TexturedActor xpbarFill;
    private BitmapFont levelText;
    
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
        
        xpbarFill = new TexturedActor(TextureLoader.XPBARFILL,193,68){
            @Override
            public void draw(Batch batch, float parentAlpha){
                batch.draw(xpbarFill.textureRegion.getTexture(), xpbarFill.getX(), xpbarFill.getY(),
                xpbarFill.textureRegion.getRegionWidth()*GameUI.this.screen.map.bernard.getXPPercentage(), 
                xpbarFill.textureRegion.getRegionHeight(),
                xpbarFill.textureRegion.getU(),xpbarFill.textureRegion.getV(),
                xpbarFill.textureRegion.getU2()*GameUI.this.screen.map.bernard.getXPPercentage(),
                xpbarFill.textureRegion.getV2());
            }
        };
        levelText = new BitmapFont();
        levelText.setColor(Color.WHITE);
    }
    
    public void draw(Batch batch, float alpha){
        xpbarFill.draw(batch, alpha);
        levelText.draw(batch, "Level "+screen.map.bernard.getLevel(), 410, 80);
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