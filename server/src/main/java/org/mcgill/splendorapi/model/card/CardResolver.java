package org.mcgill.splendorapi.model.card;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.AsPropertyTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.type.SimpleType;
import java.io.IOException;
import java.util.Collection;
import org.mcgill.splendorapi.model.exceptions.InvalidCardType;


/**
 * Class to deserialize polymorphic json types
 * Code found here: https://stackoverflow.com/questions/36202637/jackson-polymorphic-deserialization-with-type-property-that-is-nested-in-object
 */
public class CardResolver extends StdTypeResolverBuilder {

  /**
   * Most of the juice is found here. We build the object using this class which can parse
   * the json nodes to get the proper subtype back
   */
  static class CardTypeDeserializer extends AsPropertyTypeDeserializer {

    public CardTypeDeserializer(JavaType bt, TypeIdResolver idRes, String typePropertyName,
                                boolean typeIdVisible, JavaType defaultImpl) {
      super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public CardTypeDeserializer(
          final AsPropertyTypeDeserializer src, final BeanProperty property) {
      super(src, property);
    }

    @Override
    public TypeDeserializer forProperty(
          final BeanProperty prop) {
      return (prop == _property) ? this : new CardTypeDeserializer(this, prop);
    }

    @Override
    public Object deserializeTypedFromObject(
          final JsonParser jp, final DeserializationContext ctxt) throws IOException {
      JsonNode node = jp.readValueAsTree();
      Class<?> subType;
      try {
        subType = findSubType(node);
      } catch (InvalidCardType e) {
        throw new IOException(e.getCause());
      }
      JavaType type = SimpleType.construct(subType);

      JsonParser jsonParser = new TreeTraversingParser(node, jp.getCodec());
      if (jsonParser.getCurrentToken() == null) {
        jsonParser.nextToken();
      }
      /* 16-Dec-2010, tatu: Since nominal type we get here has no (generic) type parameters,
       *   we actually now need to explicitly narrow from base type
       * (which may have parameterization) using raw type.
       *
       *   One complication, though; can not change 'type class' (simple type to container);
       * otherwise
       *   we may try to narrow a SimpleType (Object.class) into MapType (Map.class), losing actual
       *   type in process (getting SimpleType of Map.class which will not work as expected)
       */
      if (_baseType != null && _baseType.getClass() == type.getClass()) {
        type = _baseType.forcedNarrowBy(type.getRawClass());
      }
      JsonDeserializer<Object> deser = ctxt.findContextualValueDeserializer(type, _property);
      return deser.deserialize(jsonParser, ctxt);
    }

    protected Class<?> findSubType(JsonNode node) throws InvalidCardType {
      String cardType = node.get("meta").get("type").asText();
      CardType type = CardType.fromValue(cardType);
      switch (type) {
        case NOBLE:
          return NobleCard.class;
        case DEVELOPMENT:
          return DevelopmentCard.class;
        case ORIENT:
          return OrientDevelopmentCard.class;
        case CITY:
          return City.class;
        default:
          return null;
      }
    }
  }

  @Override
  public TypeDeserializer buildTypeDeserializer(DeserializationConfig config, JavaType baseType,
                                                Collection<NamedType> subtypes) {
    return new CardTypeDeserializer(baseType, null,
                                    _typeProperty, _typeIdVisible, defineDefaultImpl(config,
                                                                                     baseType));
  }
}
