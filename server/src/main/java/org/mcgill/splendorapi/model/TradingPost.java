package org.mcgill.splendorapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.mcgill.splendorapi.model.card.CardMeta;

/**
 * Represents the Trading Post object.
 */
@Getter
public class TradingPost {
  private final CardMeta meta;
  private final PowerType power;
  //private List<ColorType> markers;
  private final Map<Bonus, Integer> requirements;

  @JsonIgnore
  private List<Color> coats;


  /**
   * Constructor.
   */
  @JsonCreator
  public TradingPost(@JsonProperty("meta") CardMeta meta,
                     @JsonProperty("power") PowerType powerType,
                     @JsonProperty("requirements") Map<Bonus, Integer> postRequirements) {
    this.meta = meta;
    requirements = postRequirements;
    power = powerType;
    this.coats = new ArrayList<>();
  }

  /**
   * Adds a player's colour to the trading post to indicate they have the power.
   *
   * @param color the unique player color.
   */
  public void addCoat(Color color) {
    this.coats.add(color);
  }

  public void removeCoat(Color color) {
    this.coats.remove(color);
  }
}
