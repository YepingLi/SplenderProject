package org.mcgill.splendorapi.model.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.CardType;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.DevelopmentCardMeta;

/**
 * Orient game board.
 */
@Jacksonized
@Getter
@SuperBuilder
public class OrientGameBoard extends GameBoard {

  public GameType fixType(GameType t) {
    return Optional.ofNullable(t).orElse(GameType.ORIENT);
  }

  private List<DevelopmentCard> getOrientByLevel(int level) {
    return getByLevel(level, Card::isOrientCard);
  }

  @JsonIgnore
  public List<DevelopmentCard> getL1OrientCards() {
    return getOrientByLevel(1);
  }

  @JsonIgnore
  public List<DevelopmentCard> getL2OrientCards() {
    return getOrientByLevel(2);
  }

  @JsonIgnore
  public List<DevelopmentCard> getL3OrientCards() {
    return getOrientByLevel(3);
  }
  /**
   * Initializes the base Game Board.
   *
   * @return the initialized gameBoard.
   */

  @Override
  public OrientGameBoard init() {
    super.init();
    Random random = new Random();
    // Deals four base cards and two Orient cards for each level
    for (int i = 0; i < 2; i++) {
      for (int j = 1; j < 4; j++) {
        List<DevelopmentCard> faceUp = getHiddenCards(j, Card::isOrientCard);
        faceUp.get(random.nextInt(faceUp.size())).turnOver(i);
      }
    }
    return this;
  }


  protected List<DevelopmentCard> produceHiddenChoices(DevelopmentCardMeta meta) {
    if (meta.getType().equals(CardType.DEVELOPMENT)) {
      return super.produceHiddenChoices(meta);
    }
    return getHiddenCards(meta.getLevel(), Card::isOrientCard);
  }
}
