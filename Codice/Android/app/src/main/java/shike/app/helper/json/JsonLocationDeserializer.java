package shike.app.helper.json;

import android.location.Location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Deserializzatore per oggetti di tipo Location
 */
public class JsonLocationDeserializer implements JsonDeserializer<Location> {
	@Override
	public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException {
		Location location = new Location("jsonDeserializer");
		JsonObject jsonObject = json.getAsJsonObject();

		location.setLatitude(jsonObject.get("latitude").getAsDouble());
		location.setLongitude(jsonObject.get("longitude").getAsDouble());
		location.setAltitude(jsonObject.get("altitude").getAsDouble());
		return location;
	}
}
