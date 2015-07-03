package hu.experiment_team.battleAI;

import hu.experiment_team.Battle;
import hu.experiment_team.dao.MoveDaoJDBC;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.ArrayList;
import java.util.List;

public enum BattleAI {

    INSTANCE;

    public Move calculateNextMove(Pokemon a, Pokemon d){

        List<Move> attackerMoves = new ArrayList<Move>(){{
            if(a.getMove1Id() != 0) add(MoveDaoJDBC.INSTANCE.getMoveById(a.getMove1Id()));
            if(a.getMove2Id() != 0) add(MoveDaoJDBC.INSTANCE.getMoveById(a.getMove2Id()));
            if(a.getMove3Id() != 0) add(MoveDaoJDBC.INSTANCE.getMoveById(a.getMove3Id()));
            if(a.getMove4Id() != 0) add(MoveDaoJDBC.INSTANCE.getMoveById(a.getMove4Id()));
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
                int newState = Battle.INSTANCE.damageCalculator(a, d, actualMove);
                int v = calculateNextMoveValue(a, d, newState, depth-1, aMoves);
                if(v < min){
                    min = v;
                }
            }
            return min;
        }

    }

}
