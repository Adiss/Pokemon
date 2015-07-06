package hu.experiment_team.dao;

import hu.experiment_team.models.Pokemon;

import java.util.List;

/**
 * A pokémonokkal kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * @author Jakab Ádám
 * */
public interface PokemonDaoInterface {
    /**
     * A megadott pokémon ID alapján lekérdez az adatbázisból egy alap pokémont.
     * @param pokemonId a pokémon adatbázis beli ID-je
     * @return A pokémon objektumát adja vissza
     * */
    Pokemon getBasePokemonById(int pokemonId);
    /**
     * A megadott trainer ID és pokémon ID alapján hozzáad egy pokémont a trainer gyűjteményéhez.
     * @param pokemonId A pokémon adatbázis beli ID-je
     * @param trainerId A trainer adatbázis beli ID-je
     * */
    void addOwnedPokemon(int trainerId, int pokemonId);
    /**
     * A megadott trainer ID alapján lekérdezi az adott trainer összes birtolokt pokémonját.
     * @param trainerId A trainer adatbázis beli ID-je
     * @return A birtokolt pokémonok objektumainak listáját adja vissza
     * */
    List<Pokemon> getOwnedPokemons(int trainerId);
    /**
     * Az adott pokémon ID-ja alapján reseteli a pokémont, azaz a statjait frissíti.
     * @param ownedID Annak a kifejezett pokémonnak az ID-je az ownedPokemons táblából, amelyiket resetelni akarjuk.
     * */
    Pokemon resetPokemon(int ownedID);
    /**
     * Kiválaszt egy random alap pokémont az adatbázisból.
     * @return Egy pokémon objektuma
     * */
    Pokemon getRandomPokemon();

    /**
     * Egy birtokolt pokémont választ ki az adatbázisból ID alapján
     * @param pokemonId A pokémon ID-je (ownedID)
     * @return A pokémon objektuma
     * */
    Pokemon getOwnedPokemonById(int pokemonId);
}
