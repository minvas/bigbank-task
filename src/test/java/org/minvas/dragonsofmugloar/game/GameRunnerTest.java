package org.minvas.dragonsofmugloar.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.game.logic.GameLogic;

import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameRunnerTest {

    private GameRunner gameRunner;
    private GameLogic gameLogic;

    @BeforeEach
    public void setup() {
        gameLogic = mock(GameLogic.class);
        gameRunner = new GameRunner(gameLogic);
    }

    @Test
    void Verify_game_lifecycle() {
        when(gameLogic.isEnded()).thenReturn(false, false, false, true);

        gameRunner.run(null);

        verify(gameLogic, times(1)).startGame();
        verify(gameLogic, times(3)).takeTurn();
        verify(gameLogic, times(4)).isEnded();
    }
}
