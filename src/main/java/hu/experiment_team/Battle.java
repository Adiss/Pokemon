/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.util.Random;

/**
 * This class contains methods which are required by the battles
 * @author Jakab Ádám
 * */
public enum Battle {
    INSTANCE;

    public void battleStartUp(Trainer trainer1, Trainer trainer2){

        /* TODO -> Reset pokemon stats before battle */
        System.out.println(trainer2.getDisplayName() + " wants to battle!");
        System.out.println(trainer2.getDisplayName() + " sent out " + trainer2.getPartyPokemons().get(0).getName());

    }

    /**
     * This method counts the size of the inflicted damage
     * @param a Object of the pokemon which deal the damage
     * @param d Object of the pokemon which suffer the damage
     * @param m Object of the move which being used by the attacker
     * */
    public void dealDamage(Pokemon a, Pokemon d, Move m){

        if(m.getMoveCategory().equals("Status")){

        } else {
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
