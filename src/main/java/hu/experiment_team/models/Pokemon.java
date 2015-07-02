package hu.experiment_team.models;

/**
 * This class contains information about the pokemons.
 * @author Jakab Ádám
 * */
public class Pokemon {

    /**
     * A trainer ID-je aki a pokémont birtokolja.
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private final int ownerId;
    /**
     * A pokémon adatbázis beli ID-je
     * */
    private final int Id;
    /**
     * The actual name of this species.
     * This is only used when displaying the species' name on the screen.
     * */
    private final String name;
    /**
     * This is the name the scripts refer to and use.
     * Typically written only in capital letters with no spaces or symbols.
     * It is never shown to the player.
     * */
    private final String internalName;
    /**
     * The species' kind (e.g. Bulbasaur is the Seed Pokémon).
     * The word "Pokémon" is automatically added to the end, so only "Seed" needs to be here.
     * */
    private final String kind;
    /**
     * The Pokédex entry.
     * */
    private final String pokeDex;
    /**
     * The primary and secondary elemental types of this species.
     * */
    private final String type1;
    /**
     * Type2 is optional.
     * */
    private final String type2;
    /**
     * Six comma-separated values, corresponding to:
     *  - HP
     *  - Attack
     *  - Defense
     *  - Speed
     *  - Special Attack
     *  - Special Defense
     * Each value can be between 0 and 255.
     * */
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private int spAttack;
    private int spDefense;
    /**
     * The catch rate of this species. Is a number between 0 and 255.
     * The higher the number, the more likely a capture (0 means it cannot be caught by anything except a Master Ball).
     * */
    private final int rareness;
    /**
     * The base amount of Experience gained from defeating a Pokémon of this species. Is a number between 0 and 65535.
     * This base amount is then modified depending on the level and ownership of the defeated Pokémon, along with several other factors.
     * */
    private final int baseExp;
    /**
     * A pokémon aktuális szintjén összegyűjtött aktuális XP
     * */
    private int currentXp;
    /**
     * The amount of happiness a newly caught Pokémon of this species will have.
     * Is a number between 0 and 255, although is typically 70.
     * */
    private int happiness;
    /**
     * The rate at which a Pokémon of this species gains levels (i.e. how much Experience is needed to level up). One of:
     *  - Fast
     *  - Medium or MediumFast
     *  - Slow
     *  - Parabolic or MediumSlow
     *  - Erratic
     *  - Fluctuating
     * */
    private final String growthRate;
    /**
     * The number of steps it takes to hatch an egg of this species.
     * Is typically a multiple of 255, and is typically 5355.
     * */
    private final int stepsToHatch;
    /**
     * The main colour of this species. Must be one of:
     *   - Black
     *   - Blue
     *   - Brown
     *   - Gray
     *   - Green
     *   - Pink
     *   - Purple
     *   - Red
     *   - White
     *   - Yellow
     * */
    private final String color;
    /**
     * The location this species can typically be found in. Is one of:
     *   - Cave
     *   - Forest
     *   - Grassland
     *   - Mountain
     *   - Rare
     *   - RoughTerrain
     *   - Sea
     *   - Urban
     *   - WatersEdge
     * "Rare" can be taken to mean "unknown" here.
     * */
    private final String habitat;
    /**
     * The number of EVs gained by defeating a Pokémon of this species. It is corresponding to:
     *   - HP
     *   - Attack
     *   - Defense
     *   - Speed
     *   - Special Attack
     *   - Special Defense
     * As a rule, the total of these numbers should be between 1 and 3, and higher evolutions tend to give more EVs.
     * */
    private int effortPointsHp;
    private int effortPointsAttack;
    private int effortPointsDefense;
    private int effortPointsSpeed;
    private int effortPointsSpAttack;
    private int effortPointsSpDefense;
    /**
     * Up to 4 additional abilities this species can have.
     * Is the internal names of those abilities, separated by commas.
     * Pokémon cannot have any hidden ability naturally, and must be specially given one.
     * */
    private final String hiddenAbility;
    /**
     * The egg groups this species belongs to.
     * Is either one word or two comma-separated words, depending on how many egg groups this species belongs to.
     * If either egg group is "Undiscovered", this species cannot breed.
     *  - Monster
     *  - Water1
     *  - Bug
     *  - Flying
     *  - Field
     *  - Fairy
     *  - Grass
     *  - Humanlike
     *  - Water3
     *  - Mineral
     *  - Amorphous
     *  - Water2
     *  - Ditto
     *  - Dragon
     *  - Undiscovered
     * "Water1" is for sea creatures, "Water2" is for fish, and "Water3" is for shellfish.
     * "Ditto" should contain only Ditto, as a species in this group can breed with any other breedable Pokémon.
     * */
    private final String compatibility;
    /**
     * The height of the species in meters, to one decimal place.
     * Use a period for the decimal point, and do not use commas for thousands.
     * The Pokédex will automatically show this height in feet/inches, if the game recognises that the player is in the USA.
     * This is only cosmetic; the rest of the scripts still calculate using the metres value defined.
     * */
    private final double height;
    /**
     * The weight of the species in kilograms, to one decimal place.
     * Use a period for the decimal point, and do not use commas for thousands.
     * The Pokédex will automatically show this weight in pounds, if the game recognises that the player is in the USA.
     * This is only cosmetic; the rest of the scripts still calculate using the kilograms value defined.
     * */
    private final double weight;
    /**
     * The likelihood of a Pokémon of this species being a certain gender. Must be one of:
     *   - AlwaysMale
     *   - FemaleOneEighth
     *   - Female25Percent
     *   - Female50Percent
     *   - Female75Percent
     *   - FemaleSevenEighths
     *   - AlwaysFemale
     *   - Genderless
     * */
    private final String genderRate;
    /**
     * How far down the screen the back sprite is in battle.
     * A higher number means the back sprite is placed lower down the screen.
     * Can be positive or negative, and is 0 by default.
     * */
    private final int battlerPlayerY;
    /**
     * How far down the screen the enemy (front) sprite is in battle.
     * A higher number means the front sprite is placed lower down the screen.
     * Can be positive or negative, and is 0 by default.
     * */
    private final int battlerEnemyY;
    /**
     * How far up the screen the enemy (front) sprite is in battle.
     * A higher number means the front sprite is placed lower down the screen.
     * Can only be positive or 0, and is 0 by default.
     * This is the exact opposite of BattlerEnemyY.
     * If this value is greater than 0, the Pokémon's shadow is shown in battle.
     * */
    private final int battlerAltitude;
    /**
     * A pokémon aktuális szintje
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int level;
    private int evasion;
    /**
     * A pokémon első képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int move1Id;
    /**
     * A pokémon második képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int move2Id;
    /**
     * A pokémon harmadik képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int move3Id;
    /**
     * A pokémon negyedik képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int move4Id;
    /**
     * Annak a pokémonnak az id-je amit birtokolnak. Ez egyedi.
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int ownedID;

    @Override
    public String toString() {
        return "Name: " + name + ", level: " + level + ", ID: " + ownedID;
    }


    /**
     * This class handles the constructor
     * @author Jakab Ádám
     * */
    public static class Builder {

