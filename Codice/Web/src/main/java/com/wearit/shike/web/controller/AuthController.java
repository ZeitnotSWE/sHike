package com.wearit.shike.web.controller;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.wearit.shike.web.helper.MailManager;
import com.wearit.shike.web.model.Message;
import com.wearit.shike.web.model.service.AccountService;
import com.wearit.shike.web.model.service.PoiService;
import com.wearit.shike.web.model.service.RecordedTrackService;
import com.wearit.shike.web.model.service.VirtualTrackService;
import com.wearit.shike.web.model.session.performance.Stats;
import com.wearit.shike.web.model.user.Account;

@Controller
public class AuthController {

	private AbstractAuthenticationToken auth;
	private Account currentUser;
	private AccountService accountService;

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	private void setAccount() {
		this.auth = (AbstractAuthenticationToken) SecurityContextHolder.getContext()
				.getAuthentication();
		this.currentUser = accountService.getByUsername(auth.getName());
		auth.setDetails(currentUser);
	}

	@RequestMapping(value = "/login/", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if(error != null) {
			// l'utente ha inserito dati errati
			model.addAttribute("error", "Username e password non sono validi!");
		}

		if(logout != null) {
			// l'utente ha fatto il logout
			model.addAttribute("msg", "Hai effettuato il logout con successo.");
		}

		return "./account/Login";
	}

	@RequestMapping(value = "/reset-password/", method = RequestMethod.GET)
	public String resetPassword() {
		// creo il form per il recupero password
		return "./account/ResetPassword";
	}

	@RequestMapping(value = "/reset-password/", method = RequestMethod.POST)
	public String resetPasswordConfirm(Model model, @RequestParam("email") String email) {
		try {
			// verifico che l'email sia presente nel database
			Account current = accountService.getByUsername(email);
			ApplicationContext context = new ClassPathXmlApplicationContext("Email-Beans.xml");
			MailManager mm = (MailManager) context.getBean("mailMail");

			// Creo una password casuale: 4 cifre + prima lettera email + 4 cifre
			char[] chars = "abcdefghilmnopqrstuvzABCDEFGHILMNOPQRSTUVZ".toCharArray();
			char[] numbers = "1234567890".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for(int i = 0; i <= 4; i++) {
				sb.append((char) chars[random.nextInt(chars.length)]);
				sb.append((char) numbers[random.nextInt(numbers.length)]);
			}
			String pass = sb.toString();

			// Aggiorno la password nel database
			accountService.updatePassword(current.get_id(), pass);

			try {
				// invio l'email contenente la nuova password
				mm.sendMail(currentUser.getEmailAddress(),
						"Recupera password dal sito sHike by Zeitweb",
						"<p>Come da te richiesto abbiamo resettato la tua password."
								+ "Quella nuova è: <b>" + pass
								+ "</b><br />Ti consigliamo di cambiarla al "
								+ "primo accesso.<br /> <br /> Il team <i>Zeitnot</i></p>");

			} catch(Exception e) {
				// Salvo eventuali errori nel log
				System.err.println(e.getMessage());
			}

			// Chiudo il context del gestore email
			((ConfigurableApplicationContext) context).close();

			model.addAttribute("msg", new Message("Email inviata",
					"Controlla la tua casella email (<b>" + email
							+ "</b>), ti è appena stata inviata"
							+ " la nuova password per accedere.<br /> Consigliamo di cambiarla"
							+ " non appena effettui il primo accesso."));

			return "./inc/Message";
		} catch(DataAccessException dae) {
			// se l'email non è presente:
			model.addAttribute("msg", new Message("Email non inviata", "L'email che hai inserito "
					+ "non risulta presente nel nostro database.", 1));
			return "./inc/Message";
		}
	}

	@RequestMapping(value = "/dashboard/", method = RequestMethod.GET)
	public String home(Model model) {
		// imposto le informazioni dell'account che ha appena fatto il login
		this.setAccount();

		PoiService poiService = new PoiService();
		VirtualTrackService virtualTrackService = new VirtualTrackService();

		if(currentUser.getRole().equals("ROLE_ADMIN")) {
			model.addAttribute("listaUser", accountService.getAllCommon(5));
			model.addAttribute("listaPoi", poiService.getAll(5));
			model.addAttribute("listaVirtual", virtualTrackService.getAll(0, 5));
			return "./account/admin/Dashboard";
		}

		if(currentUser.getRole().equals("ROLE_USER")) {
			RecordedTrackService recordedTrackService = new RecordedTrackService();
			Stats st = recordedTrackService.getStats(currentUser.get_id());
			if(st == null)
				st = new Stats();

			model.addAttribute("stats", st);
			model.addAttribute("listaPoi", poiService.getAll(5));
			model.addAttribute("listaVirtual", virtualTrackService.getAll(0, 8));
			model.addAttribute("token", accountService.getToken(currentUser.get_id()));
			return "./account/user/Dashboard";
		}

		// se il ruolo non corrisponde a quelli conosciuti
		model.addAttribute("msg", new Message("Errore",
				"Ruolo non riconosciuto in questo portale.", 1));
		return "./inc/Message";
	}

	@RequestMapping(value = "/dashboard/add-device/", method = RequestMethod.POST)
	public String addDevice(Model model, @RequestParam("device") int device) {
		// imposto le informazioni dell'account che ha appena fatto il login
		this.setAccount();

		if(accountService.checkCode(device)) {
			accountService.updateToken(device, currentUser.get_id());
			model.addAttribute("msg", new Message("Dispositivo connesso",
					"Il dispositivo è stato associato con successo."));
			return "./inc/Message";
		}

		model.addAttribute("msg", new Message("Errore", "Il codice digitato non è corretto.", 1));
		return "./inc/Message";
	}

	@RequestMapping(value = "/dashboard/delete-device/", method = RequestMethod.POST)
	public String deleteDevice(Model model) {
		// imposto le informazioni dell'account che ha appena fatto il login
		this.setAccount();

		accountService.deleteToken(accountService.getToken(currentUser.get_id()));
		model.addAttribute("msg", new Message("Dispositivo scollegato",
				"Il dispositivo è stato scollegato correttamente dal proprio profilo."));
		return "./inc/Message";
	}
}
