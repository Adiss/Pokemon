package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

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
        if(my.getMoves().get("move1").getActualPP() == 0) ppCounter++;
        if(my.getMoves().get("move2").getActualPP() == 0) ppCounter++;
        if(my.getMoves().get("move3").getActualPP() == 0) ppCounter++;
        if(my.getMoves().get("move4").getActualPP() == 0) ppCounter++;
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

}
