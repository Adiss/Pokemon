/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Move;

import java.util.List;

public interface MoveDaoInterface {
    Move getMoveById(int moveId);
    List<Integer> getKnownMove(int level, int pokemonId);
}
