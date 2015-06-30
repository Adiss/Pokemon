/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Pokemon;
import java.util.List;

public interface PokemonDaoInterface {
    Pokemon getBasePokemonById(int pokemonId);
    void addOwnedPokemon(int trainerId, int pokemonId);
    List<Pokemon> getOwnedPokemons(int trainerId);
}
