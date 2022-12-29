package org.mcgill.splendorapi.connector.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestScope {
  ObjectMapper mapper;
  Scope.ScopeDeserializer deserializer;

  @BeforeEach
  public void setUp(){
    mapper = new ObjectMapper();
    deserializer = new Scope.ScopeDeserializer();
  }

  @Test
  public void testBuild(){
    List<String> list = new ArrayList<>();
    list.add("tempt");
    Scope scope = Scope.builder().scopes(new HashSet<>(list)).build();
    assertThat(scope.getScopes()).containsAll(list);
  }

  @SneakyThrows({IOException.class})
  public Scope deserialize(String json) {
    InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
    JsonParser parser = mapper.getFactory().createParser(stream);
    DeserializationContext ctxt = mapper.getDeserializationContext();
    return deserializer.deserialize(parser, ctxt);
  }

  @Test
  public void testDeserialization(){
    String json = "\"read write\"";
    Scope scope = deserialize(json);
    Assertions.assertArrayEquals(new String[]{"read", "write"}, scope.getScopes().toArray());
  }
}
