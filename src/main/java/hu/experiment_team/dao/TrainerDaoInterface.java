/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Trainer;

/**
 * A trainerekkel (felhasználókkal) kapcsolatos adatbázis műveleteket tartalmazó osztály interfésze.
 * @author Jakab Ádám
 * */
public interface TrainerDaoInterface {
    /**
     * Hozzáad egy trainert (felhasználót) az adatbázishoz.
     * @param trainer A trainer osztály egy példánya a megfelelő mezőkkel feltöltve.
     * */
    void insert(Trainer trainer);
    /**
     * Kiválaszt egy trainert (felhasználót) a neve alapján.
     * @param username A trainer felhasználó neve.
     * */
    Trainer selectByName(String username);
    /**
     * Kiválaszt egy trainert (felhasználót) a jelszava alapján.
     * Ennek a jelszónak már az SHA1 kódolt jelszónak kell lennie.
     * @param password SHA1 kódolt jelszó.
     * */
    Trainer selectByPassword(String password);
    /**
     * Kiválaszt egy trainert (felhasználót) az e-mail címe alapján.
     * @param email A trainer email címe.
     * */
    Trainer selectByEmail(String email);
}
