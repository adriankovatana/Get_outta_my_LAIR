package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Protagonist extends Entity implements Observable {

    private int health;
    private int maxHealth;
    private Constants.Direction direction;
    private Skill activeSkill;
    private HashMap<String, Skill> skills;
    private SkillName skillname;

    private List<Observer> observers;

    public Protagonist(int cX, int cY) {
        super(Constants.EntityGridCode.PLAYER, TextureLoader.BERNARDTEXTURE, cX, cY);
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.direction = Constants.Direction.NONE;
        this.observers = new ArrayList();

        this.name = "Bernard";

        skillname = SkillName.NONE;
        skills = new HashMap<String, Skill>();
        skills.put("Basic Laser", new SkillOne());
        skills.put("Rotating Laser", new SkillTwo());
        skills.put("Red Laser", new RedLaserSkill());
        skills.put("Detection", new DetectionSkill());
        skills.put("Barrier", new BarrierSkill());

    }

    public void setActiveSkill() {
        switch (this.skillname) {
            case SKILLONE:
                this.activeSkill = skills.get("Basic Laser");
                break;
            case REDLASERSKILL:
                this.activeSkill = skills.get("Red Laser");
                break;
            case SKILLTWO:
                this.activeSkill = skills.get("Rotating Laser");
                break;
            case DETECTION:
                this.activeSkill = skills.get("Detection");
                break;
            case BARRIERSKILL:
                this.activeSkill = skills.get("Barrier");
                break;
            default:
        }
        this.activeSkill.playSound();
    }

    public Skill getActiveSkill() {
        return this.activeSkill;
    }

    public void setSkill(SkillName skillname) {
        this.skillname = skillname;
    }

    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    @Override
    public void performActions() {
        //this.setTurnFinished(false);
        switch (this.turnAction) {
            case MOVE:
                moveAction();
                break;
            case ATTACK:
                //attackAction();
                break;
            default:
                this.setTurnFinished(true);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);

        if (this.isDead()) {
            // Draw death animation
        } else if (this.activeSkill != null) {
            this.activeSkill.draw(batch, alpha, this);
        }
    }

    @Override
    public void performDeath() {
        this.addAction(sequence(deathAnimation(), finishTurn()));
    }

    public int getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.setDead(true);
        }
    }

    public void heal(int value) {
        this.health += value;
        if (this.health >= maxHealth) {
            this.health = maxHealth;
        }
    }

    public Constants.Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Constants.Direction direction) {
        this.direction = direction;
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.observerUpdate(this);
        }
    }

    public void addObserver(Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    public void removeAllObservers() {
        this.observers.clear();
    }

    public void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        this.addAction(sequence(moveAction, finishTurn()));
    }

    public void attackAction() {
        //Do Stuffs
        switch (this.skillname) {
            case SKILLONE:
                if (this.textureRegion.isFlipX()) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() - 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() - 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                } else {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() + 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                }
                break;
            case REDLASERSKILL:
                if (this.textureRegion.isFlipX()) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() - 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() - 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX() - 3);
                    activeSkill.damageEntities.get(2).setCY(this.getCY());
                } else {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() + 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX() + 3);
                    activeSkill.damageEntities.get(2).setCY(this.getCY());
                }
//                Gdx.app.log(activeSkill.damageEntities.get(0).name + " 0 ", "" + activeSkill.damageEntities.get(0).getCX() + " , " + activeSkill.damageEntities.get(0).getCY());
//                Gdx.app.log(activeSkill.damageEntities.get(1).name + " 1 ", "" + activeSkill.damageEntities.get(1).getCX() + " , " + activeSkill.damageEntities.get(1).getCY());
//                Gdx.app.log(activeSkill.damageEntities.get(2).name + " 2 ", "" + activeSkill.damageEntities.get(2).getCX() + " , " + activeSkill.damageEntities.get(2).getCY());
                break;
            case SKILLTWO:
                if (this.textureRegion.isFlipX()) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() - 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                } else {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());

                }
                break;
            default:

        }

        this.addAction(sequence(attackAnimation(), finishTurn()));
    }

    public Action attackAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                if (Protagonist.this.activeSkill.isAnimationFinished()) {
                    Protagonist.this.activeSkill = null;
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    public Action deathAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                //Temp death
                return true;
                /*if (this.deathAnimation.isAnimationFinished()) {
                 return true;
                 }*/
                //return false;
            }
        };
    }

    public Action finishTurn() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                Protagonist.this.setTurnFinished(true);
                Protagonist.this.turnAction = Constants.TurnAction.NONE;
                return true;
            }
        };
    }

    public enum SkillName {

        NONE,
        SKILLONE,
        SKILLTWO,
        DETECTION,
        BARRIERSKILL,
        REDLASERSKILL;
    }
}
