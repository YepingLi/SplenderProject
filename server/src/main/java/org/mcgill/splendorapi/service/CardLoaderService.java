package org.mcgill.splendorapi.service;

import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.model.Card;
import org.springframework.stereotype.Service;

/**
 * The loader class for the cards.
 */
@Getter
public class CardLoaderService {
  private final CardRoot loaded;
  private final AppProperties properties;

  /**
   * Class for parsing the card objects.
   */
  @XmlRootElement(name = "cards")
  @XmlAccessorType(XmlAccessType.PROPERTY)
  static class CardRoot {
    @XmlElement(name = "card")
    List<Card> cards;
  }

  /**
   * Create the card loader bean.
   *
   * @param props The properties
   * @throws JAXBException If the file is unreadable
   */
  public CardLoaderService(AppProperties props) throws JAXBException {
    properties = props;
    JAXBContext jaxb = JAXBContext.newInstance(CardRoot.class);
    loaded = (CardRoot) jaxb.createUnmarshaller()
                            .unmarshal(CardLoaderService.class
                                           .getResourceAsStream(properties.getCardDefs()));
  }
}
