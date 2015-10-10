/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Cary
 */
public class EnemySkill extends Entity implements Observable {

    protected float skillRunTime;
    protected Animation mainAnimation;
    protected int damage;
    private ArrayList<Observer> observers;
    protected Rectangle boundingBox;

    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    public EnemySkill(int x, int y, Animation mainAnimation) {
        super(Constants.EntityGridCode.NONE, new Texture(mainAnimation.getKeyFrame(0).getRegionWidth(), mainAnimation.getKeyFrame(0).getRegionHeight(), Pixmap.Format.Alpha), x, y);
        
        this.mainAnimation = mainAnimation;
        skillRunTime = 0f;
        
        boundingBox = new Rectangle();
        observers = new ArrayList<Observer>();
        
        this.name = "EnemySkill";
    }
    
    public void update() {
        skillRunTime += Gdx.graphics.getDeltaTime();
    }

    public boolean isAnimationFinished() {
        if (mainAnimation.isAnimationFinished(skillRunTime)) {
            skillRunTime = 0f;
            return true;
        } else {
            return false;
        }
    }
    
    public void draw(Batch batch, float alpha, Antagonist e) {
    }
    
    public int getDamage() {
        return damage;
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers) {
            o.observerUpdate(this);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

}
