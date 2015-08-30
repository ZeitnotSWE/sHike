package com.wearit.shike.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import com.wearit.shike.web.helper.sharing.Share;
import com.wearit.shike.web.helper.sharing.ShareFacebook;
import com.wearit.shike.web.helper.sharing.ShareTwitter;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.RecordedTrackService;
import com.wearit.shike.web.model.service.VirtualTrackService;
import com.wearit.shike.web.model.session.track.Location;
import com.wearit.shike.web.model.session.track.RecordedTrack;
import com.wearit.shike.web.model.session.track.VirtualTrack;
import com.wearit.shike.web.model.user.Account;

@Controller
public class RecordedTrackController {

	private Account currentUser;
	private RecordedTrackService recordedTrackService;

	@Autowired
	public void setAccountService(RecordedTrackService recordedTrackService) {
		this.recordedTrackService = recordedTrackService;
	}

	/**
	 * Costruttore di default
	 */
	public RecordedTrackController() {
	}

	/**
	 * @param currentUser
	 */
	public RecordedTrackController(int currentUser) {
		this.currentUser = new Account();
		this.currentUser.set_id(currentUser);
	}

	private void setAccount() {
		this.currentUser = (Account) SecurityContextHolder.getContext().getAuthentication()
				.getDetails();
	}

	@RequestMapping("/user/tracks/")
	public String listOfTracks(Model model, HttpServletRequest request) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		List<RecordedTrack> listTracks = recordedTrackService.getAll(currentUser.get_id());
		Share fbShare = new ShareFacebook();
		Share twShare = new ShareTwitter();
		String currentUrl = request.getRequestURL().toString();
		currentUrl = currentUrl.replace("/user", "");
		model.addAttribute("fbLink", fbShare.share(currentUrl) + currentUser.get_id() + "/");
		model.addAttribute("twLink", twShare.share(currentUrl) + currentUser.get_id() + "/");
		model.addAttribute("tracks", listTracks);
		return "track/recorded/ListTrack";
	}

	@RequestMapping(value = "/user/tracks/delete/{id}/{sync}/", method = RequestMethod.GET)
	public String delete(Model model, @PathVariable("id") int id,
			@PathVariable("sync") int syncNumber) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		try {
			recordedTrackService.getById(id, currentUser.get_id(), syncNumber);
			recordedTrackService.delete(id, currentUser.get_id(), syncNumber);
			model.addAttribute("msg", new Message("Eliminazione completata",
					"Eliminazione del tracciato selezionato completata."));
			return "./inc/Message";
		} catch(DataAccessException dae) {
			model.addAttribute("msg", new Message("Eliminazione non riuscita",
					"Il tracciato selezionato non è stato trovato.", 1));
			return "./inc/Message";
		}
	}

	@RequestMapping(value = "/user/tracks/show/{id}/{sync}/", method = RequestMethod.GET)
	public String showTrack(Model model, @PathVariable("id") int id,
			@PathVariable("sync") int syncNumber) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		RecordedTrack track = recordedTrackService.getById(id, currentUser.get_id(), syncNumber);
		List<Location> listPoint = recordedTrackService.getAllPoints(id, currentUser.get_id(),
				syncNumber);
		model.addAttribute("track", track);
		model.addAttribute("listPoint", listPoint);
		model.addAttribute("stats",
				recordedTrackService.getPerformanceById(id, currentUser.get_id(), syncNumber));
		return "track/recorded/DetailsTrack";
	}

	@RequestMapping(value = "/tracks/{user}/show/{id}/{sync}/", method = RequestMethod.GET)
	public String showTrackShared(Model model, @PathVariable("id") int id,
			@PathVariable("sync") int syncNumber, @PathVariable("user") int user) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		RecordedTrack track = recordedTrackService.getById(id, user, syncNumber);
		List<Location> listPoint = recordedTrackService.getAllPoints(id, user, syncNumber);
		model.addAttribute("track", track);
		model.addAttribute("listPoint", listPoint);
		model.addAttribute("stats", recordedTrackService.getPerformanceById(id, user, syncNumber));
		return "track/recorded/DetailsTrack";
	}

	@RequestMapping(value = "/user/tracks/stats/")
	public String getStats(Model model) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		model.addAttribute("stats", recordedTrackService.getStats(currentUser.get_id()));
		return "./track/Stats";
	}

	@RequestMapping(value = "/user/tracks/share/{id}/{sync}/", method = RequestMethod.GET)
	public String shareTrack(Model model, @PathVariable("id") int id,
			@PathVariable("sync") int syncNumber) {
		return "track/recorded/ShareTrack";
	}

	@RequestMapping(value = "/user/tracks/share/{id}/{sync}/", method = RequestMethod.POST)
	public String shareTrackConfirm(Model model, SessionStatus status, @PathVariable("id") int _id,
			@PathVariable("sync") int syncNumber,
			@RequestParam(value = "name", required = true) String name) {
		if((name.length() < 3) || (name.length() > 60)) {
			model.addAttribute("msg", new Message("Condivisione non riuscita",
					"Il nome è troppo corto o troppo lungo.", 1));
			return "./inc/Message";
		}

		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		VirtualTrackService virtualTrackService = new VirtualTrackService();

		RecordedTrack track = recordedTrackService.getById(_id, currentUser.get_id(), syncNumber);

		VirtualTrack virtualTrack = new VirtualTrack(name);
		virtualTrack.setAuthor_id(currentUser.get_id());
		virtualTrack.setCreationDate(track.getCreationDate());
		virtualTrack.setHeightDiff(track.getHeightDiff());
		virtualTrack.setLength(track.getLengthCalc());

		List<Location> listOfPoints = recordedTrackService.getAllPoints(_id, currentUser.get_id(),
				syncNumber);

		virtualTrackService.add(virtualTrack, listOfPoints);

		model.addAttribute("msg", new Message("Condivisione completata",
				"Ora il tuo percorso è raggiungibile dalla sotto sezione "
						+ "\"Percorsi degli utenti\" nella sezione \"Percorsi\"."));
		status.setComplete();
		return "./inc/Message";
	}
}
