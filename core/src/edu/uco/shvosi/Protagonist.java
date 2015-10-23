package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Protagonist extends Entity implements Observable {

    private int health;
    private int maxHealth;
    private Constants.Direction direction;
    private Skill activeSkill;
    private HashMap<String, Skill> skills;
    private Constants.SkillName skillname;
    ParticleEffect smokeParticle;
    ParticleEffect poisonParticle;
    private Label effectLabel;
    private int heldItem = 0;
    public int mute = 0;
    public int redLaserCooldown = 0;
    private Constants.MapGridCode[][] currentMap;
    
    public SequenceAction seqAction;
    private TextureRegion healthbarBackground;
    private TextureRegion healthbarFill;

    public void setCurrentMap(Constants.MapGridCode[][] currentMap) {
        this.currentMap = currentMap;
    }

    private boolean scaleEffect = false;
    private boolean blind = false;
    private boolean poison = false;
    private boolean transform = false;
    private boolean sliding = false;
    private boolean stopped = false;
    private int blindCounter = 0;
    private int poisonCounter = 0;
    private int transformCounter = 0;
    private Animation jump;
    private float elapsedJump = 0f;
    private TextureRegion tempJump;
    private Animation smoke;
    private float elapsedSmoke = 0f;
    private TextureRegion tempSmoke;
    private boolean smokeStart = true;
    private boolean executeDetection;
    private boolean executeBarrier;
    private boolean executeLightBarrier;
    private int barrierLimit = 0;
    private int lightBarrierLimit = 0;
    private int barrierDamage = 0;
    private boolean healEffect;
    private boolean greyKey;
    private boolean redKey;

    private List<Observer> observers;

    public Protagonist(int cX, int cY) {
        super(Constants.EntityGridCode.PLAYER, TextureLoader.BERNARDTEXTURE, cX, cY);
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.direction = Constants.Direction.NONE;
        this.observers = new ArrayList();

        this.name = "Bernard";

        skillname = Constants.SkillName.NONE;
        skills = new HashMap<String, Skill>();
        skills.put("Basic Laser", new SkillOne());
        skills.put("Rotating Laser", new SkillTwo());
        skills.put("Red Laser", new RedLaserSkill());
        skills.put("Detection", new DetectionSkill());
        skills.put("Barrier", new BarrierSkill());
        skills.put("Light Barrier", new LightBarrierSkill());

        smokeParticle = new ParticleEffect();
        smokeParticle.load(Gdx.files.internal("traps/smoke.p"), Gdx.files.internal("traps"));
        poisonParticle = new ParticleEffect();
        poisonParticle.load(Gdx.files.internal("traps/poison.p"), Gdx.files.internal("traps"));
        poisonParticle.scaleEffect(-0.40f);

        this.effectLabel = new Label("", TextureLoader.SKIN);

        jump = TextureLoader.jump;
        smoke = TextureLoader.smokeTrap;
        
        greyKey = false;
        redKey = false;
        
        seqAction = new SequenceAction();
        healthbarBackground = new TextureRegion(TextureLoader.HPBARBACKGROUND);
        healthbarFill = new TextureRegion(TextureLoader.HPBARFILL);
    }

    public void setActiveSkill() {
        switch (this.skillname) {
            case SKILLONE:
                this.activeSkill = skills.get("Basic Laser");
                break;
            case REDLASERSKILL:
                this.activeSkill = skills.get("Red Laser");

                if (this.textureRegion.isFlipX()) {
                    for (int i = 1; i <= this.activeSkill.getWidth(); i++) {
                        if (currentMap[this.getCX() - i][this.getCY()] != Constants.MapGridCode.FLOOR) {
                            activeSkill.setWidth(i - 1);
                            break;
                        }
                    }
                } else {
                    for (int i = 1; i <= this.activeSkill.getWidth(); i++) {
                        if (currentMap[this.getCX() + i][this.getCY()] != Constants.MapGridCode.FLOOR) {
                            activeSkill.setWidth(i - 1);
                            break;
                        }
                    }
                }
                this.redLaserCooldown = 5;
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
            case LIGHTBARRIERSKILL:
                this.activeSkill = skills.get("Light Barrier");
                break;
            default:
        }
        this.activeSkill.playSound();
    }

    public Skill getActiveSkill() {
        return this.activeSkill;
    }

    public void setSkill(Constants.SkillName skillname) {
        this.skillname = skillname;
    }

    public HashMap<String, Skill> getSkills() {
        return skills;
    }

    public int getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(int n) {
        heldItem = n;
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
        redLaserCooldown--;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (executeLightBarrier == true) {
            if (lightBarrierLimit >= 3) {
                TextureLoader.lightBarrier.setFrameDuration(.04f);
            }
            if (lightBarrierLimit == 1) {
                TextureLoader.lightBarrier.setFrameDuration(.09f);
            }
            skills.get("Light Barrier").draw(batch, alpha, this);
        }
        if (executeBarrier == true) {
            if (barrierLimit == 3) {
                TextureLoader.barrierSkill.setFrameDuration(.02f);
            }
            if (barrierLimit == 2) {
                TextureLoader.barrierSkill.setFrameDuration(.05f);
            }
            if (barrierLimit == 1) {
                TextureLoader.barrierSkill.setFrameDuration(.07f);
            }
            skills.get("Barrier").draw(batch, alpha, this);
        }

        if (transformCounter >= 2 && transform == true) {
            if (smokeStart == true) {
                elapsedSmoke += Gdx.graphics.getDeltaTime();
                tempSmoke = smoke.getKeyFrame(elapsedSmoke);
                batch.draw(smoke.getKeyFrame(elapsedSmoke), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                if (smoke.isAnimationFinished(elapsedSmoke)) {
                    smokeStart = false;
                }
            }
            elapsedJump += Gdx.graphics.getDeltaTime();
            tempJump = jump.getKeyFrame(elapsedJump);
            batch.draw(jump.getKeyFrame(elapsedJump), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (jump.isAnimationFinished(elapsedJump)) {
                elapsedJump = 0f;
            }
            if (transformCounter == 4) {
                elapsedSmoke = 0f;
                smokeStart = true;
                transform = false;
                transformCounter = 0;
            }
        } else {
            super.draw(batch, alpha);
        }

        if (this.isDead()) {
            // Draw death animation
        } else if (this.activeSkill != null) {
            this.activeSkill.draw(batch, alpha, this);
        }

        if (poisonCounter >= 2 && poison == true) {
            poisonParticle.start();
            poisonParticle.getEmitters().first().setPosition(this.getX() + 50, this.getY() + 35);

            poisonParticle.draw(batch, Gdx.graphics.getDeltaTime());
            if (poisonCounter == 6) {
                poison = false;
                poisonCounter = 0;
                poisonParticle.reset();
            }
        }
        if (executeDetection == true) {
            resetStatusCounter();
            smokeParticle.reset();
            poisonParticle.reset();
            poison = false;
            blind = false;
            executeDetection = false;
        }

        if (blindCounter >= 2 && blind == true) {
            smokeParticle.start();
            smokeParticle.getEmitters().first().setPosition(this.getX() + 50, this.getY() + 35);
            if (scaleEffect == true) {
                smokeParticle.scaleEffect(1.40f);
                scaleEffect = false;
            }

            smokeParticle.draw(batch, Gdx.graphics.getDeltaTime());
            if (blindCounter == 7) {
                smokeParticle.scaleEffect(.13281f);
                blind = false;
                blindCounter = 0;
                smokeParticle.reset();
            }
        }
        
        //HP BAR
        batch.draw(healthbarBackground, this.getX()+10, this.getY());
        batch.draw(healthbarFill, this.getX()+11, this.getY()+1, healthbarFill.getRegionWidth()*((float)health/(float)maxHealth), healthbarFill.getRegionHeight());
    }

    @Override
    public void performDeath() {
        this.addAction(sequence(deathAnimation(), finishTurn()));
    }

    public int getHealth() {
        return this.health;
    }

    public void takeDamage(int damage) {
        if (executeBarrier == true) {
            this.health -= damage / 2;
            this.barrierDamage += damage / 2;
            this.barrierLimit -= 1;
            if (barrierLimit == 0) {
                healEffect = true;
            }
        } else {
            this.health -= damage;
        }
        this.addAction(this.takeDamageAnimation());
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

    public Constants.SkillName getSkillName() {
        return this.skillname;
    }

    public Rectangle2D.Double getDetectionCollisionBox() {
        return new Rectangle2D.Double(this.getCX(), this.getCY(), 3, 3);
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
        if(sliding){
            seqAction.addAction(moveAction);
            sliding = false;
        }
        else{
            seqAction.addAction(moveAction);
            //seqAction.addAction(finishTurn());
            this.addAction(seqAction);
            this.addAction(finishTurn());
        }
    }

    public void attackAction() {
        //Do Stuffs
        switch (this.skillname) {
            case SKILLONE:
                if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage);
                }
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
                if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(2).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(2).setDamage(activeSkill.damage);
                }
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
                if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                }
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

    public Action takeDamageAnimation() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                return true;
            }
        };
    }

    public Action finishTurn() {
        if (blind == true) {
            blindCounter++;
            scaleEffect = true;
        }
        if (poison == true) {
            poisonCounter++;
            if (poisonCounter >= 2) {
                this.takeDamage(8);
            }
        }
        if (transform == true) {
            transformCounter++;
        }
        return new Action() {
            @Override
            public boolean act(float delta) {
                if(Protagonist.this.getActions().size > 1)
                    return false;
                Protagonist.this.setTurnFinished(true);
                Protagonist.this.turnAction = Constants.TurnAction.NONE;
                return true;
            }
        };
    }

    void useItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void resetStatusCounter() {
        poison = false;
        blind = false;
        blindCounter = 0;
        poisonCounter = 0;
    }

    public void setBlind(boolean blind) {
        this.blind = blind;
    }

    public boolean getBlind() {
        return blind;
    }

    public void setPoison(boolean poison) {
        this.poison = poison;
    }

    public boolean getPoison() {
        return poison;
    }

    public void setExecuteDetection(boolean executeDetection) {
        this.executeDetection = executeDetection;
    }

    public void setTransform(boolean transform) {
        this.transform = transform;
    }

    public boolean getTransform() {
        return transform;
    }

    public int getTransformCounter() {
        return transformCounter;
    }

    public void setExecuteBarrier(boolean executeBarrier) {
        this.executeBarrier = executeBarrier;
    }

    public void setExecuteLightBarrier(boolean executeLightBarrier) {
        this.executeLightBarrier = executeLightBarrier;
    }

    public boolean getExecuteBarrier() {
        return executeBarrier;
    }

    public boolean getExecuteLightBarrier() {
        return executeLightBarrier;
    }

    public void setBarrierLimit(int b) {
        barrierLimit = b;
    }

    public void setLightBarrierLimit(int b) {
        lightBarrierLimit = b;
    }

    public int getLightBarrierLimit() {
        return lightBarrierLimit;
    }

    public int getBarrierDamage() {
        return barrierDamage;
    }

    public void resetBarrierDamage() {
        barrierDamage = 0;
    }

    public boolean getHealEffect() {
        return healEffect;
    }

    public void setHealEffect(boolean heal) {
        this.healEffect = heal;
    }

    void setSliding(boolean b) {
        this.sliding = b;
    }
    
    boolean getSliding() {
        return sliding;
    }

    void setRedKey(boolean b) {
        redKey = b;
    }
    void setGreyKey(boolean b) {
        greyKey = b;
    }
    boolean getRedKey() {
        return redKey;
    }
    boolean getGreyKey() {
        return greyKey;
    }
    
}
