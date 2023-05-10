package org.mcgill.splendorapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.api.move.model.Payment;
import org.mcgill.splendorapi.model.card.Card;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.paukov.combinatorics3.Generator;


/**
 * Player object.
 */
@Jacksonized
@Builder
@Getter
public class Player {
  private Map<GemType, Integer> gems;

  protected Map<Bonus, Integer> bonuses;

  protected List<Card> reservedCards;
  private int playerPrestige = 0;
  private Color colour;
  private final String name;

  private List<PowerType> powers;
  private List<NobleCard> nobles;
  private List<City> cities;
  private int numGoldCards = 0;

  private List<NobleCard> reservedNobles;


  /**
   * Initializes the player.
   */
  public void init() {
  }

  /**
   * Adds a city to the player.
   *
   * @param city city
   */
  public void addCity(City city) {
    if (this.cities == null) {
      ArrayList<City> newCities = new ArrayList<>();
      newCities.add(city);
      this.cities = newCities;
    } else {
      this.cities.add(city);
    }
  }

  /**
   * add a reserved noble.
   *
   * @param noble noble
   */
  public void addReservedNoble(NobleCard noble) {
    if (reservedNobles == null) {
      ArrayList<NobleCard> tempReservedNobles = new ArrayList<NobleCard>();
      tempReservedNobles.add(noble);
      this.reservedNobles = tempReservedNobles;
    } else {
      this.reservedNobles.add(noble);
    }
  }

  /**
   * Set gems for players.
   *
   * @param gems gems of player
   */
  public void setGems(Map<GemType, Integer> gems) {
    if (this.gems == null) {
      this.gems = new HashMap<>();
    }
    this.gems.putAll(gems);
  }

  /**
   * Adds a gold card to the player.
   */
  public void addGoldCard() {
    this.numGoldCards += 1;
  }

  public void removeGoldCard() {
    this.numGoldCards -= 1;
  }

  /**
   * Set bonuses for players.
   *
   * @param bonuses player's bonuses
   */
  public void setBonuses(Map<Bonus, Integer> bonuses) {
    if (this.bonuses == null) {
      this.bonuses = new HashMap<>();
    }
    this.bonuses.putAll(bonuses);
  }

  /**
   * Checks if the player can purchase this Trading Post.
   *
   * @param requirements the cost of the Trading post.
   * @return true if they meet the requirements and false otherwise.
   */

  public boolean meetsTradingPostRequirements(Map<Bonus, Integer> requirements) {
    return requirements.entrySet().stream()
                       .allMatch(
                         entry -> bonuses.getOrDefault(entry.getKey(), 0) >= entry.getValue());

  }

  /**
   * Check if able to acquire a city.
   *
   * @param city city
   * @return if able to acquire a city
   */

  public boolean meetsCityRequirements(City city) {
    boolean match = city.getRequirements()
                        .entrySet()
                        .stream()
                        .allMatch(entry -> bonuses.getOrDefault(entry.getKey(), 0)
                          >= entry.getValue());
    boolean sameRequirement = false;
    for (Map.Entry<Bonus, Integer> bonus : bonuses.entrySet()) {
      if (bonus.getValue() > city.getSameRequirement()) {
        sameRequirement = true;
        break;
      }
    }

    return match && sameRequirement && (playerPrestige > city.getPrestigePoints());
  }

  /**
   * Reserves the card and adds the gold token.
   *
   * @param card the Reserved card.
   */

  public void reserveCard(Card card) {
    if (reservedCards == null) {
      reservedCards = new ArrayList<>();
    }
    reservedCards.add(card);
    //Adds a gold token to the player.
    if (this.gems.get(GemType.GOLD) == null || this.gems.get(GemType.GOLD) == 0) {
      this.gems.put(GemType.GOLD, 1);
    } else {
      int curGold = gems.get(GemType.GOLD);
      this.gems.put(GemType.GOLD, curGold + 1);
    }
  }

  /**
   * add a bonus.
   *
   * @param bonus bonus to be added
   */

  public void addBonus(Bonus bonus) {
    bonuses.compute(bonus, (bonus1, value) -> {
      if (value == null) {
        return 1;
      }
      return value + 1;
    });
  }

