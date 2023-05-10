package org.mcgill.splendorapi.api.assetloader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.model.TradingPost;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;
import org.mcgill.splendorapi.model.card.OrientDevelopmentCard;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;
import org.springframework.stereotype.Service;



/**
 * The loader class for the cards.
 */
@Service
public class ProducerService {
  private final List<DevelopmentCard> developmentCards;
  private final List<DevelopmentCard> orientCards;
  private final List<NobleCard> nobleCards;
  private final List<TradingPost> tradingPosts;
  private final List<City> cities;
  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * Deserialize all cards and other items generically.

   * @param stream The input stream
   *
   * @param conversion the conversion object
   * @param <T> The required type
   * @return The built (parsed) json object
   * @throws IOException If the items cannot be read
   */
  private static <T> T reader(InputStream stream, TypeReference<T> conversion) throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
    StringBuilder builder = new StringBuilder();
    String nextLine;
    while ((nextLine = bufferedReader.readLine()) != null) {
      builder.append(nextLine);
    }
    stream.close();

    return mapper.readValue(
      builder.toString(),
      conversion
    );
  }


  /**
   * Create the card loader bean.
   *
   * @param properties The properties
   */
  public ProducerService(AppProperties properties) throws IOException {
    // Loads the deck as an object that can be read into the list of cards as defined above
    InputStream cardStream = this.getClass()
                             .getClassLoader()
                             .getResourceAsStream(properties.getCardDefs());
    InputStream orientStream = this.getClass()
                                   .getClassLoader()
                                   .getResourceAsStream(properties.getOrientDefs());
    InputStream nobleStream = this.getClass()
                                  .getClassLoader()
                                  .getResourceAsStream(properties.getNobleDefs());
    InputStream tradingPostStream = this.getClass()
                                  .getClassLoader()
                                  .getResourceAsStream(properties.getTradingPostDefs());
    InputStream cityStream = this.getClass()
                                 .getClassLoader()
                                 .getResourceAsStream(properties.getCityDefs());

    assert cardStream != null && nobleStream != null
      && tradingPostStream != null && orientStream != null && cityStream != null;

    developmentCards = reader(cardStream, new TypeReference<>() {});

    orientCards = reader(orientStream, new TypeReference<>() {});

    nobleCards = reader(nobleStream, new TypeReference<>() {});

    tradingPosts = reader(tradingPostStream, new TypeReference<>() {});

    cities = reader(cityStream, new TypeReference<>() {});
  }

  /**
   * Gets the Cards.
   *
   * @return the Cards.
   */
  public List<DevelopmentCard> getCards() {
    return this.developmentCards.stream()
                                .map(DevelopmentCard::clone)
                                .collect(Collectors.toList());
  }

  /**
   * Gets the orient cards.
   */
  public List<OrientDevelopmentCard> getOrientCards() {
    return this.orientCards.stream().map(DevelopmentCard::clone)
                           .map(c -> (OrientDevelopmentCard) c)
                           .collect(Collectors.toList());
  }

  public List<NobleCard> getNobleCards() {
    return this.nobleCards.stream().map(NobleCard::clone).collect(Collectors.toList());
  }

  /**
   * Get all TrendingPosts.

   * @return a list of TrendingPosts
   */
  public List<TradingPost> getTradingPosts() {
    return this.tradingPosts;
  }

  /**
   * get all cities.

   * @return a list of cities
   */
  public List<City> getCities() {
    return this.cities.stream().map(card -> {
      try {
        return new City(card);
      } catch (InvalidCardType e) {
        throw new RuntimeException(e);
      }
    }).collect(Collectors.toList());
  }
}
