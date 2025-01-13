
## How to use

```java
main () {
    LastGamesReader reader = new LastGamesReader("7065948116e3cc02bf56d5f5e3d5745796377c64");
    Stats stats = new Stats(reader.games(), game -> new W24Stat(game));
    ...
}

public class W24Stat extends Stat {
    ....
}
```` 

then output stats : 

```java
    stats.printWinLoseStats( ...);

    stats.printStat( ... );
````