  /**
   * Remove Bonus from player.
   *
   * @param bonus bonus to be removed
   */

  public void removeBonus(Bonus bonus) {
    bonuses.compute(bonus, (bonus1, value) -> {
      if (value == null) {
        return 0;
      }
      return value - 1;
    });
  }

  private int getBonusForType(GemType gemType) {
    return bonuses.getOrDefault(Bonus.fromGem(gemType), 0);
  }

  /**
   * Called when payment is specified.  Just adds victory Points and Bonus.
   *
   * @param card card to buy
   *             * @return price paid
   */
  @SuppressWarnings("checkstyle:WhitespaceAround")
  public Payment buyCard(DevelopmentCard card, Payment payment) throws IllegalMoveException {
    //Add the prestige points.
    playerPrestige += card.getPrestigePoints();


    Payment pay = Optional.ofNullable(payment).orElseGet(() -> {
      Map<GemType, Integer> cost = copyMap(card.getPrice());
      copyMap(bonuses).forEach((key, bonusValue) -> {
        GemType.fromBonus(key).ifPresent(gem -> {
          cost.computeIfPresent(gem,
                                (g, costForGem) -> Math.max(0, costForGem - bonusValue));
        });
      });
      int numMissing = findNumMissing(copyMap(cost));
      Map<GemType, Integer> newCost = new HashMap<>();
      for (Map.Entry<GemType, Integer> entry : cost.entrySet()) {
        int curCost = entry.getValue();
        GemType gem = entry.getKey();
        if (curCost > 0 && curCost - gems.get(gem) <= 0) {
          newCost.put(gem, curCost);
        } else if (curCost > 0 && curCost - gems.get(gem) > 0) {
          newCost.put(gem, curCost - gems.get(gem));
        }
      }
      newCost.put(GemType.GOLD, numMissing);
      return Payment.builder().gems(newCost).build();
    });

    //Add the Bonus.
    this.addBonus(card.getBonus());
    removeGems(pay.getGems());
    return pay;
  }

  /**
   * Removes the list of Gems from the players stash taking into account their bonuses.
   *
   * @param gemsToRemove gems.
   */

  public void removeGems(Map<GemType, Integer> gemsToRemove) {
    //TODO: handle double gold
    gemsToRemove.forEach((gem, num) ->
        gems.merge(gem, num, (numBefore, numToRemove) -> numBefore - numToRemove));
  }

  /**
   * Adds a card to the player withhout decreasing any gems.
   *
   * @param card the free card.
   */

  public void addFreeCard(DevelopmentCard card) throws IllegalMoveException {
    //TODO: add change card state
    card.changeState(CardState.PURCHASED);
    playerPrestige += card.getPrestigePoints();
    //Add the Bonus.
    Bonus cardBonus = card.getBonus();
    this.addBonus(cardBonus);
  }


  /**
   * Ensure the player has enough bonuses and gems to actually purchase the card.
   *
   * @param price The Hash map of the price.
   * @return If the payment has been met.
   */
  public boolean validatePrice(Map<GemType, Integer> price) {
    int remainingGold = gems.getOrDefault(GemType.GOLD, 0);
    remainingGold += 2 * (this.numGoldCards);
    for (Map.Entry<GemType, Integer> token : price.entrySet()) {
      GemType curType = token.getKey();
      Integer amount = token.getValue();
      //The total amount of gems and bonuses for the player.
      int total = gems.getOrDefault(curType, 0) + getBonusForType(curType);
      if (total < amount) {
        if ((total + remainingGold) < amount) {
          return false;
        } else {
          if (remainingGold > 0) {
            remainingGold -= 1; //Uses the spare gold.
          }
        }
      }
    }
    return true;
  }

  /**
   * Validate that the noble can visit the player.
   *
   * @param card The noble card trying to visit the player
   * @return If the noble can visit
   */
  public boolean validateNobleVisit(NobleCard card) {
    if (card.getState().equals(CardState.FREE) || (card.getState()
                                                       .equals(CardState.RESERVED)
        && hasReservedCard(card))) {
      return card.getPrice()
                 .keySet()
                 .stream()
                 .allMatch(gemType -> card.getPrice()
                                          .get(gemType) <= Optional.ofNullable(bonuses.get(gemType))
                                                                   .orElse(0));
    }
    return false;
  }

