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
import org.springframework.web.bind.support.SessionStatus;
import com.wearit.shike.web.helper.validator.HelpNumberValidator;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.HelpNumberService;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.HelpNumber;

@Controller
@RequestMapping("/user/helpnumbers")
public class HelpNumberController {

	private HelpNumberValidator validator;
	private int currentId;
	private HelpNumberService helpNumberService;

	@Autowired
	public HelpNumberController(HelpNumberValidator validator) {
		this.validator = validator;
	}

	@Autowired
	public void setHelpNumberService(HelpNumberService helpNumberService) {
		this.helpNumberService = helpNumberService;
	}

	public HelpNumberController(HelpNumberValidator validator, int i) {
		this.validator = validator;
		currentId = i;
	}

	private void setCurrentId() {
		Account info = (Account) SecurityContextHolder.getContext().getAuthentication()
				.getDetails();
		this.currentId = info.get_id();
	}

	/**
	 * Visualizza la lista dei numeri presenti e permette l'inserimento di un nuovo numero
	 * d'emergenza
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String listHelpNumber(Model model) {
		if(currentId == 0) {
			this.setCurrentId();
		}
		model.addAttribute("listaHelpNum", helpNumberService.getAll(currentId));
		return "./account/user/ListHelpNumbers";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addHelpNumber(Model model) {
		model.addAttribute("hnumb", new HelpNumber());
		return "./account/user/AddHelpNumber";
	}

	@RequestMapping(value = "/add/", method = RequestMethod.POST)
	public String addHelpNumberConfirm(@ModelAttribute("hnumb") HelpNumber hnumb,
			BindingResult result, SessionStatus status, Model model) {
		validator.validate(hnumb, result);

		if(result.hasErrors()) {
			// se il validatore trova errori
			return "./account/user/AddHelpNumber";
		} else {
			status.setComplete();
			// se non ci sono errori
			if(currentId == 0) {
				this.setCurrentId();
			}
			try {
				helpNumberService.add(currentId, hnumb);
				Message msg = new Message("Inserimento completato",
						"Inserimento del numero completato.");
				msg.setBackToList(true);
				model.addAttribute("msg", msg);
				return "./inc/Message";
			} catch(DataAccessException dae) {
				Message errore;
				if(dae.getCause().getMessage().contains("Duplicate entry")) {
					errore = new Message("Inserimento fallito",
							"Il numero risulta già presente nella tua rubrica.", 1);
				} else {
					errore = new Message("Inserimento fallito", dae.getCause().getMessage(), 1);
				}
				model.addAttribute("msg", errore);
				return "./inc/Message";
			}
		}
	}

	@RequestMapping(value = "/delete/{number}", method = RequestMethod.GET)
	public String deleteHelpNumber(Model model, @PathVariable("number") String number) {
		if(currentId == 0) {
			this.setCurrentId();
		}
		helpNumberService.delete(currentId, number);
		model.addAttribute("msg", new Message("Eliminazione completata",
				"Eliminazione del numero selezionato completata."));
		return "./inc/Message";
	}

	@RequestMapping(value = "/edit/{number}", method = RequestMethod.GET)
	public String editHelpNumber(Model model, @PathVariable("number") String number) {
		if(currentId == 0) {
			this.setCurrentId();
		}
		HelpNumber selected = helpNumberService.get(currentId, number);
		model.addAttribute("numbOld", number);
		model.addAttribute("hnumb", selected);
		return "./account/user/EditHelpNumber";
	}

	@RequestMapping(value = "/edit/{number}", method = RequestMethod.POST)
	public String editHelpNumberConfirm(@ModelAttribute("hnumb") HelpNumber hnumb,
			BindingResult result, SessionStatus status, @PathVariable("number") String oldNum,
			Model model) {
		validator.validate(hnumb, result);

		if(result.hasErrors()) {
			// se il validatore trova errori
			model.addAttribute("numbOld", oldNum);
			return "./account/user/EditHelpNumber";
		} else {
			status.setComplete();
			// se non ci sono errori
			if(currentId == 0) {
				this.setCurrentId();
			}
			helpNumberService.update(currentId, oldNum, hnumb);
			Message msg = new Message("Modifica completata",
					"Modifica del numero selezionato completata.");
			msg.setBackToList(true);
			model.addAttribute("msg", msg);
			return "./inc/Message";
		}
	}

}
