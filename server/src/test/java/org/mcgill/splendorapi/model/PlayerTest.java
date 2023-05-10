package org.mcgill.splendorapi.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mcgill.splendorapi.api.move.model.Payment;
import org.mcgill.splendorapi.model.card.*;
import org.mcgill.splendorapi.model.exceptions.IllegalMoveException;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;

class PlayerTest {

  Player player = Player.builder().build();
  @Test
  void getColor() {
    player.setColor(Color.YELLOW);
    assertEquals("YELLOW", player.getColour().toString());
  }

  @Test
  void builder() {
    Player builderPlayer = Player.builder().name("bcd").playerPrestige(15).build();
    assertEquals("bcd", builderPlayer.getName());
    assertEquals(15, builderPlayer.getPlayerPrestige());
  }

  @Test
  void testAddGems(){
    HashMap<GemType, Integer> newGems1 = new HashMap<>();
    newGems1.put(GemType.RUBY,2);
    Player player = Player.builder().gems(new HashMap<>()).build();
    player.addGems(newGems1);
    assertEquals(2,player.getGems().get(GemType.RUBY));
    assertEquals(2, player.countGems());
    HashMap<GemType, Integer> newGems2 = new HashMap<>();
    newGems2.put(GemType.DIAMOND,1);
    newGems2.put(GemType.RUBY,1);
    newGems2.put(GemType.EMERALD,1);
    player.addGems(newGems2);
    assertEquals(5, player.countGems());
    assertEquals(3,player.getGems().get(GemType.RUBY));
    assertEquals(1,player.getGems().get(GemType.DIAMOND));
    assertEquals(1,player.getGems().get(GemType.EMERALD));
  }

  @Test
  void testRemoveGems(){
    HashMap<GemType, Integer> price1 = new HashMap<>();
    price1.put(GemType.RUBY,2);
    HashMap<GemType, Integer> price2 = new HashMap<>();
    price2.put(GemType.RUBY,1);
    price2.put(GemType.EMERALD,3);
    price2.put(GemType.ONYX,4);
    HashMap<GemType, Integer> curGems = new HashMap<>();
    curGems.put(GemType.RUBY,5);
    curGems.put(GemType.EMERALD,3);
    curGems.put(GemType.ONYX,6);
    Player player = Player.builder().gems(curGems).bonuses(new HashMap<>()).build();
    assertEquals(5,player.getGems().get(GemType.RUBY));
    player.removeGems(price1);
    assertEquals(3,player.getGems().get(GemType.RUBY));
    player.removeGems(price2);
    assertEquals(2,player.getGems().get(GemType.RUBY));
    assertEquals(0,player.getGems().get(GemType.EMERALD));
    assertEquals(2,player.getGems().get(GemType.ONYX));
  }

  @Test
  public void testValidatePrice()throws InvalidCardType {
    //THE PLAYERS TOKENS
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY,6);
    tokens.put(GemType.EMERALD,1);
    tokens.put(GemType.ONYX,1);

    //THE PLAYERS BONUSES
    HashMap<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 2);

    //CARD 1's PRICE
    HashMap<GemType, Integer> price1 = new HashMap<>();
    price1.put(GemType.RUBY,5);

    //CARD 2's PRICE
    HashMap<GemType, Integer> price2 = new HashMap<>();
    price2.put(GemType.RUBY,3);
    price2.put(GemType.EMERALD,2);
    price2.put(GemType.ONYX,1);

    //CARD 3's PRICE:
    HashMap<GemType, Integer> price3 = new HashMap<>();
    price3.put(GemType.RUBY,4);
    price3.put(GemType.EMERALD,2);
    price3.put(GemType.ONYX,2);
    price3.put(GemType.DIAMOND,1);
    //BUILD THE PLAYER
    Player player = Player.builder().playerPrestige(0).gems(tokens).bonuses(bonuses).build();
    assertEquals(6,player.getGems().get(GemType.RUBY));

    //TEST 1: Ensures that bonuses are added correctly.
    DevelopmentCard card1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                       CardType.DEVELOPMENT),
                                               Bonus.EMERALD, 3, price1);

    assertTrue(player.canBuyCard(card1));

    //TEST 2: Ensure that the gold is counted.
    DevelopmentCard card2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 2,CardType.DEVELOPMENT),
                                                Bonus.DIAMOND, 5, price2);
    assertFalse(player.canBuyCard(card2));


    Map<GemType, Integer> gold = new HashMap<>();
    gold.put(GemType.GOLD,1);
    player.addGems(gold);
    assertTrue(player.canBuyCard(card2));

    //TEST 3: Ensure that multiple golds are being taken into account.
    DevelopmentCard card3 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 3,CardType.DEVELOPMENT),
                                                Bonus.DIAMOND, 5, price3);
    player.addGems(gold);
    assertFalse(player.canBuyCard(card3));
    player.addGems(gold);
    assertTrue(player.canBuyCard(card3));

    //TEST 4: Ensure that the double Gold card payment is working.
    player.removeGems(gold);
    player.removeGems(gold);
    player.removeGems(gold);
    player.addGoldCard();
    assertFalse(player.canBuyCard(card3));
    assertTrue(player.canBuyCard(card2));
    player.addGems(gold);
    assertTrue(player.canBuyCard(card3));
  }