  /**
   * Assert the validity of the move for the player.
   *
   * @param card Card the player is trying to purchase
   * @return Whether the player can purchase the card
   */
  public boolean canBuyCard(DevelopmentCard card) {
    if (card.getState().equals(CardState.FREE)) {
      return validatePrice(card.getPrice());
    }

    if (card.getState().equals(CardState.RESERVED) && hasReservedCard(card)) {
      return validatePrice(card.getPrice());
    }
    return false;
  }

  /**
   * Checks if the player has reserved the card.
   *
   * @param card The card in question
   * @return boolean value of the statement
   */
  public boolean hasReservedCard(Card card) {
    return reservedCards.stream().anyMatch(reserved -> reserved.equals(card));
  }

  public void addPrestigePoints(int newPoints) {
    this.playerPrestige += newPoints;
  }

  /**
   * Adds the gems to the Players stash.
   *
   * @param newGems newGems
   */
  public void addGems(Map<GemType, Integer> newGems) {
    newGems.forEach((key, value) -> {
      gems.compute(key, (gem, oldValue) -> {
        if (oldValue == null) {
          return value;
        }
        return oldValue + value;
      });
    });
  }

  /**
   * Add a noble to player.
   *
   * @param noble the noble
   */
  public void addNoble(NobleCard noble) throws IllegalMoveException {
    this.nobles.add(noble);
    noble.changeState(CardState.PURCHASED);
    this.playerPrestige += noble.getPrestigePoints();
  }

  public void setColor(Color color) {
    this.colour = color;
  }

  /**
   * Adds a power to the players powers.
   *
   * @param powerType the enum associated with a tradingPost.
   */
  public void addPower(PowerType powerType) {
    powers.add(powerType);
  }

  /**
   * Remove power.
   *
   * @param powerType the power to remove
   */
  public void removePower(PowerType powerType) {
    powers.remove(powerType);
  }