        // Required parameters
        private int id;
        private String name;
        private String internalName;
        private int hp;
        private int attack;
        private int defense;
        private int speed;
        private int spAttack;
        private int spDefense;

        // Optional parameters
        private int ownerId = 0;
        private String kind = "null";
        private String pokeDex = "null";
        private String type1 = "null";
        private String type2 = "null";
        private int rareness = 0;
        private int baseExp = 0;
        private int currentXp = 0;
        private int happiness = 0;
        private String growthRate = "null";
        private int stepsToHatch = 0;
        private String color = "null";
        private String habitat = "null";
        private int effortPointsHp = 0;
        private int effortPointsAttack = 0;
        private int effortPointsDefense = 0;
        private int effortPointsSpeed = 0;
        private int effortPointsSpAttack = 0;
        private int effortPointsSpDefense = 0;
        private String hiddenAbility = "null";
        private String compatibility = "null";
        private double height = 0.0;
        private double weight = 0.0;
        private String genderRate = "null";
        private int battlerPlayerY = 0;
        private int battlerEnemyY = 0;
        private int battlerAltitude = 0;
        private int level = 1;
        private int evasion = 100;
        private int move1Id = 0;
        private int move2Id = 0;
        private int move3Id = 0;
        private int move4Id = 0;
        private int ownedID = 0;

        public Builder(int id, String name, String internalName, int hp, int attack, int defense, int speed, int spAttack, int spDefense){
            this.id = id;
            this.name = name;
            this.internalName = internalName;
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
            this.speed= speed;
            this.spAttack = spAttack;
            this.spDefense = spDefense;
        }

        public Builder ownerId(int val){ ownerId = val; return this; }
        public Builder kind(String val){ kind = val; return this; }
        public Builder pokeDex(String val){ pokeDex = val; return this; }
        public Builder type1(String val){ type1 = val; return this; }
        public Builder type2(String val){ type2 = val; return this; }
        public Builder rareness(int val){ rareness = val; return this; }
        public Builder baseExp(int val){ baseExp = val; return this; }
        public Builder currentXp(int val){ currentXp = val; return this; }
        public Builder happiness(int val){ happiness = val; return this; }
        public Builder growthRate(String val){ growthRate = val; return this; }
        public Builder stepsToHatch(int val){ stepsToHatch = val; return this; }
        public Builder color(String val){ color = val; return this; }
        public Builder habitat(String val){ habitat = val; return this; }
        public Builder effortPointsHp(int val){ effortPointsHp = val; return this; }
        public Builder effortPointsAttack(int val){ effortPointsAttack = val; return this; }
        public Builder effortPointsDefense(int val){ effortPointsDefense = val; return this; }
        public Builder effortPointsSpeed(int val){ effortPointsSpeed = val; return this; }
        public Builder effortPointsSpAttack(int val){ effortPointsSpAttack = val; return this; }
        public Builder effortPointsSpDefense(int val){ effortPointsSpDefense = val; return this; }
        public Builder hiddenAbility(String val){ hiddenAbility = val; return this; }
        public Builder compatibility(String val){ compatibility = val; return this; }
        public Builder height(double val){ height = val; return this; }
        public Builder weight(double val){ weight = val; return this; }
        public Builder genderRate(String val){ genderRate = val; return this; }
        public Builder battlerEnemyY(int val){ battlerEnemyY = val; return this; }
        public Builder battlerPlayerY(int val){ battlerPlayerY = val; return this; }
        public Builder battlerAltitude(int val){ battlerAltitude = val; return this; }
        public Builder level(int val){ level = val; return this; }
        public Builder evasion(int val){ evasion = val; return this; }
        public Builder move1Id(int val){ move1Id = val; return this; }
        public Builder move2Id(int val){ move2Id = val; return this; }
        public Builder move3Id(int val){ move3Id = val; return this; }
        public Builder move4Id(int val){ move4Id = val; return this; }
        public Builder ownedID(int val){ ownedID = val; return this; }
        public Pokemon build(){ return new Pokemon(this); }

    }

