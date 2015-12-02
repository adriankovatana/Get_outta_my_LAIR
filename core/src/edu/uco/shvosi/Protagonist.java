package edu.uco.shvosi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;

public class Protagonist extends Entity {

    private Dialog levelUpDialog;
    private Dialog newSkillDialog;
    private int levelUpCounter;
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
    public int barrierCooldown = 0;
    public int lightningInfusionCooldown = 0;
    public int redLaserCooldown = 0;
    public int freezingCooldown = 0;
    public int fusionCooldown = 0;
    private Constants.MapGridCode[][] currentMap;
    private int currentXp = 0;
    private int xpToNextLevel = 100;
    private int level = 1;
    private float strengthMod = 1f;
    private boolean shieldActive = false;
    public SequenceAction seqAction;
    public Constants.Direction slideDirection;
    public int slideCounter;

//    private TextureRegion healthbarBackground;
//    private TextureRegion healthbarFill;
    private boolean scaleEffect = false;
    private boolean powerReduce = false;
    private boolean blind = false;
    private boolean poison = false;
    private boolean transform = false;
    private boolean sliding = false;
    private boolean stopped = false;
    private int reduceCounter = 0;
    private int blindCounter = 0;
    private int poisonCounter = 0;
    private int transformCounter = 0;
    private Animation reduce;
    private Animation jump;
    private Animation death;
    private TextureRegion temp;
    private float elapsedReduce = 0f;
    private float elapsedJump = 0f;
    private float elapsedDeath = 0f;
    private Animation smoke;
    private float elapsedSmoke = 0f;
    private boolean smokeStart = true;
    private boolean executeDetection;
    private boolean executeBarrier;
    private boolean executeLightBarrier;
    private int barrierLimit = 0;
    private int lightBarrierLimit = 0;
    private int barrierDamage = 0;
    private boolean healEffect;
    String levelup = "You are now level " + level + "!";
    String textlevel = "Choose to upgrade Health or Damage\n" + levelup;
    static ArrayList<Entity> inventory = new ArrayList<Entity>();
    private int index = 0;
    static Entity active = null;
    protected int sightRadius;

    public Protagonist(int cX, int cY) {
        super(Constants.EntityGridCode.PLAYER, TextureLoader.BERNARDTEXTURE, cX, cY);
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.direction = Constants.Direction.NONE;
        this.sightRadius = 3;

        this.name = "Bernard";

        skillname = Constants.SkillName.NONE;
        skills = new HashMap<String, Skill>();
        skills.put("Basic Laser", new SkillOne());
        skills.put("Rotating Laser", new SkillTwo());
        skills.put("Red Laser", new RedLaserSkill());
        skills.put("Detection", new DetectionSkill());
        skills.put("Barrier", new BarrierSkill());
        skills.put("Light Barrier", new LightBarrierSkill());
        skills.put("Freezing", new FreezingSkill());
        skills.put("Laser Fusion", new LaserSkill());

        smokeParticle = new ParticleEffect();
        smokeParticle.load(Gdx.files.internal("traps/smoke.p"), Gdx.files.internal("traps"));
        poisonParticle = new ParticleEffect();
        poisonParticle.load(Gdx.files.internal("traps/poison.p"), Gdx.files.internal("traps"));
        poisonParticle.scaleEffect(-0.40f);

        this.effectLabel = new Label("", TextureLoader.SKIN);

        jump = TextureLoader.jump;
        smoke = TextureLoader.smokeTrap;
        death = TextureLoader.bernardDeath;
        reduce = TextureLoader.powerTrap;

        seqAction = new SequenceAction();
        slideDirection = Constants.Direction.NONE;
        slideCounter = 0;

//        healthbarBackground = new TextureRegion(TextureLoader.HPBARBACKGROUND);
//        healthbarFill = new TextureRegion(TextureLoader.HPBARFILL);
        levelUpDialog = new Dialog("Level Up!", TextureLoader.SKIN) {

            @Override
            protected void result(Object obj) {
                if (obj.toString() == "true") {
                    Protagonist.this.maxHealth += 10;
                } else {
                    strengthMod += 0.1f;
                    for (Skill s : Protagonist.this.skills.values()) {
                        s.setDamage((int) (s.getBaseDamage() * strengthMod));
                    }
                }
                Protagonist.this.levelUpCounter--;
            }

            @Override
            public void hide() {
                if (levelUpCounter == 0) {
                    this.setVisible(false);
                }
            }

        }.text(textlevel).button("Health", true).button("Damage", false);

        levelUpDialog.setMovable(
                false);

        newSkillDialog = new Dialog("New Skill Acquired!", TextureLoader.SKIN) {
            @Override
            public void hide() {
                this.setVisible(false);
            }
        }.button("Awesome, thanks!").text("Unlocked Lightning Infusion skill! Press the '5' key to activate.");

        newSkillDialog.setMovable(false);
    }

