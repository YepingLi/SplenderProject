package org.mcgill.splendorapi.api.move;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.api.move.model.BuyCardMove;
import org.mcgill.splendorapi.api.move.model.LevelOnePairingMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2BaseMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2CascadeMove;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2Move;
import org.mcgill.splendorapi.api.move.model.LevelThreePairingL2NobleMove;
import org.mcgill.splendorapi.api.move.model.LevelTwoNobleMove;
import org.mcgill.splendorapi.api.move.model.LevelTwoPairingL1Move;
import org.mcgill.splendorapi.api.move.model.LevelTwoPairingMove;
import org.mcgill.splendorapi.api.move.model.MoveDto;
import org.mcgill.splendorapi.api.move.model.ReserveDevCardMove;
import org.mcgill.splendorapi.api.move.model.TokenTakingCascadeMove;
import org.mcgill.splendorapi.api.move.model.TokenTakingMove;
import org.mcgill.splendorapi.model.Action;
import org.mcgill.splendorapi.model.ActionType;
import org.mcgill.splendorapi.model.Move;
import org.springframework.stereotype.Component;

/**
 * Map the move objects to their move Dto's which are represented by a list of actions.
 */
@Component
public class MoveMapper {

  /**
   * Takes in a Card Reserving move and splits it into a list of actions.
   *
   * @param move the original Card Reserving Move.
   * @return the MoveDto object which is a list of actions.
   */
  public MoveDto map(ReserveDevCardMove move) {
    List<Action<?>> actions = new ArrayList<>();
    //PART 1: Add the reserve Card action:
    actions.add(new Action<>(ActionType.RESERVE_CARD, move.getCard().getMeta()));

    //TODO: TO BE TESTED!!

    //PART 2: If the move required the burning of a gem, add the action:
    if (move.getGem() != null) {
      actions.add(new Action<>(ActionType.BURN_TOKEN, move.getGem()));
    }
    return new MoveDto(actions);
  }


