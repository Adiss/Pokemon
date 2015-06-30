/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team.models;

/**
 * This class contains information about the pokemon moves
 * @author Jakab Ádám
 * */
public class Move {

    private final int Id;
    private final String internalName;
    private final String displayName;
    private final String functionCode;
    private final int baseDamage;
    private final String type;
    private final String moveCategory;
    private final int accuracy;
    private final int totalPP;
    private final int additionalEffort;
    private final int target;
    private final int priority;
    private final String flags;
    private final String contestType;
    private final String description;

    /**
     * This class handles the constructor
     * @author Jakab Ádám
     * */
    public static class Builder {

        // Required parameters
        private int Id;
        private int baseDamage;
        private String type;
        private String moveCategory;
        private int accuracy;
        private int totalPP;
        private int additionalEffort;

        // Optional parameters
        private String internalName = "null";
        private String displayName = "null";
        private String functionCode = "null";
        private int target = 0;
        private int priority = 0;
        private String flags = "null";
        private String contestType = "null";
        private String description = "null";

        public Builder(int id, int baseDamage, String type, String moveCategory, int accuracy, int totalPP, int additionalEffort){
            this.Id = id;
            this.baseDamage = baseDamage;
            this.type = type;
            this.moveCategory = moveCategory;
            this.accuracy = accuracy;
            this.totalPP = totalPP;
            this.additionalEffort = additionalEffort;
        }

        public Builder internalName(String val){ internalName = val; return this; }
        public Builder displayName(String val){ displayName = val; return this; }
        public Builder functionCode(String val){ functionCode = val; return this; }
        public Builder target(int val){ target = val; return this; }
        public Builder priority(int val){ priority = val; return this; }
        public Builder flags(String val){ flags = val; return this; }
        public Builder contestType(String val){ contestType = val; return this; }
        public Builder description(String val){ description = val; return this; }
        public Move build(){ return new Move(this); }

    }

    private Move(Builder builder){
        Id = builder.Id;
        internalName = builder.internalName;
        displayName = builder.displayName;
        functionCode = builder.functionCode;
        baseDamage = builder.baseDamage;
        type = builder.type;
        moveCategory = builder.moveCategory;
        accuracy = builder.accuracy;
        totalPP = builder.totalPP;
        additionalEffort = builder.additionalEffort;
        target = builder.target;
        priority = builder.priority;
        flags = builder.flags;
        contestType = builder.contestType;
        description = builder.description;
    }

    public int getId() { return Id; }
    public String getInternalName() { return internalName; }
    public String getDisplayName() { return displayName; }
    public String getFunctionCode() { return functionCode; }
    public int getBaseDamage() { return baseDamage; }
    public String getType() { return type; }
    public String getMoveCategory() { return moveCategory; }
    public int getAccuracy() { return accuracy; }
    public int getTotalPP() { return totalPP; }
    public int getAdditionalEffort() { return additionalEffort; }
    public int getTarget() { return target; }
    public int getPriority() { return priority; }
    public String getFlags() { return flags; }
    public String getContestType() { return contestType; }
    public String getDescription() { return description; }

}
