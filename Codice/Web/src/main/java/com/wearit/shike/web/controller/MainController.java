package com.wearit.shike.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.wearit.shike.web.model.Message;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public String home() {
		return "Home";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		Message msg = new Message("HTTP Status 403 - Access is denied",
				"Non hai il permesso di accedere a questa pagina!", 1);
		model.addAttribute("msg", msg);
		return "inc/Message";
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String notFound(Model model) {
		Message msg = new Message("HTTP Status 404 - Not Found",
				"La pagina selezionata non è stata trovata!", 1);
		model.addAttribute("msg", msg);
		return "inc/Message";
	}

}
