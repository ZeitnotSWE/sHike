package com.wearit.shike.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.PoiService;
import com.wearit.shike.web.model.session.track.Poi;
import com.wearit.shike.web.model.user.Account;

@Controller
@RequestMapping(value = { "/user/tracks/poi", "/admin/tracks/poi" })
public class PoiController {

	private Account currentUser;
	private PoiService poiService;

	/**
	 * Costruttore di default
	 */
	public PoiController() {
	}

	/**
	 * @param currentUser
	 */
	public PoiController(Account currentUser) {
		this.currentUser = currentUser;
		poiService = new PoiService();
	}

	@Autowired
	public void setPoiService(PoiService poiService) {
		this.poiService = poiService;
	}

	private void setAccount() {
		this.currentUser = (Account) SecurityContextHolder.getContext().getAuthentication()
				.getDetails();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listPoi(Model model) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		model.addAttribute("currentId", currentUser.get_id());
		model.addAttribute("listaPoi", poiService.getAll());
		return "track/poi/ListPoi";
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public String listPoiDetails(Model model, @PathVariable("id") int id) {
		model.addAttribute("poi", poiService.get(id));
		return "track/poi/DetailsPoi";
	}

	@RequestMapping(value = "/add/", method = RequestMethod.GET)
	public String addPoi() {
		return "track/poi/AddPoi";
	}

	@RequestMapping(value = "/add/", method = RequestMethod.POST)
	public String addPoiConfirm(@ModelAttribute("poi") Poi poi, BindingResult result,
			SessionStatus status, Model model, @RequestParam("type_id") int type_id) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		// imposto alcuni valori del POI
		poi.setAuthor_id(currentUser.get_id());
		poi.setType(type_id);
		// aggiungo il POI
		poiService.add(poi);

		Message msg = new Message("Inserimento completato", "Inserimento del POI completato.");
		msg.setBackToList(true);
		model.addAttribute("msg", msg);
		return "./inc/Message";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deletePoi(Model model, @PathVariable("id") int id) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		try {
			// controllo se chi fa la richiesta è il proprietario del POI o un admin
			if(currentUser.get_id() == poiService.get(id).getAuthor_id()
					|| currentUser.getRole().equals("ROLE_ADMIN")) {
				poiService.delete(id);
				model.addAttribute("msg", new Message("Eliminazione completata",
						"Eliminazione del POI selezionato completata."));
			} else {
				model.addAttribute("msg", new Message("Eliminazione non riuscita",
						"Non puoi eliminare i POI che non hai creato tu.", 1));
			}
			return "./inc/Message";
		} catch(DataAccessException dae) {
			model.addAttribute("msg", new Message("Eliminazione non riuscita",
					"Il POI selezionato non è stato trovato.", 1));
			return "./inc/Message";
		}

	}

}
