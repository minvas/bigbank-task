package org.minvas.dragonsofmugloar.game.model;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TaskDifficultyTest {

    @ParameterizedTest
    @CsvSource(value = {
            "SURE_THING, 1",
            "PIECE_OF_CAKE, 10",
            "WALK_IN_THE_PARK, 10",
            "QUITE_LIKELY, 20",
            "GAMBLE, 40",
            "HMMM, 40",
            "RISKY, 50",
            "RATHER_DETRIMENTAL, 60",
            "PLAYING_WITH_FIRE, 70",
            "SUICIDE_MISSION, 80",
            "IMPOSSIBLE, 90",
            "UNKNOWN, 100"
    })
    void Task_difficulty_level_has_expected_value(TaskDifficulty taskDifficulty, int expectedLevel) {
        assertSame(expectedLevel, taskDifficulty.getLevel());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Piece of cake, PIECE_OF_CAKE",
            "Walk in the park, WALK_IN_THE_PARK,",
            "Sure thing, SURE_THING",
            "Quite likely, QUITE_LIKELY",
            "Gamble, GAMBLE",
            "Hmmm...., HMMM",
            "Risky, RISKY",
            "Rather detrimental, RATHER_DETRIMENTAL",
            "Playing with fire, PLAYING_WITH_FIRE",
            "Suicide mission, SUICIDE_MISSION",
            "Impossible, IMPOSSIBLE"
    })
    void Task_difficulty_is_determined_from_known_probability_description(String probabilityDescription,
                                                                          TaskDifficulty expectedTaskDifficulty) {
        TaskDifficulty taskDifficulty = TaskDifficulty.fromProbabilityDescription(probabilityDescription);
        assertSame(expectedTaskDifficulty, taskDifficulty);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "asdf"})
    void Task_difficulty_is_determined_from_unknown_probability_description(String probabilityDescription) {
        TaskDifficulty taskDifficulty = TaskDifficulty.fromProbabilityDescription(probabilityDescription);
        assertSame(TaskDifficulty.UNKNOWN, taskDifficulty);
    }
}
