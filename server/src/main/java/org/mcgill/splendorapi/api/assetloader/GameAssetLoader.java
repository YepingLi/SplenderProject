package org.mcgill.splendorapi.api.assetloader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.mcgill.splendorapi.config.AppProperties;
import org.mcgill.splendorapi.config.AssetPath;
import org.mcgill.splendorapi.model.Asset;
import org.mcgill.splendorapi.model.AssetType;
import org.mcgill.splendorapi.model.GemType;
import org.springframework.stereotype.Service;

/**
 * Loads the Game Assets.
 */
@Service
@RequiredArgsConstructor
public class GameAssetLoader {
  private static final Pattern onlyValidNumbers = Pattern.compile("^[0-9]+$");
  private final AppProperties properties;

  /**
   * Checks the id is a number of throws an exception.
   *
   * @param id The id of the card
   * @return This object to continue the chaining
   */
  private GameAssetLoader validateNumberAndLoad(String id) {
    if (!onlyValidNumbers.matcher(id).matches()) {
      throw new NumberFormatException(
        String.format("Invalid number formatting %s. Id for this resource can only have ", id));
    }

    return this;
  }

  /**
   * Loads the resources requested. If resource invalid, should throw error.
   *
   * @param id    The id of the card
   * @param level level of the card
   * @return Object required
   */
  public byte[] loadCardAsset(String id, Integer level, AssetType type) throws IOException {
    level = Optional.ofNullable(level)
                    .orElseThrow(() -> new IllegalArgumentException("Must provide a level!"));
    // Get the path to the card
    List<Asset<?>> assetDefaults = new ArrayList<>(List.of(new Asset<>(id, "id"),
                                                           new Asset<>(level, "level")));
    if (type.equals(AssetType.Orient)) {
      assetDefaults.add(new Asset<>(type.name(), "name"));
    }
    return validateNumberAndLoad(id).loadAsset(properties.getPathToCards(), assetDefaults);
  }

  /**
   * Load the noble/tradingpost as a byte string. To be sent over the network
   *
   * @param id The id of the noble card
   * @return byte array of the noble
   * @throws IOException input output exception
   */
  public byte[] loadNobleTradingPostAsset(String id,
                                          AssetType type) throws IOException {
    AssetPath path = type.equals(AssetType.Noble)
        ?
        properties.getPathToNobles() : properties.getPathToTradingPosts();
    return validateNumberAndLoad(id).loadAsset(path, List.of(new Asset<>(Short.parseShort(id),
                                                                         "id")));
  }

  public byte[] loadCityAsset(String id, AssetType type) throws IOException {
    return loadAsset(properties.getPathToCities(), List.of(new Asset<>(id, "id")));
  }

  /**
   * Retrieves the assets based on if we need a noble or not.
   *
   * @param id The replacement elements
   * @return Loaded asset if it exists
   */
  public byte[] loadTokenAsset(String id) throws IOException {
    return loadAsset(properties.getPathToTokens(),
                     List.of(new Asset<>(GemType.valueOf(id.toUpperCase())
                                                .name()
                                                .toLowerCase(),
                                         "id")));
  }

  /**
   * load background asset.
   *
   * @param id id
   * @return list of bytes
   * @throws IOException input output exception
   */
  public byte[] loadBackgroundAsset(String id) throws IOException {
    return loadAsset(properties.getPathToBackgrounds(), Collections.emptyList());
  }


  /**
   * Loads generically all the assets given the replacements.
   *
   * @param pathToAsset  The path object to the asset
   * @param replacements The replacements
   * @return The loaded asset
   */
  public byte[] loadAsset(AssetPath pathToAsset, List<Asset<?>> replacements)
      throws IOException {
    String safeUri = pathToAsset.replace(
        replacements.stream()
                  .map(Asset::asView)
                  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
    );

    try (
        InputStream stream = Optional.ofNullable(this.getClass()
                                                   .getClassLoader()
                                                   .getResourceAsStream(safeUri))
                                   .orElseThrow(() -> new IOException(
                                     String.format("unable to read file at path %s!", safeUri)))) {
      return stream.readAllBytes();
    }
  }
}