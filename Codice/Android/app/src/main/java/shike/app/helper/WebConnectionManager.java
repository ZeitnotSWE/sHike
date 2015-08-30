package shike.app.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.http.AndroidHttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import shike.app.helper.json.JsonConverter;
import shike.app.model.dao.db.DbUtil;
import shike.app.model.service.AccountService;
import shike.app.model.service.GeneralService;
import shike.app.model.service.HelpNumberService;
import shike.app.model.service.PerformanceService;
import shike.app.model.service.PoiService;
import shike.app.model.service.TrackWeatherService;
import shike.app.model.service.VirtualTrackService;
import shike.app.model.sync.AccountLinkData;
import shike.app.model.sync.SyncDataWeb;
import shike.app.model.user.Account;

/**
 * Classe contenente metodi statici che consentono il collegamento alla piattaforma web e la
 * sincronizzazione
 */
public class WebConnectionManager {
	private static String SYNC_URL = "http://79.30.209.122:8080/web/sync/update";
	private static String CONN_URL = "http://79.30.209.122:8080/web/sync/connect";
	//private static final String SYNC_URL = "http://127.0.0.1:8080/web/sync/update";
	//private static final String CONN_URL = "http://127.0.0.1:8080/web/sync/connect";
	private static AndroidHttpClient client;

	/**
	 * Costruttore privato: la classe non ha bisogno di essere istanziata
	 */
	private WebConnectionManager() {}

	public static void setSyncUrl(String syncUrl){
		SYNC_URL = syncUrl;
	}

	public static void setConnUrl(String connUrl) {
		CONN_URL = connUrl;
	}

