/**
 * Created by Jakab on 2015.06.30..
 */
package hu.experiment_team.dao;

import hu.experiment_team.models.Trainer;

public interface TrainerDaoInterface {
    void insert(Trainer trainer);
    Trainer selectByName(String username);
    Trainer selectByPassword(String password);
    Trainer selectByEmail(String email);
}
