package org.mcgill.splendorapi.model.board;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.mcgill.splendorapi.model.GameType;
import org.mcgill.splendorapi.model.GemType;
import org.mcgill.splendorapi.model.card.CardState;
import org.mcgill.splendorapi.model.card.City;
import org.mcgill.splendorapi.model.card.DevelopmentCard;
import org.mcgill.splendorapi.model.card.NobleCard;

/**
 * Represents the game board with Orient and Trading post extensions.
 */
@Jacksonized
@Getter
@SuperBuilder
public class CityGameBoard extends OrientGameBoard {
  protected final List<City> cities;

  public GameType fixType(GameType t) {
    return Optional.ofNullable(t).orElse(GameType.CITIES_ORIENT);
  }
}