//  @Test
//  void testBuyCard() throws InvalidCardType, IllegalMoveException {
//    HashMap<GemType, Integer> tokens = new HashMap<>();
//    tokens.put(GemType.RUBY,5);
//    tokens.put(GemType.SAPPHIRE,1);
//    tokens.put(GemType.DIAMOND,4);
//
//    //Card 1. Player has enough tokens to pay
//    Player player = Player.builder().playerPrestige(0).gems(tokens).bonuses(new HashMap<>()).build();
//    HashMap<GemType, Integer> price1 = new HashMap<>();
//    price1.put(GemType.RUBY,3);
//    DevelopmentCard card = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
//                                                   CardType.DEVELOPMENT),
//                                                    Bonus.SAPPHIRE, 3, price1);
//    card.turnOver(1);
//    player.buyCard(card);
//    assertEquals(3,player.getPlayerPrestige());
//    assertEquals(1, player.bonuses.size());
//    assertEquals(2,player.getGems().get(GemType.RUBY));
//
//    //TEST 2: PLayer needs their bonus to pay.
//    HashMap<GemType, Integer> price2 = new HashMap<>();
//    price2.put(GemType.SAPPHIRE,2);
//    price2.put(GemType.DIAMOND,2);
//    DevelopmentCard card2 = new DevelopmentCard(new DevelopmentCardMeta((short) 2, (short) 1,
//                                                                       CardType.DEVELOPMENT),
//                                               Bonus.EMERALD, 3, price2);
//    card2.turnOver(2);
//    player.buyCard(card2);
//    assertEquals(6,player.getPlayerPrestige());
//    assertEquals(2, player.bonuses.size());
//    assertEquals(2,player.getGems().get(GemType.RUBY));
//    assertEquals(2,player.getGems().get(GemType.DIAMOND));
//    assertEquals(0,player.getGems().get(GemType.SAPPHIRE));
//  }

  @Test
  void testGeneratePossiblePayments() throws InvalidCardType, IllegalMoveException {
    //TEST 1: THE PLAYER HAS 1 GOLD.
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);
    tokens.put(GemType.GOLD, 1); //1 gold token.

    //CARD 1's PRICE
    HashMap<GemType, Integer> price1 = new HashMap<>();
    price1.put(GemType.RUBY, 2);
    price1.put(GemType.EMERALD, 1);
    price1.put(GemType.ONYX, 1);


    //
    Map<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 0);
    bonuses.put(Bonus.EMERALD, 0);
    bonuses.put(Bonus.ONYX, 0);
    bonuses.put(Bonus.SAPPHIRE , 0);
    bonuses.put(Bonus.DIAMOND , 0);
    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(0).gems(tokens).bonuses(bonuses).build();
    DevelopmentCard card1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                        CardType.DEVELOPMENT),
                                                Bonus.EMERALD, 3, price1);


    //TEST 2: THE PLAYER HAS TWO GOLD:
    Map<GemType, Integer> newGold = new HashMap<>();
    newGold.put(GemType.GOLD, 1);
    player.addGems(newGold);
    int numPossiblePayments2 = player.getPossiblePayments(card1.getPrice()).size();
    assertEquals(10, numPossiblePayments2);

    //TEST 3: THE PLAYER HAS THREE GOLD:
    player.addGems(newGold);
    int numPossiblePayments3 = player.getPossiblePayments(card1.getPrice()).size();
    assertEquals(14, numPossiblePayments3);
  }

