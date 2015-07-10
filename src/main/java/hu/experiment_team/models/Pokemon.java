package hu.experiment_team.models;

import hu.experiment_team.Move_Functions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
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
     * Annak a pokémonnak az id-je amit birtokolnak. Ez egyedi.
     * Ez a stat csak akkor van, a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private int ownedID;
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
     *  - Accuracy
     *  - Evasion
     * Each value can be between 0 and 255.
     * */
    private Map<String, Integer> baseStats;
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
    private Map<String, Integer> effortPoints;
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
    /**
     * A pokémon első képességei
     * Ez a mező csak akkor használatos, ha a Pokémont valamelyik trainer birtokolja, azaz benne van az ownedPokemons táblába az adatbázisban.
     * */
    private Map<Integer, Move> moves;
    /**
     * Ebben a Map-ban tároljuk a pokémon alapértékeit, azaz a buffok nélküli statokat.
     * Erre az effektek miatt van szükség.
     * */
    private Map<String, Integer> maxStats;
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
    private int statusEffect;
    private Map<String, Integer> counters;
    private Map<String, Integer> statStages;
    private Map<String, Integer> flags;

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
        private Map<String, Integer> baseStats;

        // Optional parameters
        private int ownerID = 0;
        private int ownedID = 0;
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
        private Move move1 = null;
        private Move move2 = null;
        private Move move3 = null;
        private Move move4 = null;

        public Builder(int id, String name, String internalName, int hp, int attack, int defense, int speed, int spAttack, int spDefense){
            this.id = id;
            this.name = name;
            this.internalName = internalName;
            this.baseStats = new HashMap<String, Integer>(){{
                put("hp", hp);
                put("attack", attack);
                put("defense", defense);
                put("spattack", spAttack);
                put("spdefense", spDefense);
                put("speed", speed);
                put("accuracy", 0);
                put("evasion", 0);
                put("crit", 6);
            }};
        }

        public Builder ownerId(int val){ ownerID = val; return this; }
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
        public Builder move1(Move val){ move1 = val; return this; }
        public Builder move2(Move val){ move2 = val; return this; }
        public Builder move3(Move val){ move3 = val; return this; }
        public Builder move4(Move val){ move4 = val; return this; }
        public Builder ownedID(int val){ ownedID = val; return this; }
        public Pokemon build(){ return new Pokemon(this); }

    }

    private Pokemon(Builder builder){
        ownerId = builder.ownerID;
        Id = builder.id;
        name = builder.name;
        internalName = builder.internalName;
        kind = builder.kind;
        pokeDex = builder.pokeDex;
        type1 = builder.type1;
        type2 = builder.type2;
        baseStats = builder.baseStats;
        rareness = builder.rareness;
        baseExp = builder.baseExp;
        currentXp = builder.currentXp;
        happiness = builder.happiness;
        growthRate = builder.growthRate;
        stepsToHatch = builder.stepsToHatch;
        color = builder.color;
        habitat = builder.habitat;
        effortPoints = new HashMap<String, Integer>(){{
            put("hp", builder.effortPointsHp);
            put("attack", builder.effortPointsHp);
            put("defense", builder.effortPointsAttack);
            put("spattack", builder.effortPointsDefense);
            put("spdefense", builder.effortPointsSpAttack);
            put("speed", builder.effortPointsSpDefense);
        }};
        hiddenAbility = builder.hiddenAbility;
        compatibility = builder.compatibility;
        height = builder.height;
        weight = builder.weight;
        genderRate = builder.genderRate;
        battlerPlayerY = builder.battlerPlayerY;
        battlerEnemyY = builder.battlerEnemyY;
        battlerAltitude = builder.battlerAltitude;
        level = builder.level;
        moves = new HashMap<Integer, Move>(){{
            put(1, builder.move1);
            put(2, builder.move2);
            put(3, builder.move3);
            put(4, builder.move4);
        }};
        ownedID = builder.ownedID;
        maxStats = new HashMap<String, Integer>(){{
            put("hp", builder.baseStats.get("hp"));
            put("attack", builder.baseStats.get("attack"));
            put("defense", builder.baseStats.get("defense"));
            put("spattack", builder.baseStats.get("spattack"));
            put("spdefense", builder.baseStats.get("spdefense"));
            put("speed", builder.baseStats.get("speed"));
        }};
        counters = new HashMap<String, Integer>(){{
            put("sleep", 0);
            put("confusion", 0);
            put("safeguard", 0);
            put("badlypoison", 1);
        }};
        statStages = new HashMap<String, Integer>(){{
            put("hp", 0);
            put("attack", 0);
            put("defense", 0);
            put("spattack", 0);
            put("spdefense", 0);
            put("speed", 0);
            put("accuracy", 0);
            put("evasion", 0);
        }};
        statusEffect = 0;
        flags = new HashMap<String, Integer>(){{
            put("flinched", 0);
            put("minimized", 0);
            put("safeguarded", 0);
        }};
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getOwnedID() {
        return ownedID;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getKind() {
        return kind;
    }

    public String getPokeDex() {
        return pokeDex;
    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public Map<String, Integer> getBaseStats() {
        return baseStats;
    }

    public void setBaseStat(String s, Integer i) {
        this.baseStats.put(s, i);
    }

    public int getRareness() {
        return rareness;
    }

    public int getBaseExp() {
        return baseExp;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public int getStepsToHatch() {
        return stepsToHatch;
    }

    public String getColor() {
        return color;
    }

    public String getHabitat() {
        return habitat;
    }

    public Map<String, Integer> getEffortPoints() {
        return effortPoints;
    }

    public void setEffortPoint(String s, Integer i) {
        this.effortPoints.put(s, i);
    }

    public String getHiddenAbility() {
        return hiddenAbility;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public String getGenderRate() {
        return genderRate;
    }

    public int getBattlerPlayerY() {
        return battlerPlayerY;
    }

    public int getBattlerEnemyY() {
        return battlerEnemyY;
    }

    public int getBattlerAltitude() {
        return battlerAltitude;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<Integer, Move> getMoves() {
        return moves;
    }

    public void setMove(Integer i, Move m) {
        this.moves.put(i, m);
    }

    public Map<String, Integer> getMaxStats() {
        return maxStats;
    }

    public void setMaxStat(String s, Integer i) {
        this.maxStats.put(s, i);
    }

    public int getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(int statusEffect) {
        this.statusEffect = statusEffect;
    }

    public Map<String, Integer> getCounters() {
        return counters;
    }

    public void setCounter(String s, Integer i) {
        this.counters.put(s, i);
    }

    public Map<String, Integer> getStatStages() {
        return statStages;
    }

    public void setStatStage(String s, Integer i) {
        this.statStages.put(s, i);
    }

    public Map<String, Integer> getFlags() {
        return flags;
    }

    public void setFlag(String s, Integer i) {
        this.flags.put(s, i);
    }

    /**
     * This method counts the size of the inflicted damage
     * @param opponent Object of the pokemon which suffer the damage
     * @param m Object of the move which being used by the attacker
     * */
    public void dealDamage(Pokemon opponent, Move m){

        Random r = new Random();
        int rand = r.nextInt(99) + 1;

        if((this.getStatusEffect() != 2) && (this.getStatusEffect() != 3) && (this.getStatusEffect() != 6) && (this.getStatusEffect() != 7) && (this.getStatusEffect() != 8)){

            for(Method method : Move_Functions.INSTANCE.getClass().getMethods()){
                if(method.getName().contains(m.getFunctionCode()))
                    try {
                        method.invoke(Move_Functions.INSTANCE, this, opponent, m);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
            }

            m.usePP();

        }

    }

    /**
     * BURN.<br>
     * Burn is one of the seldom used status afflictions, despite the fact that it has serious reprecussions on the Pokémon on which it is afflicted.
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>Each turn, the Pokémon afflicted with the Burn loses 1/8th of it's Max HP</li>
     *              <li>The Pokémon's Physical Attack Stat is cut by Half. This effect does not work on Pokémon with the Guts ability</li>
     *              <li>The Pokémon's Special Attack Stat is doubled on Pokémon with the Flare Boost ability</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Fire Type Pokémon</li>
     *              <li>Pokémon with the Water Veil ability</li>
     *          </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability</li>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyBurn(){
        this.statusEffect = 1;
    }
    public void doBurn(){
        if(this.statusEffect == 1){
            if(!(this.type1.equals("FIRE")) && !(this.type2.equals("FIRE"))){
                this.baseStats.put("hp", (int) Math.floor(this.baseStats.get("hp") - (this.maxStats.get("hp") * (0.875))));
                if(!(this.hiddenAbility.equals("GUTS")) && (this.baseStats.get("attack") == this.maxStats.get("attack")))
                    this.baseStats.put("attack", (int) Math.ceil(this.baseStats.get("attack") / 2));
            }
            if(this.hiddenAbility.equals("FLAREBOOST") && (this.baseStats.get("spattack") == this.maxStats.get("spattack"))){
                this.baseStats.put("spattack", (int)Math.ceil(this.baseStats.get("spattack")*2));
            }
        }
    }
    public void healBurn(){
        this.statusEffect = 0;
        this.baseStats.put("spattack", this.maxStats.get("spattack"));
        this.baseStats.put("attack", this.maxStats.get("attack"));
    }

    /**
     * Freeze.<br>
     * Freezing is another seldom used status affliction, mostly due to the limited ways of afflicting it.
     * This status affliction completely immobolizes the Pokémon on which it has been afflicted until it is thawed, which can be done randomly with a 20% chance each turn
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon cannot use any attacks (apart from those that thaw it)</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Ice Type Pokémon</li>
     *              <li>Pokémon with the Magma Armor ability</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Being hit by a Fire-Type Attack</li>
     *              <li>Using the attacks Flame Wheel, Flare Blitz, Sacred Fire, Scald, Steam Eruption</li>
     *              <li>Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability</li>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyFreeze(){
        if(!(this.type1.equals("ICE")) && !(this.type2.equals("ICE")) && !(this.hiddenAbility.equals("MAGMAARMOR"))){
            this.statusEffect = 2;
        }
    }
    public void healFreeze(){
        this.statusEffect = 0;
    }

    /**
     * Paralysis.<br>
     * Paralysis is one of the more commonly used status afflictions. It is able easily to immobolize your foe and give you the upper hand.
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon afflicted's Speed stat is reduced to 25% of it's Maximum. Pokémon with the Quick Feet ability are not affected</li>
     *              <li>The Pokémon has a 25% chance of being unable to attack each turn</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Pokémon with the Limber ability</li>
     *              <li>Electric-type Pokémon</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability</li>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">Serebii</a>
     * */
    public void applyParalysis(){
        if(!(this.hiddenAbility.equals("LIMBER")) && !(this.type1.equals("ELECTRIC")) && !(this.type2.equals("ELECTRIC"))){
            this.statusEffect = 3;
            if(this.baseStats.get("speed") == this.maxStats.get("speed"))
                this.baseStats.put("speed", (int)Math.ceil(this.baseStats.get("speed") * 0.25));
        }
    }
    public void healParalysis(){
        this.statusEffect = 0;
        this.baseStats.put("speed", this.maxStats.get("speed"));
    }

    /**
     * Poison<br>
     * Poison is another commonly utilised status affliction. It gradually lowers the Pokémon's Hit Points until the Pokémon fains
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon loses 1/8th Max HP each turn</li>
     *              <li>For every 4 steps the trainer takes, the Pokémon loses 1 HP until it reaches 1 HP remaining</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Poison Type &amp; Steel Type Pokémon</li>
     *              <li>Pokémon with the Immunity ability</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability</li>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyPoison(){
        this.statusEffect = 4;
    }
    public void doPoison(){
        if(this.statusEffect == 4){
            if(!(this.type1.equals("POISON")) && !(this.type2.equals("POISON")) && !(this.type1.equals("STEEL")) && !(this.type2.equals("STEEL")) && !(this.hiddenAbility.equals("IMMUNITY"))){
                this.baseStats.put("hp", (int)Math.floor(this.baseStats.get("hp") - (this.maxStats.get("hp") * (0.875))));
            }
        }
    }
    public void healPoison(){
        this.statusEffect = 0;
    }

    /**
     * Badly Poisoned<br>
     * Badly Poisoned acts like Poison in the same manner, however the effects it gives is cumulative. It only lasts in the battle it was afflicted in, in which it reverts to normal poison
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon loses 1/16th Max HP for the first turn and then adds 1/16th to the amount to be lost so on 2nd turn 2/16th, 3rd 3/16th and so on until the Pokémon faints</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>See Poison</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>See Poison</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyBadlyPoison(){
        this.statusEffect = 5;
    }
    public void doBadlyPoison(){
        if(this.statusEffect == 5){
            if(!(this.type1.equals("POISON")) && !(this.type2.equals("POISON")) && !(this.type1.equals("STEEL")) && !(this.type2.equals("STEEL")) && !(this.hiddenAbility.equals("IMMUNITY"))){
                this.baseStats.put("hp", (int) Math.floor(this.baseStats.get("hp") - ((this.maxStats.get("hp") * (0.9375)) * this.counters.get("badlypoision"))));
                this.counters.put("badlypoison", this.counters.get("badlypoison") + 1);
            }
        }
    }
    public void healBadlyPoison(){
        this.statusEffect = 0;
        this.counters.put("badlypoison", 1);
    }

    /**
     * Sleep<br>
     * Sleep is the primary used status affliction as it is utilised in conjunction with a healing move, however it is also good at stopping your opponent in it's tracks
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon cannot attack for 1 to 7 turns, the turn count is lowered with the Early Bird ability</li>
     *              <li>Sleep Talk &amp; Snore can be used</li>
     *              <li>Allows the attacks Dream Eater &amp; Nightmare as well as the ability Bad Dreams to be used against you</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Pokémon with the Insomnia &amp; Vital Spirit abilities</li>
     *              <li>All Pokémon when Electric Terrain is in effect</li>
     *              <li>Partner Pokémon if a Pokémon has Sweet Veil</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Being a Pokémon with the Natural Cure ability and switching out, Hydration while its raining or having the Shed Skin ability</li>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *              <li>Using the attack Wake-Up Slap</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applySleep(){
        if(!(this.hiddenAbility.equals("INSOMNIA")) && !(this.hiddenAbility.equals("VITALSPIRIT"))){
            Random r = new Random();
            this.statusEffect = 6;
            this.counters.put("sleep", r.nextInt(6)+1);
        }
    }
    public void healSleep(){
        this.statusEffect = 0;
    }

    /**
     * Attract<br>
     * Attraction is a status affliction that only occurs a few times. It requires the user to have a gender and the opponent to have a differing gender to work
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon afflicted cannot attack 50% of the time</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Pokémon with the Oblivious ability</li>
     *              <li>Pokémon of the same gender as the user</li>
     *              <li>Genderless Pokémon</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
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
     * Confusion<br>
     * Confusion is another status effect that is common in use to hinder your opponent for 1 to 4 turns
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon afflicted cannot attack 50% of the time for 1-4 turns</li>
     *              <li>Raises Evasion for Pokémon with the Tangled Feet ability</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>Pokémon with the Own Tempo ability</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Using the attacks Heal Bell or Aromatherapy</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyConfusion(){
        if(!(this.hiddenAbility.equals("OBLIVIOUS"))){
            Random r = new Random();
            this.statusEffect = 8;
            this.counters.put("confusion", r.nextInt(3)+1);
        }
    }
    public void doConfusion(){
        if(this.statusEffect == 8){
            this.baseStats.put("hp", (int) Math.floor(this.baseStats.get("hp") - ((this.maxStats.get("hp") * 0.05))));
            if(this.hiddenAbility.equals("TANGLEDFEET")){
                if(this.statStages.get("evasion") != 6){
                    this.baseStats.put("evasion", this.baseStats.get("evasion")+1);
                }
            }
        }
    }
    public void healConfusion(){
        this.statusEffect = 0;
        this.baseStats.put("evasion", 0);
    }

    /**
     * Curse<br>
     * Curse is a seldom used affliction. Partly because the affliction only occurs when the attack has been used by a Ghost Type Pokémon
     * <ul>
     *     <li>Effects:</li>
     *     <li>
     *         <ul>
     *              <li>The Pokémon afflicted loses 1/4 of it's Max HP each turn</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Immunities:</li>
     *     <li>
     *         <ul>
     *              <li>None</li>
     *         </ul>
     *     </li>
     * </ul>
     * <ul>
     *     <li>Methods of Healing:</li>
     *     <li>
     *         <ul>
     *              <li>Switching Out</li>
     *         </ul>
     *     </li>
     * </ul>
     *  @see <a href="http://www.serebii.net/games/status.shtml">http://www.serebii.net/games/status.shtml</a>
     * */
    public void applyCurse(){
        this.statusEffect = 9;
    }
    public void doCurse(){
        if(this.statusEffect == 9){
            this.baseStats.put("hp", (int) Math.floor(this.baseStats.get("hp") - ((this.maxStats.get("hp") * 0.25))));
        }
    }
    public void healCurse(){
        this.statusEffect = 0;
    }

    /**
     * Flinch<br>
     * Flinching is when a Pokémon becomes unable to attack for one turn.
     * */
    public void applyFlinch(){
        this.setFlag("flinched", 1);
    }

    /**
     * Minimize
     * */
    public void applyMinimize(){
        this.setFlag("minimized", 1);
        this.setBaseStat("evasion", (this.baseStats.get("evasion") + 2));
    }
    public void clearMinimize(){
        this.setFlag("minimized", 0);
        this.setBaseStat("evasion", (this.baseStats.get("evasion") - 2));
    }

    /**
     * SafeGuard
     * */
    public void applySafeGuard(){
        this.setFlag("safeguarded", 1);
        this.counters.put("safeguard", 5);
    }
    public void doSafeGuard(){
        this.counters.put("safeguard", (this.counters.get("safeguard")-1));
    }
    public void clearSafeGuard(){
        this.setFlag("safeguarded", 0);
    }

    /**
     * Bonus Accuracy
     * */
    public void addAccuracy(){
        this.baseStats.put("accuracy", (this.baseStats.get("accuracy") + 10));
    }
    public void resetAccuracy(){
        this.baseStats.put("accuracy", 0);
    }

    /**
     * Bonus Crit
     * */
    public void resetCrit(){
        this.baseStats.put("crit", 6);
    }
}
