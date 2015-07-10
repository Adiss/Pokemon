package hu.experiment_team;

import hu.experiment_team.dao.MoveDaoJDBC;
import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.lang.reflect.Method;

public class Main{

    public static void main(String[] args){

        Pokemon p1 = PokemonDaoJDBC.INSTANCE.getOwnedPokemonById(3);
        Pokemon p2 = PokemonDaoJDBC.INSTANCE.getOwnedPokemonById(4);
        Move m = MoveDaoJDBC.INSTANCE.getMoveById(284);

        p1.dealDamage(p2, m);

    }

}
