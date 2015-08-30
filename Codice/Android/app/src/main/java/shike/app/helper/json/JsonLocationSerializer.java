package shike.app.helper.json;

import android.location.Location;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Admin on 05/06/2015.
 */
public class JsonLocationSerializer implements JsonSerializer<Location> {
	@Override
	public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject locationJson = new JsonObject();
		locationJson.addProperty("latitude", src.getLatitude());
		locationJson.addProperty("longitude", src.getLongitude());
		locationJson.addProperty("altitude", src.getAltitude());
		return locationJson;
	}
}
