package com.wearit.shike.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.VirtualTrackService;
import com.wearit.shike.web.model.service.WeatherService;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.session.track.VirtualTrack;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.weather.TrackWeather;

@Controller
@RequestMapping(value = { "/user/virtualtracks", "/admin/virtualtracks" })
public class VirtualTrackController {
	private int currentId;
	private VirtualTrackService virtualTrackService;

	/**
	 * Costruttore di default
	 */
	public VirtualTrackController() {
	}

	/**
	 * @param currentId
	 */
	public VirtualTrackController(int currentId) {
		this.currentId = currentId;
	}

	@Autowired
	public void setVirtualTrackService(VirtualTrackService virtualTrackService) {
		this.virtualTrackService = virtualTrackService;
	}

	private void setCurrentId() {
		Account info = (Account) SecurityContextHolder.getContext().getAuthentication()
				.getDetails();
		this.currentId = info.get_id();
	}

	@RequestMapping("/")
	public String listOfTracks(Model model) {
		if(currentId == 0) {
			this.setCurrentId();
		}
		List<VirtualTrack> ls = virtualTrackService.getAll(currentId);
		model.addAttribute("vt", ls);
		return "track/virtual/ListTrack";
	}

	@RequestMapping(value = "/change-sync-track/{track_id}", method = RequestMethod.GET)
	public String listOfTracksToogle(Model model, @PathVariable("track_id") int track_id) {
		if(currentId == 0) {
			this.setCurrentId();
		}
		virtualTrackService.changeSync(currentId, track_id);
		List<VirtualTrack> ls = virtualTrackService.getAll(currentId);
		model.addAttribute("vt", ls);
		return "track/virtual/ListTrack";
	}

	@RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
	public String showTrack(Model model, @PathVariable("id") int id) {
		VirtualTrack vt = virtualTrackService.getById(id);
		List<Location> ltp = virtualTrackService.getAllPoints(id);
		List<Poi> lpoi = virtualTrackService.getAllPoi();
		WeatherService ws = new WeatherService();
		TrackWeather tw = ws.getTrackWeather(id);

		model.addAttribute("vt", vt);
		model.addAttribute("ltp", ltp);
		model.addAttribute("lpoi", lpoi);
		model.addAttribute("tw", tw);
		return "track/virtual/DetailsTrack";
	}

	@RequestMapping(value = "/editname/{id}", method = RequestMethod.GET)
	public String editTrackName(Model model, @PathVariable("id") int id) {
		VirtualTrack vt = virtualTrackService.getById(id);
		List<Location> ltp = virtualTrackService.getAllPoints(id);
		model.addAttribute("vt", vt);
		model.addAttribute("ltp", ltp);
		return "track/virtual/EditTrack";
	}

	@RequestMapping(value = "/editname/{id}", method = RequestMethod.POST)
	public String editTrackNameConfirm(Model model,
			@RequestParam(value = "name", required = true) String newname,
			@PathVariable("id") int id) {
		VirtualTrack vt = virtualTrackService.getById(id);
		if((newname.length() < 3) || (newname.length() > 60)) {
			Message errore = new Message("Modifica fallita",
					"Il nuovo nome è troppo corto o troppo lungo.", 1);
			model.addAttribute("msg", errore);
			return "./inc/Message";
		}

		vt.setName(newname);
		try {
			virtualTrackService.update(vt);
			model.addAttribute("msg", new Message("Modifica completata",
					"Modifica del nome completata."));
		} catch(Exception e) {
			Message errore = new Message("Modifica fallita", "Il nuovo nome non è corretto.", 1);
			model.addAttribute("msg", errore);
		}

		return "./inc/Message";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTrack(Model model, @PathVariable("id") int id) {
		try {
			virtualTrackService.delete(id);
			Message msg = new Message("Eliminazione completata",
					"Eliminazione del tracciato completata con successo.");
			model.addAttribute("msg", msg);
		} catch(Exception e) {
			Message errore = new Message("Eliminazione fallita",
					"Non è stato possibile rimuovere il percorso.", 1);
			model.addAttribute("msg", errore);
		}

		return "./inc/Message";
	}

}
