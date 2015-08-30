package com.wearit.shike.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wearit.shike.web.model.service.AccountService;
import com.wearit.shike.web.model.service.HelpNumberService;
import com.wearit.shike.web.model.service.PoiService;
import com.wearit.shike.web.model.service.RecordedTrackService;
import com.wearit.shike.web.model.service.VirtualTrackService;
import com.wearit.shike.web.model.service.WeatherService;
import com.wearit.shike.web.model.session.performance.Performance;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.session.track.VirtualTrack;
import com.wearit.shike.web.model.sync.AccountLinkData;
import com.wearit.shike.web.model.sync.SyncDataApp;
import com.wearit.shike.web.model.sync.SyncDataWeb;
import com.wearit.shike.web.model.sync.SyncDataWeb.Error;
import com.wearit.shike.web.model.user.CommonAccount;
import com.wearit.shike.web.model.weather.TrackWeather;

@RestController
@RequestMapping("/sync")
public class SyncController {

	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	public SyncDataWeb updateSync(@RequestBody SyncDataApp sda) {
		SyncDataWeb sw = new SyncDataWeb();
		//
		try {
			UUID token = sda.getConnectionToken();
			AccountService as = new AccountService();
			CommonAccount ca = as.getCommonByToken(token);

			if(ca == null) {
				sw.setError(Error.TOKEN_NOT_FOUND);
				return sw;
			}

			sw.setAccount(ca);
			// new sync
			int isync = as.getNewSyncNum(ca.get_id());

			RecordedTrackService rts = new RecordedTrackService();
			List<Performance> lperf = sda.getPerformances();
			for(Performance p : lperf) {
				if(!rts.addPerformance(p, ca.get_id(), isync)) { // fallito l'inserimento
																	// della Performance
					sw.setError(Error.SAVE_FAILED);
					return sw;
				}
			}

			HelpNumberService hns = new HelpNumberService();
			sw.setHelpNumbers(hns.getAll(ca.get_id()));

			VirtualTrackService vts = new VirtualTrackService();
			WeatherService ws = new WeatherService();
			List<VirtualTrack> lvt = vts.getAllToSync(ca.get_id());
			List<TrackWeather> ltw = new ArrayList<TrackWeather>();
			for(VirtualTrack vt : lvt) {
				int auxid = vt.get_id();
				vt.setPoints(vts.getAllPoints(auxid));
				vt.setCenter(vts.getCenter(auxid));
				ltw.add(ws.getTrackWeather(auxid));
			}
			sw.setVirtualTracks(lvt);

			PoiService ps = new PoiService();
			List<Poi> lp = ps.getAll();
			sw.setPois(lp);

			// forecast
			sw.setForecasts(ltw);

			sw.setError(Error.NONE); // nessun errore yeppa!
		} catch(Exception e) {
			sw.setError(Error.SYNC_FAILED);
		}

		return sw;
	}

	@RequestMapping(value = "/connect", method = { RequestMethod.GET, RequestMethod.POST })
	public AccountLinkData connectDevice() {
		// creo un nuovo accountlink
		AccountService as = new AccountService();
		return as.generateNewAccLink();
	}

}
