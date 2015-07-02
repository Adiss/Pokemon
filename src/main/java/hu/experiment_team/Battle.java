package hu.experiment_team;

import hu.experiment_team.dao.MoveDaoJDBC;
import hu.experiment_team.dao.PokemonDaoJDBC;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class contains methods which are required by the battles
 * @author Jakab Ádám
 * */
public enum Battle {
    INSTANCE;

    public void doBattle(Trainer t1, Trainer t2){

        Scanner sc = new Scanner(new InputStreamReader(System.in));

        System.out.println(t2.getDisplayName() + " wants to battle!\n\n");
        t1 = TrainerMethods.INSTANCE.choosePartyPokemons(t1);

        List<Pokemon> t2PartyPokemons = t2.getPartyPokemons();
        List<Pokemon> t1PartyPokemons = t1.getPartyPokemons();

        while(t1PartyPokemons.size() != 0 || t1PartyPokemons.size() != 0){
            Collections.shuffle(t2PartyPokemons);
            Pokemon t2Choosen = t2PartyPokemons.get(0);
            System.out.println(t2.getDisplayName() + " has choosen " + t2Choosen.getName() + '\n' + '\n');
            System.out.println("Choose a pokemon to fight: " + '\n' + '\n');
            t1PartyPokemons.forEach(System.out::println);
            Pokemon t1Choosen = PokemonDaoJDBC.INSTANCE.getOwnedPokemonById(Integer.parseInt(sc.nextLine()));
            while(t1Choosen.getHp() > 0 || t2Choosen.getHp() > 0){
                if(t1Choosen.getHp() <=0){
                    System.out.println("Your pokemon has been fainted." + '\n' + '\n');
                    t1PartyPokemons.remove(t1Choosen);
                    break;
                }

                    System.out.println("Choose a spell to damage with: " + '\n' + '\n');
                    System.out.println(MoveDaoJDBC.INSTANCE.getMoveById(t1Choosen.getMove1Id()));
                    System.out.println(MoveDaoJDBC.INSTANCE.getMoveById(t1Choosen.getMove2Id()));
                    System.out.println(MoveDaoJDBC.INSTANCE.getMoveById(t1Choosen.getMove3Id()));
                    System.out.println(MoveDaoJDBC.INSTANCE.getMoveById(t1Choosen.getMove4Id()));
                Move t1ChoosenMove = MoveDaoJDBC.INSTANCE.getMoveById(Integer.parseInt(sc.nextLine()));
                Move t2ChoosenMove = MoveDaoJDBC.INSTANCE.getRandomKnownMove(t2Choosen);

                dealDamage(t1Choosen, t2Choosen, t1ChoosenMove);

                if(t2Choosen.getHp() <=0){
                    System.out.println("Enemy pokemon has been fainted." + '\n' + '\n');
                    t2PartyPokemons.remove(t2Choosen);
                    break;
                } else
                    dealDamage(t2Choosen, t1Choosen, t2ChoosenMove);
            }
            if(t1Choosen.getHp() <= 0){
                System.out.println("Your pokemon has been fainted!" + '\n' + '\n');
                t1.setActivePokemons(t1.getActivePokemons()-1);
            } else {
                System.out.println("Enemy pokemon has been fainted!" + '\n' + '\n');
                t2.setActivePokemons(t2.getActivePokemons()-1);
            }
        }
        if(t1.getActivePokemons() == 0)
            System.out.println("You have been defeated!" + '\n' + '\n');
        else
            System.out.println("You win!" + '\n' + '\n');
    }

    /**
     * This method counts the size of the inflicted damage
     * @param a Object of the pokemon which deal the damage
     * @param d Object of the pokemon which suffer the damage
     * @param m Object of the move which being used by the attacker
     * */
    public void dealDamage(Pokemon a, Pokemon d, Move m){

        if(!m.getMoveCategory().equals("Status")){
            double STAB = a.getType1().equals(m.getType()) || a.getType2().equals(m.getType()) ? 1.5 : 1.0;
            double typeEffectiveness = Effectiveness.INSTANCE.get(m.getType(), d.getType1())*10;
            double criticalStrikeChance = 1;
            double others = 1;
            Random r = new Random(); double rand = 0.85 + (1.0-0.85) * r.nextDouble();
            double modifier = STAB * typeEffectiveness * criticalStrikeChance * others * rand;
            double damage = 0;
            if(m.getMoveCategory().equals("Physical"))
                damage = Math.floor(Math.floor((Math.floor((Math.floor((Math.floor(((Math.floor((2*a.getLevel())/5)+2)*a.getAttack()*m.getBaseDamage())/d.getDefense()))/50)+2)*STAB))*typeEffectiveness)*rand);
            else if(m.getMoveCategory().equals("Special"))
                damage = Math.floor(Math.floor((Math.floor((Math.floor((Math.floor(((Math.floor((2*a.getLevel())/5)+2)*a.getSpAttack()*m.getBaseDamage())/d.getSpDefense()))/50)+2)*STAB))*typeEffectiveness)*rand);

            d.setHp(d.getHp()-(int)damage);
            System.out.println(a.getName() + " has dealt " + damage + " damage to " + d.getName() + " with " + m.getDisplayName());
            System.out.println(d.getName() + " now has " + d.getHp() + " health");
        }
    }

}
