package com.montaury.citadels;

import com.montaury.citadels.player.HumanController;
import com.montaury.citadels.player.ComputerController;
import com.montaury.citadels.player.Player;
import io.vavr.collection.List;

import java.util.Scanner;

public class SoloGameConfigurator {

    public Game newGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Quel est votre nom ? ");
        String playerName = scanner.next();
        System.out.println("Quel est votre age ? ");
        int playerAge = scanner.nextInt();
        Board board = new Board();
        List<Player> players = List.of(new Player(playerName, playerAge, new City(board), new HumanController()));
        int playerNumbers = askNumberOfPlayers(scanner);
        players = players.appendAll(List.range(0, playerNumbers-1)
                .map(i -> new Player("Computer " + i, 35, new City(board), new ComputerController())));
        return new Game(players);
    }

    private static int askNumberOfPlayers(Scanner scanner) {
        System.out.println("Saisir le nombre de joueurs total (entre 2 et 8): ");
        int numberOfPlayers;
        do {
            numberOfPlayers = scanner.nextInt();
        } while(numberOfPlayers < MINIMUM_PLAYERS || numberOfPlayers > MAXIMUM_PLAYERS);
        return numberOfPlayers;
    }

    private static final int MINIMUM_PLAYERS = 2;
    private static final int MAXIMUM_PLAYERS = 8;
}