	/**
	 * Effettua tutte le azioni necessarie per sincronizzare l'applicazione con la piattaforma
	 * web. Viene utilizzato il token di connessione per confermare l'identita dell'utente che
	 * richiede la sincronizzazione. Se il token è errato (perchè l'account è stato scollegato
	 * dall'app o il dispositivo è stato scollegato dal web), i dati memorizzati nel database
	 * dell'applicazione vengono cancellati (token compreso), riportando l'app allo stato iniziale.
	 *
	 * @param context contesto dell'applicazione, necessario per l'accesso al database
	 * @return true se la sincronizzazione è andata a buon fine, false se si è verificato un errore
	 */
	public static Result sync(Context context) {
		client = AndroidHttpClient.newInstance("WearIT");
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
		HttpConnectionParams.setSoTimeout(client.getParams(), 15000);

		HttpPost post = new HttpPost(SYNC_URL);
		String json = JsonConverter.generateSyncJson(context);
		AccountService accountService = new AccountService(context);
		Account account = accountService.get();

		if (account != null) {
			String response = null;
			try {
				StringEntity entity = new StringEntity(json);
				entity.setContentType("application/json;charset=UTF-8");
				entity.setContentEncoding(
					new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

				post.setEntity(entity);
				response = client.execute(post, new BasicResponseHandler());
			} catch (UnsupportedEncodingException uex) {
				uex.printStackTrace();
				return Result.INTERNAL_DATA_ERROR;
			} catch (Exception ex) {
				ex.printStackTrace();
				return Result.CONNECTION_ERROR;
			} finally {
				client.close();
			}

			if (response != null && response.length() > 0) {
				SQLiteDatabase db = DbUtil.getInstance(context).getWritableDatabase();
				VirtualTrackService virtualTrackService = new VirtualTrackService
					(context);
				PoiService poiService = new PoiService(context);
				TrackWeatherService trackWeatherDaoService = new TrackWeatherService
					(context);
				HelpNumberService helpNumberService = new HelpNumberService(context);


				db.beginTransaction();
				Result result = Result.SUCCESS;
				try {
					SyncDataWeb dataWeb = JsonConverter.convertToSyncDataWeb(response);
					if (dataWeb.hasError()) {
						switch (dataWeb.getError()) {
							case TOKEN_NOT_FOUND:
								new GeneralService(context).resetAll();
								result = Result.UNLINKED;
								break;
							default:
								result = Result.WEB_ERROR;
								break;
						}
					} else {
						dataWeb.getAccount()
							.setConnectionToken(accountService.getConnectionToken());
						accountService.set(dataWeb.getAccount());
						virtualTrackService.add(dataWeb.getVirtualTracks());
						poiService.add(dataWeb.getPois());
						trackWeatherDaoService.add(dataWeb.getForecasts());
						helpNumberService.add(dataWeb.getHelpNumbers());
					}

					new PerformanceService(context).removeAll();
					db.setTransactionSuccessful();
				} catch (Exception ex) {
					ex.printStackTrace();
					result = Result.RECEIVED_DATA_ERROR;
				} finally {
					db.endTransaction();
				}
				return result;
			}
			return Result.RECEIVED_DATA_ERROR;
		}
		return Result.NOT_LINKED_ERROR;
	}

	/**
	 * Richiede al server il codice da utilizzare per effettuare il collegamento dell'account al
	 * dispositivo, e un token di connessione temporaneo. Il codice verrà mostrato a schermo
	 * durante la prima connessione e l'utente dovrà immetterlo nella sua pagina della piattaforma
	 * web. Se il codice inserito è corretto, il collegamento è completato e il token di
	 * connessione ricevuto dal web viene associato all'account da cui è stato inserito. Se il
	 * codice è errato, il token viene cancellato dopo 1 giorno.
	 *
	 * @return codice usa e getta numerico di 5 cifre. Se si sono verificati problemi nella
	 * comunicazione con la piattaforma web, ritorna null. Se un account è già presente
	 */
	public static String linkAccount(Context context) {
		client = AndroidHttpClient.newInstance("WearIT");
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
		HttpConnectionParams.setSoTimeout(client.getParams(), 15000);
		
		HttpPost post = new HttpPost(CONN_URL);

		String response = null;
		try {
			response = client.execute(post, new BasicResponseHandler());
		} catch (Exception ex) {
			ex.printStackTrace();
			return Result.CONNECTION_ERROR.toString();
		} finally {
			client.close();
		}

		if (response != null && response.length() > 0) {
			AccountLinkData data = JsonConverter.convertToAccountLinkData(response);

			if (data != null && data.getConnectionToken() != null
				&& data.getOneTimeCode() != null && data.getOneTimeCode().matches("^\\d{5}$")) {
				AccountService accountService = new AccountService(context);
				UUID savedToken = accountService.getConnectionToken();

				if (savedToken == null) {
					Account tempAccount = Account.createTempAccount(data.getConnectionToken());
					accountService.set(tempAccount);
					return data.getOneTimeCode();
				} else {
					return Result.ALREADY_LINKED_ERROR.toString();
				}

			}
		}
		return Result.RECEIVED_DATA_ERROR.toString();
	}

	/**
	 * Scollega l'account dall'applicazione, cancellando di conseguenza tutti i dati utente.
	 *
	 * @param context contesto dell'applicazione
	 * @return true se ha cancellato dati, false altrimenti (potrebbe essere perchè non c'era
	 * niente da cancellare)
	 */
	public static boolean unlink(Context context) {
		return new GeneralService(context).resetAll();
	}

	/**
	 * Enum che indica i possibili risultati ottenibili da un'operazione di sincronizzazione o
	 * collegamento account
	 */
	public enum Result {
		SUCCESS(0, "Operazione riuscita"),
		CONNECTION_ERROR(1,
			"Errore di connessione."),
		ALREADY_LINKED_ERROR(2, "Sei già collegato ad un account."),
		NOT_LINKED_ERROR(3, "Non sei collegato ad un account."),
		INTERNAL_DATA_ERROR(4, "Errore interno dell'applicazione."),
		RECEIVED_DATA_ERROR(5, "Errore nei dati ricevuti."),
		UNLINKED(6, "L'account è stato scollegato."),
		WEB_ERROR(7, "Errore remoto.");

		private int code;
		private String message;

		Result(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		@Override
		public String toString() {
			return code + ": " + message;
		}
	}
}