  /**
   * helper to remove duplicate code.
   *
   * @param payment payment being processed
   * @return The payment stream mapped
   */
  private static Map<GemType, Integer> fixEntryStream(Stream<Map.Entry<GemType, Integer>> payment) {
    return payment.filter(entry -> entry.getValue() > 0)
                  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Generate gold replacements.
   *
   * @param price    base price to which we need to replace
   * @param numGolds num of gold being replaced
   * @return The new payments
   */
  private List<Map<GemType, Integer>> generateGoldTokenReplacement(Map<GemType, Integer> price,
                                                                   int numGolds) {
    List<Map<GemType, Integer>> payments = new ArrayList<>();

    //Loops across the number of gems replaces and returns all combinations.
    for (int goldToReplace = 0; goldToReplace <= numGolds; goldToReplace++) {
      int finalGold = goldToReplace;
      List<GemType> combos = price.entrySet().stream().filter(x -> x.getValue() > 0).flatMap(x -> {
        int numToPull = Math.min(x.getValue(), finalGold);
        return IntStream.range(0, numToPull).mapToObj(y -> x.getKey());
      }).collect(Collectors.toList());
      List<List<GemType>> finalCombos = Generator.combination(combos)
                                                 .simple(goldToReplace)
                                                 .stream()
                                                 .collect(Collectors.toList());
      for (List<GemType> combo : finalCombos) {
        Map<GemType, Integer> replaced = combo.stream()
                                              .collect(Collectors.groupingBy(Function.identity(),
                                                                             Collectors.summingInt(
                                                                               x -> 1)));
        Map<GemType, Integer> copiedPrice = fixEntryStream(
            copyMap(price)
              .entrySet()
              .stream()
              .peek(entry ->
                  entry.setValue(entry.getValue() - replaced.getOrDefault(entry.getKey(), 0)))
        );
        copiedPrice.put(GemType.GOLD, goldToReplace);
        payments.add(copiedPrice);
      }
    }
    return payments;
  }

  private int findNumMissing(Map<GemType, Integer> price) {
    return copyMap(price).entrySet().stream().peek((x) -> {
      x.setValue(x.getValue() - gems.get(x.getKey()));
    }).filter(x -> x.getValue() > 0).mapToInt(Map.Entry::getValue).sum();
  }

  /**
   * Returns the list of possible payments the player can make.
   * Only called if they have at least one gold token.
   *
   * @param price the card they are purchasing.
   * @return The list of all possible payments.
   */
  public List<Payment> getPossiblePayments(Map<GemType, Integer> price) {
    // If the payment requires gold, decrease the amount of available Gold.
    Map<Bonus, Integer> bonusEntries = copyMap(bonuses);
    price = fixEntryStream(copyMap(price).entrySet().stream().peek((x) -> {
      x.setValue(x.getValue() - bonusEntries.get(Bonus.fromGem(x.getKey())));
    }));
    int numMissing = findNumMissing(copyMap(price));

    int numGolds = Math.min(gems.getOrDefault(GemType.GOLD, 0), numGoldNeeded(price));
    if (numGolds == 0 && numMissing > 0) {
      throw new IllegalStateException("produced an invalid move");

      //All the available Gold was required to purchase the card.
    } else if (numGolds == numMissing) {
      return List.of();
    }

    List<Payment> payments = new ArrayList<>(List.of(Payment.builder().gems(price).build()));
    //Loops across the number of gems replaces and returns all combinations.
    payments.addAll(generateGoldTokenReplacement(price, numGolds)
                      .stream()
                      .map(pay -> Payment.builder()
                                         .gems(pay)
                                         .build())
                      .collect(Collectors.toList()));
    // (ORIENT) double gold
    if (numGoldCards <= 0) {
      return payments;
    }
    payments.addAll(payments.stream().map(payment -> {
      int numberOfGoldTokens = numGoldCards * 2;
      List<Payment> newPayments = new ArrayList<>();
      for (int i = 1; i < numGoldCards; i++) {
        List<GemType> copyPayment = copyMap(payment.getGems())
            .entrySet()
            .stream()
            .flatMap(
              entry -> IntStream.range(0,
                                       entry.getValue())
                                .mapToObj(
                                  x -> entry.getKey()))
            .collect(Collectors.toList());
        for (int j = 0; j < copyPayment.size() - numberOfGoldTokens; j++) {
          IntStream.range(j, j + i).forEach(copyPayment::remove);
          Map<GemType, Integer> pay = copyPayment.stream().collect(
              Collectors.groupingBy(Function.identity(),
                                    Collectors.summingInt(x -> 1))
          );
          newPayments.add(Payment.builder().gems(pay).numDoubleGold(i).build());
        }
      }
      return newPayments;
    }).flatMap(List::stream).collect(Collectors.toList()));
    return payments;
  }

  /**
   * Helper function which returns how many gold is needed to buy the card.
   *
   * @param price The cost of the card
   * @return The number of gold needed to buy the card.  (0,1,2,3...)
   */
  protected int numGoldNeeded(Map<GemType, Integer> price) {
    return price.values().stream().mapToInt(integer -> integer).sum();
  }

  // TODO: MOVE TO UTILITY CLASS
  public static <T, U> Map<T, U> copyMap(Map<T, U> map) {
    return map.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * remove a reserved card.
   *
   * @param card the development card to remove
   */
  public void removeReservedCard(DevelopmentCard card) {
    reservedCards.remove(card);
  }

  /**
   * add a gem to player.
   *
   * @param type the gem type you want to add
   */
  public void addGem(GemType type) {
    gems.compute(type, (t, v) -> v == null ? 1 : v + 1);
  }

  /**
   * remove a gem to player.
   *
   * @param type the gem type you want to add
   */
  public void removeGem(GemType type) {
    if (this.gems.get(type) == null) {
      int x = 0;
    } else {
      this.gems.put(type, this.gems.get(type) - 1);
    }
  }

  /**
   * Helper function which counts the total number of player gems.
   *
   * @return The total number of gems the player has.
   */
  public int countGems() {
    int total = 0;
    for (Map.Entry<GemType, Integer> gem : this.gems.entrySet()) {
      total += gem.getValue();
    }
    return total;
  }
}

