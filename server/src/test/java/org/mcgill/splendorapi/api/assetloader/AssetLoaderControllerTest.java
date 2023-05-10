package org.mcgill.splendorapi.api.assetloader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgill.splendorapi.model.AssetType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;

@ExtendWith(MockitoExtension.class)
public class AssetLoaderControllerTest {

  private AssetLoaderController assetLoaderController;

  @Mock private GameAssetLoader loader;

  @BeforeEach
  void setUp() {
    assetLoaderController = new AssetLoaderController(loader);
  }

  @Test
  void givenCardAssetType_whenLoadAsset_thenReturnsPngImage() throws IOException, MissingRequestValueException {
    byte[] assetData = new byte[] { 0x10, 0x11, 0x12 };
    when(loader.loadCardAsset(anyString(), anyInt(), any())).thenReturn(assetData);

    ResponseEntity<byte[]> response =
      assetLoaderController.loadAsset(AssetType.Card, "card_id", 1);

    assert response.getStatusCode().equals(HttpStatus.OK);
    assert response.getHeaders().getContentType().equals(MediaType.IMAGE_PNG);
    assert response.getBody().equals(assetData);
  }
  @Test
  void givenException_whenHandleException_thenReturnsBadRequestResponse() {
    Exception exception = new Exception("Some error occurred");
    AssetLoaderController assetLoaderController = new AssetLoaderController(mock(GameAssetLoader.class));

    ResponseEntity<String> response = assetLoaderController.handleException(exception);

    assert response.getStatusCode().equals(HttpStatus.BAD_REQUEST);
    assert response.getBody().equals("Unable to handle request!");
  }
  @Test
  public void givenCardAssetType_whenDispatch_thenCallsLoadCardAsset() throws IOException, MissingRequestValueException {
    AssetLoaderController assetLoaderController = new AssetLoaderController(loader);
    byte[] assetData = new byte[] { 0x50, 0x4e, 0x47 };
    when(loader.loadCardAsset(anyString(), anyInt(), any())).thenReturn(assetData);

    byte[] result = assetLoaderController.dispatch(AssetType.Card, "card_id", 1);
    Assertions.assertArrayEquals(assetData, result);
  }

}

