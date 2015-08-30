package shike.app.helper.json;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import shike.app.model.sync.AccountLinkData;
import shike.app.model.sync.SyncDataApp;
import shike.app.model.sync.SyncDataWeb;

/**
 * Classe contenente metodi statici per la conversione da/a JSON per i dati da sincronizzare
 */
public class JsonConverter {
	/**
	 * Convertitore di oggetti java verso JSON e viceversa
	 */
	private static Gson gson = null;

	/**
	 * Costruttore privato, per impedire l'istanziazione della classe.
	 */
	private JsonConverter() {}

	/**
	 * Converte i dati da inviare alla piattaforma web in json
	 *
	 * @param context contesto dell'applicazione, necessario per istanziare SyncDataApp
	 * @return stringa contenente l'oggetto SyncDataApp da inviare in formato JSON
	 */
	public static String generateSyncJson(Context context) {
		Gson gson = getGson();
		SyncDataApp syncDataApp = new SyncDataApp(context);
		return gson.toJson(syncDataApp);
	}

	/**
	 * Getter con inizializzazione "lazy" di gson
	 *
	 * @return riferimento all'attributo gson inizializzato
	 */
	private static Gson getGson() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Location.class, new JsonLocationSerializer());
			gsonBuilder.registerTypeAdapter(Location.class, new JsonLocationDeserializer());
			gsonBuilder.registerTypeAdapter(Date.class, new JsonDateSerializer());
			gsonBuilder.registerTypeAdapter(Date.class, new JsonDateDeserializer());
			gson = gsonBuilder.create();
		}
		return gson;
	}

	/**
	 * Converte il JSON ricevuto dalla piattaforma web in un oggetto SyncDataWeb
	 *
	 * @param json stringa contenente il JSON ricevuto
	 * @return nuova istanza di SyncDataWeb contenente i dati ricevuti dalla piattaforma web.
	 */
	public static SyncDataWeb convertToSyncDataWeb(String json) {
		Gson gson = getGson();
		return gson.fromJson(json, SyncDataWeb.class);
	}

	/**
	 * Converte il JSON ricevuto dalla piattaforma web in un oggetto AccountLinkData
	 *
	 * @param json stringa contenente il JSON ricevuto
	 * @return nuova istanza di AccountLinkData contenente i dati ricevuti dalla piattaforma web.
	 */
	public static AccountLinkData convertToAccountLinkData(String json) {
		Gson gson = getGson();
		return gson.fromJson(json, AccountLinkData.class);
	}
}
