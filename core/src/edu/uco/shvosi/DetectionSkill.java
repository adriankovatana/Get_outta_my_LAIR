/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author cody
 */
public class DetectionSkill extends Skill {

    public DetectionSkill(int x, int y, Animation mainAnimation, int damage) {
       super(x, y, mainAnimation);
       this.damage = damage;  
    }
    
    @Override
    public void draw(Batch batch, float alpha, Protagonist bernard) {
        update();
        
        batch.draw(mainAnimation.getKeyFrame(skillRunTime), bernard.getX() - 205, bernard.getY() - 205, Constants.TILEDIMENSION * 5, Constants.TILEDIMENSION * 5);
        
    }
}