//  @Test
//  void testAddGoldMoves() throws InvalidCardType {
//    //TEST 1: THE PLAYER DOES NOT HAVE GOLD.
//
//    HashMap<GemType, Integer> tokens = new HashMap<>();
//    tokens.put(GemType.RUBY, 3);
//    tokens.put(GemType.EMERALD, 1);
//    tokens.put(GemType.ONYX, 2);
//    //tokens.put(GemType.GOLD, 1); //1 gold token.
//
//    //CARD 1's PRICE
//    HashMap<GemType, Integer> price1 = new HashMap<>();
//    price1.put(GemType.RUBY, 2);
//    price1.put(GemType.EMERALD, 1);
//    price1.put(GemType.ONYX, 1);
//
//    //BUILD THE PLAYER
//    Player player =
//      Player.builder().playerPrestige(0).gems(tokens).bonuses(new HashMap<>()).build();
//    DevelopmentCard card1 = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
//                                                                        CardType.DEVELOPMENT),
//                                                Bonus.EMERALD, 3, price1);
//    player.addGoldCard();
//    List<Map<GemType, Integer>> possiblePayments = new ArrayList<>();
//    possiblePayments.add(card1.getPrice());
//    List<Map<GemType, Integer>> newPayments = player.addGoldCardPayments(possiblePayments, card1.getPrice(), 1);
//    System.out.println(newPayments);
//    assertEquals(6, newPayments.size());
//    //[{RUBY=2, EMERALD=1}, {RUBY=1, EMERALD=1}, {ONYX=1, EMERALD=1}, {ONYX=1, RUBY=2}, {RUBY=2}]
//    // Making sense after print the combination since the gold card cam be 1 or two gold
//    //It's possible to fully use it or not
//    List<Map<GemType, Integer>> newPayments2 = player.addGoldCardPayments(possiblePayments,
//                                                                         card1.getPrice(),2);
//    assertEquals(3, newPayments2.size());
//
//    //TEST 2: The Player has a Gold token already.
//    player.addGem(GemType.GOLD);
//    List<Map<GemType, Integer>> basePossiblePayments = player.getPossiblePayments(card1.getPrice());
//    List<Map<GemType, Integer>> newPayments3 = player.addGoldCardPayments(basePossiblePayments, card1.getPrice(), 1);
//    assertEquals(10, newPayments3.size()); //the answer supposes to be 10
//    //[{RUBY=2, EMERALD=1}, {RUBY=1, EMERALD=1}, {ONYX=1, EMERALD=1}, {ONYX=1, RUBY=2}
//    // , {RUBY=2}, {GOLD=1, EMERALD=1}, {ONYX=1, RUBY=1}, {GOLD=1, RUBY=1}, {ONYX=1, GOLD=1}]
//    // Making sense after print the combination since the gold card cam be 1 or two gold
//    //It's possible to fully use it or not
//  }

  @Test
  void testAddBonus() {
    Player player = Player.builder().bonuses(new HashMap<>()).build();

    // Test adding a new bonus
    player.addBonus(Bonus.RUBY);
    assertEquals(1, player.getBonuses().get(Bonus.RUBY));

    // Test incrementing an existing bonus
    player.addBonus(Bonus.RUBY);
    assertEquals(2, player.getBonuses().get(Bonus.RUBY));

    // Test adding a different bonus
    player.addBonus(Bonus.SAPPHIRE);
    assertEquals(1, player.getBonuses().get(Bonus.SAPPHIRE));
    assertEquals(2, player.getBonuses().get(Bonus.RUBY));
  }
  @Test
  void testRemoveBonus() {
    HashMap<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 3);
    bonuses.put(Bonus.EMERALD, 2);
    Player player = Player.builder().bonuses(bonuses).build();

    // Test case 1: Remove a bonus that the player has
    player.removeBonus(Bonus.RUBY);
    assertEquals(2, player.getBonuses().get(Bonus.RUBY));

    // Test case 2: Remove a bonus that the player doesn't have
    player.removeBonus(Bonus.SAPPHIRE);
    assertEquals(0, player.getBonuses().get(Bonus.SAPPHIRE));

    // Test case 3: Remove a bonus until it reaches 0
    player.removeBonus(Bonus.EMERALD);
    player.removeBonus(Bonus.EMERALD);
    assertEquals(0, player.getBonuses().get(Bonus.EMERALD));
  }
  @Test
  void testCanBuyReservedCard() throws InvalidCardType {
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 5);

    Player player = Player.builder().gems(tokens).bonuses(new HashMap<>()).build();

    HashMap<GemType, Integer> price = new HashMap<>();
    price.put(GemType.RUBY, 3);

    DevelopmentCard card = new DevelopmentCard(new DevelopmentCardMeta((short) 1, (short) 1,
                                                                       CardType.DEVELOPMENT),
                                               Bonus.SAPPHIRE, 3, price);
    card.setState(CardState.RESERVED);

    player.reserveCard(card);

    assertTrue(player.canBuyCard(card));
  }
  @Test
  void testAddPrestigePoints() {
    Player player = Player.builder().playerPrestige(0).build();
    assertEquals(0, player.getPlayerPrestige());

    player.addPrestigePoints(3);
    assertEquals(3, player.getPlayerPrestige());

    player.addPrestigePoints(4);
    assertEquals(7, player.getPlayerPrestige());
  }
  @Test
  void testAddCity() throws InvalidCardType {
    // Test adding the first city to an empty cities list
    CardMeta meta = new CardMeta((short) 1, CardType.CITY);
    City city1 = new City(meta, 1, new HashMap<>(), 2);
    player.addCity(city1);
    assertEquals(1, player.getCities().size());
    assertEquals(1, player.getCities().get(0).getPrestigePoints());

    // Test adding another city to the cities list
    City city2 = new City(meta, 3, new HashMap<>(), 4);
    player.addCity(city2);
    assertEquals(2, player.getCities().size());
    assertEquals(3, player.getCities().get(1).getPrestigePoints());
  }

  @Test
  void testAddCityWithBuilder() throws InvalidCardType {
    // Test creating a player with cities using the builder
    CardMeta meta = new CardMeta((short) 1, CardType.CITY);
      City city1 = new City(meta, 1, new HashMap<>(), 2);
      City city2 = new City(meta, 3, new HashMap<>(), 4);

    ArrayList<City> cities = new ArrayList<>();
    cities.add(city1);
    cities.add(city2);

    Player builderPlayer = Player.builder().cities(cities).build();
    assertEquals(1, builderPlayer.getCities().get(0).getPrestigePoints());
    assertEquals(2, builderPlayer.getCities().size());
    assertEquals(3, builderPlayer.getCities().get(1).getPrestigePoints());
  }

  @Test
  void testAddReservedNoble() throws InvalidCardType {
    CardMeta meta = new CardMeta((short) 1, CardType.NOBLE);
    Map<Bonus, Integer>price = new HashMap<>();
    // Test 1: Add a noble card to an empty list of reserved nobles
    NobleCard noble1 = new NobleCard(meta, 1, 2, price);
    Player player1 = Player.builder().build();
    player1.addReservedNoble(noble1);
    assertEquals(1, player1.getReservedNobles().size());
    assertTrue(player1.getReservedNobles().contains(noble1));

    // Test 2: Add another noble card to the existing list of reserved nobles
    NobleCard noble2 = new NobleCard(meta, 3, 4, price);
    player1.addReservedNoble(noble2);
    assertEquals(2, player1.getReservedNobles().size());
    assertTrue(player1.getReservedNobles().contains(noble2));

    // Test 3: Add the same noble card again to the existing list of reserved nobles
    player1.addReservedNoble(noble1);
    assertEquals(3, player1.getReservedNobles().size());
    assertTrue(player1.getReservedNobles().contains(noble1));

    // Test 4: Create a new player with a non-empty reserved noble list and add a noble card
    List<NobleCard> initialReservedNobles = new ArrayList<>();
    initialReservedNobles.add(noble1);
    Player player2 = Player.builder().reservedNobles(initialReservedNobles).build();
    player2.addReservedNoble(noble2);
    assertEquals(2, player2.getReservedNobles().size());
    assertTrue(player2.getReservedNobles().contains(noble2));
  }
  @Test
  void testSetGems() {
    // Test 1: Set gems for a player with an empty gem map
    HashMap<GemType, Integer> initialGems = new HashMap<>();
    initialGems.put(GemType.RUBY, 3);
    initialGems.put(GemType.EMERALD, 1);
    initialGems.put(GemType.ONYX, 2);

    Player player1 = Player.builder().gems(new HashMap<>()).build();
    player1.setGems(initialGems);
    assertEquals(3, player1.getGems().get(GemType.RUBY));
    assertEquals(1, player1.getGems().get(GemType.EMERALD));
    assertEquals(2, player1.getGems().get(GemType.ONYX));

    // Test 2: Set gems for a player with existing gems in the map
    HashMap<GemType, Integer> additionalGems = new HashMap<>();
    additionalGems.put(GemType.RUBY, 2);
    additionalGems.put(GemType.SAPPHIRE, 1);

    player1.setGems(additionalGems);
    assertEquals(2, player1.getGems().get(GemType.RUBY));
    assertEquals(1, player1.getGems().get(GemType.EMERALD));
    assertEquals(2, player1.getGems().get(GemType.ONYX));
    assertEquals(1, player1.getGems().get(GemType.SAPPHIRE));
  }
  @Test
  void testRemoveGoldCard() {
    // Test 1: The player has one gold card.
    player.addGoldCard();
    assertEquals(1, player.getNumGoldCards());
    player.removeGoldCard();
    assertEquals(0, player.getNumGoldCards());

    // Test 2: The player has multiple gold cards.
    player.addGoldCard();
    player.addGoldCard();
    player.addGoldCard();
    assertEquals(3, player.getNumGoldCards());
    player.removeGoldCard();
    assertEquals(2, player.getNumGoldCards());
    player.removeGoldCard();
    assertEquals(1, player.getNumGoldCards());
  }
  @Test
  void testAddGem() {
    // Initialize player with empty gem map
    Player player = Player.builder().gems(new HashMap<>()).build();

    // Test adding a new gem type that does not exist in the player's gem map
    player.addGem(GemType.RUBY);
    assertEquals(1, player.getGems().get(GemType.RUBY));

    // Test adding another gem of the same type to increase the count
    player.addGem(GemType.RUBY);
    assertEquals(2, player.getGems().get(GemType.RUBY));

    // Test adding a different gem type
    player.addGem(GemType.DIAMOND);
    assertEquals(1, player.getGems().get(GemType.DIAMOND));
  }
  @Test
  void testRemoveGem() {
    // Initialize player with some gems
    HashMap<GemType, Integer> initialGems = new HashMap<>();
    initialGems.put(GemType.RUBY, 5);
    initialGems.put(GemType.DIAMOND, 3);
    initialGems.put(GemType.EMERALD, 2);

    Player player = Player.builder().gems(initialGems).build();

    // Test removing a gem that exists
    player.removeGem(GemType.RUBY);
    assertEquals(4, player.getGems().get(GemType.RUBY));

    // Test removing a gem that does not exist
    player.removeGem(GemType.SAPPHIRE);
    assertNull(player.getGems().get(GemType.SAPPHIRE));

    // Test removing a gem when the count is zero
    player.removeGem(GemType.EMERALD);
    player.removeGem(GemType.EMERALD);
    assertEquals(0, player.getGems().get(GemType.EMERALD));
  }

  @Test
  void meetsTradingPostRequirementsTest(){
    //TEST 1: THE PLAYER HAS 1 GOLD.
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);
    tokens.put(GemType.GOLD, 1); //1 gold token.
    Map<Bonus, Integer> requirements = new HashMap<>();
    requirements.put(Bonus.DIAMOND, 1);

    Map<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 2);
    bonuses.put(Bonus.EMERALD, 3);
    bonuses.put(Bonus.ONYX, 3);
    bonuses.put(Bonus.SAPPHIRE , 2);
    bonuses.put(Bonus.DIAMOND , 2);
    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(0).gems(tokens).bonuses(bonuses).build();

    assertTrue(player.meetsTradingPostRequirements(requirements));
  }

  private City city;
  private CardMeta meta;
  private int prestigePoints;
  private int sameRequirement;
  @BeforeEach
  void setUpCity() {
    meta = new CardMeta((short) 1, CardType.CITY);
    prestigePoints = 5;
    sameRequirement = 2;
    HashMap<Bonus, Integer> requirement = new HashMap<>();
    requirement.put(Bonus.RUBY, 1);
    try {
      city = new City(meta, prestigePoints, requirement , sameRequirement);
    } catch (InvalidCardType e) {
      fail("Unexpected InvalidCardType exception");
    }
  }
  @Test
  void meetsCityRequirementsTest(){

    setUpCity();

    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);
    tokens.put(GemType.GOLD, 1); //1 gold token.
    Map<Bonus, Integer> requirements = new HashMap<>();
    requirements.put(Bonus.DIAMOND, 1);

    Map<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 2);
    bonuses.put(Bonus.EMERALD, 3);
    bonuses.put(Bonus.ONYX, 3);
    bonuses.put(Bonus.SAPPHIRE , 2);
    bonuses.put(Bonus.DIAMOND , 2);
    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(100).gems(tokens).bonuses(bonuses).build();
    assertTrue(player.meetsCityRequirements(city));
  }

  @Test
  void buyCardTest() throws InvalidCardType, IllegalMoveException {
    DevelopmentCard aCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1,
   (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 2, new HashMap<>());
    aCard.setState(CardState.RESERVED);
    aCard.turnOver(1);

    Map<GemType, Integer> pay = new HashMap<>();
    pay.put(GemType.DIAMOND, 1);
    Payment payment = Payment.builder().gems(pay).numDoubleGold(0).build();

    //Build player
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);
    tokens.put(GemType.DIAMOND, 1);
    tokens.put(GemType.GOLD, 1); //1 gold token.
    Map<Bonus, Integer> requirements = new HashMap<>();
    requirements.put(Bonus.DIAMOND, 1);

    Map<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 0);
    bonuses.put(Bonus.EMERALD, 0);
    bonuses.put(Bonus.ONYX, 0);
    bonuses.put(Bonus.SAPPHIRE , 0);
    bonuses.put(Bonus.DIAMOND , 0);
    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(100).gems(tokens).bonuses(bonuses).build();
    player.buyCard(aCard, payment);
    assertEquals(0, player.getGems().get(GemType.DIAMOND));
  }

  @Test
  void addFreeCardTest() throws InvalidCardType, IllegalMoveException {
    DevelopmentCard aCard = new DevelopmentCard(new DevelopmentCardMeta((short) 1,
    (short) 1, CardType.DEVELOPMENT), Bonus.EMERALD, 2, new HashMap<>());
    aCard.setState(CardState.FREE);
    aCard.turnOver(1);

    Map<GemType, Integer> pay = new HashMap<>();
    pay.put(GemType.DIAMOND, 1);
    Payment payment = Payment.builder().gems(pay).numDoubleGold(0).build();

    //Build player
    HashMap<GemType, Integer> tokens = new HashMap<>();
    tokens.put(GemType.RUBY, 3);
    tokens.put(GemType.EMERALD, 1);
    tokens.put(GemType.ONYX, 2);
    tokens.put(GemType.DIAMOND, 1);
    tokens.put(GemType.GOLD, 1); //1 gold token.
    Map<Bonus, Integer> requirements = new HashMap<>();
    requirements.put(Bonus.DIAMOND, 1);

    Map<Bonus, Integer> bonuses = new HashMap<>();
    bonuses.put(Bonus.RUBY, 0);
    bonuses.put(Bonus.EMERALD, 0);
    bonuses.put(Bonus.ONYX, 0);
    bonuses.put(Bonus.SAPPHIRE , 0);
    bonuses.put(Bonus.DIAMOND , 0);
    //BUILD THE PLAYER
    Player player =
      Player.builder().playerPrestige(0).gems(tokens).bonuses(bonuses).build();

    player.addFreeCard(aCard);

    assertEquals(2, player.getPlayerPrestige());
  }


}