package org.mcgill.splendorapi.api.assetloader;

import com.sun.jdi.request.InvalidRequestStateException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcgill.splendorapi.model.AssetType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Loads the Card, Noble and Trading Post assets.
 */
@Slf4j
@RestController("Asset Loader Controller")
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetLoaderController {
  private final GameAssetLoader loader;

  /**
   * Request exception handler. Log the error as well!
   *
   * @return Response telling the user the request was bad/invalid
   */
  @ExceptionHandler({MissingRequestValueException.class,
                     IOException.class,
                     InvalidRequestStateException.class})
  public ResponseEntity<String> handleException(Exception err) {
    log.error("Failed to handle request. Either bad request or file was not found!", err);
    return ResponseEntity.status(400).body("Unable to handle request!");
  }

  /**
   * Handle the exception when user requests an id which is not a number,
   * and we converted to a number.
   *
   * @return Response telling the user the request was bad/invalid and that a number is required
   */
  @ExceptionHandler({NumberFormatException.class, IllegalArgumentException.class})
  public ResponseEntity<String> handleExceptionDuringValidation(Exception err) {
    log.error("Number is required!", err);
    return ResponseEntity.status(400).body(err.getMessage());
  }

  /**
   * Dispatch and return the raw byte array of the data.
   *
   * @param assetId The asset id of the object
   * @param id The id of the item
   * @param level the level (if required)
   * @return byte array of the file
   * @throws MissingRequestValueException if we are missing inputs
   * @throws IOException If the file cannot be read
   */
  byte[] dispatch(AssetType assetId, String id, Integer level)
      throws MissingRequestValueException, IOException {
    switch (assetId) {
      case Card:
      case Orient:
        return loader.loadCardAsset(id, level, assetId);
      case Noble:
      case TradingPost:
        return loader.loadNobleTradingPostAsset(id, assetId);
      case Token:
        return loader.loadTokenAsset(id);
      case City:
        return loader.loadCityAsset(id, assetId);
      case Background:
        return loader.loadBackgroundAsset(id);
      default:
        throw new MissingRequestValueException(
          "Unknown request parameter type for asset requested");
    }
  }

  /**
   * Get the asset or throw an error (either missing param or file could not be loaded).
   * Log the error
   *
   * @param assetId The type id of the asset
   * @param id      The id of the asset
   * @param level   the level (if it has one)
   * @return The asset loaded as a response body
   * @throws MissingRequestValueException If invalid request
   * @throws IOException                  If the file doesn't exist or can't be read
   * @throws InvalidRequestStateException If the state is invalid
   */
  @GetMapping("/{assetId}")
  public ResponseEntity<byte[]> loadAsset(@PathVariable AssetType assetId,
                                          @RequestParam("id") String id,
                                          @RequestParam(value = "level", required = false)
                                          Integer level)
      throws MissingRequestValueException, IOException, InvalidRequestStateException {
    byte[] body = dispatch(assetId, id, level);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(body);
  }

}