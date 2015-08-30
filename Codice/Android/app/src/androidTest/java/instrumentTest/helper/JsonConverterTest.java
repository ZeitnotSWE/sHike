package instrumentTest.helper;

import android.test.AndroidTestCase;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import instrumentTest.model.dao.PerformanceDaoImplTest;
import shike.app.helper.json.JsonConverter;
import shike.app.model.service.AccountService;
import shike.app.model.service.PerformanceService;
import shike.app.model.session.performance.Performance;
import shike.app.model.sync.AccountLinkData;
import shike.app.model.sync.SyncDataApp;
import shike.app.model.user.Account;

/**
 * Classe di test di {@link JsonConverter}
 */
public class JsonConverterTest extends AndroidTestCase {

	public void testAccountLinkDataConversion() {
		String json = "{\"oneTimeCode\":\"80774\",\"connectionToken\":\"85837cd7-8544-4e4c-abca" +
			"-fd48f38c1f4d\"}";
		String oneTimeCode = "80774";
		UUID token = UUID.fromString("85837cd7-8544-4e4c-abca-fd48f38c1f4d");

		AccountLinkData accountLinkData = JsonConverter.convertToAccountLinkData(json);

		assertEquals(oneTimeCode, accountLinkData.getOneTimeCode());
		assertEquals(token, accountLinkData.getConnectionToken());
	}

}
