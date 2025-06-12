# Dragons of Mugloar

## Game lifecycle

_GameRunner_ is responsible for orchestrating the main game loop when the application starts.
Upon execution, it initializes the game and then repeatedly processes game turns until the game ends (no lives left).

## Turn rule engine

On each turn decision is made what action to take based on the predefined rules.
Rules can be specified through application properties by listing rule IDs.
Rule order matters - if rule is applicable, all further rules (if exist) are ignored.

## Running the game

To build and run the application locally with a single command, use:

```sh
./mvnw spring-boot:run
```