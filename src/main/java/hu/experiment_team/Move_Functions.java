package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.Random;
import java.util.logging.Logger;

public enum Move_Functions {
    INSTANCE;

    private final static Logger logger = Logger.getLogger(Move_Functions.class.getName());
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
            userAttack = (2 * a.getLevel() + 10) * a.getAttack() * m.getBaseDamage();
            oppDefense = 250 * (d.getDefense());
        } else {
            userAttack = (2 * a.getLevel() + 10) * a.getSpAttack() * m.getBaseDamage();
            oppDefense = 250 * (d.getSpDefense());
        }
        double modifiers = typeEffectiveness * STAB * rand;

        return (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);
    }

    /**
     * Function code: 000.
     * Nincs a spellnek semmilyen effektje, csak sebez.
     * */
    public void Move_Function_000(Pokemon my, Pokemon opp, Move m){
        if((r.nextInt(99) + 1) > m.getAccuracy())
            opp.setHp(opp.getHp() - getDamage(my, opp, m));
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
        int ppCounter = 0;
        if(my.getMove1().getActualPP() == 0) ppCounter++;
        if(my.getMove2().getActualPP() == 0) ppCounter++;
        if(my.getMove3().getActualPP() == 0) ppCounter++;
        if(my.getMove4().getActualPP() == 0) ppCounter++;
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(ppCounter == 3){
                logger.info(my.getName() + " is struggling!");
                // Sebzi az ellenfelet a kiszámolt damage-el
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                // Magát is megsebzi egy kicsit
                my.setHp(my.getHp()-(int)(Math.floor(my.getMaxHp()*0.25)));
                logger.info(my.getName() + " is hit with recoil!");
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applySleep();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyPoison();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBadlyPoison();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
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

        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyParalysis();
                }
            } else {
                opp.applyParalysis();
            }
            if( (r.nextInt(9) + 1) == 1){
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBurn();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyBurn();
                }
            } else {
                opp.applyBurn();
            }
            if( (r.nextInt(9) + 1) == 1){
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
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

        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyFreeze();
                }
            } else {
                opp.applyFreeze();
            }
            if( (r.nextInt(9) + 1) == 1){
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
        opp.setHp(opp.getHp() - getDamage(my, opp, m));
        opp.applyFlinch();
    }

    /**
     * Function code: 010.
     * Causes the target to flinch.  Does double damage if the target is Minimized.
     * */
    public void Move_Function_010(Pokemon my, Pokemon opp, Move m){
        if(opp.isMinimized() == 1){
            opp.setHp(opp.getHp() - (getDamage(my, opp, m) * 2));
        } else {
            opp.setHp(opp.getHp() - getDamage(my, opp, m));
        }
        opp.applyFlinch();
    }

    /**
     * Function code: 011.
     * Causes the target to flinch.  Fails if the user is not asleep.
     * */
    public void Move_Function_011(Pokemon my, Pokemon opp, Move m){
        if(opp.getStatusEffect() != 6){
            logger.info("It's failed!");
        } else {
            opp.setHp(opp.getHp() - getDamage(my, opp, m));
            opp.applyFlinch();
        }
    }

    /**
     * Function code: 012.
     * Causes the target to flinch.  Fails if this isn't the user's first turn.
     * */
    public void Move_Function_012(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még implementálva a körönkénti spellezés.
        opp.applyFlinch();
    }

    /**
     * Function code: 013.
     * Confuses the target.
     * */
    public void Move_Function_013(Pokemon my, Pokemon opp, Move m){
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
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

        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
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

        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyConfusion();
                }
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
        if((r.nextInt(99) + 1) > m.getAccuracy()){
            if(!m.getMoveCategory().equals("Status")){
                opp.setHp(opp.getHp() - getDamage(my, opp, m));
                if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                    opp.applyAttract();
                }
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
        switch(r.nextInt(2)+1){
            case 1:
                if((r.nextInt(99) + 1) > m.getAccuracy()){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setHp(opp.getHp() - getDamage(my, opp, m));
                        if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyFreeze();
                        }
                    } else {
                        opp.applyFreeze();
                    }
                    logger.info(opp.getName() + " was frozen solid!");
                }
                break;
            case 2:
                if((r.nextInt(99) + 1) > m.getAccuracy()){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setHp(opp.getHp() - getDamage(my, opp, m));
                        if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyBurn();
                        }
                    } else {
                        opp.applyBurn();
                    }
                    logger.info(opp.getName() + " was burned!");
                }
                break;
            case 3:
                if((r.nextInt(99) + 1) > m.getAccuracy()){
                    if(!m.getMoveCategory().equals("Status")){
                        opp.setHp(opp.getHp() - getDamage(my, opp, m));
                        if( (r.nextInt(99) + 1) <= m.getAdditionalEffectChance()){
                            opp.applyParalysis();
                        }
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
        if(my.getAttackStage() <= 6) {
            my.setAttackStage(my.getAttackStage() + 1);
            my.setAttack((int) Math.ceil(my.getAttack() * 1.25));
            logger.info(my.getName() + "'s Attack rose!");
        } else {
            logger.info(my.getName() + "'s Attack won't go higher!");
        }
    }

    /**
     * Function code: 01D.
     * Increases the user's Defense by 1 stage.
     * */
    public void Move_Function_01D(Pokemon my, Pokemon opp, Move m){
        if(my.getDefenseStage() <= 6) {
            my.setDefenseStage(my.getDefenseStage() + 1);
            my.setDefense((int) Math.ceil(my.getDefense() * 1.25));
            logger.info(my.getName() + "'s Defense rose!");
        } else {
            logger.info(my.getName() + "'s Defense won't go higher!");
        }
    }

    /**
     * Function code: 01E.
     * Increases the user's Defense by 1 stage.  User curls up.
     * */
    public void Move_Function_01E(Pokemon my, Pokemon opp, Move m){

        // Ez valami animációval kapcsolatos dolog lenne szerinte még.

        if(my.getDefenseStage() <= 6) {
            my.setDefenseStage(my.getDefenseStage() + 1);
            my.setDefense((int) Math.ceil(my.getDefense() * 1.25));
            logger.info(my.getName() + "'s Defense rose!");
        } else {
            logger.info(my.getName() + "'s Defense won't go higher!");
        }
    }

    /**
     * Function code: 01F.
     * Increases the user's Speed by 1 stage.
     * */
    public void Move_Function_01F(Pokemon my, Pokemon opp, Move m){
        if(my.getSpeedStage() <= 6) {
            my.setSpeedStage(my.getSpeedStage() + 1);
            my.setSpeed((int) Math.ceil(my.getSpeed() * 1.25));
            logger.info(my.getName() + "'s Speed rose!");
        } else {
            logger.info(my.getName() + "'s Speed won't go higher!");
        }
    }

    /**
     * Function code: 020.
     * Increases the user's Special Attack by 1 stage.
     * */
    public void Move_Function_020(Pokemon my, Pokemon opp, Move m){
        if(my.getSpAttackStage() <= 6) {
            my.setSpAttackStage(my.getSpAttackStage() + 1);
            my.setSpAttack((int) Math.ceil(my.getSpAttack() * 1.25));
            logger.info(my.getName() + "'s Special Attack rose!");
        } else {
            logger.info(my.getName() + "'s Special Attack won't go higher!");
        }
    }

    /**
     * Function code: 021.
     * Increases the user's Special Defense by 1 stage.  Charges up Electric attacks.
     * */
    public void Move_Function_021(Pokemon my, Pokemon opp, Move m){
        if(my.getSpAttackStage() <= 6) {
            logger.info(my.getName() + " began charging power!");
            my.setSpAttackStage(my.getSpAttackStage() + 1);
            my.setSpAttack((int) Math.ceil(my.getSpAttack() * 1.25));
            logger.info(my.getName() + "'s Special Attack rose!");
        } else {
            logger.info(my.getName() + "'s Special Attack won't go higher!");
        }
    }

    /**
     * Function code: 022.
     * Increases the user's Special Defense by 1 stage.  Charges up Electric attacks.
     * */
    public void Move_Function_022(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még evasion
    }

    /**
     * Function code: 023.
     * Increases the user's Special Defense by 1 stage.  Charges up Electric attacks.
     * */
    public void Move_Function_023(Pokemon my, Pokemon opp, Move m){
        // TODO -> Nincs még Crit
    }

    /**
     * Function code: 024.
     * Increases the user's Attack and Defense by 1 stage each.
     * */
    public void Move_Function_024(Pokemon my, Pokemon opp, Move m){
        if((my.getAttackStage() <= 6) && (my.getDefenseStage() <= 6)) {
            my.setAttackStage(my.getAttackStage() + 1);
            my.setAttack((int) Math.ceil(my.getAttack() * 1.25));
            my.setDefenseStage(my.getDefenseStage() + 1);
            my.setDefense((int) Math.ceil(my.getDefense() * 1.25));
            logger.info(my.getName() + "'s Attack rose!");
            logger.info(my.getName() + "'s Defense rose!");
        } else {
            logger.info(my.getName() + "'s Attack or Defense won't go higher!");
        }
    }

}
