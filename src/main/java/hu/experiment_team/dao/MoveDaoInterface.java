package hu.experiment_team.dao;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;

import java.util.List;

/**
 * A képességekkel kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * */
public interface MoveDaoInterface {
    /**
     * A megadott ID alapján lekérdez egy teljes képességet az adatbázisból.
     * @param moveId A képesség adatbázis beli ID-je
     * */
    Move getMoveById(int moveId);

    /**
     * A megadott pokemon ID és szint alapján lekérdezi az adott pokemon adott szintjén és alatta tudható spelleket.
     * @param level A pokémon szintje.
     * @param pokemonId A pokémon ID-je
     * */
    List<Integer> getKnownMove(int level, int pokemonId);

    Move getRandomKnownMove(Pokemon p);
}