    public void setActiveSkill() {
        switch (this.skillname) {
            case SKILLONE:
                this.activeSkill = skills.get("Basic Laser");
                break;
            case REDLASERSKILL:
                this.activeSkill = skills.get("Red Laser");

//                if (this.textureRegion.isFlipX()) {
//                    for (int i = 1; i <= this.activeSkill.getWidth(); i++) {
//                        if (currentMap[this.getCX() - i][this.getCY()] != Constants.MapGridCode.FLOOR) {
//                            activeSkill.setWidth(i - 1);
//                            break;
//                        }
//                    }
//                } else {
//                    for (int i = 1; i <= this.activeSkill.getWidth(); i++) {
//                        if (currentMap[this.getCX() + i][this.getCY()] != Constants.MapGridCode.FLOOR) {
//                            activeSkill.setWidth(i - 1);
//                            break;
//                        }
//                    }
//                }
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
            case FREEZINGSKILL:
                this.activeSkill = skills.get("Freezing");
                this.freezingCooldown = 5;
                break;
            case LASERSKILL:
                this.activeSkill = skills.get("Laser Fusion");
                this.fusionCooldown = 3;
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
                attackAction();
                break;
            default:
                this.setTurnFinished(true);
                break;
        }
        redLaserCooldown--;
        barrierCooldown--;
        lightningInfusionCooldown--;
        freezingCooldown--;
        fusionCooldown--;
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

        if (transformCounter >= 2 && transform == true) {
            if (smokeStart == true) {
                elapsedSmoke += Gdx.graphics.getDeltaTime();
                batch.draw(smoke.getKeyFrame(elapsedSmoke), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
                if (smoke.isAnimationFinished(elapsedSmoke)) {
                    smokeStart = false;
                }
            }
            elapsedJump += Gdx.graphics.getDeltaTime();
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
        } else if (this.isDead()) {
            elapsedDeath += Gdx.graphics.getDeltaTime();
            if (this.textureRegion.isFlipX()) {
                temp = death.getKeyFrame(elapsedDeath);
                temp.flip(true, false);
                batch.draw(death.getKeyFrame(elapsedDeath), this.getX() + 6, this.getY(), Constants.TILEDIMENSION - 2, Constants.TILEDIMENSION - 12);
                temp.flip(true, false);
            } else {
                batch.draw(death.getKeyFrame(elapsedDeath), this.getX() - 6, this.getY(), Constants.TILEDIMENSION - 2, Constants.TILEDIMENSION - 12);
            }
        } else {
            super.draw(batch, alpha);
        }

        if (this.activeSkill != null) {
            this.activeSkill.draw(batch, alpha, this);
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

        if (powerReduce == true) {
            elapsedReduce += Gdx.graphics.getDeltaTime();
            batch.draw(reduce.getKeyFrame(elapsedReduce), this.getX(), this.getY(), Constants.TILEDIMENSION, Constants.TILEDIMENSION);
            if (reduce.isAnimationFinished(elapsedReduce)) {
                elapsedReduce = 0f;
            }
            if (reduceCounter == 6) {
                powerReduce = false;
                reduceCounter = 0;
            }
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
            powerReduce = false;
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
//        batch.draw(healthbarBackground, this.getX() + 10, this.getY());
//        batch.draw(healthbarFill, this.getX() + 11, this.getY() + 1, healthbarFill.getRegionWidth() * ((float) health / (float) maxHealth), healthbarFill.getRegionHeight());
        if (shieldActive) {
            batch.draw(TextureLoader.BERNARDSHIELDTEXTURE, this.getX(), this.getY());
        }
    }

    @Override
    public void performDeath() {
        this.addAction(deathAnimation());
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public float getHealthPercentage() {
        return (float) this.health / this.maxHealth;
    }

    public float getXPPercentage() {
        return (float) this.currentXp / this.xpToNextLevel;
    }

    public void takeDamage(int damage) {
        if (shieldActive) {
            shieldActive = false;
        } else {
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
        }

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

    public void moveAction() {
        seqAction.addAction(normalMoveToAction());
        this.addAction(seqAction);
    }

    public MoveToAction normalMoveToAction() {
        MoveToAction moveAction = new MoveToAction() {
            @Override
            protected void end() {
                if (!Protagonist.this.sliding) {
                    seqAction.addAction(finishTurn());
                }
            }
        };
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION);
        return moveAction;
    }

    public MoveToAction slideMoveToAction() {
        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition((float) (this.getCX() * Constants.TILEDIMENSION),
                (float) (this.getCY() * Constants.TILEDIMENSION));
        moveAction.setDuration(Constants.MOVEACTIONDURATION * slideCounter);
        return moveAction;
    }

    public void attackAction() {
        //Do Stuffs
        switch (this.skillname) {
            case SKILLONE:
                if (powerReduce == true && executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                    activeSkill.damageEntities.get(1).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                } else if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else if (powerReduce == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage);
                }
                if (this.direction == Constants.Direction.LEFT) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() - 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() - 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                } else if (this.direction == Constants.Direction.RIGHT) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() + 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                } else if (this.direction == Constants.Direction.UP) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX());
                    activeSkill.damageEntities.get(0).setCY(this.getCY() + 1);
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX());
                    activeSkill.damageEntities.get(1).setCY(this.getCY() + 2);
                } else if (this.direction == Constants.Direction.DOWN) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX());
                    activeSkill.damageEntities.get(0).setCY(this.getCY() - 1);
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX());
                    activeSkill.damageEntities.get(1).setCY(this.getCY() - 2);
                }
                break;
            case REDLASERSKILL:
                if (powerReduce == true && executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                    activeSkill.damageEntities.get(1).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                    activeSkill.damageEntities.get(2).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                } else if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(2).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else if (powerReduce == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage / 2);
                    activeSkill.damageEntities.get(2).setDamage(activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(2).setDamage(activeSkill.damage);
                }
                if (this.direction == Constants.Direction.LEFT) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() - 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() - 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX() - 3);
                    activeSkill.damageEntities.get(2).setCY(this.getCY());
                } else if (this.direction == Constants.Direction.RIGHT) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                    activeSkill.damageEntities.get(0).setCY(this.getCY());
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX() + 2);
                    activeSkill.damageEntities.get(1).setCY(this.getCY());
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX() + 3);
                    activeSkill.damageEntities.get(2).setCY(this.getCY());
                } else if (this.direction == Constants.Direction.UP) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX());
                    activeSkill.damageEntities.get(0).setCY(this.getCY() + 1);
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX());
                    activeSkill.damageEntities.get(1).setCY(this.getCY() + 2);
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX());
                    activeSkill.damageEntities.get(2).setCY(this.getCY() + 3);
                } else if (this.direction == Constants.Direction.DOWN) {
                    activeSkill.damageEntities.get(0).setDead(false);
                    activeSkill.damageEntities.get(0).setCX(this.getCX());
                    activeSkill.damageEntities.get(0).setCY(this.getCY() - 1);
                    activeSkill.damageEntities.get(1).setDead(false);
                    activeSkill.damageEntities.get(1).setCX(this.getCX());
                    activeSkill.damageEntities.get(1).setCY(this.getCY() - 2);
                    activeSkill.damageEntities.get(2).setDead(false);
                    activeSkill.damageEntities.get(2).setCX(this.getCX());
                    activeSkill.damageEntities.get(2).setCY(this.getCY() - 3);
                }
