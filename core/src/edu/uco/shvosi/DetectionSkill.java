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

    public DetectionSkill() {
        super(0, 0, TextureLoader.detectionSkill,
                Gdx.audio.newSound(Gdx.files.internal("sounds/skill3.mp3")));
        this.damage = 0;
        this.width = 6;
        this.height = 6;
        this.name = Constants.SkillName.DETECTION;
    }

    @Override
    public void draw(Batch batch, float alpha, Entity entity) {
        this.update();
        if (entity.textureRegion.isFlipX()) {
            temp = animation.getKeyFrame(elapsed);
            temp.flip(true, false);
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION - 150, entity.getY() - Constants.TILEDIMENSION - 150, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
            temp.flip(true, false);
        } else {
            batch.draw(animation.getKeyFrame(elapsed), entity.getX() - Constants.TILEDIMENSION - 150, entity.getY() - Constants.TILEDIMENSION - 150, Constants.TILEDIMENSION * width, Constants.TILEDIMENSION * height);
        }

    }
}
