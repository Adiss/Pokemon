/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team;

import static org.junit.Assert.*;

import hu.experiment_team.dao.MoveDaoJDBC;
import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AppTest {

    @Test
    public void TestTrainer(){
        List<Pokemon> pp = new ArrayList<Pokemon>(){{
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(113));
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(114));
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(115));
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(116));
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(117));
            add(PokemonDaoJDBC.INSTANCE.getBasePokemonById(118));
        }};
        java.util.Date d = new java.util.Date();
        Trainer t = new Trainer.Builder("adiss", "Jakab Adam", "tesztJelszo", "adiss.b17@gmail.com")
                .partyPokemons(pp)
                .activePokemons(6)
                .matchWin(101)
                .matchLoose(100)
                .register_date((new java.sql.Date(d.getTime())))
                .online(1)
                .build();

        t.setOnline(2);
        t.setDisplayName("Adisso");
        t.setMatchLoose(200);
        t.setMatchWin(2001);
        t.setActivePokemons(3);

        pp.set(0, PokemonDaoJDBC.INSTANCE.getBasePokemonById(119));
        t.setPartyPokemons(pp);

        assertTrue(t.getUsername().equals("adiss"));
        assertTrue(t.getDisplayName().equals("Adisso"));
        assertTrue(t.getPassword().equals("tesztJelszo"));
        assertTrue(t.getEmail().equals("adiss.b17@gmail.com"));
        assertTrue(t.getPartyPokemons().get(0).getName().equals("Seaking"));
        assertTrue(t.getActivePokemons() == 3);
        assertTrue(t.getMatchWin() == 2001);
        assertTrue(t.getMatchLoose() == 200);
        assertTrue(t.getOnline() == 2);
        assertTrue(t.getRegisterDate() != null);

    }

    @Test
    public void TestPokemon(){
        Pokemon p = new Pokemon.Builder(1, "TesztPokemon", "TESZTPOKEMON", 200, 50, 50, 50, 50, 50)
                .ownerId(1)
                .kind("Dragon")
                .pokeDex("PokeDex leiras")
                .type1("GRASS")
                .type2("ROCK")
                .rareness(75)
                .baseExp(0)
                .happiness(100)
                .growthRate("quick")
                .stepsToHatch(6000)
                .color("GREEN")
                .habitat("TESZT")
                .effortPointsHp(0)
                .effortPointsAttack(1)
                .effortPointsDefense(2)
                .effortPointsSpeed(3)
                .effortPointsSpAttack(3)
                .effortPointsSpDefense(2)
                .hiddenAbility("CLOUDNINE")
                .compatibility("1,1")
                .height(10)
                .weight(20)
                .genderRate("Female50Precent")
                .battlerPlayerY(0)
                .battlerEnemyY(0)
                .battlerAltitude(0)
                .level(1)
                .currentXp(10000)
                .move1(MoveDaoJDBC.INSTANCE.getMoveById(1))
                .move2(MoveDaoJDBC.INSTANCE.getMoveById(2))
                .move3(MoveDaoJDBC.INSTANCE.getMoveById(3))
                .move4(MoveDaoJDBC.INSTANCE.getMoveById(4))
                .evasion(1)
                .build();

        p.setEvasion(5);
        p.setHp(5000);
        p.setMove1(MoveDaoJDBC.INSTANCE.getMoveById(10));
        p.setMove2(MoveDaoJDBC.INSTANCE.getMoveById(9));
        p.setMove3(MoveDaoJDBC.INSTANCE.getMoveById(8));
        p.setMove4(MoveDaoJDBC.INSTANCE.getMoveById(7));
        p.setCurrentXp(10001);

        assertTrue(p.getName().equals("TesztPokemon"));
        assertTrue(p.getColor().equals("GREEN"));
        assertTrue(p.getCompatibility().equals("1,1"));
        assertTrue(p.getGenderRate().equals("Female50Precent"));
        assertTrue(p.getGrowthRate().equals("quick"));
        assertTrue(p.getHabitat().equals("TESZT"));
        assertTrue(p.getHiddenAbility().equals("CLOUDNINE"));
        assertTrue(p.getInternalName().equals("TESZTPOKEMON"));

        assertTrue(p.getKind().equals("Dragon"));
        assertTrue(p.getPokeDex().equals("PokeDex leiras"));
        assertTrue(p.getType1().equals("GRASS"));
        assertTrue(p.getType2().equals("ROCK"));
        assertTrue(p.getHp() == 5000);
        assertTrue(p.getAttack() == 50);
        assertTrue(p.getDefense() == 50);
        assertTrue(p.getSpeed() == 50);
        assertTrue(p.getSpAttack() == 50);
        assertTrue(p.getSpDefense() == 50);
        assertTrue(p.getRareness() == 75);
        assertTrue(p.getBaseExp() == 0);
        assertTrue(p.getCurrentXp() == 10001);
        assertTrue(p.getHappiness() == 100);
        assertTrue(p.getOwnerId() == 1);
        assertTrue(p.getId() == 1);
        assertTrue(p.getEffortPointsAttack() == 1);
        assertTrue(p.getEffortPointsDefense() == 2);
        assertTrue(p.getEffortPointsSpeed() == 3);
        assertTrue(p.getEffortPointsHp() == 0);
        assertTrue(p.getEffortPointsSpAttack() == 3);
        assertTrue(p.getEffortPointsSpDefense() == 2);
        assertTrue(p.getHeight() == 10);
        assertTrue(p.getWeight() == 20);
        assertTrue(p.getBattlerPlayerY() == 0);
        assertTrue(p.getBattlerEnemyY() == 0);
        assertTrue(p.getBattlerAltitude() == 0);
        assertTrue(p.getLevel() == 1);
        assertTrue(p.getEvasion() == 5);
        assertTrue(p.getMove1().getId() == 10);
        assertTrue(p.getMove2().getId() == 9);
        assertTrue(p.getMove3().getId() == 8);
        assertTrue(p.getMove4().getId() == 7);
        assertTrue(p.getStepsToHatch() == 6000);

    }

}
