/**
 * Created by Jakab on 2015.06.28..
 */
package hu.experiment_team;

/**
 * This class contains information about the pokemons
 * @author Jakab Ádám
 * */
public class Pokemon {

    private final int ownerId;
    private final int Id;
    private final String name;
    private final String internalName;
    private final String kind;
    private final String pokeDex;
    private final String type1;
    private final String type2;
    private int hp;
    private int attack;
    private int defense;
    private int speed;
    private int spAttack;
    private int spDefense;
    private final int rareness;
    private final int baseExp;
    private int currentXp;
    private int happiness;
    private final String growthRate;
    private final int stepsToHatch;
    private final String color;
    private final String habitat;
    private int effortPointsHp;
    private int effortPointsAttack;
    private int effortPointsDefense;
    private int effortPointsSpeed;
    private int effortPointsSpAttack;
    private int effortPointsSpDefense;
    private final String hiddenAbility;
    private final String compatibility;
    private final double height;
    private final double weight;
    private final String genderRate;
    private final int battlerPlayerY;
    private final int battlerEnemyY;
    private final int battlerAltitude;
    private int level;
    private int evasion;
    private int move1Id;
    private int move2Id;
    private int move3Id;
    private int move4Id;



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
    }

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

    public void setHp(int val){ this.hp = val; }
    public void setEvasion(int val){ this.evasion = val; }
    public void setMove1Id(int val){ this.move1Id = val; }
    public void setMove2Id(int val){ this.move2Id = val; }
    public void setMove3Id(int val){ this.move3Id = val; }
    public void setMove4Id(int val){ this.move4Id = val; }

}