    private Pokemon(Builder builder){
        ownerId = builder.ownerId;
        Id = builder.id;
        name = builder.name;
        internalName = builder.internalName;
        kind = builder.kind;
        pokeDex = builder.pokeDex;
        type1 = builder.type1;
        type2 = builder.type2;
        hp = builder.hp;
        attack = builder.attack;
        defense = builder.defense;
        speed = builder.speed;
        spAttack = builder.spAttack;
        spDefense = builder.spDefense;
        rareness = builder.rareness;
        baseExp = builder.baseExp;
        currentXp = builder.currentXp;
        happiness = builder.happiness;
        growthRate = builder.growthRate;
        stepsToHatch = builder.stepsToHatch;
        color = builder.color;
        habitat = builder.habitat;
        effortPointsHp = builder.effortPointsHp;
        effortPointsAttack = builder.effortPointsAttack;
        effortPointsDefense = builder.effortPointsDefense;
        effortPointsSpeed = builder.effortPointsSpeed;
        effortPointsSpAttack = builder.effortPointsSpAttack;
        effortPointsSpDefense = builder.effortPointsSpDefense;
        hiddenAbility = builder.hiddenAbility;
        compatibility = builder.compatibility;
        height = builder.height;
        weight = builder.weight;
        genderRate = builder.genderRate;
        battlerPlayerY = builder.battlerPlayerY;
        battlerEnemyY = builder.battlerEnemyY;
        battlerAltitude = builder.battlerAltitude;
        level = builder.level;
        evasion = builder.evasion;
        move1Id = builder.move1Id;
        move2Id = builder.move2Id;
        move3Id = builder.move3Id;
        move4Id = builder.move4Id;
        ownedID = builder.ownedID;
    }

    /**
     * GETTER
     * */
    public int getOwnerId() { return ownerId; }
    public int getId() { return Id; }
    public String getName() { return name; }
    public String getInternalName() { return internalName; }
    public String getKind() { return kind; }
    public String getPokeDex() { return pokeDex; }
    public String getType1() { return type1; }
    public String getType2() { return type2; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public int getSpAttack() { return spAttack; }
    public int getSpDefense() { return spDefense; }
    public int getRareness() { return rareness; }
    public int getBaseExp() { return baseExp; }
    public int getCurrentXp() { return currentXp; }
    public int getHappiness() { return happiness; }
    public String getGrowthRate() { return growthRate; }
    public int getStepsToHatch() { return stepsToHatch; }
    public String getColor() { return color; }
    public String getHabitat() { return habitat; }
    public int getEffortPointsHp() { return effortPointsHp; }
    public int getEffortPointsAttack() { return effortPointsAttack; }
    public int getEffortPointsDefense() { return effortPointsDefense; }
    public int getEffortPointsSpeed() { return effortPointsSpeed; }
    public int getEffortPointsSpAttack() { return effortPointsSpAttack; }
    public int getEffortPointsSpDefense() { return effortPointsSpDefense; }
    public String getHiddenAbility() { return hiddenAbility; }
    public String getCompatibility() { return compatibility; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getGenderRate() { return genderRate; }
    public int getBattlerPlayerY() { return battlerPlayerY; }
    public int getBattlerEnemyY() { return battlerEnemyY; }
    public int getBattlerAltitude() { return battlerAltitude; }
    public int getLevel() { return level; }
    public int getEvasion() { return evasion; }
    public int getMove1Id() { return move1Id; }
    public int getMove2Id() { return move2Id; }
    public int getMove3Id() { return move3Id; }
    public int getMove4Id() { return move4Id; }
    public int getOwnedID() { return ownedID; }

    /**
     * SETTER
     * */
    public void setHp(int val){ this.hp = val; }
    public void setEvasion(int val){ this.evasion = val; }
    public void setMove1Id(int val){ this.move1Id = val; }
    public void setMove2Id(int val){ this.move2Id = val; }
    public void setMove3Id(int val){ this.move3Id = val; }
    public void setMove4Id(int val){ this.move4Id = val; }
    public void setCurrentXp(int val){ this.currentXp = val; }
    public void setOwnedID(int val){ this.ownedID = val; }

}
