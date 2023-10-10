package com.example.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = "/crud")
public class MainController {

	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping("/inicio")
    public String home(Model model) {
    	List<User> users = (List<User>) userRepository.findAll();
    	model.addAttribute("users", users);
        return "home";
    }
    
    @GetMapping("/form")
    public String add() {
        return "add-user";
    }

	@RequestMapping("/add")
	public @ResponseBody RedirectView addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return new RedirectView("inicio");
	}

	@GetMapping("delete/{id}")
	public @ResponseBody RedirectView deleteUser(@PathVariable(name = "id") Integer id) {
		userRepository.deleteById(id);
		return new RedirectView("/crud/inicio");
	}
	
	@GetMapping(value = "read/{id}")
	public ModelAndView readUser(@PathVariable("id") User user) {
		return new ModelAndView("read-user", "user", user);
	}

	@RequestMapping("/update")
	public @ResponseBody RedirectView updateUser(@RequestParam Integer id, @RequestParam String name, @RequestParam String email) {
		Optional<User> optionalUser = userRepository.findById(id);
		User n = optionalUser.get();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return new RedirectView("/crud/inicio");
	}
}