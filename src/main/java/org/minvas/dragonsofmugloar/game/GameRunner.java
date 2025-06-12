package org.minvas.dragonsofmugloar.game;

import org.minvas.dragonsofmugloar.game.logic.GameLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GameRunner implements ApplicationRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRunner.class);

    private final GameLogic gameLogic;

    public GameRunner(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("Started running a game");

        gameLogic.startGame();
        while (!gameLogic.isEnded()) {
            gameLogic.takeTurn();
        }

        LOGGER.info("Ended running a game");
    }
}
