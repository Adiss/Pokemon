package hu.experiment_team.models;

import hu.experiment_team.Effectiveness;
import hu.experiment_team.SpellScripts;

import java.util.Random;

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
    private int maxEvasion;
    /**
     * A pokémon első képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private Move move1;
    /**
     * A pokémon második képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private Move move2;
    /**
     * A pokémon harmadik képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private Move move3;
    /**
     * A pokémon negyedik képessége
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private Move move4;
    /**
     * Annak a pokémonnak az id-je amit birtokolnak. Ez egyedi.
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int ownedID;
    /**
     * Ez az érték mutatja a pokémon maximális HP-ját.
     * Erre az effektek miatt van szükség.
     * */
    private int maxHp;
    /**
     * Ez az érték mutatja a pokémon maximális Attack-ját.
     * Erre az effektek miatt van szükség.
     * */
    private int maxAttack;
    /**
     * Ez az érték mutatja a pokémon maximális Defense-ét.
     * Erre az effektek miatt van szükség.
     * */
    private int maxDefense;
    /**
     * Ez az érték mutatja a pokémon maximális Special Attack-ját.
     * Erre az effektek miatt van szükség.
     * */
    private int maxSpecialAttack;
    /**
     * Ez az érték mutatja a pokémon maximális Special Defense-ét.
     * Erre az effektek miatt van szükség.
     * */
    private int maxSpecialDefense;
    /**
     * Ez az érték mutatja a pokémon maximális Speed-jét.
     * Erre az effektek miatt van szükség.
     * */
    private int maxSpeed;
    /**
     * Ez a flag jelzi, hogy a pokémonon van-e valamilyen status effect
     *
     * Effectek:
     *   - 0 = Healthy
     *   - 1 = Burn
     *   - 2 = Freeze
     *   - 3 = Paralysis
     *   - 4 = Poison
     *   - 5 = Badly Poisoned
     *   - 6 = Sleep
     *   - 7 = Attract
     *   - 8 = Confusion
     *   - 9 = Curse
    * */
    private int statusEffect = 0;
    private int sleepCounter = 0;
    private int confusionCounter = 0;

    @Override
    public String toString() {
        return "Name: " + name + ", level: " + level;
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
        private Move move1 = null;
        private Move move2 = null;
        private Move move3 = null;
        private Move move4 = null;
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
        public Builder move1(Move val){ move1 = val; return this; }
        public Builder move2(Move val){ move2 = val; return this; }
        public Builder move3(Move val){ move3 = val; return this; }
        public Builder move4(Move val){ move4 = val; return this; }
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
        maxEvasion = builder.evasion;
        move1 = builder.move1;
        move2 = builder.move2;
        move3 = builder.move3;
        move4 = builder.move4;
        ownedID = builder.ownedID;
        maxHp = builder.hp;
        maxAttack = builder.attack;
        maxDefense = builder.defense;
        maxSpecialAttack = builder.spAttack;
        maxSpecialDefense = builder.spDefense;
        maxSpeed = builder.speed;
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
    public Move getMove1() { return move1; }
    public Move getMove2() { return move2; }
    public Move getMove3() { return move3; }
    public Move getMove4() { return move4; }
    public int getOwnedID() { return ownedID; }
    public int getStatusEffect() { return statusEffect; }

    /**
     * SETTER
     * */
    public void setHp(int val){ this.hp = val; }
    public void setEvasion(int val){ this.evasion = val; }
    public void setMove1(Move val){ this.move1 = val; }
    public void setMove2(Move val){ this.move2 = val; }
    public void setMove3(Move val){ this.move3 = val; }
    public void setMove4(Move val){ this.move4 = val; }
    public void setCurrentXp(int val){ this.currentXp = val; }
    public void setOwnedID(int val){ this.ownedID = val; }

    /**
     * Methods
     * */

    /**
     * This method counts the size of the inflicted damage
     * @param opponent Object of the pokemon which suffer the damage
     * @param m Object of the move which being used by the attacker
     * */
    public void dealDamage(Pokemon opponent, Move m){

        // A sebzés mértékének kiszámítása.
        double STAB = this.getType1().equals(m.getType()) || this.getType2().equals(m.getType()) ? 1.5 : 1.0;
        double typeEffectiveness = Effectiveness.INSTANCE.get(m.getType(), opponent.getType1())*10;
        Random r = new Random(); double rand = 0.85 + (1.0-0.85) * r.nextDouble();

        double userAttack;
        double oppDefense;
        if(m.getMoveCategory().equals("Physical")){
            userAttack = (2 * this.level + 10) * this.attack * m.getBaseDamage();
            oppDefense = 250 * (opponent.getDefense());
        } else {
            userAttack = (2 * this.level + 10) * this.spAttack * m.getBaseDamage();
            oppDefense = 250 * (opponent.getSpDefense());
        }
        double modifiers = typeEffectiveness * STAB * rand;
        int damage = (int)Math.floor(( userAttack / oppDefense + 2 ) * modifiers);

        if((this.getStatusEffect() != 2) && (this.getStatusEffect() != 3) && (this.getStatusEffect() != 6) && (this.getStatusEffect() != 7) && (this.getStatusEffect() != 8)){

            // A sebzés értékét kivonjuk az ellenfél életpontjaiból.
            opponent.setHp(opponent.getHp()-(int)damage);

            // Státusz effectek felrakása.
            rand = r.nextInt(99) + 1;
            // BURN:
            if(SpellScripts.GET.BurnSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyBurn();
                }
            }
            // FREEZE:
            if(SpellScripts.GET.FreezeSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyFreeze();
                }
            }
            // PARALYSIS:
            if(SpellScripts.GET.ParalysisSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyParalysis();
                }
            }
            // POISON:
            if(SpellScripts.GET.PoisonSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyPoison();
                }
            }
            // BADLY POISON:
            if(SpellScripts.GET.BadlyPoisonSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyBadlyPoison();
                }
            }
            // SLEEP:
            if(SpellScripts.GET.SleepSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applySleep();
                }
            }
            // ATTRACT:
            if(SpellScripts.GET.AttractSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyAttract();
                }
            }
            // CONFUSION:
            if(SpellScripts.GET.ConfusionSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyConfusion();
                }
            }
            // CURSE:
            if(SpellScripts.GET.CurseSpells().contains(m.getInternalName())) {
                if ((int) rand <= m.getAdditionalEffectChance()) {
                    opponent.applyCurse();
                }
            }

            // Státusz effektek végrehajtása
            switch(this.statusEffect){
                case 0:
                    break;
                case 1:
                    opponent.doBurn();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    opponent.doPoison();
                    break;
                case 5:
                    opponent.doBadlyPoison();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    opponent.doConfusion();
                    break;
                case 9:
                    opponent.doCurse();
                    break;
                default:
                    break;
            }

            // Logoljuk a dolgokat. TODO -> Loggert beépíteni.
            System.out.println(this.getName() + " has dealt " + damage + " damage to " + opponent.getName() + " with " + m.getDisplayName());
            System.out.println(opponent.getName() + " now has " + opponent.getHp() + " health");

        } else {
            rand = r.nextInt(99) + 1;
            if(this.statusEffect == 3){
                if(rand >= 25){
                    // A sebzés értékét kivonjuk az ellenfél életpontjaiból.
                    opponent.setHp(opponent.getHp()-(int)damage);
                }
            }
            if(this.statusEffect == 6){
                if(this.sleepCounter == 0){
                    // A sebzés értékét kivonjuk az ellenfél életpontjaiból.
                    opponent.setHp(opponent.getHp()-(int)damage);
                }
                this.sleepCounter -= 1;
            }
            if(this.statusEffect == 7){
                if(rand <= 50){
                    // A sebzés értékét kivonjuk az ellenfél életpontjaiból.
                    opponent.setHp(opponent.getHp()-(int)damage);
                }
            }
            if(this.statusEffect == 8){
                if(confusionCounter == 0){
                    // A sebzés értékét kivonjuk az ellenfél életpontjaiból.
                    opponent.setHp(opponent.getHp()-(int)damage);
                }
                this.confusionCounter -= 1;
            }
        }

    }

    /**
     * BURN
     * Burn is one of the seldom used status afflictions, despite the fact that it has serious reprecussions on the Pokémon on which it is afflicted.
     *
     * Effects:
     *  - Each turn, the Pokémon afflicted with the Burn loses 1/8th of it's Max HP
     *  - The Pokémon's Physical Attack Stat is cut by Half. This effect does not work on Pokémon with the Guts ability
     *  - The Pokémon's Special Attack Stat is doubled on Pokémon with the Flare Boost ability
     *
     * Immunities:
     *  - Fire Type Pokémon
     *  - Pokémon with the Water Veil ability
     *
     * Methods of Healing
     *  - Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyBurn(){
        this.statusEffect = 1;
    }
    public void doBurn(){
        if(this.statusEffect == 1){
            if(!(this.type1.equals("FIRE")) && !(this.type2.equals("FIRE"))){
                this.hp = (int)Math.floor(this.hp - (this.maxHp * (0.875)));
                if(!(this.hiddenAbility.equals("GUTS")) && (this.attack == this.maxAttack))
                    this.attack = (int)Math.ceil(this.attack/2);
            }
            if(this.hiddenAbility.equals("FLAREBOOST") && (this.spAttack == this.maxSpecialAttack)){
                this.spAttack = (int)Math.ceil(this.spAttack*2);
            }
        }
    }
    public void healBurn(){
        this.statusEffect = 0;
        this.spAttack = this.maxSpecialAttack;
        this.attack = this.maxAttack;
    }

    /**
     * Freeze
     * Freezing is another seldom used status affliction, mostly due to the limited ways of afflicting it.
     * This status affliction completely immobolizes the Pokémon on which it has been afflicted until it is thawed, which can be done randomly with a 20% chance each turn
     *
     * Effects:
     *  - The Pokémon cannot use any attacks (apart from those that thaw it)
     *
     * Immunities:
     *  - Ice Type Pokémon
     *  - Pokémon with the Magma Armor ability
     *
     * Methods of Healing
     *  - Being hit by a Fire-Type Attack
     *  - Using the attacks Flame Wheel, Flare Blitz, Sacred Fire, Scald, Steam Eruption
     *  - Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyFreeze(){
        if(!(this.getType1().equals("ICE")) && !(this.getType2().equals("ICE")) && !(this.getHiddenAbility().equals("MAGMAARMOR"))){
            this.statusEffect = 2;
        }
    }
    public void healFreeze(){
        this.statusEffect = 0;
    }

    /**
     * Paralysis
     * Paralysis is one of the more commonly used status afflictions. It is able easily to immobolize your foe and give you the upper hand
     *
     * Effects:
     *  - The Pokémon afflicted's Speed stat is reduced to 25% of it's Maximum. Pokémon with the Quick Feet ability are not affected
     *  - The Pokémon has a 25% chance of being unable to attack each turn
     * Immunities:
     *  - Pokémon with the Limber ability
     *  - Electric-type Pokémon
     * Methods of Healing
     *  - Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyParalysis(){
        if(!(this.getHiddenAbility().equals("LIMBER")) && !(this.getType1().equals("ELECTRIC")) && !(this.getType2().equals("ELECTRIC"))){
            this.statusEffect = 3;
            if(this.speed == this.maxSpeed)
                this.speed = (int)Math.ceil(this.speed * 0.25);
        }
    }
    public void healParalysis(){
        this.statusEffect = 0;
        this.speed = this.maxSpeed;
    }

    /**
     * Poison
     * Poison is another commonly utilised status affliction. It gradually lowers the Pokémon's Hit Points until the Pokémon fains
     *
     * Effects:
     *  - The Pokémon loses 1/8th Max HP each turn
     *  - For every 4 steps the trainer takes, the Pokémon loses 1 HP until it reaches 1 HP remaining (Pre-Black & White)
     *
     * Immunities:
     *  - Poison Type & Steel Type Pokémon
     *  - Pokémon with the Immunity ability
     *
     * Methods of Healing
     *  - Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyPoison(){
        this.statusEffect = 4;
    }
    public void doPoison(){
        if(this.statusEffect == 4){
            if(!(this.type1.equals("POISON")) && !(this.type2.equals("POISON")) && !(this.type1.equals("STEEL")) && !(this.type2.equals("STEEL")) && !(this.hiddenAbility.equals("IMMUNITY"))){
                this.hp = (int)Math.floor(this.hp - (this.maxHp * (0.875)));
            }
        }
    }
    public void healPoison(){
        this.statusEffect = 0;
    }

    /**
     * Badly Poisoned
     * Badly Poisoned acts like Poison in the same manner, however the effects it gives is cumulative. It only lasts in the battle it was afflicted in, in which it reverts to normal poison
     *
     * Effects:
     *  - The Pokémon loses 1/16th Max HP for the first turn and then adds 1/16th to the amount to be lost so on 2nd turn 2/16th, 3rd 3/16th and so on until the Pokémon faints
     *
     * Immunities:
     *  - See Poison
     *
     * Method of Healing:
     *  - See Poison
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyBadlyPoison(){
        this.statusEffect = 5;
    }
    private double badlyPoisonStack = 1;
    public void doBadlyPoison(){
        if(this.statusEffect == 5){
            if(!(this.type1.equals("POISON")) && !(this.type2.equals("POISON")) && !(this.type1.equals("STEEL")) && !(this.type2.equals("STEEL")) && !(this.hiddenAbility.equals("IMMUNITY"))){
                this.hp = (int)Math.floor(this.hp - ((this.maxHp * (0.9375))*badlyPoisonStack));
                badlyPoisonStack +=1;
            }
        }
    }
    public void healBadlyPoison(){
        this.statusEffect = 0;
        badlyPoisonStack = 1;
    }

    /**
     * Sleep
     * Sleep is the primary used status affliction as it is utilised in conjunction with a healing move, however it is also good at stopping your opponent in it's tracks
     *
     * Effects:
     *  - The Pokémon cannot attack for 1 to 7 turns, the turn count is lowered with the Early Bird ability
     *  - Sleep Talk & Snore can be used
     *  - Allows the attacks Dream Eater & Nightmare as well as the ability Bad Dreams to be used against you
     * Immunities:
     *  - Pokémon with the Insomnia & Vital Spirit abilities
     *  - All Pokémon when Electric Terrain is in effect
     *  - Partner Pokémon if a Pokémon has Sweet Veil
     *
     * Methods of Healing
     *  - Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability
     *  - Using the attacks Heal Bell or Aromatherapy
     *  - Using the attack Wake-Up Slap
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applySleep(){
        if(!(this.hiddenAbility.equals("INSOMNIA")) && !(this.hiddenAbility.equals("VITALSPIRIT"))){
            Random r = new Random();
            this.statusEffect = 6;
            this.sleepCounter = r.nextInt(6)+1;
        }
    }
    public void healSleep(){
        this.statusEffect = 0;
    }

    /**
     * Attract
     * Attraction is a status affliction that only occurs a few times. It requires the user to have a gender and the opponent to have a differing gender to work
     *
     * Effects:
     *  - The Pokémon afflicted cannot attack 50% of the time
     * Immunities:
     *  - Pokémon with the Oblivious ability
     *  - Pokémon of the same gender as the user
     *  - Genderless Pokémon
     * Methods of Healing
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyAttract(){
        if(!(this.hiddenAbility.equals("OBLIVIOUS"))){
            this.statusEffect = 7;
        }
    }
    public void healAttract(){
        this.statusEffect = 0;
    }

    /**
     * Confusion
     * Confusion is another status effect that is common in use to hinder your opponent for 1 to 4 turns
     *
     * Effects:
     *  - The Pokémon afflicted cannot attack 50% of the time for 1-4 turns
     *  - Raises Evasion for Pokémon with the Tangled Feet ability
     *
     * Immunities:
     *  - Pokémon with the Own Tempo ability
     *
     * Methods of Healing
     *  - Using the attacks Heal Bell or Aromatherapy
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyConfusion(){
        if(!(this.hiddenAbility.equals("OBLIVIOUS"))){
            Random r = new Random();
            this.statusEffect = 8;
            this.confusionCounter = r.nextInt(3)+1;
        }
    }
    public void doConfusion(){
        if(this.statusEffect == 8){
            this.hp = (int)Math.floor(this.hp - ((this.maxHp * 0.05)));
            if(this.hiddenAbility.equals("TANGLEDFEET")){
                if(this.evasion == this.maxEvasion){
                    this.evasion *= 1.2;
                }
            }
        }
    }
    public void healConfusion(){
        this.statusEffect = 0;
        this.evasion = maxEvasion;
    }

    /**
     * Curse
     * Curse is a seldom used affliction. Partly because the affliction only occurs when the attack has been used by a Ghost Type Pokémon
     *
     * Effects:
     *  - The Pokémon afflicted loses 1/4 of it's Max HP each turn
     *
     * Immunities:
     *  - None
     *
     * Methods of Healing
     *  - Switching Out
     *
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyCurse(){
        this.statusEffect = 9;
    }
    public void doCurse(){
        if(this.statusEffect == 9){
            this.hp = (int)Math.floor(this.hp - ((this.maxHp * 0.25)));
        }
    }
    public void healCurse(){
        this.statusEffect = 0;
    }

}
