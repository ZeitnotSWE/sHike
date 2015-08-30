package com.wearit.shike.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import com.wearit.shike.web.helper.validator.CommonAccountValidator;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.AccountService;
import com.wearit.shike.web.model.user.Account;
import com.wearit.shike.web.model.user.CommonAccount;

@Controller
@RequestMapping("/")
public class AccountController {

	private CommonAccountValidator validator;
	private Account currentUser;
	private AccountService accountService;

	@Autowired
	public AccountController(CommonAccountValidator validator) {
		this.validator = validator;
	}

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountController(CommonAccountValidator validator, Account user) {
		this.validator = validator;
		currentUser = user;
	}

	private void setAccount() {
		this.currentUser = (Account) SecurityContextHolder.getContext().getAuthentication()
				.getDetails();
	}

	@RequestMapping(value = "signup/", method = RequestMethod.GET)
	public String addAccount(Model model) {
		model.addAttribute("account", new CommonAccount());
		return "./account/AddCommonAccount";
	}

	@RequestMapping(value = "signup/", method = RequestMethod.POST)
	public String addAccountConfirm(@ModelAttribute("account") CommonAccount account,
			@RequestParam("gender") int gender, BindingResult result, SessionStatus status,
			Model model) {
		validator.validate(account, result);

		if(result.hasErrors()) {
			// se il validatore trova errori
			return "./account/AddCommonAccount";
		} else {
			status.setComplete();
			// se non ci sono errori
			try {
				account.setGender(gender);
				accountService.addCommon(account);
				Message msg = new Message("Registrazione completata",
						"Ora puoi effettuare l'accesso con i dati inseriti.");
				msg.setBackToList(true);
				model.addAttribute("msg", msg);
				return "./inc/Message";
			} catch(DataAccessException dae) {
				Message errore;
				if(dae.getCause().getMessage().contains("Duplicate entry")) {
					errore = new Message("Registrazione fallita",
							"L'email risulta già presente nel nostro database.", 1);
				} else {
					errore = new Message("Registrazione fallita", dae.getCause().getMessage(), 1);
				}
				model.addAttribute("msg", errore);
				return "./inc/Message";
			}
		}
	}

	@RequestMapping("admin/accounts/")
	public String createList(Model model) {
		model.addAttribute("listaUser", accountService.getAllCommon());
		return "./account/admin/ListCommonAccount";
	}

	@RequestMapping(value = "admin/accounts/del/{id}", method = RequestMethod.GET)
	public String delAccount(Model model, @PathVariable("id") int id) {
		accountService.delete(id);
		model.addAttribute("msg", new Message("Cancellazione completata",
				"Account eliminato con succeso."));
		return "./inc/Message";
	}

	@RequestMapping(value = "user/edit/", method = RequestMethod.GET)
	public String editUser(Model model) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		model.addAttribute("account", accountService.getCommonById(currentUser.get_id()));
		return "./account/user/EditUser";
	}

	@RequestMapping(value = "user/edit/", method = RequestMethod.POST)
	public String editUserConfirm(@ModelAttribute("account") CommonAccount account,
			BindingResult result, SessionStatus status, Model model) {

		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		account.setEmailAddress(currentUser.getEmailAddress());
		validator.validate(account, result);

		if(!BCrypt.checkpw(account.getPasswordHash(), currentUser.getPasswordHash())) {
			model.addAttribute("msg", new Message("Errore verifica password",
					"La password di conferma non corrisponde con la tua attuale password.", 1));
			return "./inc/Message";
		}

		if(result.hasErrors()) { // se il validatore trova errori
			return "./account/user/EditUser";
		}

		status.setComplete();

		try {
			accountService.updateUser(currentUser.get_id(), account);
			model.addAttribute("msg", new Message("Modifica completata",
					"I dati del tuo account sono stati modificati con successo"));
			return "./inc/Message";
		} catch(DataAccessException dae) {
			model.addAttribute("msg", new Message("Modifica fallita", dae.getCause().getMessage(),
					1));
			return "./inc/Message";
		}

	}

	@RequestMapping(value = { "user/edit-password/", "admin/edit-password/" },
			method = RequestMethod.GET)
	public String editPass(Model model) {
		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}
		model.addAttribute("account", currentUser);
		return "./account/EditPassword";
	}

	@RequestMapping(value = { "user/edit-password/", "admin/edit-password/" },
			method = RequestMethod.POST)
	public String editPassConfirm(@ModelAttribute("account") Account account, BindingResult result,
			SessionStatus status, @RequestParam("password") String oldPass, Model model) {

		if(currentUser == null) { // imposto l'account corrente
			this.setAccount();
		}

		if(!BCrypt.checkpw(oldPass, currentUser.getPasswordHash())) {
			model.addAttribute("msg", new Message("Errore verifica password",
					"La password vecchia non corrisponde con la tua password attuale", 1));
			return "./inc/Message";
		}

		CommonAccountValidator.checkPassword(account.getPasswordHash(),
				account.getPasswordHashRepeat(), result);

		if(result.hasErrors()) { // se il validatore trova errori
			return "./account/EditPassword";
		}

		status.setComplete();

		try {
			accountService.updatePassword(currentUser.get_id(), account.getPasswordHash());
			model.addAttribute("msg", new Message("Modifica completata",
					"La password del tuo account è stata modificata con successo"));
			return "./inc/Message";
		} catch(DataAccessException dae) {
			model.addAttribute("msg", new Message("Modifica fallita", dae.getCause().getMessage(),
					1));
			return "./inc/Message";
		}

	}
}
