package org.minvas.dragonsofmugloar.game.model;

import org.minvas.dragonsofmugloar.client.dto.Game;

public record GameState(
        String id,
        int lives,
        int score,
        int gold,
        int turn,
        int level
) {
    public static class Builder {
        private String gameId;
        private int lives;
        private int gold;
        private int score;
        private int turn;
        private int level;

        public static Builder from(Game game) {
            Builder gameBuilder = new Builder();
            return gameBuilder.gameId(game.gameId())
                    .lives(game.lives())
                    .gold(game.gold())
                    .score(game.score())
                    .turn(game.turn())
                    .level(game.level());
        }

        public static Builder from(GameState gameState) {
            Builder gameBuilder = new Builder();
            return gameBuilder.gameId(gameState.id())
                    .lives(gameState.lives())
                    .gold(gameState.gold())
                    .score(gameState.score())
                    .turn(gameState.turn())
                    .level(gameState.level());
        }

        public Builder gameId(String gameId) {
            this.gameId = gameId;
            return this;
        }

        public Builder lives(int lives) {
            this.lives = lives;
            return this;
        }

        public Builder gold(int gold) {
            this.gold = gold;
            return this;
        }

        public Builder score(int score) {
            this.score = score;
            return this;
        }

        public Builder turn(int turn) {
            this.turn = turn;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public GameState build() {
            return new GameState(gameId, lives, score, gold, turn, level);
        }
    }
}
