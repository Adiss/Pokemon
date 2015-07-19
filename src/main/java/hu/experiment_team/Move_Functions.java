package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public enum Move_Functions {
    INSTANCE;

    protected static Logger logger = Logger.getLogger(Move_Functions.class.getName());
    private Random r = new Random();

    /**
     * A sebzés mértékének kiszámítása
     * */
    public int getDamage(Pokemon a, Pokemon d, Move m){

        double STAB = a.getType1().equals(m.getType()) || a.getType2().equals(m.getType()) ? 1.5 : 1.0;
        double typeEffectiveness = Effectiveness.INSTANCE.get(m.getType(), d.getType1())*10;
        Random r = new Random(); double rand = 0.85 + (1.0-0.85) * r.nextDouble();

        double userAttack;
        double oppDefense;
        if(m.getMoveCategory().equals("Physical")){
            userAttack = (2 * a.getLevel() + 10) * a.getBaseStats().get("attack") * m.getBaseDamage();
            oppDefense = 250 * (d.getBaseStats().get("defense"));
        } else {
            userAttack = (2 * a.getLevel() + 10) * a.getBaseStats().get("spattack") * m.getBaseDamage();
            oppDefense = 250 * (d.getBaseStats().get("spdefense"));
        }
        double modifiers = typeEffectiveness * STAB * rand;

        if(r.nextInt(100)+1 > a.getBaseStats().get("crit")){
            return (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);
        } else {
            return (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers) * a.getBaseStats().get("crit");
        }
    }

    /**
     * Function code: 000.
     * Nincs a spellnek semmilyen effektje, csak sebez.
     * */
    public void Move_Function_000(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy")))
            opp.setBaseStat("hp", opp.getBaseStats().get("hp")  - damage);
        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
    }

    /**
     * Function code: 001.
     * Nem csinál semmit, abszolút semmit (Splash).
     * */
    public void Move_Function_001(Pokemon my, Pokemon opp, Move m){
        logger.info("Nothing happened!");
    }

    /**
     * Function code: 002.
     * Stagger.
     * An attack that is used in desperation only if the user has no PP. This also damages the user a little.
     * */
    public void Move_Function_002(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        int ppCounter = 0;
        if(my.getMoves().get(1).getActualPP() == 0) ppCounter++;
        if(my.getMoves().get(2).getActualPP() == 0) ppCounter++;
        if(my.getMoves().get(3).getActualPP() == 0) ppCounter++;
        if(my.getMoves().get(4).getActualPP() == 0) ppCounter++;
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(ppCounter == 3){
                logger.info(my.getName() + " is struggling!");
                // Sebzi az ellenfelet a kiszámolt damage-el
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                // Magát is megsebzi egy kicsit
                my.setBaseStat("hp", my.getBaseStats().get("hp") - (int) (Math.floor(my.getMaxStats().get("hp") * 0.25)));
                logger.info(my.getName() + " is hit with recoil!");
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                logger.info("You can't do that yet!");
            }
        }
    }

    /**
     * Function code: 003.
     * Puts the target to sleep.
     * */
    public void Move_Function_003(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applySleep();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applySleep();
            }
            logger.info(opp.getName() + " went to sleep!");
        }
    }

    /**
     * Function code: 004.
     * Makes the target drowsy.  It will fall asleep at the end of the next turn.
     * */
    public void Move_Function_004(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még megoldva a többkörös spellezés.
    }

    /**
     * Function code: 005.
     * Poisons the target.
     * */
    public void Move_Function_005(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyPoison();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyPoison();
            }
            logger.info(opp.getName() + " is poisoned!");
        }
    }

    /**
     * Function code: 006.
     * Badly poisons the target.
     * */
    public void Move_Function_006(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBadlyPoison();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyBadlyPoison();
            }
            logger.info(opp.getName() + " is badly poisoned!");
        }
    }

    /**
     * Function code: 007.
     * Paralyzes the target.
     * */
    public void Move_Function_007(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyParalysis();
            }
            logger.info(opp.getName() + " is paralyzed!  It may be unable to move!");
        }
    }

    /**
     * Function code: 008.
     * Paralyzes the target.  Hits some semi-invulnerable targets.  (Thunder)
     * Always hits in rain.
     * (Handled in pbCheck): Accuracy 50% in sunshine.
     * */
    public void Move_Function_008(Pokemon my, Pokemon opp, Move m){

        // TODO -> Nincs még leprogramozva az időjárás.

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyParalysis();
            }
            logger.info(opp.getName() + " is paralyzed!  It may be unable to move!");
        }
    }

    /**
     * Function code: 009.
     * Paralyzes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_009(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyParalysis();
            }
            if( (r.nextInt(10) + 1) == 1){
                opp.applyFlinch();
            }
            logger.info(opp.getName() + " is paralyzed!  It may be unable to move!");
        }
    }

    /**
     * Function code: 00A.
     * Burns the target.
     * */
    public void Move_Function_00A(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBurn();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyBurn();
            }
            logger.info(opp.getName() + " was burned!");
        }
    }

    /**
     * Function code: 00B.
     * Burns the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00B(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBurn();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyBurn();
            }
            if( (r.nextInt(10) + 1) == 1){
                opp.applyFlinch();
            }
            logger.info(opp.getName() + " was burned!");
        }
    }

    /**
     * Function code: 00C.
     * Freezes the target.
     * */
    public void Move_Function_00C(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyFreeze();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 00D.
     * Freezes the target.  Always hits in hail.
     * */
    public void Move_Function_00D(Pokemon my, Pokemon opp, Move m){

        // TODO -> Nincs még leprogramozva az időjárás.

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyFreeze();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 00E.
     * Freezes the target.  May cause the target to flinch.
     * Az esély a flinch-re 1:10-hez
     * */
    public void Move_Function_00E(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyFreeze();
            }
            if( (r.nextInt(10) + 1) == 1){
                opp.applyFlinch();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 00F.
     * Causes the target to flinch.
     * */
    public void Move_Function_00F(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
        opp.applyFlinch();
        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
    }

    /**
     * Function code: 010.
     * Causes the target to flinch.  Does double damage if the target is Minimized.
     * */
    public void Move_Function_010(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if(opp.getFlags().get("minimized") == 1){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp")  - (getDamage(my, opp, m) * 2));
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        } else {
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
        opp.applyFlinch();
    }

    /**
     * Function code: 011.
     * Causes the target to flinch.  Fails if the user is not asleep.
     * */
    public void Move_Function_011(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if(opp.getStatusEffect() != 6){
            logger.info("It's failed!");
        } else {
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            opp.applyFlinch();
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 012.
     * Causes the target to flinch.  Fails if this isn't the user's first turn.
     * */
    public void Move_Function_012(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még implementálva a körönkénti spellezés.
        int damage = getDamage(my, opp, m);
        opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
        opp.applyFlinch();
        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
    }

    /**
     * Function code: 013.
     * Confuses the target.
     * */
    public void Move_Function_013(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyConfusion();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 014.
     * Confuses the target.  Chance of causing confusion depends on the cry's volume.
     * Works for Chatot only.  (Chatter)
     * */
    public void Move_Function_014(Pokemon my, Pokemon opp, Move m){

        // TODO -> fogalmam sincs milyen cry volume-ról beszél, de majd implementálni kéne.

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyConfusion();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 015.
     * Confuses the target.  Hits some semi-invulnerable targets.  (Hurricane)
     * Always hits in rain.
     * Accuracy 50% in sunshine.
     * */
    public void Move_Function_015(Pokemon my, Pokemon opp, Move m){

        // TODO -> Nincs időjárás..

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyConfusion();
            }
            logger.info(opp.getName() + " was frozen solid!");
        }
    }

    /**
     * Function code: 016.
     * Attracts the target.
     * */
    public void Move_Function_016(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyAttract();
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                opp.applyAttract();
            }
            logger.info(opp.getName() + " was attracted!");
        }
    }

    /**
     * Function code: 017.
     * Burns, freezes or paralyzes the target.
     * */
    public void Move_Function_017(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        switch((r.nextInt(3)+1)){
            case 1:
                if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                        if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyFreeze();
                        }
                        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
                    } else {
                        opp.applyFreeze();
                    }
                    logger.info(opp.getName() + " was frozen solid!");
                }
                break;
            case 2:
                if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                        if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyBurn();
                        }
                        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
                    } else {
                        opp.applyBurn();
                    }
                    logger.info(opp.getName() + " was burned!");
                }
                break;
            case 3:
                if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                        if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyParalysis();
                        }
                        logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                        logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
                    } else {
                        opp.applyParalysis();
                    }
                    logger.info(opp.getName() + " is paralyzed!  It may be unable to move!");
                }
                break;
        }
    }

    /**
     * Function code: 018.
     * Cures user of burn, poison and paralysis.
     * */
    public void Move_Function_018(Pokemon my, Pokemon opp, Move m){
        if(my.getStatusEffect() == 1 || my.getStatusEffect() == 3 || my.getStatusEffect() == 4) {
            my.healBurn();
            my.healPoison();
            my.healParalysis();
            logger.info(my.getName() + "'s status returned to normal!");
        } else {
            logger.info("It failed!");
        }
    }

    /**
     * Function code: 019.
     * Cures all party Pokémon of permanent status problems.
     * */
    public void Move_Function_019(Pokemon my, Pokemon opp, Move m){
        // TODO -> Na ehez kéne még a trainer is.
        if(my.getStatusEffect() == 1 || my.getStatusEffect() == 3 || my.getStatusEffect() == 4) {
            my.healBurn();
            my.healPoison();
            my.healParalysis();
            logger.info(my.getName() + "'s status returned to normal!");
        } else {
            logger.info("It failed!");
        }
    }

    /**
     * Function code: 01B.
     * User passes its status problem to the target.
     * */
    public void Move_Function_01B(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if(my.getStatusEffect() == 0){
            logger.info("It failed!");
        } else {
            switch(my.getStatusEffect()){
                case 1:
                    my.healBurn();
                    logger.info(my.getName() + " was cured of its burn.");
                    break;
                case 2:
                    my.healFreeze();
                    logger.info(my.getName() + " was defrosted.");
                    break;
                case 3:
                    my.healParalysis();
                    logger.info(my.getName() + " was cured of its paralysis.");
                    break;
                case 4:
                    my.healPoison();
                    logger.info(my.getName() + " was cured of its poisoning.");
                    break;
                case 5:
                    my.healBadlyPoison();
                    logger.info(my.getName() + " was cured of its poisoning.");
                    break;
                case 6:
                    my.healSleep();
                    logger.info(my.getName() + " was woken from its sleep.");
                    break;
            }
        }
    }

    /**
     * Function code: 01C.
     * Increases the user's Attack by 1 stage.
     * */
    public void Move_Function_01C(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("attack") <= 6) {
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("attack") <= 6) {
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
            }
        }

    }

    /**
     * Function code: 01D.
     * Increases the user's Defense by 1 stage.
     * */
    public void Move_Function_01D(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("defense") <= 6) {
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        logger.info(my.getName() + "'s Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("defense") <= 6) {
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    logger.info(my.getName() + "'s Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }

    }

    /**
     * Function code: 01E.
     * Increases the user's Defense by 1 stage.  User curls up.
     * */
    public void Move_Function_01E(Pokemon my, Pokemon opp, Move m){

        // Ez valami animációval kapcsolatos dolog lenne szerinte még.

        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("defense") <= 6) {
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        logger.info(my.getName() + "'s Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("defense") <= 6) {
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    logger.info(my.getName() + "'s Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 01F.
     * Increases the user's Speed by 1 stage.
     * */
    public void Move_Function_01F(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("speed") <= 6) {
                        my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                        logger.info(my.getName() + "'s Speed rose!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("speed") <= 6) {
                    my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                    logger.info(my.getName() + "'s Speed rose!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
            }
        }

    }

    /**
     * Function code: 020.
     * Increases the user's Special Attack by 1 stage.
     * */
    public void Move_Function_020(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("spattack") <= 6) {
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("spattack") <= 6) {
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
            }
        }

    }

    /**
     * Function code: 021.
     * Increases the user's Special Defense by 1 stage.  Charges up Electric attacks.
     * */
    public void Move_Function_021(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(my.getStatStages().get("spdefense") <= 6) {
                        logger.info(my.getName() + " began charging power!");
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(my.getStatStages().get("spdefense") <= 6) {
                    logger.info(my.getName() + " began charging power!");
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
            }
        }

    }

    /**
     * Function code: 022.
     * Increases the user's evasion by 1 stage.
     * */
    public void Move_Function_022(Pokemon my, Pokemon opp, Move m){
        if(my.getBaseStats().get("evasion") <= 6){
            my.setBaseStat("evasion", (my.getBaseStats().get("evasion") + 1));
            logger.info(my.getName() + "'s evasiveness rose!");
        } else {
            logger.info(my.getName() + "'s evasiveness won't go higher!");
        }
    }

    /**
     * Function code: 023.
     * Increases the user's critical hit rate.
     * */
    public void Move_Function_023(Pokemon my, Pokemon opp, Move m){
        my.setBaseStat("crit", (my.getBaseStats().get("crit") * 2));
    }

    /**
     * Function code: 024.
     * Increases the user's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_024(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                    if((my.getStatStages().get("defense") <= 6)){
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        logger.info(my.getName() + "'s Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    logger.info(my.getName() + "'s Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 025.
     * Increases the user's Attack, Defense and accuracy by 1 stage each.
     * */
    public void Move_Function_025(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("attack") <= 6) && (my.getStatStages().get("defense") <= 6)) {
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        my.addAccuracy();
                        logger.info(my.getName() + "'s Attack rose!");
                        logger.info(my.getName() + "'s Defense rose!");
                        logger.info(my.getName() + "'s Accuracy rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack or Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("attack") <= 6) && (my.getStatStages().get("defense") <= 6)) {
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    my.addAccuracy();
                    logger.info(my.getName() + "'s Attack rose!");
                    logger.info(my.getName() + "'s Defense rose!");
                    logger.info(my.getName() + "'s Accuracy rose!");
                } else {
                    logger.info(my.getName() + "'s Attack or Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 026.
     * Increases the user's Attack and Speed by 1 stage each.
     * */
    public void Move_Function_026(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                        logger.info(my.getName() + "'s Speed Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Speed Attack won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                    logger.info(my.getName() + "'s Speed Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Speed Attack won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 027.
     * Increases the user's Attack and Special Attack by 1 stage each.
     * */
    public void Move_Function_027(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 028.
     * Increases the user's Attack and Special Attack by 1 stage each (2 each in sunshine).
     * */
    public void Move_Function_028(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        // TODO -> Nincs még időjárás.
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 029.
     * Increases the user's Attack and accuracy by 1 stage each.
     * */
    public void Move_Function_029(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("attack") <= 6)) {
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                        my.addAccuracy();
                        logger.info(my.getName() + "'s Accuracy rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack or Accuracy won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("attack") <= 6)) {
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    my.addAccuracy();
                    logger.info(my.getName() + "'s Attack rose!");
                    logger.info(my.getName() + "'s Accuracy rose!");
                } else {
                    logger.info(my.getName() + "'s Attack or Accuracy won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02A.
     * Increases the user's Defense and Special Defense by 1 stage each.
     * */
    public void Move_Function_02A(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spdefense") <= 6)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                        logger.info(my.getName() + "'s Special Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Defense won't go higher!");
                    }
                    if((my.getStatStages().get("defense") <= 6)){
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        logger.info(my.getName() + "'s Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                    logger.info(my.getName() + "'s Special Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Special Defense won't go higher!");
                }
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    logger.info(my.getName() + "'s Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02B.
     * Increases the user's Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02B(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("spdefense") <= 6)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                        logger.info(my.getName() + "'s Special Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Defense won't go higher!");
                    }
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                        logger.info(my.getName() + "'s Speed rose!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                    logger.info(my.getName() + "'s Special Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Special Defense won't go higher!");
                }
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                    logger.info(my.getName() + "'s Speed rose!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02C.
     * Increases the user's Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02C(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("spdefense") <= 6)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                        logger.info(my.getName() + "'s Special Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                    logger.info(my.getName() + "'s Special Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Special Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02D.
     * Increases the user's Attack, Defense, Speed, Special Attack and Special Defense by 1 stage each.
     * */
    public void Move_Function_02D(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                        logger.info(my.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("spdefense") <= 6)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                        logger.info(my.getName() + "'s Special Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Special Defense won't go higher!");
                    }
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                        logger.info(my.getName() + "'s Speed rose!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s Attack rose!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                    if((my.getStatStages().get("defense") <= 6)){
                        my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                        logger.info(my.getName() + "'s Defense rose!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                    logger.info(my.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("spdefense") + 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.25));
                    logger.info(my.getName() + "'s Special Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Special Defense won't go higher!");
                }
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.25));
                    logger.info(my.getName() + "'s Speed rose!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s Attack rose!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.25));
                    logger.info(my.getName() + "'s Defense rose!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02E.
     * Increases the user's Attack by 2 stages.
     * */
    public void Move_Function_02E(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 2);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.5));
                        logger.info(my.getName() + "'s Attack rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 2);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.5));
                    logger.info(my.getName() + "'s Attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 02F.
     * Increases the user's Defense by 2 stages.
     * */
    public void Move_Function_02F(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("defense") <= 6)){
                        my.setStatStage("defense", my.getStatStages().get("defense") + 2);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.5));
                        logger.info(my.getName() + "'s Defense rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 2);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.5));
                    logger.info(my.getName() + "'s Defense rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 030.
     * Increases the user's Speed by 2 stages.
     * */
    public void Move_Function_030(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                        logger.info(my.getName() + "'s Speed rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                    logger.info(my.getName() + "'s Speed rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 031.
     * Increases the user's Speed by 2 stages.  Halves the user's weight.
     * */
    public void Move_Function_031(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        // TODO -> Halves the user's weight.

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                        logger.info(my.getName() + "'s Speed rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                    logger.info(my.getName() + "'s Speed rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 032.
     * Increases the user's Special Attack by 2 stages.
     * */
    public void Move_Function_032(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                        logger.info(my.getName() + "'s Special Attack rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                    logger.info(my.getName() + "'s Special Attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 033.
     * Increases the user's Special Defense by 2 stages.
     * */
    public void Move_Function_033(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spdefense") <= 6)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 2);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.5));
                        logger.info(my.getName() + "'s Special Defense rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Special Defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 2);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.5));
                    logger.info(my.getName() + "'s Special Defense rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Special Defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 034.
     * Increases the user's evasion by 2 stages.  Minimizes the user.
     * */
    public void Move_Function_034(Pokemon my, Pokemon opp, Move m){
        if(my.getBaseStats().get("evasion") <= 6){
            my.setBaseStat("evasion", (my.getBaseStats().get("evasion") + 2));
            logger.info(my.getName() + "'s evasiveness rose sharply!");
        } else {
            logger.info(my.getName() + "'s evasiveness won't go higher!");
        }
    }

    /**
     * Function code: 035.
     * Increases the user's Attack, Speed and Special Attack by 2 stages each.
     * Decreases the user's Defense and Special Defense by 1 stage each.
     * */
    public void Move_Function_035(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                        logger.info(my.getName() + "'s Special Attack rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Special Attack won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 2);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.5));
                        logger.info(my.getName() + "'s Attack rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Attack won't go higher!");
                    }
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                        logger.info(my.getName() + "'s speed rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s speed won't go higher!");
                    }
                    if((my.getStatStages().get("defense") >= 0)){
                        my.setStatStage("defense", my.getStatStages().get("defense") - 1);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 0.75));
                        logger.info(my.getName() + "'s Defense fell!");
                    } else {
                        logger.info(my.getName() + "'s defense won't go higher!");
                    }
                    if((my.getStatStages().get("spdefense") >= 0)){
                        my.setStatStage("spdefense", my.getStatStages().get("spdefense") - 1);
                        my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 0.75));
                        logger.info(my.getName() + "'s special defense fell!");
                    } else {
                        logger.info(my.getName() + "'s special defense won't go lower!");
                    }

                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                    logger.info(my.getName() + "'s Special Attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Special Attack won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 2);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.5));
                    logger.info(my.getName() + "'s Attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Attack won't go higher!");
                }
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                    logger.info(my.getName() + "'s speed rose sharply!");
                } else {
                    logger.info(my.getName() + "'s speed won't go higher!");
                }
                if((my.getStatStages().get("defense") >= 0)){
                    my.setStatStage("defense", my.getStatStages().get("defense") - 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 0.75));
                    logger.info(my.getName() + "'s Defense fell!");
                } else {
                    logger.info(my.getName() + "'s defense won't go higher!");
                }
                if((my.getStatStages().get("spdefense") >= 0)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") - 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 0.75));
                    logger.info(my.getName() + "'s special defense fell!");
                } else {
                    logger.info(my.getName() + "'s special defense won't go lower!");
                }
            }
        }
    }

    /**
     * Function code: 036.
     * Increases the user's Speed by 2 stages, and its Attack by 1 stage.
     * */
    public void Move_Function_036(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("speed") <= 6)){
                        my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                        my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                        logger.info(my.getName() + "'s Speed rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s Speed won't go higher!");
                    }
                    if((my.getStatStages().get("attack") <= 6)){
                        my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                        my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                        logger.info(my.getName() + "'s attack rose sharply!");
                    } else {
                        logger.info(my.getName() + "'s attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                    logger.info(my.getName() + "'s Speed rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.25));
                    logger.info(my.getName() + "'s attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 037.
     * Increases one random stat of the user by 2 stages (except HP).
     * */
    public void Move_Function_037(Pokemon my, Pokemon opp, Move m){
        switch(r.nextInt(7)+1){
            case 1:
                if((my.getStatStages().get("attack") <= 6)){
                    my.setStatStage("attack", my.getStatStages().get("attack") + 2);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 1.5));
                    logger.info(my.getName() + "'s attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s attack won't go higher!");
                }
                break;
            case 2:
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 2);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.5));
                    logger.info(my.getName() + "'s defense rose sharply!");
                } else {
                    logger.info(my.getName() + "'s defense won't go higher!");
                }
                break;
            case 3:
                if((my.getStatStages().get("speed") <= 6)){
                    my.setStatStage("speed", my.getStatStages().get("speed") + 2);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 1.5));
                    logger.info(my.getName() + "'s Speed rose sharply!");
                } else {
                    logger.info(my.getName() + "'s Speed won't go higher!");
                }
                break;
            case 4:
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                    logger.info(my.getName() + "'s special attack rose sharply!");
                } else {
                    logger.info(my.getName() + "'s special attack won't go higher!");
                }
                break;
            case 5:
                if((my.getStatStages().get("spdefense") <= 6)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") + 2);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 1.5));
                    logger.info(my.getName() + "'s special defense rose sharply!");
                } else {
                    logger.info(my.getName() + "'s special defense won't go higher!");
                }
                break;
            case 6:
                my.addAccuracy();
                logger.info(my.getName() + "'s accuracy rose sharply!");
                break;
            case 7:
                if((my.getStatStages().get("evasion") <= 6)){
                    my.setStatStage("evasion", my.getStatStages().get("evasion") + 2);
                    my.setBaseStat("evasion", (int) Math.ceil(my.getBaseStats().get("evasion") * 1.5));
                    logger.info(my.getName() + "'s evasiness rose sharply!");
                } else {
                    logger.info(my.getName() + "'s evasiness won't go higher!");
                }
                break;
        }
    }

    /**
     * Function code: 038.
     * Increases the user's Defense by 3 stages.
     * */
    public void Move_Function_038(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("defense") <= 6)){
                        my.setStatStage("defense", my.getStatStages().get("defense") + 3);
                        my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.75));
                        logger.info(my.getName() + "'s defense rose drastically!");
                    } else {
                        logger.info(my.getName() + "'s defense won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("defense") <= 6)){
                    my.setStatStage("defense", my.getStatStages().get("defense") + 3);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 1.75));
                    logger.info(my.getName() + "'s defense rose drastically!");
                } else {
                    logger.info(my.getName() + "'s defense won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 039.
     * Increases the user's Special Attack by 3 stages.
     * */
    public void Move_Function_039(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);

        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if((my.getStatStages().get("spattack") <= 6)){
                        my.setStatStage("spattack", my.getStatStages().get("spattack") + 3);
                        my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.75));
                        logger.info(my.getName() + "'s Special attack rose drastically!");
                    } else {
                        logger.info(my.getName() + "'s Special attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if((my.getStatStages().get("spattack") <= 6)){
                    my.setStatStage("spattack", my.getStatStages().get("spattack") + 3);
                    my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.75));
                    logger.info(my.getName() + "'s Special attack rose drastically!");
                } else {
                    logger.info(my.getName() + "'s Special attack won't go higher!");
                }
            }
        }
    }

    /**
     * Function code: 03A
     * Reduces the user's HP by half of max, and sets its Attack to maximum.
     * */
    public void Move_Function_03A(Pokemon my, Pokemon opp, Move m){
        switch(my.getStatStages().get("attack")){
            case 0:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 6);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 3.25));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 1:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 5);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 2.75));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 2:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 4);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 2.25));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 3:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 3);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.75));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 4:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 2);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.5));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 5:
                my.setBaseStat("hp", (my.getBaseStats().get("hp") - my.getMaxStats().get("hp")/2));
                my.setStatStage("spattack", my.getStatStages().get("spattack") + 1);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 1.25));
                logger.info(my.getName() + " cut its own HP and maximized its Attack!");
                break;
            case 6:
                logger.info("It failed!");
                break;
        }
    }

    /**
     * Function code: 03B.
     * Decreases the user's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_03B(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                if((my.getStatStages().get("attack") >= 0)){
                    my.setStatStage("attack", my.getStatStages().get("attack") - 1);
                    my.setBaseStat("attack", (int) Math.ceil(my.getBaseStats().get("attack") * 0.75));
                    logger.info(my.getName() + "'s Attack fell!!");
                } else {
                    logger.info(my.getName() + "'s attack won't go lower!");
                }
                if((my.getStatStages().get("defense") >= 0)){
                    my.setStatStage("defense", my.getStatStages().get("defense") - 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 0.75));
                    logger.info(my.getName() + "'s Defense fell!!");
                } else {
                    logger.info(my.getName() + "'s defense won't go lower!");
                }
            }
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 03C.
     * Decreases the user's Defense and Special Defense by 1 stage each.
     * */
    public void Move_Function_03C(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                if((my.getStatStages().get("spdefense") >= 0)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") - 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 0.75));
                    logger.info(my.getName() + "'s special defense fell!!");
                } else {
                    logger.info(my.getName() + "'s special defense won't go lower!");
                }
                if((my.getStatStages().get("defense") >= 0)){
                    my.setStatStage("defense", my.getStatStages().get("defense") - 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 0.75));
                    logger.info(my.getName() + "'s Defense fell!!");
                } else {
                    logger.info(my.getName() + "'s defense won't go lower!");
                }
            }
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 03D.
     * Decreases the user's Defense, Speed and Special Defense by 1 stage each.
     * User's ally loses 1/16 of its total HP.
     * */
    public void Move_Function_03D(Pokemon my, Pokemon opp, Move m){

        // TODO -> User's ally loses 1/16 of its total HP.

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                if((my.getStatStages().get("spdefense") >= 0)){
                    my.setStatStage("spdefense", my.getStatStages().get("spdefense") - 1);
                    my.setBaseStat("spdefense", (int) Math.ceil(my.getBaseStats().get("spdefense") * 0.75));
                    logger.info(my.getName() + "'s special defense fell!!");
                } else {
                    logger.info(my.getName() + "'s special defense won't go lower!");
                }
                if((my.getStatStages().get("defense") >= 0)){
                    my.setStatStage("defense", my.getStatStages().get("defense") - 1);
                    my.setBaseStat("defense", (int) Math.ceil(my.getBaseStats().get("defense") * 0.75));
                    logger.info(my.getName() + "'s Defense fell!!");
                } else {
                    logger.info(my.getName() + "'s defense won't go lower!");
                }
                if((my.getStatStages().get("speed") >= 0)){
                    my.setStatStage("speed", my.getStatStages().get("speed") - 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 0.75));
                    logger.info(my.getName() + "'s speed fell!!");
                } else {
                    logger.info(my.getName() + "'s speed won't go lower!");
                }
            }
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 03E.
     * Decreases the user's Speed by 1 stage.
     * */
    public void Move_Function_03E(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                if((my.getStatStages().get("speed") >= 0)){
                    my.setStatStage("speed", my.getStatStages().get("speed") - 1);
                    my.setBaseStat("speed", (int) Math.ceil(my.getBaseStats().get("speed") * 0.75));
                    logger.info(my.getName() + "'s speed fell!!");
                } else {
                    logger.info(my.getName() + "'s speed won't go lower!");
                }
            }
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 03F.
     * Decreases the user's Special Attack by 2 stages.
     * */
    public void Move_Function_03F(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                my.setStatStage("spattack", my.getStatStages().get("spattack") - 2);
                my.setBaseStat("spattack", (int) Math.ceil(my.getBaseStats().get("spattack") * 0.50));
                logger.info(my.getName() + "'s special attack fell!!");
            }
            logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 040.
     * Increases the target's Special Attack by 1 stage.  Confuses the target.
     * */
    public void Move_Function_040(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(opp.getStatStages().get("spattack") <= 6) {
                        opp.setStatStage("spattack", opp.getStatStages().get("spattack") + 1);
                        opp.setBaseStat("spattack", (int) Math.ceil(opp.getBaseStats().get("spattack") * 1.25));
                        logger.info(opp.getName() + "'s Special Attack rose!");
                    } else {
                        logger.info(opp.getName() + "'s Special Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(opp.getStatStages().get("spattack") <= 6) {
                    opp.setStatStage("spattack", opp.getStatStages().get("spattack") + 1);
                    opp.setBaseStat("spattack", (int) Math.ceil(opp.getBaseStats().get("spattack") * 1.25));
                    logger.info(opp.getName() + "'s Special Attack rose!");
                } else {
                    logger.info(opp.getName() + "'s Special Attack won't go higher!");
                }
            }
            opp.applyConfusion();
        }
    }

    /**
     * Function code: 041.
     * Increases the target's Attack by 2 stages.  Confuses the target.
     * */
    public void Move_Function_041(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            if(!m.getMoveCategory().equals("Status")){
                opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
                if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                    if(opp.getStatStages().get("spattack") <= 6) {
                        opp.setStatStage("attack", opp.getStatStages().get("attack") + 2);
                        opp.setBaseStat("attack", (int) Math.ceil(opp.getBaseStats().get("attack") * 1.5));
                        logger.info(opp.getName() + "'s Attack rose!");
                    } else {
                        logger.info(opp.getName() + "'s Attack won't go higher!");
                    }
                }
                logger.info(my.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
                logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            } else {
                if(opp.getStatStages().get("attack") <= 6) {
                    opp.setStatStage("attack", opp.getStatStages().get("attack") + 2);
                    opp.setBaseStat("attack", (int) Math.ceil(opp.getBaseStats().get("attack") * 1.5));
                    logger.info(opp.getName() + "'s Attack rose!");
                } else {
                    logger.info(opp.getName() + "'s Attack won't go higher!");
                }
            }
            opp.applyConfusion();
        }
    }

    /**
     * Function code: 042.
     * Decreases the target's Attack by 1 stage.
     * */
    public void Move_Function_042(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("attack", opp.getStatStages().get("attack") - 1);
                opp.setBaseStat("attack", (int) Math.ceil(opp.getBaseStats().get("attack") * 0.75));
                logger.info(opp.getName() + "'s attack fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 043.
     * Decreases the target's Defense by 1 stage.
     * */
    public void Move_Function_043(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("defense", opp.getStatStages().get("defense") - 1);
                opp.setBaseStat("defense", (int) Math.ceil(opp.getBaseStats().get("defense") * 0.75));
                logger.info(opp.getName() + "'s defense fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 044.
     * Decreases the target's Speed by 1 stage.
     * */
    public void Move_Function_044(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("speed", opp.getStatStages().get("speed") - 1);
                opp.setBaseStat("speed", (int) Math.ceil(opp.getBaseStats().get("speed") * 0.75));
                logger.info(opp.getName() + "'s speed fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 045.
     * Decreases the target's Special Attack by 1 stage.
     * */
    public void Move_Function_045(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("spattack", opp.getStatStages().get("spattack") - 1);
                opp.setBaseStat("spattack", (int) Math.ceil(opp.getBaseStats().get("spattack") * 0.75));
                logger.info(opp.getName() + "'s special attack fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 046.
     * Decreases the target's Special Defense by 1 stage.
     * */
    public void Move_Function_046(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("spdefense", opp.getStatStages().get("spdefense") - 1);
                opp.setBaseStat("spdefense", (int) Math.ceil(opp.getBaseStats().get("spdefense") * 0.75));
                logger.info(opp.getName() + "'s special defense fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 047.
     * Decreases the target's accuracy by 1 stage.
     * */
    public void Move_Function_047(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            opp.setBaseStat("accuracy", (int) Math.ceil(opp.getBaseStats().get("accuracy") - 10));
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 048.
     * Decreases the target's evasion by 1 stage.
     * */
    public void Move_Function_048(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            opp.setBaseStat("evasion", (int) Math.ceil(opp.getBaseStats().get("evasion") - 1));
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 049.
     * Decreases the target's evasion by 1 stage.  Ends all barriers and entry hazards for the target's side.
     * */
    public void Move_Function_049(Pokemon my, Pokemon opp, Move m){

        // TODO -> Decreases the target's evasion by 1 stage.  Ends all barriers and entry hazards for the target's side.

        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            opp.setBaseStat("evasion", (int) Math.ceil(opp.getBaseStats().get("evasion") - 1));
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04A.
     * Decreases the target's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_04A(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if((opp.getStatStages().get("defense") >= 0)){
                opp.setStatStage("defense", opp.getStatStages().get("defense") - 1);
                opp.setBaseStat("defense", (int) Math.ceil(opp.getBaseStats().get("defense") * 0.75));
                logger.info(opp.getName() + "'s defense fell!");
            } else {
                logger.info(opp.getName() + "'s defense won't go lower!");
            }
            if((opp.getStatStages().get("attack") >= 0)){
                opp.setStatStage("attack", opp.getStatStages().get("attack") - 1);
                opp.setBaseStat("attack", (int) Math.ceil(opp.getBaseStats().get("attack") * 0.75));
                logger.info(opp.getName() + "'s attack fell!");
            } else {
                logger.info(opp.getName() + "'s attack won't go lower!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04B.
     * Decreases the target's Attack by 1 stage.
     * */
    public void Move_Function_04B(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("attack", opp.getStatStages().get("attack") - 2);
                opp.setBaseStat("attack", (int) Math.ceil(opp.getBaseStats().get("attack") * 0.5));
                logger.info(opp.getName() + "'s attack fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04C.
     * Decreases the target's Defense by 1 stage.
     * */
    public void Move_Function_04C(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("defense", opp.getStatStages().get("defense") - 2);
                opp.setBaseStat("defense", (int) Math.ceil(opp.getBaseStats().get("defense") * 0.5));
                logger.info(opp.getName() + "'s defense fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04D.
     * Decreases the target's Speed by 1 stage.
     * */
    public void Move_Function_04D(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("speed", opp.getStatStages().get("speed") - 2);
                opp.setBaseStat("speed", (int) Math.ceil(opp.getBaseStats().get("speed") * 0.5));
                logger.info(opp.getName() + "'s speed fell!!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04E.
     * Decreases the target's Special Attack by 2 stages.  Only works on the opposite gender.
     * */
    public void Move_Function_04E(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még gender.
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("spattack", opp.getStatStages().get("spattack") - 2);
                opp.setBaseStat("spattack", (int) Math.ceil(opp.getBaseStats().get("spattack") * 0.5));
                logger.info(opp.getName() + "'s special attack fell!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 04F.
     * Decreases the target's Special Defense by 2 stages.
     * */
    public void Move_Function_04F(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);
            if( (r.nextInt(100) + 1) <= m.getAdditionalEffectChance()){
                opp.setStatStage("spdefense", opp.getStatStages().get("spdefense") - 2);
                opp.setBaseStat("spdefense", (int) Math.ceil(opp.getBaseStats().get("spdefense") * 0.5));
                logger.info(opp.getName() + "'s special defense fell!");
            }
            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
        }
    }

    /**
     * Function code: 050.
     * Resets all target's stat stages to 0.
     * */
    public void Move_Function_050(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);

            opp.getBaseStats().put("hp", opp.getMaxStats().get("hp"));
            opp.getBaseStats().put("attack", opp.getMaxStats().get("attack"));
            opp.getBaseStats().put("defense", opp.getMaxStats().get("defense"));
            opp.getBaseStats().put("speed", opp.getMaxStats().get("speed"));
            opp.getBaseStats().put("spattack", opp.getMaxStats().get("spattack"));
            opp.getBaseStats().put("spdefense", opp.getMaxStats().get("spdefense"));
            opp.getBaseStats().put("accuracy", 0);
            opp.getBaseStats().put("evasion", 0);

            opp.getStatStages().put("hp", 0);
            opp.getStatStages().put("attack", 0);
            opp.getStatStages().put("defense", 0);
            opp.getStatStages().put("spattack", 0);
            opp.getStatStages().put("spdefense", 0);
            opp.getStatStages().put("speed", 0);
            opp.getStatStages().put("accuracy", 0);
            opp.getStatStages().put("evasion", 0);

            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            logger.info(opp.getName() + "'s stat changes were removed!");
        }
    }

    /**
     * Function code: 051.
     * Resets all stat stages for all battlers to 0.
     * */
    public void Move_Function_051(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs több pokémonos fight.
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);

            opp.getBaseStats().put("hp", opp.getMaxStats().get("hp"));
            opp.getBaseStats().put("attack", opp.getMaxStats().get("attack"));
            opp.getBaseStats().put("defense", opp.getMaxStats().get("defense"));
            opp.getBaseStats().put("speed", opp.getMaxStats().get("speed"));
            opp.getBaseStats().put("spattack", opp.getMaxStats().get("spattack"));
            opp.getBaseStats().put("spdefense", opp.getMaxStats().get("spdefense"));
            opp.getBaseStats().put("accuracy", 0);
            opp.getBaseStats().put("evasion", 0);

            opp.getStatStages().put("hp", 0);
            opp.getStatStages().put("attack", 0);
            opp.getStatStages().put("defense", 0);
            opp.getStatStages().put("spattack", 0);
            opp.getStatStages().put("spdefense", 0);
            opp.getStatStages().put("speed", 0);
            opp.getStatStages().put("accuracy", 0);
            opp.getStatStages().put("evasion", 0);

            logger.info(opp.getName() + " has dealt " + damage + " damage to " + opp.getName() + " with " + m.getDisplayName());
            logger.info(opp.getName() + " now has " + opp.getBaseStats().get("hp") + " health");
            logger.info(opp.getName() + "'s stat changes were removed!");
        }
    }

    /**
     * Function code: 052.
     * User and target swap their Attack and Special Attack stat stages.
     * */
    public void Move_Function_052(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);

            int opAttStage = opp.getStatStages().get("attack");
            int opSpAttStage = opp.getStatStages().get("spattack");
            int myAttStage = my.getStatStages().get("attack");
            int mySpAttStage = my.getStatStages().get("spattack");

            opp.setBaseStat("attack", opp.getMaxStats().get("attack"));
            opp.setBaseStat("spattack", opp.getMaxStats().get("spattack"));
            my.setBaseStat("attack", my.getMaxStats().get("attack"));
            my.setBaseStat("spattack", my.getMaxStats().get("spattack"));

            if(myAttStage > 0)
                opp.setBaseStat("attack", opp.getBaseStats().get("attack")*((int)Math.ceil(myAttStage*1.25)));
            if(mySpAttStage > 0)
                opp.setBaseStat("spattack", opp.getBaseStats().get("spattack")*((int)Math.ceil(mySpAttStage*1.25)));
            if(opAttStage > 0)
                my.setBaseStat("attack", my.getBaseStats().get("attack") * ((int) Math.ceil(opAttStage * 1.25)));
            if(opSpAttStage > 0)
                my.setBaseStat("spattack", my.getBaseStats().get("spattack") * ((int) Math.ceil(opSpAttStage * 1.25)));

            my.setStatStage("attack", opAttStage);
            my.setStatStage("spattack", opSpAttStage);
            opp.setStatStage("attack", myAttStage);
            opp.setStatStage("spattack", mySpAttStage);

            logger.info("User and target swap their Attack and Special Attack stat stages.");
        }
    }

    /**
     * Function code: 053.
     * User and target swap their Defense and Special Defense stat stages.
     * */
    public void Move_Function_053(Pokemon my, Pokemon opp, Move m){
        int damage = getDamage(my, opp, m);
        if((r.nextInt(100) + 1) <= (m.getAccuracy() + my.getBaseStats().get("accuracy"))){
            opp.setBaseStat("hp", opp.getBaseStats().get("hp") - damage);

            int opDefStage = opp.getStatStages().get("defense");
            int opSpDefStage = opp.getStatStages().get("spdefense");
            int myDefStage = my.getStatStages().get("defense");
            int mySpDefStage = my.getStatStages().get("spdefense");

            opp.setBaseStat("defense", opp.getMaxStats().get("defense"));
            opp.setBaseStat("spdefense", opp.getMaxStats().get("spdefense"));
            my.setBaseStat("defense", my.getMaxStats().get("defense"));
            my.setBaseStat("spdefense", my.getMaxStats().get("spdefense"));

            if(myDefStage > 0)
                opp.setBaseStat("defense", opp.getBaseStats().get("defense")*((int)Math.ceil(myDefStage*1.25)));
            if(mySpDefStage > 0)
                opp.setBaseStat("spdefense", opp.getBaseStats().get("spdefense")*((int)Math.ceil(mySpDefStage*1.25)));
            if(opDefStage > 0)
                my.setBaseStat("defense", my.getBaseStats().get("defense") * ((int) Math.ceil(opDefStage * 1.25)));
            if(opSpDefStage > 0)
                my.setBaseStat("spdefense", my.getBaseStats().get("spdefense") * ((int) Math.ceil(opSpDefStage * 1.25)));

            my.setStatStage("defense", opDefStage);
            my.setStatStage("spdefense", opSpDefStage);
            opp.setStatStage("defense", myDefStage);
            opp.setStatStage("spdefense", mySpDefStage);

            logger.info("User and target swap their defense and special defense stat stages.");
        }
    }

    /**
     * Function code: 054.
     * User and target swap all their stat stages.
     * */
    public void Move_Function_054(Pokemon my, Pokemon opp, Move m){
        Map<String, Integer> oppStages = opp.getStatStages();
        Map<String, Integer> myStages = my.getStatStages();

        opp.setBaseStat("attack", opp.getMaxStats().get("attack"));
        opp.setBaseStat("spattack", opp.getMaxStats().get("spattack"));
        opp.setBaseStat("defense", opp.getMaxStats().get("defense"));
        opp.setBaseStat("spdefense", opp.getMaxStats().get("spdefense"));
        opp.setBaseStat("speed", opp.getMaxStats().get("speed"));
        opp.setBaseStat("accuracy", opp.getMaxStats().get("accuracy"));
        opp.setBaseStat("evasion", opp.getMaxStats().get("evasion"));

        my.setBaseStat("attack", my.getMaxStats().get("attack"));
        my.setBaseStat("spattack", my.getMaxStats().get("spattack"));
        my.setBaseStat("defense", my.getMaxStats().get("defense"));
        my.setBaseStat("spdefense", my.getMaxStats().get("spdefense"));
        my.setBaseStat("speed", my.getMaxStats().get("speed"));
        my.setBaseStat("accuracy", my.getMaxStats().get("accuracy"));
        my.setBaseStat("evasion", my.getMaxStats().get("evasion"));

        if(oppStages.get("attack") > 0)
            my.setBaseStat("attack", my.getBaseStats().get("attack")*((int)Math.ceil(oppStages.get("attack")*1.25)));
        if(oppStages.get("defense") > 0)
            my.setBaseStat("defense", my.getBaseStats().get("defense")*((int)Math.ceil(oppStages.get("defense")*1.25)));
        if(oppStages.get("spattack") > 0)
            my.setBaseStat("spattack", my.getBaseStats().get("spattack") * ((int) Math.ceil(oppStages.get("spattack") * 1.25)));
        if(oppStages.get("spdefense") > 0)
            my.setBaseStat("spdefense", my.getBaseStats().get("spdefense") * ((int) Math.ceil(oppStages.get("spdefense") * 1.25)));
        if(oppStages.get("speed") > 0)
            my.setBaseStat("speed", my.getBaseStats().get("speed") * ((int) Math.ceil(oppStages.get("speed") * 1.25)));
        if(oppStages.get("accuracy") > 0)
            my.setBaseStat("accuracy", my.getBaseStats().get("accuracy") * ((int) Math.ceil(oppStages.get("accuracy") * 1.25)));
        if(oppStages.get("evasion") > 0)
            my.setBaseStat("evasion", my.getBaseStats().get("evasion") * ((int) Math.ceil(oppStages.get("evasion") * 1.25)));

        if(myStages.get("attack") > 0)
            opp.setBaseStat("attack", opp.getBaseStats().get("attack")*((int)Math.ceil(myStages.get("attack")*1.25)));
        if(myStages.get("defense") > 0)
            opp.setBaseStat("defense", opp.getBaseStats().get("defense")*((int)Math.ceil(myStages.get("defense")*1.25)));
        if(myStages.get("spattack") > 0)
            opp.setBaseStat("spattack", opp.getBaseStats().get("spattack") * ((int) Math.ceil(myStages.get("spattack") * 1.25)));
        if(myStages.get("spdefense") > 0)
            opp.setBaseStat("spdefense", opp.getBaseStats().get("spdefense") * ((int) Math.ceil(myStages.get("spdefense") * 1.25)));
        if(myStages.get("speed") > 0)
            opp.setBaseStat("speed", opp.getBaseStats().get("speed") * ((int) Math.ceil(myStages.get("speed") * 1.25)));
        if(myStages.get("accuracy") > 0)
            opp.setBaseStat("accuracy", opp.getBaseStats().get("accuracy") * ((int) Math.ceil(myStages.get("accuracy") * 1.25)));
        if(myStages.get("evasion") > 0)
            opp.setBaseStat("evasion", opp.getBaseStats().get("evasion") * ((int) Math.ceil(myStages.get("evasion") * 1.25)));

        opp.setStatStage("attack", my.getStatStages().get("attack"));
        opp.setStatStage("spattack", my.getStatStages().get("spattack"));
        opp.setStatStage("defense", my.getStatStages().get("defense"));
        opp.setStatStage("spdefense", my.getStatStages().get("spdefense"));
        opp.setStatStage("speed", my.getStatStages().get("speed"));
        opp.setStatStage("accuracy", my.getStatStages().get("accuracy"));
        opp.setStatStage("evasion", my.getStatStages().get("evasion"));

        my.setStatStage("attack", opp.getStatStages().get("attack"));
        my.setStatStage("spattack", opp.getStatStages().get("spattack"));
        my.setStatStage("defense", opp.getStatStages().get("defense"));
        my.setStatStage("spdefense", opp.getStatStages().get("spdefense"));
        my.setStatStage("speed", opp.getStatStages().get("speed"));
        my.setStatStage("accuracy", opp.getStatStages().get("accuracy"));
        my.setStatStage("evasion", opp.getStatStages().get("evasion"));

        logger.info(my.getName() + " switched its stat boosts with " + opp.getName());
    }

    /**
     * Function code: 055.
     * User copies the target's stat stages.
     * */
    public void Move_Function_055(Pokemon my, Pokemon opp, Move m){
        Map<String, Integer> oppStages = opp.getStatStages();

        my.setBaseStat("attack", my.getMaxStats().get("attack"));
        my.setBaseStat("spattack", my.getMaxStats().get("spattack"));
        my.setBaseStat("defense", my.getMaxStats().get("defense"));
        my.setBaseStat("spdefense", my.getMaxStats().get("spdefense"));
        my.setBaseStat("speed", my.getMaxStats().get("speed"));
        my.setBaseStat("accuracy", my.getMaxStats().get("accuracy"));
        my.setBaseStat("evasion", my.getMaxStats().get("evasion"));

        if(oppStages.get("attack") > 0)
            my.setBaseStat("attack", my.getBaseStats().get("attack")*((int)Math.ceil(oppStages.get("attack")*1.25)));
        if(oppStages.get("defense") > 0)
            my.setBaseStat("defense", my.getBaseStats().get("defense")*((int)Math.ceil(oppStages.get("defense")*1.25)));
        if(oppStages.get("spattack") > 0)
            my.setBaseStat("spattack", my.getBaseStats().get("spattack") * ((int) Math.ceil(oppStages.get("spattack") * 1.25)));
        if(oppStages.get("spdefense") > 0)
            my.setBaseStat("spdefense", my.getBaseStats().get("spdefense") * ((int) Math.ceil(oppStages.get("spdefense") * 1.25)));
        if(oppStages.get("speed") > 0)
            my.setBaseStat("speed", my.getBaseStats().get("speed") * ((int) Math.ceil(oppStages.get("speed") * 1.25)));
        if(oppStages.get("accuracy") > 0)
            my.setBaseStat("accuracy", my.getBaseStats().get("accuracy") * ((int) Math.ceil(oppStages.get("accuracy") * 1.25)));
        if(oppStages.get("evasion") > 0)
            my.setBaseStat("evasion", my.getBaseStats().get("evasion") * ((int) Math.ceil(oppStages.get("evasion") * 1.25)));

        my.setStatStage("attack", opp.getStatStages().get("attack"));
        my.setStatStage("spattack", opp.getStatStages().get("spattack"));
        my.setStatStage("defense", opp.getStatStages().get("defense"));
        my.setStatStage("spdefense", opp.getStatStages().get("spdefense"));
        my.setStatStage("speed", opp.getStatStages().get("speed"));
        my.setStatStage("accuracy", opp.getStatStages().get("accuracy"));
        my.setStatStage("evasion", opp.getStatStages().get("evasion"));

        logger.info(my.getName() + " copied " + opp.getName() + "'s stat changes!");
    }

    /**
     * Function code: 056.
     * For 5 rounds, user's and ally's stat stages cannot be lowered by foes.
     * */
    public void Move_Function_056(Pokemon my, Pokemon opp, Move m){
        // TODO -> Majd megcsináljuk lel.
    }

    /**
     * Function code: 057.
     * Swaps the user's Attack and Defense.
     * */
    public void Move_Function_057(Pokemon my, Pokemon opp, Move m){
        int myAtt = my.getBaseStats().get("attack");
        int myDef = my.getBaseStats().get("defense");

        my.setBaseStat("attack", myDef);
        my.setBaseStat("defense", myAtt);

        logger.info(my.getName() + " switched its Attack and Defense!");
    }

    /**
     * Function code: 058.
     * Averages the user's and target's Attack and Special Attack (separately).
     * */
    public void Move_Function_058(Pokemon my, Pokemon opp, Move m){
        int att = (my.getBaseStats().get("attack") + opp.getBaseStats().get("attack")) / 2;
        int spAttack = (my.getBaseStats().get("spattack") + opp.getBaseStats().get("spattack")) / 2;

        my.setBaseStat("attack", att);
        my.setBaseStat("spattack", spAttack);
        opp.setBaseStat("attack", att);
        opp.setBaseStat("spattack", spAttack);

        logger.info(my.getName() + " shared its power with the target!");
    }

    /**
     * Function code: 059.
     * Averages the user's and target's Defense and Special Defense (separately).
     * */
    public void Move_Function_059(Pokemon my, Pokemon opp, Move m){
        int def = (my.getBaseStats().get("defense") + opp.getBaseStats().get("defense")) / 2;
        int spdefense = (my.getBaseStats().get("spdefense") + opp.getBaseStats().get("spdefense")) / 2;

        my.setBaseStat("defense", def);
        my.setBaseStat("spdefense", spdefense);
        opp.setBaseStat("defense", def);
        opp.setBaseStat("spdefense", spdefense);

        logger.info(my.getName() + " shared its guard with the target!");
    }

    /**
     * Function code: 05A.
     * Averages the user's and target's current HP.
     * */
    public void Move_Function_05A(Pokemon my, Pokemon opp, Move m){
        int hp = (my.getBaseStats().get("hp") + opp.getBaseStats().get("hp")) / 2;

        my.setBaseStat("hp", hp);
        opp.setBaseStat("hp", hp);

        logger.info("The battlers shared their pain!");
    }

    /**
     * Function code: 05B.
     * For 5 rounds, doubles the user's and ally's Speed.
     * */
    public void Move_Function_05B(Pokemon my, Pokemon opp, Move m){
        // TODO -> Kell még az 5körös cucc meg az allyk.
        my.setBaseStat("speed", (my.getBaseStats().get("speed") * 2));

        logger.info("The tailwind blew from behind your team!");
    }

    /**
     * Function code: 05C.
     * This move turns into the last move used by the target, until user switches out.
     * */
    public void Move_Function_05C(Pokemon my, Pokemon opp, Move m){

        // TODO -> until user switches out.

        if(opp.getLastMove() == null ||
           opp.getLastMove().getInternalName().equals("CHATTER") ||
           opp.getLastMove().getInternalName().equals("MIMIC") ||
           opp.getLastMove().getInternalName().equals("SKETCH") ||
           opp.getLastMove().getInternalName().equals("STRUGGLE") ||
           opp.getLastMove().getInternalName().equals("METRONOME")){
           logger.info("It failed!");
        } else {
            int index = Utility.INSTANCE.getIndex(my.getMoves(), m.getInternalName());
            if(index != -1)
                my.getMoves().set(index, opp.getLastMove());
            else
                logger.info("It failed!");
        }

        logger.info(my.getName() + " learned " + m.getDisplayName() + "!");
    }

    /**
     * Function code: 05D.
     * This move turns into the last move used by the target, until user switches out.
     * */
    public void Move_Function_05D(Pokemon my, Pokemon opp, Move m){

        // TODO -> until user switches out.

        if(opp.getLastMove() == null ||
                opp.getLastMove().getInternalName().equals("CHATTER") ||
                opp.getLastMove().getInternalName().equals("MIMIC") ||
                opp.getLastMove().getInternalName().equals("SKETCH") ||
                opp.getLastMove().getInternalName().equals("STRUGGLE") ||
                opp.getLastMove().getInternalName().equals("METRONOME")){
            logger.info("It failed!");
        } else {
            int index = Utility.INSTANCE.getIndex(my.getMoves(), m.getInternalName());
            if(index != -1)
                my.getMoves().set(index, opp.getLastMove());
            else
                logger.info("It failed!");
        }

        logger.info(my.getName() + " sketched " + m.getDisplayName() + "!");
    }

    /**
     * Function code: 05E.
     * Conversion changes the user's current type to match the type of the first of the user's moves.
     * */
    public void Move_Function_05E(Pokemon my, Pokemon opp, Move m){
        if(my.getMoves().get(0) != null){
            my.setType1(my.getMoves().get(0).getType());
        } else {
            logger.info("It failed!");
        }
        logger.info(my.getName() + " transformed into " + my.getMoves().get(0).getType() + " type!");
    }

    /**
     * Function code: 05F.
     * Changes user's type to a random one that resists the last attack against user.
     * */
    public void Move_Function_05F(Pokemon my, Pokemon opp, Move m){

        // TODO -> Ez nem biztos, hogy jó.

        my.setType1(opp.getLastMove().getType());
        logger.info(my.getName() + " transformed into " + opp.getLastMove().getType() + " type!");
    }

    /**
     * Function code: 060.
     * Changes user's type depending on the environment.
     * */
    public void Move_Function_060(Pokemon my, Pokemon opp, Move m){

        // TODO -> Nincs még environment.

    }

    /**
     * Function code: 061.
     * Target becomes Water type.
     * */
    public void Move_Function_061(Pokemon my, Pokemon opp, Move m){
        opp.setType1("WATER");
        logger.info(my.getName() + " transformed into Water type!");
    }

    /**
     * Function code: 062.
     * User copes target's types.
     * */
    public void Move_Function_062(Pokemon my, Pokemon opp, Move m){
        my.setType1(opp.getType1());
        logger.info(my.getName() + "'s type changed to match the target!");
    }

    /**
     * Function code: 063.
     * @see <a href="http://bulbapedia.bulbagarden.net/wiki/Simple_(Ability)">Bubapedia</a>
     * */
    public void Move_Function_063(Pokemon my, Pokemon opp, Move m){
        // TODO -> nincs még ability scriptelve.
    }

    /**
     * Function code: 064.
     * Target's ability becomes Insomnia.
     * */
    public void Move_Function_064(Pokemon my, Pokemon opp, Move m){
        // TODO -> nincs még ability scriptelve.
    }

    /**
     * Function code: 065.
     * User copes target's ability.
     * */
    public void Move_Function_065(Pokemon my, Pokemon opp, Move m){
        my.setHiddenAbility(opp.getHiddenAbility());
        logger.info(my.getName() + " copied " + opp.getName() + "'s " + opp.getHiddenAbility() + "!");
    }

    /**
     * Function code: 066.
     * Target copies user's ability.
     * */
    public void Move_Function_066(Pokemon my, Pokemon opp, Move m){
        opp.setHiddenAbility(my.getHiddenAbility());
        logger.info(opp.getName() + " copied " + my.getName() + "'s " + my.getHiddenAbility() + "!");
    }

    /**
     * Function code: 067.
     * User and target swap abilities.
     * */
    public void Move_Function_067(Pokemon my, Pokemon opp, Move m){
        String myAbbility = my.getHiddenAbility();
        my.setHiddenAbility(opp.getHiddenAbility());
        opp.setHiddenAbility(myAbbility);
        logger.info(opp.getName() + " swapped its " + opp.getHiddenAbility() + " ability with its target's " + my.getHiddenAbility() + " ability!");
    }

}