//                Gdx.app.log(activeSkill.damageEntities.get(0).name + " 0 ", "" + activeSkill.damageEntities.get(0).getCX() + " , " + activeSkill.damageEntities.get(0).getCY());
//                Gdx.app.log(activeSkill.damageEntities.get(1).name + " 1 ", "" + activeSkill.damageEntities.get(1).getCX() + " , " + activeSkill.damageEntities.get(1).getCY());
//                Gdx.app.log(activeSkill.damageEntities.get(2).name + " 2 ", "" + activeSkill.damageEntities.get(2).getCX() + " , " + activeSkill.damageEntities.get(2).getCY());
                break;

            case SKILLTWO:
                if (powerReduce == true && executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                } else if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else if (powerReduce == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage / 2);
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
            case FREEZINGSKILL:
                if (powerReduce == true && executeLightBarrier == true) {
                    for (int i = 0; i < 8; i++) {
                        activeSkill.damageEntities.get(i).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                    }
                } else if (executeLightBarrier == true) {
                    for (int i = 0; i < 8; i++) {
                        activeSkill.damageEntities.get(i).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    }
                } else if (powerReduce == true) {
                    for (int i = 0; i < 8; i++) {
                        activeSkill.damageEntities.get(i).setDamage(activeSkill.damage / 2);
                    }
                } else {
                    for (int i = 0; i < 8; i++) {
                        activeSkill.damageEntities.get(i).setDamage(activeSkill.damage);
                    }
                }
                activeSkill.damageEntities.get(0).setDead(false);
                activeSkill.damageEntities.get(0).setCX(this.getCX() + 1);
                activeSkill.damageEntities.get(0).setCY(this.getCY());
                activeSkill.damageEntities.get(1).setDead(false);
                activeSkill.damageEntities.get(1).setCX(this.getCX() + 1);
                activeSkill.damageEntities.get(1).setCY(this.getCY() + 1);
                activeSkill.damageEntities.get(2).setDead(false);
                activeSkill.damageEntities.get(2).setCX(this.getCX() + 1);
                activeSkill.damageEntities.get(2).setCY(this.getCY() - 1);
                activeSkill.damageEntities.get(3).setDead(false);
                activeSkill.damageEntities.get(3).setCX(this.getCX());
                activeSkill.damageEntities.get(3).setCY(this.getCY() + 1);
                activeSkill.damageEntities.get(4).setDead(false);
                activeSkill.damageEntities.get(4).setCX(this.getCX());
                activeSkill.damageEntities.get(4).setCY(this.getCY() - 1);
                activeSkill.damageEntities.get(5).setDead(false);
                activeSkill.damageEntities.get(5).setCX(this.getCX() - 1);
                activeSkill.damageEntities.get(5).setCY(this.getCY());
                activeSkill.damageEntities.get(6).setDead(false);
                activeSkill.damageEntities.get(6).setCX(this.getCX() - 1);
                activeSkill.damageEntities.get(6).setCY(this.getCY() + 1);
                activeSkill.damageEntities.get(7).setDead(false);
                activeSkill.damageEntities.get(7).setCX(this.getCX() - 1);
                activeSkill.damageEntities.get(7).setCY(this.getCY() - 1);
                break;
            case LASERSKILL:
                if (powerReduce == true && executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                    activeSkill.damageEntities.get(1).setDamage((activeSkill.damage + activeSkill.damage / 2) / 2);
                } else if (executeLightBarrier == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage + activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage + activeSkill.damage / 2);
                } else if (powerReduce == true) {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage / 2);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage / 2);
                } else {
                    activeSkill.damageEntities.get(0).setDamage(activeSkill.damage);
                    activeSkill.damageEntities.get(1).setDamage(activeSkill.damage);
                }
                activeSkill.damageEntities.get(0).setDead(false);
                activeSkill.damageEntities.get(0).setCX(this.getCX());
                activeSkill.damageEntities.get(0).setCY(this.getCY() + 1);
                activeSkill.damageEntities.get(1).setDead(false);
                activeSkill.damageEntities.get(1).setCX(this.getCX());
                activeSkill.damageEntities.get(1).setCY(this.getCY() + 2);
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
                if (death.isAnimationFinished(elapsedDeath)) {
                    Protagonist.this.remove();
                    Protagonist.this.setTurnFinished(true);
                    Protagonist.this.turnAction = Constants.TurnAction.NONE;
                    return true;
                }
                return false;
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
        if (powerReduce == true) {
            reduceCounter++;
        }
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
                if (Protagonist.this.getActions().size > 1) {
                    return false;
                }
                Protagonist.this.setTurnFinished(true);
                Protagonist.this.turnAction = Constants.TurnAction.NONE;
                Protagonist.this.sliding = false;
                return true;
            }
        };
    }

    public void resetStatusCounter() {
        powerReduce = false;
        poison = false;
        blind = false;
        blindCounter = 0;
        poisonCounter = 0;
        reduceCounter = 0;
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

    float getDamage() {
        return strengthMod;
    }

    public void setPowerReduce(boolean powerReduce) {
        this.powerReduce = powerReduce;
    }

    public boolean getPowerReduce() {
        return powerReduce;
    }

    private void levelUp() {
        this.level++;
        levelup = "You are now level " + level + "!";
        textlevel = "Choose to upgrade Health or Damage\n" + levelup;
        ((Label) levelUpDialog.getContentTable().getCells().get(0).getActor()).setText(textlevel);
        levelUpDialog.setModal(true);
        levelUpDialog.setX(this.getX() - levelUpDialog.getWidth() / 2 + this.getWidth() / 2);
        levelUpDialog.setY(this.getY());
        levelUpDialog.setVisible(true);

        checkForNewSkills();
        this.currentXp -= this.xpToNextLevel;
        this.xpToNextLevel *= 1.2f;

    }

    public void addExp(int exp) {
        this.currentXp += exp;
        while (this.currentXp >= this.xpToNextLevel) {
            levelUpCounter++;
            this.levelUp();
        }
    }

    int getLevel() {
        return this.level;
    }

    int getExpToLevel() {
        return xpToNextLevel;
    }

    public void addDamage() {
        strengthMod *= 1.1f;
    }

    void addInventory(Entity i) {
        inventory.add(i);
        index++;
    }

    static ArrayList<Entity> getInventory() {
        return inventory;
    }

    static void setActive(Entity i) {
        active = i;
        if (i == null) {
            GameScreen.invent.setImage(TextureLoader.INVENTORYTEXTURE);
        }
        if (i instanceof ItemShield) {
            GameScreen.invent.setImage(TextureLoader.INVENTORYSHIELDTEXTURE);
        }
        if (i instanceof ItemWhistle) {
            GameScreen.invent.setImage(TextureLoader.INVENTORYWHISTLETEXTURE);
        }
        if (i instanceof GreyKey) {
            GameScreen.invent.setImage(TextureLoader.INVENTORYGREYKEYTEXTURE);
        }
        if (i instanceof RedKey) {
            GameScreen.invent.setImage(TextureLoader.INVENTORYREDKEYTEXTURE);
        }
    }

    public void useItem() {
        if (active == null) {
        } else if (active instanceof ItemShield) {
            shieldActive = true;
            removeItem();
        } else if (active instanceof ItemWhistle) {
            barrierCooldown = 0;
            lightningInfusionCooldown = 0;
            redLaserCooldown = 0;
            removeItem();
        } else if (active instanceof GreyKey) {
            if (GreyGate.isCollision(this)) {
                Map.miscEntityList.add(new DamageEntity(this.getCX() + 1, this.getCY() + 1, 100000));
                Map.miscEntityList.add(new DamageEntity(this.getCX() - 1, this.getCY() + 1, 100000));
                Map.miscEntityList.add(new DamageEntity(this.getCX(), this.getCY() + 1, 100000));
                removeItem();
            }
        } else if (active instanceof RedKey) {
            if (RedGate.isCollision(this)) {
                Map.miscEntityList.add(new DamageEntity(this.getCX() + 1, this.getCY() + 1, 100000));
                Map.miscEntityList.add(new DamageEntity(this.getCX() - 1, this.getCY() + 1, 100000));
                Map.miscEntityList.add(new DamageEntity(this.getCX(), this.getCY() + 1, 100000));
                removeItem();
            }
        }
    }

    public void removeItem() {
        inventory.remove(active);
        if (inventory.isEmpty()) {
            setActive(null);
        } else {
            setActive(inventory.get(0));
        }
    }

    Entity getActive() {
        return active;
    }

    private void checkForNewSkills() {
        newSkillDialog.setModal(true);
        newSkillDialog.setX(this.getX() - newSkillDialog.getWidth() / 2 + this.getWidth() / 2);
        newSkillDialog.setY(this.getY());
        newSkillDialog.setVisible(true);
        switch (level) {
            case Constants.BARRIERREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Barrier skill! Press the '4' key to activate.");
                break;
            case Constants.DETECTIONREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Detection skill! Press the '3' key to activate.");
                break;
            case Constants.FREEZINGREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Freezing skill! Press the '6' key to activate.");
                break;
            case Constants.LASERREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Laser Fusion skill! Press the '7' key to activate.");
                break;
            case Constants.LIGHTBARRIERREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Lightning Infusion skill! Press the '5' key to activate.");
                break;
            case Constants.REDLASERREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Big Red Laser skill! Press the space bar to activate.");
                break;
            case Constants.SKILLTWOREQ:
                ((Label) newSkillDialog.getContentTable().getCells().get(0).getActor()).setText("Unlocked Rotating Laser skill! Press the '2' key to activate.");
                break;
            default:
                newSkillDialog.setVisible(false);
                break;
        }
    }

    public Dialog getLevelUpDialog() {
        return levelUpDialog;
    }

    public void setCurrentMap(Constants.MapGridCode[][] currentMap) {
        this.currentMap = currentMap;
    }

    public Dialog getNewSkillDialog() {
        return newSkillDialog;
    }

    @Override
    public void dispose() {
        this.smokeParticle.dispose();
        this.poisonParticle.dispose();
        for (Skill s : skills.values()) {
            s.dispose();
        }
    }
}
