package shike.app.helper.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Serializzatore personalizzato di Date a json. Creato perchè il serializzatore standard tronca le
 * informazioni sui millisecondi della data
 */
public class JsonDateSerializer implements JsonSerializer<Date> {
	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getTime());
	}
}
