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
class SkillTwo extends Skill {

    private float rotation;

    public SkillTwo(int x, int y, Animation mainAnimation, int damage) {
        super(x, y, mainAnimation);
        this.damage = damage;
        rotation = 0f;
    }

    @Override
    public void update() {
        super.update();
        rotation += 200 * Gdx.graphics.getDeltaTime();
    }

    @Override
    public boolean isAnimationFinished() {
        if (mainAnimation.isAnimationFinished(skillRunTime / 6)) {
            rotation = 0f;
            skillRunTime = 0f;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void draw(Batch batch, float alpha, Protagonist bernard) {
        update();
        
        boundingBox.set(this.getX(), this.getY(), Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION);
        
        batch.draw(mainAnimation.getKeyFrame(skillRunTime), bernard.getX(), bernard.getY(), bernard.getWidth() / 2, bernard.getHeight() / 2, Constants.TILEDIMENSION * 2, Constants.TILEDIMENSION, 1, 1, rotation);

    }
}
