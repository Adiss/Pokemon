package hu.experiment_team.battleAI;

import hu.experiment_team.Effectiveness;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A gépi trainer gondolkodó egysége.
 * @author Jakab Ádám
 * */
public enum BattleAI {

    /**
     * Ezen a mezőn keresztül érhetőek el az osztály metódusai.
     * */
    INSTANCE;

    public Move calculateNextMove(Pokemon a, Pokemon d){

        List<Move> attackerMoves = new ArrayList<Move>(){{
            if(a.getMove1() != null) add(a.getMove1());
            if(a.getMove2() != null) add(a.getMove2());
            if(a.getMove3() != null) add(a.getMove3());
            if(a.getMove4() != null) add(a.getMove4());
        }};

        int min = Integer.MAX_VALUE;
        Move nextMove = null;
        int depth = 4;

        for(Move actualMove : attackerMoves){
            int newState = d.getHp();
            int v = calculateNextMoveValue(a, d, newState, depth-1, attackerMoves);
            if(v < min){
                min = v;
                nextMove = actualMove;
            }
        }

        return nextMove;
    }

    private int calculateNextMoveValue(Pokemon a, Pokemon d, int state, int depth, List<Move> aMoves){

        if((state <= 0) || (depth == 0)){
            return state;
        } else {
            int min = Integer.MAX_VALUE;
            for(Move actualMove : aMoves){
                int newState = damageCalculator(a, d, actualMove);
                int v = calculateNextMoveValue(a, d, newState, depth-1, aMoves);
                if(v < min){
                    min = v;
                }
            }
            return min;
        }

    }

    /**
     * Visszaadja az ellenfél sebzés után maradt HP-ját
     * @param a Object of the pokemon which deal the damage
     * @param d Object of the pokemon which suffer the damage
     * @param m Object of the move which being used by the attacker
     * */
    public int damageCalculator(Pokemon a, Pokemon d, Move m){

        double damage = 0;

        if(!m.getMoveCategory().equals("Status")){
            double STAB = a.getType1().equals(m.getType()) || a.getType2().equals(m.getType()) ? 1.5 : 1.0;
            double typeEffectiveness = Effectiveness.INSTANCE.get(m.getType(), d.getType1())*10;
            double criticalStrikeChance = 1;
            double others = 1;
            Random r = new Random(); double rand = 0.85 + (1.0-0.85) * r.nextDouble();
            double modifier = STAB * typeEffectiveness * criticalStrikeChance * others * rand;
            if(m.getMoveCategory().equals("Physical"))
                damage = Math.floor(Math.floor((Math.floor((Math.floor((Math.floor(((Math.floor((2*a.getLevel())/5)+2)*a.getAttack()*m.getBaseDamage())/d.getDefense()))/50)+2)*STAB))*typeEffectiveness)*rand);
            else if(m.getMoveCategory().equals("Special"))
                damage = Math.floor(Math.floor((Math.floor((Math.floor((Math.floor(((Math.floor((2*a.getLevel())/5)+2)*a.getSpAttack()*m.getBaseDamage())/d.getSpDefense()))/50)+2)*STAB))*typeEffectiveness)*rand);
        }

        return d.getHp()-(int)damage;
    }

}
