package org.mcgill.splendorapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.Builder;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;


/**
 * Player object.
 */
public class GamePlayer extends BasePlayer {

  public final Map<Gem.Type, List<Gem>> gems = new HashMap<>();
  private final Map<Bonus.BonusType, List<Bonus>> bonuses = new HashMap<>();
  protected final List<Card> reservedCard = new ArrayList<>();
  private int playerPrestige = 0;


  @Builder
  public GamePlayer(String name) {
    super(name);
  }

  public void reserveCard(Card card) throws IllegalMoveException {
    card.changeState(Card.State.RESERVED);
    reservedCard.add(card);
  }

  private int getBonusForType(Gem.Type gemType) {
    return Optional.of(bonuses.get(Bonus.BonusType.valueOf(gemType.name())))
        .orElse(new ArrayList<>()).stream().mapToInt(Bonus::getTotalBonus).sum();
  }

  /**
   * Perform the purchase of a card, adding the prestige points and bonuses to the player object.
   *
   * @param card Card to be purchased
   * @return List of the gems removed
   */
  public List<Gem> buyCard(Card card) throws IllegalMoveException {
    card.changeState(Card.State.PURCHASED);
    playerPrestige += card.getPrestigePoints();
    if (!bonuses.containsKey(card.getBonus().getBonusType())) {
      bonuses.put(card.getBonus().getBonusType(), new ArrayList<>());
    }

    bonuses.get(card.getBonus().getBonusType()).add(card.getBonus());
    // return the gems
    List<Gem> removed = new ArrayList<>();
    for (Gem.Type type : card.getPrice().keySet()) {
      for (int i = getBonusForType(type); i > 0; i--) {
        removed.add(gems.get(type).remove(0));
      }
    }

    return removed;
  }

  /**
   * Ensure the player has enough bonuses and gems to actually purchase the card.
   *
   * @param gemType The type of gem required to purchase the item
   * @return If the payment has been met.
   */
  private Integer getPayment(Gem.Type gemType) {
    List<Gem> validGems = Optional.of(gems.get(gemType)).orElse(new ArrayList<>());
    return getBonusForType(gemType) + validGems.size();
  }

  /**
   * Ensure the player has the required number of gems/bonuses for the card.
   *
   * @param card The card to purchase
   * @return Validity of the statement
   */
  private boolean validatePrice(Card card) {
    return card.getPrice()
        .keySet()
        .stream()
        .allMatch(gemType -> card.getPrice().get(gemType) <= getPayment(gemType));
  }

  /**
   * Assert the validity of the move for the player.
   *
   * @param card Card the player is trying to purchase
   * @return Whether the player can purchase the card
   */
  public boolean canBuyCard(Card card) {
    if (card.getState().equals(Card.State.FREE)) {
      return validatePrice(card);
    }

    if (card.getState().equals(Card.State.RESERVED) && hasReservedCard(card)) {
      return validatePrice(card);
    }

    return false;
  }

  /**
   * Checks if the player has reserved the card.
   *
   * @param card The card in question
   * @return boolean value of the statement
   */
  boolean hasReservedCard(Card card) {
    return reservedCard.stream().anyMatch(reserved -> reserved.equals(card));
  }

  /**
   * Getter for the player prestige points.
   *
   * @return Players prestige points
   */
  int getPlayerPrestige() {
    return playerPrestige;
  }

  public void addGem(Gem gem) {
    List<Gem> gemByType = gems.computeIfAbsent(gem.getType(), k -> new ArrayList<>());
    gemByType.add(gem);
  }

  public void addGems(List<Gem> newGems) {
    newGems.forEach(this::addGem);
  }
}
