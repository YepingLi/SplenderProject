package org.mcgill.splendorapi.api.move.generators;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.move.model.TokenTakingMove;
import org.mcgill.splendorapi.model.Bonus;
import org.mcgill.splendorapi.model.Game;

import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.Move;
import org.mcgill.splendorapi.model.Player;
import org.mcgill.splendorapi.model.PowerType;
import org.mcgill.splendorapi.model.board.GameBoard;
import org.mcgill.splendorapi.model.board.OrientGameBoard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class TakeTokenTest {
  GameBoard testBoard;
  Player testPlayer1;
  Game testGame;

  private static Comparator<Move> compare() {
    return (Move x, Move y) -> {
      TokenTakingMove t1 = (TokenTakingMove) x;
      TokenTakingMove t2 = (TokenTakingMove) y;
      return t1.getExchangeGems().containsAll(t2.getExchangeGems()) ? 0: -1;
    };
  }

  @BeforeEach
  public void setup() throws InvalidCardType {
    testPlayer1 = Player.builder()
      .powers(new ArrayList<>()).gems(Map.of(GemType.ONYX, 0, GemType.DIAMOND, 0, GemType.RUBY, 0,
                                               GemType.GOLD, 0, GemType.EMERALD, 0,  GemType.SAPPHIRE, 0)).build();
  }


  @Test
  void buildMove_NoMove() throws InvalidCardType {
    testBoard = GameBoard.builder().gems(Map.of()).nobles(List.of()).deck(List.of()).build();
    testGame = Game.builder().board(testBoard).curPlayer(testPlayer1).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> testList1 = testTakeTokenService.buildMoves();
    assertTrue(testList1.isEmpty());

  }

  @Test
  void buildMove_Single() throws InvalidCardType {
    testBoard = GameBoard.builder().gems(Map.of(GemType.ONYX, 1, GemType.DIAMOND, 4)).nobles(List.of()).deck(List.of()).build();
    testGame = Game.builder().board(testBoard).curPlayer(testPlayer1).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> result = testTakeTokenService.buildMoves();
    assertThat(result).hasSize(1)
                      .usingElementComparator(compare())
                      .hasSameElementsAs(List.of(new TokenTakingMove(List.of(GemType.DIAMOND,
                                                                             GemType.DIAMOND))));
  }

  @Test
  void buildMove_TestMultiple() throws InvalidCardType {
    testBoard = GameBoard.builder()
                         .gems( Map.of(GemType.ONYX, 1, GemType.DIAMOND, 4, GemType.RUBY, 1))
                         .nobles(List.of()).deck(List.of()).build();
    testGame = Game.builder().board(testBoard).curPlayer(testPlayer1).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> results = testTakeTokenService.buildMoves();

    List<Move> expected = List.of(new TokenTakingMove(List.of(GemType.DIAMOND,
                                                              GemType.DIAMOND)),
                                  new TokenTakingMove(List.of(GemType.DIAMOND,
                                                              GemType.ONYX,
                                                              GemType.RUBY)),
                                  new TokenTakingMove(List.of(GemType.ONYX,
                                                              GemType.DIAMOND,
                                                              GemType.RUBY)),
                                  new TokenTakingMove(List.of(GemType.RUBY,
                                                              GemType.ONYX,
                                                              GemType.DIAMOND)),
                                  new TokenTakingMove(List.of(GemType.ONYX,
                                                              GemType.RUBY,
                                                              GemType.DIAMOND)),
                                  new TokenTakingMove(List.of(GemType.DIAMOND,
                                                              GemType.RUBY,
                                                              GemType.ONYX)),
                                  new TokenTakingMove(List.of(GemType.RUBY,
                                                              GemType.DIAMOND,
                                                              GemType.ONYX)));
    assertThat(results).hasSize(7)
                       .usingElementComparator(compare())
                       .hasSameElementsAs(expected);
  }
  @Test
  void buildMove_GOLD() throws InvalidCardType {
    testBoard = GameBoard.builder()
                         .gems(Map.of(GemType.GOLD, 2))
                         .nobles(List.of()).deck(List.of()).build();
    testGame = Game.builder().board(testBoard).curPlayer(testPlayer1).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> testList1 = testTakeTokenService.buildMoves();

    assertThat(testList1).hasSize(1)
                         .usingElementComparator(compare())
                         .hasSameElementsAs(List.of(new TokenTakingMove(List.of(GemType.GOLD))));
  }

  @Test
  void buildMove_TWO_AND_ONE() throws InvalidCardType {
    testBoard = GameBoard.builder()
                         .gems( Map.of(GemType.ONYX, 1, GemType.RUBY, 4))
                         .nobles(List.of()).deck(List.of()).build();
    List<PowerType> powerTypeList = new ArrayList<>();
    powerTypeList.add(PowerType.TWO_AND_ONE);
    Player testPlayer2 = Player.builder()
                               .bonuses(Map.of(Bonus.DIAMOND, 2))
                               .powers(powerTypeList)
                                .gems(new HashMap<>())
                               .build();

    testGame = Game.builder().board(testBoard).curPlayer(testPlayer2).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> testList1 = testTakeTokenService.buildMoves();

    List<Move> expected = List.of(new TokenTakingMove(List.of(GemType.RUBY,
                                                              GemType.RUBY)),
                                  new TokenTakingMove(List.of(GemType.ONYX,
                                                              GemType.RUBY,
                                                              GemType.RUBY)));

    assertThat(testList1).hasSize(2)
                       .usingElementComparator(compare())
                       .hasSameElementsAs(expected);
  }

  @Test
  void cascadeTest() throws InvalidCardType {
    testBoard = GameBoard.builder()
                         .gems( Map.of(GemType.ONYX, 1, GemType.RUBY, 4))
                         .nobles(List.of()).deck(List.of()).build();
    List<PowerType> powerTypeList = new ArrayList<>();
    powerTypeList.add(PowerType.TWO_AND_ONE);
    Player testPlayer2 = Player.builder()
                               .bonuses(Map.of(Bonus.DIAMOND, 10))
                               .powers(powerTypeList)
                               .gems(Map.of(GemType.DIAMOND, 10))
                               .build();

    testGame = Game.builder().board(testBoard).curPlayer(testPlayer2).build();

    TakeToken testTakeTokenService = new TakeToken(testGame);
    List<Move> testList1 = testTakeTokenService.buildMoves();
    assertEquals(9, testList1.size());
  }
}