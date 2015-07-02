package hu.experiment_team.models;

/**
 * This class contains information about the pokemon moves
 * @author Jakab Ádám
 * */
public class Move {

    /**
     * Each move's ID number must be different.
     * The ID number must be a whole number greater than 0 (i.e. 1,2,3...), up to 999.
     * Missing numbers are not a problem (e.g. the sequence 23,24,25,58,59,60... is allowed).
     * */
    private final int Id;
    /**
     * Typically written only in capital letters with no spaces or symbols.
     * This is the name the scripts refer to and use.
     * This name is never seen by the player.
     * */
    private final String internalName;
    /**
     * The actual name of the move.
     * This is only used when displaying the move's name on the screen.
     * */
    private final String displayName;
    /**
     * The move's function code.
     * A function code is a 3-digit hexadecimal number that describes a defined effect (e.g. poisons the opponent).
     * */
    private final String functionCode;
    /**
     * The base amount of damage the move will inflict on the attack target.
     * Is 0 if it is a status move (i.e. doesn't inflict damage).
     * Is 1 if the move calculates the base damage later (e.g. depending on the user's weight).
     * For multi-hit moves, this is the damage dealt per hit.
     * */
    private final int baseDamage;
    /**
     * The internal name of the move's elemental type.
     * */
    private final String type;
    /**
     * Is one of the following:
     *  - Physical (calculates damage using Attack and Defense)
     *  - Special (calculates damage using Special Attack and Special Defense)
     *  - Status (inflicts no damage)
     * */
    private final String moveCategory;
    /**
     * The move's accuracy, out of 100.
     * Set to 0 if the move doesn't perform an accuracy check (i.e. it cannot be evaded).
     * */
    private final int accuracy;
    /**
     * The maximum amount of PP this move can have, not counting modifiers such as the item PP Up.
     * If this is 0, the move is infinite use.
     * */
    private final int totalPP;
    /**
     * The probability that the move's additional effect occurs, out of 100.
     * For example, Poison Sting poisons the opponent 30% of the time, so this value would be 30.
     * Set to 0 if this move does nothing other than its effect (e.g. for all status moves).
     * */
    private final int additionalEffort;
    /**
     * The battler(s) that this move will strike. Is one of the following values:
     *  - 00 - Single Pokémon other than the user
     *  - 01 - No target
     *  - 02 - One opposing Pokémon selected at random
     *  - 04 - All opposing Pokémon
     *  - 08 - All Pokémon other than the user
     *  - 10 - User
     *  - 20 - Both sides (e.g. Light Screen, Reflect, Heal Bell)
     *  - 40 - User's side
     *  - 80 - Opposing Pokémon's side
     *  - 100 - User's partner
     *  - 200 - Player's choice of user or user's partner (e.g. Acupressure)
     *  - 400 - Single Pokémon on opponent's side (e.g. Me First)
     *  - 800 - Directly opposite Pokémon on opponent's side (used by Curse)
     * */
    private final int target;
    /**
     * The order the move will be used in (overrides Speed calculations).
     * Usually 0, but can be between -6 and 6.
     * A higher value means higher priority.
     * Moves with equal priority will be used depending on their user's Speed.
     * - For example, Quick Attack has a priority of 1.
     * */
    private final int priority;
    /**
     * Any combination of the following letters:
     *  - a - This move makes physical contact with the opponent.
     *  - b - The opponent can use Protect or Detect to protect itself from this move.
     *  - c - The opponent can use Magic Coat to redirect the effect of this move. Use this flag if the move deals no damage but causes a negative effect on an opponent. (Flags c and d are mutually exclusive.)
     *  - d - The opponent can use Snatch to steal the effect of this move. Use this flag for most moves that target the user. (Flags c and d are mutually exclusive.)
     *  - e - This move can be copied by Mirror Move.
     *  - f - This move has a 10% chance of making the opponent flinch if the user is holding a King's Rock. Use this flag for all damaging moves.
     *  - g - If the user is frozen, this move will thaw it out before it is used.
     *  - h - This move has a high critical hit rate.
     *  - i - This is a healing move.
     *  - j - This move is a punching move.
     *  - k - This is a sound-based move.
     *  - l - While Gravity is in effect, this move is disabled for all active Pokémon.
     * */
    private final String flags;
    private final String contestType;
    /**
     * The description of the move.
     * If there are any commas or apostrophes/double quote marks in the description, the entire description must be enclosed in double quote marks.
     * The double quote marks in the description must also be preceded by a backslash "\".
     * */
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

    @Override
    public String toString() {
        return "Name: " + displayName +
                ", baseDamage: " + baseDamage +
                ", type: '" + type + '\'' +
                ", accuracy: " + accuracy +
                ", ID: " + Id;
    }

}
