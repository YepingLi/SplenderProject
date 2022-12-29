package org.mcgill.splendorapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Card;
import org.mcgill.splendorapi.model.Game;
import org.mcgill.splendorapi.model.GameBoard;
import org.mcgill.splendorapi.model.GamePlayer;
import org.mcgill.splendorapi.model.Gem;
import org.mcgill.splendorapi.model.exceptions.NoChangeFoundException;
import org.mcgill.splendorapi.model.exceptions.NoGameFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * Loader service, only service which interacts with the FS.
 * Encapsulates the FS interaction.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GameLoaderService {
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final Map<String, Game> loadedGames = new HashMap<>();
  private final AppProperties properties;

  /**
   * Load the game from the disk.
   *
   * @param gameId    Game id
   * @param sessionId The session id
   * @throws IOException          If we cannot find the game
   * @throws InvalidPathException If the path is invalid.
   */
  private synchronized void loadGameFromDisk(String sessionId, String gameId)
      throws IOException, InvalidPathException, IllegalAccessException {
    if (gameId.equals("")) {
      throw new IllegalAccessException("Cannot load file with no name");
    }

    Path path = Paths.get(
        properties.getAbsPathToGames().toString(),
        String.format("%s.json", gameId)
    );

    BufferedReader buffered = new BufferedReader(new FileReader(path.toFile()));
    StringBuilder fileAsString = new StringBuilder();
    String line;

    while ((line = buffered.readLine()) != null) {
      fileAsString.append(line);
    }

    buffered.close();
    Game game = mapper.readValue(fileAsString.toString(), Game.class);
    loadedGames.put(sessionId, game);
  }

  /**
   * Loads a game from the file system.
   *
   * @param id Game id
   * @return A game object or fails to open it
   * @throws InvalidPathException Invalid path (Should be thrown only if it is misconfigured)
   */
  public synchronized Game getGame(String id)
      throws IllegalArgumentException {
    return Optional.ofNullable(loadedGames.get(id))
               .orElseThrow(
                   () -> new IllegalArgumentException(String.format("No game of id %s", id)));
  }

  /**
   * Save the game to the disk.
   *
   * @param game the game
   * @throws IOException During saving
   */
  public synchronized void saveGame(Game game) throws IOException {
    String randomId = UUID.randomUUID()
                          .toString()
                          .substring(0, properties.getGameIdLength())
                          .replace("-", "");

    Path path = Paths.get(
        properties.getAbsPathToGames().toString(),
        String.format("%s.json", randomId)
    );

    BufferedWriter buffered = new BufferedWriter(new FileWriter(path.toFile()));
    buffered.write(mapper.writeValueAsString(game));
    buffered.flush();
    buffered.close();
  }

  /**
   * Saves the game.
   *
   * @param gameId Game id
   * @throws IOException          Cannot find the file.
   * @throws InvalidPathException Invalid path (Should be thrown only if it is misconfigured).
   */
  public synchronized void saveGame(String gameId, String user)
      throws InvalidPathException, IllegalAccessException, NoGameFoundException, IOException {
    Game game = Optional.ofNullable(loadedGames.get(gameId))
                    .orElseThrow(() -> new NoGameFoundException(gameId))
                    .checkUserRights(user);
    saveGame(game);
  }

  /**
   * Create a new board and start the game.
   *
   * @param id   The game id
   * @param game The game (in its current form)
   */
  private synchronized void startGame(String id, @NotNull Game game) {
    loadedGames.put(id, game);
    // Build the deck, player objects, etc
    List<Gem> gems =  Arrays.stream(Gem.Type.values()).map(gemType -> {
      String idBuilder = "%s-%d";
      return IntStream.range(0, gemType.getMaxNumber(game.getPlayers().size()))
                 .mapToObj(intValue -> Gem.builder()
                                           .id(String.format(
                                               idBuilder,
                                               gemType.name(),
                                               intValue)
                                           )
                                           .type(gemType)
                                           .isAvailable(true).build())
                 .collect(Collectors.toList());
    }).flatMap(List::stream).collect(Collectors.toList());

    List<GamePlayer> players = game.getPlayers()
                                   .stream()
                                   .map(player -> GamePlayer.builder().name(player.getName())
                                                      .build())
                                   .collect(Collectors.toList());
    // Adds three gems to each player
    players.forEach(player -> {
      List<Gem> myGems = gems.stream()
                             .filter(Gem::isAvailable)
                             .limit(3)
                             .peek(Gem::purchase)
                             .collect(Collectors.toList());
      player.addGems(myGems);
    });

    // Generate the cards
    List<Card> cards = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      Card card;
      Bonus bonus;
      Map<Gem.Type, Integer> cost = new HashMap<>();
      switch (i % 3) {
        case 2:
          bonus = Bonus.builder().bonusType(Bonus.BonusType.EMERALD).totalBonus(1).build();
          cost.put(Gem.Type.DIAMOND, 2);
          cost.put(Gem.Type.SAPPHIRE, 1);
          card = new Card(1, i, bonus, 0, "card3", cost);
          break;
        case 1:
          bonus = Bonus.builder().bonusType(Bonus.BonusType.EMERALD).totalBonus(1).build();
          cost.put(Gem.Type.RUBY, 2);
          cost.put(Gem.Type.SAPPHIRE, 2);
          card = new Card(1, i, bonus, 0, "card4", cost);
          break;
        default:
          bonus = Bonus.builder().bonusType(Bonus.BonusType.DIAMOND).totalBonus(1).build();
          cost.put(Gem.Type.RUBY, 3);
          card = new Card(1, i, bonus, 1, "card6", cost);
      }
      cards.add(card);
    }

    GameBoard board = GameBoard.builder()
                          .players(players)
                          .curPlayer(players.get(0))
                          .deck(cards)
                          .build();
    game.startGame(board);
  }

  /**
   * Create a new board and start the game.
   *
   * @param id   The game id
   * @param game The game (in its current form)
   * @throws IOException            Loading the game
   * @throws IllegalAccessException Player not having access
   */
  public synchronized void startOrLoadGame(String id, @NotNull Game game)
      throws IOException, IllegalAccessException {
    if (!game.getSaveGame().equals("")) {
      loadGameFromDisk(id, game.getSaveGame());
      log.debug("loaded the game");
    } else {
      startGame(id, game);
      log.debug("Started new game with id {}", id);
    }
  }

  /**
   * Handles the checking of the game hash.
   *
   * @param board    The board reqeusted
   * @param gameHash The player game has as seen by the player
   * @param id       The id of the game
   * @return The board
   * @throws JsonProcessingException  During creation as string
   * @throws InterruptedException     While waiting, receive interrupt
   * @throws NoChangeFoundException   If no change is found after waiting for alloted timeout,
   *                                  raised.
   */
  private GameBoard checkGameHash(GameBoard board, String gameHash, String id)
      throws JsonProcessingException, InterruptedException, NoChangeFoundException {
    String md5 = Arrays.toString(DigestUtils.md5Digest(mapper.writeValueAsBytes(board)));
    if (md5.equals(gameHash)) {
      board.wait(properties.getMaxTimeout());
    }
    // If it is still the same (after timeout), then no update was found. Fail (ending the thread)
    if (md5.equals(gameHash)) {
      throw NoChangeFoundException.builder().id(id).build();
    }

    return board;
  }

  /**
   * Get the game board based on the current users game hash.
   *
   * @param id       The id of the game
   * @param user     The user requesting
   * @param gameHash The game hash
   * @return The game board if there was an update
   * @throws IOException            Loading the game
   * @throws IllegalAccessException Player cannot access the game
   * @throws InterruptedException   During waiting
   * @throws NoChangeFoundException The game has not changed during the time we were waiting
   */
  public synchronized GameBoard getBoard(String id, String user, String gameHash)
      throws IOException, IllegalAccessException, InterruptedException, NoChangeFoundException {
    return checkGameHash(getGame(id).checkUserRights(user).getBoard(), gameHash, id);
  }
}
