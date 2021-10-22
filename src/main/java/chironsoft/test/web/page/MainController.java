package chironsoft.test.web.page;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController implements ErrorController {

	@GetMapping(value = {"/", "/error"})
	public String main() {
		return "index.html";
	}


	@Override
	public String getErrorPath() {
		return "/error";
	}

}
