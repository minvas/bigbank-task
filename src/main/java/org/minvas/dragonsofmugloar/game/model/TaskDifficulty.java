package org.minvas.dragonsofmugloar.game.model;

public enum TaskDifficulty {
    SURE_THING("Sure thing", 1),
    PIECE_OF_CAKE("Piece of cake", 10),
    WALK_IN_THE_PARK("Walk in the park", 10),
    QUITE_LIKELY("Quite likely", 20),
    GAMBLE("Gamble", 40),
    HMMM("Hmmm....", 40),
    RISKY("Risky", 50),
    RATHER_DETRIMENTAL("Rather detrimental", 60),
    PLAYING_WITH_FIRE("Playing with fire", 70),
    SUICIDE_MISSION("Suicide mission", 80),
    IMPOSSIBLE("Impossible", 90),
    UNKNOWN(null, 100);

    private final String probabilityDescription;
    private final int level;

    TaskDifficulty(String probabilityDescription, int level) {
        this.probabilityDescription = probabilityDescription;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getProbabilityDescription() {
        return probabilityDescription;
    }

    public static TaskDifficulty fromProbabilityDescription(String probabilityDescription) {
        for (TaskDifficulty probability : values()) {
            if (probability.probabilityDescription != null && probability.probabilityDescription.equalsIgnoreCase(probabilityDescription)) {
                return probability;
            }
        }
        return UNKNOWN;
    }
}
