package hu.experiment_team;

import java.util.ArrayList;
import java.util.List;

public enum SpellScripts {
    GET;

    private List<String> burnSpells = new ArrayList<String>(){{
        add("BLAZEKICK");
        add("BLUEFLARE");
        add("EMBER");
        add("FIREBLAST");
        add("FIREFANG");
        add("FIREPUNCH");
        add("FLAMEWHEEL");
        add("FLAMETHROWER");
        add("HEATWAVE");
        add("ICEBURE");
        add("INFERNO");
        add("LAVAPLUME");
        add("SACREDFIRE");
        add("SCALD");
        add("SEARINGSHOT");
        add("TRIATTACK");
        add("WILLOWISP");
    }};
    private List<String>freezeSpells = new ArrayList<String>(){{
        add("BLIZZARD");
        add("FREEZEDRY");
        add("ICEBEAM");
        add("ICEFANG");
        add("ICEPUNCH");
        add("POWDERSNOW");
        add("SECRETPOWER");
        add("TRIATTACK");
    }};
    private List<String>paralysisSpells = new ArrayList<String>(){{
        add("BODYSLAM");
        add("BOLTSTRIKE");
        add("BOUNCE");
        add("DISCHARGE");
        add("FORCEPALM");
        add("FREEZESHOCK");
        add("GLARE");
        add("LICK");
        add("NUZZLE");
        add("SECRETPOWER");
        add("SPARK");
        add("STUNSPORE");
        add("THUNDER");
        add("THUNDERFANG");
        add("THUNDERWAVE");
        add("THUNDERBOLT");
        add("TUNDERPUNCH");
        add("THUNDERSHOCK");
        add("TRIATTACK");
        add("ZAPCANNON");
    }};
    private List<String>poisonSpells = new ArrayList<String>(){{
        add("CROSSPOISON");
        add("GUNKSHOT");
        add("POISONGAS");
        add("POISONJAB");
        add("POISONSTING");
        add("POISONTAIL");
        add("POISONPOWDER");
        add("SECRETPOWER");
        add("SLUDGE");
        add("SLUDGEBOMB");
        add("SLUDGEWAVE");
        add("SMOG");
        add("TOXICSPIKES");
        add("TWINEEDLE");
    }};
    private List<String>badlyPoisonSpells = new ArrayList<String>(){{
        add("POISONFANG");
        add("TOXIC");
        add("TOXICSPIKES");
    }};
    private List<String>sleepSpells = new ArrayList<String>(){{
        add("DARKVOID");
        add("GRASSWHISTLE");
        add("HYPNOSIS");
        add("LOVELYKISS");
        add("RELICSONG");
        add("REST");
        add("SING");
        add("SLEEPPOWER");
        add("SPORE");
        add("YAWN");
    }};
    private List<String>attractSpells = new ArrayList<String>(){{
        add("ATTRACT");
    }};
    private List<String>confusionSpells = new ArrayList<String>(){{
        add("CHATTER");
        add("CONFUSERAY");
        add("CONFUSION");
        add("DIZZYPUNCH");
        add("DYNAMICPUNCH");
        add("FLATTER");
        add("HURRICANE");
        add("PSYBEAM");
        add("ROCKCLIMB");
        add("SIGNALBEAM");
        add("SUPERSONIC");
        add("SWAGGER");
        add("SWEETKISS");
        add("TEETERDANCE");
        add("WATERPULSE");
        add("OUTRAGE");
        add("PETALDANCE");
        add("THRASH");
    }};
    private List<String>curseSpells = new ArrayList<String>(){{
        add("CURSE");
    }};

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Burn debuffot.
     * */
    public List<String> BurnSpells() {
        return burnSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Freeze debuffot.
     * */
    public List<String> FreezeSpells() {
        return freezeSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Paralysis debuffot.
     * */
    public List<String> ParalysisSpells() {
        return paralysisSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Poision debuffot.
     * */
    public List<String> PoisonSpells() {
        return poisonSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Badly Poision debuffot.
     * */
    public List<String> BadlyPoisonSpells() {
        return badlyPoisonSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Sleep debuffot.
     * */
    public List<String> SleepSpells() {
        return sleepSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Attract debuffot.
     * */
    public List<String> AttractSpells() {
        return attractSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Confusion debuffot.
     * */
    public List<String> ConfusionSpells() {
        return confusionSpells;
    }

    /**
     * @return Azon képességek listája, amelyek képesek felhelyezni a Curse debuffot.
     * */
    public List<String> CurseSpells() {
        return curseSpells;
    }
}
