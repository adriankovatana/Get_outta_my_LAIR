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
    
    private TexturedActor basiclaserIcon;
    private TexturedActor rotatinglaserIcon;
    private TexturedActor detectionIcon;
    private TexturedActor barrierIcon;
    private TexturedActor redlaserIcon;
    private TexturedActor lightninginfusionIcon;
    private TexturedActor fusionIcon;
//    private TexturedActor blizzardIcon;
    
    private BitmapFont detectionText;
    private BitmapFont barrierText;
    private BitmapFont redlaserText;
    private BitmapFont lightningText;
    private BitmapFont fusionText;
//    private BitmapFont blizzardText;
    
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
        
        basiclaserIcon = new TexturedActor(TextureLoader.SKILLONEICON, 194, 6);
        
        rotatinglaserIcon = new TexturedActor(TextureLoader.SKILLTWOICON, 262, 6);
        
        detectionIcon = new TexturedActor(TextureLoader.DETECTICON, 328, 6);
        detectionText = new BitmapFont();
        detectionText.setColor(Color.WHITE);
        
        barrierIcon = new TexturedActor(TextureLoader.BARRIERICON, 394, 6);
        barrierText = new BitmapFont();
        barrierText.setColor(Color.WHITE);
        
        redlaserIcon = new TexturedActor(TextureLoader.REDLASERICON, 460, 6);
        redlaserText = new BitmapFont();
        redlaserText.setColor(Color.WHITE);
        
        lightninginfusionIcon = new TexturedActor(TextureLoader.LIGHTINFUSEICON, 526, 6);
        lightningText = new BitmapFont();
        lightningText.setColor(Color.WHITE);
        
        fusionIcon = new TexturedActor(TextureLoader.FUSIONICON, 592, 6);
        fusionText = new BitmapFont();
        fusionText.setColor(Color.WHITE);
        
//        blizzardIcon = new TexturedActor(TextureLoader.SKILLONEICON, 194, 6);
//        detectionText = new BitmapFont();
//        detectionText.setColor(Color.WHITE);
    }
    
    public void draw(Batch batch, float alpha){
        xpbarFill.draw(batch, alpha);
        levelText.draw(batch, "Level "+screen.map.bernard.getLevel(), 410, 80);
        health.draw(batch, alpha);
        healthText.draw(batch, screen.map.bernard.getHealth()+" / "+screen.map.bernard.getMaxHealth(), 115, 65);
        
        //Skill Icons
        /*if(screen.map.bernard.getLevel() >= Constants.DETECTIONREQ &&
                screen.map.bernard.barrierCooldown > 0)
            detectionIcon.draw(batch, alpha);*/
//        if(screen.map.bernard.getLevel() >= Constants.BARRIERREQ &&
//                screen.map.bernard.barrierCooldown > 0)
//            barrierIcon.draw(batch, alpha);
//        if(screen.map.bernard.getLevel() >= Constants.REDLASERREQ &&
//                screen.map.bernard.redLaserCooldown > 0)
//            redlaserIcon.draw(batch, alpha);
        
        hud.draw(batch, alpha);
        
        if(screen.map.bernard.getLevel() >= Constants.SKILLONEREQ) /*&&
                screen.map.bernard.barrierCooldown > 0)*/
            basiclaserIcon.draw(batch, alpha);
        
        if(screen.map.bernard.getLevel() >= Constants.SKILLTWOREQ) /*&&
                screen.map.bernard.barrierCooldown > 0)*/
            rotatinglaserIcon.draw(batch, alpha);
        
        if(screen.map.bernard.getLevel() >= Constants.DETECTIONREQ) /*&&
                screen.map.bernard.barrierCooldown > 0)*/
            detectionIcon.draw(batch, alpha);
        
        if(screen.map.bernard.getLevel() >= Constants.BARRIERREQ){
            if(screen.map.bernard.barrierCooldown <= 0)
                barrierIcon.draw(batch, alpha);
            else
                barrierText.draw(batch, ""+screen.map.bernard.barrierCooldown, 426, 33);
        }
        
        if(screen.map.bernard.getLevel() >= Constants.REDLASERREQ){
            if(screen.map.bernard.redLaserCooldown <= 0)
                redlaserIcon.draw(batch, alpha);
            else
                redlaserText.draw(batch, ""+screen.map.bernard.redLaserCooldown, 492, 33);
        }
        
        if(screen.map.bernard.getLevel() >= Constants.LIGHTBARRIERREQ){
            if(screen.map.bernard.lightningInfusionCooldown <= 0)
                lightninginfusionIcon.draw(batch, alpha);
            else
                lightningText.draw(batch, ""+screen.map.bernard.lightningInfusionCooldown, 558, 33);
        }
        
        if(screen.map.bernard.getLevel() >= Constants.LASERREQ){
            if(screen.map.bernard.fusionCooldown <= 0)
                fusionIcon.draw(batch, alpha);
            else
                fusionText.draw(batch, ""+screen.map.bernard.fusionCooldown, 624, 33);
        }
        
//        if(screen.map.bernard.getLevel() >= Constants.FREEZINGREQ){
//            if(screen.map.bernard.freezingCooldown <= 0)
//                blizzardIcon.draw(batch, alpha);
//            else
//                blizzardText.draw(batch, ""+screen.map.bernard.freezingCooldown, 558, 33);
//        }
        
        screen.invent.draw(batch, alpha);
    }

    @Override
    public void dispose() {
        healthText.dispose();
    }
}