  /**
   * Takes in a Card Purchasing move and splits it into a list of actions.
   *
   * @param move the original Card Purchasing Move.
   * @return the MoveDto object which is a list of actions.
   */
  public MoveDto map(BuyCardMove move) {
    List<Action<?>> actions = new ArrayList<>();
    //1. Adds the purchased Card action.
    actions.add(new Action<>(ActionType.BUY_CARD, move.getCard().getMeta()));

    //2. If the Player specified their payment, then add an action for each gem in the payment.

    Optional.ofNullable(move.getPayment())
            .ifPresent(pay -> actions.add(new Action<>(ActionType.PAYMENT,
                                                       move.getPayment())));

    Optional.ofNullable(move.getNobleChoice())
            .ifPresent(pay -> actions.add(new Action<>(ActionType.CLAIM_NOBLE,
                                                       move.getNobleChoice().getMeta().getId())));

    Optional.ofNullable(move.getFreeGemOnPurchase())
            .ifPresent(pay -> actions.add(new Action<>(ActionType.TAKE_TOKEN,
                                                       move.getFreeGemOnPurchase())));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelOnePairingMove.
   *
   * @param move LevelOnePairingMove
   * @return moveDto
   */
  public MoveDto map(LevelOnePairingMove move) {
    List<Action<?>> actions = map((BuyCardMove) move).getActions();
    actions.add(new Action<>(ActionType.LEVEL_ONE_PAIR, move.getPairing()));
    return new MoveDto(actions);
  }


  /**
   * maps a LevelThreePairingL2BaseMove.
   *
   * @param move LevelThreePairingL2BaseMove
   * @return moveDto
   */
  public MoveDto map(LevelThreePairingL2BaseMove move) {
    List<Action<?>> actions = map((BuyCardMove) move).getActions();
    actions.add(new Action<>(ActionType.TAKE_LEVEL_TWO, move.getLevelTwoCascade().getMeta()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelThreePairingL2CascadeMove.
   *
   * @param move LevelThreePairingL2CascadeMove
   * @return moveDto
   */
  public MoveDto map(LevelThreePairingL2CascadeMove move) {
    List<Action<?>> actions = map((LevelTwoPairingL1Move) move).getActions();
    actions.add(new Action<>(ActionType.TAKE_LEVEL_TWO, move.getLevelTwoCascade().getMeta()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelThreePairingL2Move.
   *
   * @param move LevelThreePairingL2Move
   * @return moveDto
   */
  public MoveDto map(LevelThreePairingL2Move move) {
    List<Action<?>> actions = map((LevelTwoPairingMove) move).getActions();
    actions.add(new Action<>(ActionType.TAKE_LEVEL_TWO, move.getLevelTwoCascade().getMeta()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelThreePairingL2NobleMove.
   *
   * @param move LevelThreePairingL2NobleMove
   * @return moveDto
   */
  public MoveDto map(LevelThreePairingL2NobleMove move) {
    List<Action<?>> actions = map((LevelTwoNobleMove) move).getActions();
    actions.add(new Action<>(ActionType.TAKE_LEVEL_TWO, move.getLevelTwoCascadeCard().getMeta()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelTwoNobleMove.
   *
   * @param move LevelTwoNobleMove
   * @return moveDto
   */
  public MoveDto map(LevelTwoNobleMove move) {
    List<Action<?>> actions = map((BuyCardMove) move).getActions();
    actions.add(new Action<>(ActionType.RESERVE_NOBLE, move.getReservedNoble().getMeta().getId()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelTwoPairingL1Move.
   *
   * @param move LevelTwoPairingL1Move
   * @return moveDto
   */
  public MoveDto map(LevelTwoPairingL1Move move) {
    List<Action<?>> actions = map((LevelTwoPairingMove) move).getActions();
    actions.add(new Action<>(ActionType.LEVEL_ONE_PAIR, move.getLevelOnePairing()));
    return new MoveDto(actions);
  }

  /**
   * maps a LevelTwoPairingMove.
   *
   * @param move LevelTwoPairingMove
   * @return moveDto
   */
  public MoveDto map(LevelTwoPairingMove move) {
    List<Action<?>> actions = map((BuyCardMove) move).getActions();
    actions.add(new Action<>(ActionType.LEVEL_TWO_PAIR, move.getLevelTwoPairing()));
    actions.add(new Action<>(ActionType.TAKE_LEVEL_ONE, move.getLevelOneCard().getMeta()));
    return new MoveDto(actions);
  }

  /**
   * Takes in a Token Taking move and splits it into a list of actions.
   *
   * @param move the original TokenTakingMove.
   * @return the MoveDto object which is a list of actions.
   */
  public MoveDto map(TokenTakingMove move) {
    List<Action<?>> actions = new ArrayList<>();
    //1. Adds the TokenTaking Card action.
    actions.addAll(move.getExchangeGems()
                       .stream()
                       .map(x -> new Action<>(ActionType.TAKE_TOKEN, x))
                       .collect(Collectors.toList()));

    //2. If the player need to burn gem, then add an action for each gem to burn.
    if (move instanceof TokenTakingCascadeMove) {
      TokenTakingCascadeMove theMove = (TokenTakingCascadeMove) move;
      actions.add(new Action<>(ActionType.BURN_TOKEN, theMove
          .getGemTypeListToBurn()
          .stream()
          .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(x -> 1)))));
    }
    return new MoveDto(actions);
  }

  /**
   * map move to dto move.
   *
   * @param move a move
   * @return dto move
   */
  public MoveDto map(Move move) {
    MoveDto moveDto = mapPreSort(move);
    moveDto.getActions().sort(Comparator.comparingInt(x -> x.getType().getOrder()));
    return moveDto;
  }

  private MoveDto mapPreSort(Move move) {
    if (move instanceof LevelOnePairingMove) {
      return map((LevelOnePairingMove) move);
    }

    if (move instanceof LevelThreePairingL2BaseMove) {
      return map((LevelThreePairingL2BaseMove) move);
    }

    if (move instanceof LevelThreePairingL2CascadeMove) {
      return map(( LevelThreePairingL2CascadeMove) move);
    }

    if (move instanceof LevelThreePairingL2Move) {
      return map(( LevelThreePairingL2Move) move);
    }

    if (move instanceof LevelTwoPairingL1Move) {
      return map(( LevelTwoPairingL1Move) move);
    }

    if (move instanceof LevelTwoPairingMove) {
      return map(( LevelTwoPairingMove) move);
    }

    if (move instanceof LevelThreePairingL2NobleMove) {
      return map(( LevelThreePairingL2NobleMove) move);
    }

    if (move instanceof LevelTwoNobleMove) {
      return map(( LevelTwoNobleMove) move);
    }

    if (move instanceof BuyCardMove) {
      return map((BuyCardMove) move);
    }

    if (move instanceof TokenTakingMove) {
      return map((TokenTakingMove) move);
    }

    if (move instanceof ReserveDevCardMove) {
      return map((ReserveDevCardMove) move);
    }

    throw new RuntimeException("Unhandled move type" + move.toString());
  }
}
