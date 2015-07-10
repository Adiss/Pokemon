package hu.experiment_team;

import hu.experiment_team.battleAI.BattleAI;
import hu.experiment_team.models.Move;
import hu.experiment_team.models.Pokemon;
import hu.experiment_team.models.Trainer;

import java.io.InputStreamReader;
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
        t1.choosePartyPokemons();

        while(t1.getPartyPokemons().size() != 0 && t2.getPartyPokemons().size() != 0){

            Pokemon t2Choosen = t2.chooseOneRandomPartyPokemon();
            System.out.println('\n' + t2.getDisplayName() + " has choosen " + t2Choosen.getName() + '\n' + '\n');

            System.out.println("Choose a pokemon to fight: " + '\n' + '\n');
            for(int i = 0; i < t1.getPartyPokemons().size(); i++){
                System.out.println("ID: "+ (i+1) + " " + t1.getPartyPokemons().get(i));
            }
            Pokemon t1Choosen = t1.getPartyPokemons().get(Integer.parseInt(sc.nextLine())-1);

            do {
                System.out.println('\n' + "Choose a spell to damage with: " + '\n');
                    System.out.println("ID: 1 " + t1Choosen.getMoves().get(1));
                    System.out.println("ID: 2 " + t1Choosen.getMoves().get(2));
                    System.out.println("ID: 3 " + t1Choosen.getMoves().get(3));
                    System.out.println("ID: 4 " + t1Choosen.getMoves().get(4));

                Move t1ChoosenMove = null;
                switch (Integer.parseInt(sc.nextLine())){
                    case 1:
                        t1ChoosenMove = t1Choosen.getMoves().get(1);
                        break;
                    case 2:
                        t1ChoosenMove = t1Choosen.getMoves().get(2);
                        break;
                    case 3:
                        t1ChoosenMove = t1Choosen.getMoves().get(3);
                        break;
                    case 4:
                        t1ChoosenMove = t1Choosen.getMoves().get(4);
                        break;
                    default:
                        System.out.println("Nincs ilyen Spelled!");
                }
                Move t2ChoosenMove = BattleAI.INSTANCE.calculateNextMove(t2Choosen, t1Choosen);

                if(t1ChoosenMove != null)
                    t1Choosen.dealDamage(t2Choosen, t1ChoosenMove);
                t2Choosen.dealDamage(t1Choosen, t2ChoosenMove);
            } while(t1Choosen.getBaseStats().get("hp") > 0 && t2Choosen.getBaseStats().get("hp") > 0);

            if(t1Choosen.getBaseStats().get("hp") <= 0){
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

}
