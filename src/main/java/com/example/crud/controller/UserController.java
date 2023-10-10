package com.example.crud.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;

@Controller
@RequestMapping(path = "/usuarios")
public class UserController {

    @Autowired
	private UserRepository userRepository;
    private final String USUARIO_URI = "usuarios/";

    @RequestMapping("list-usuario")
    public String listarUsuarios(Model model) {
    	List<User> users = (List<User>) userRepository.findAll();
    	model.addAttribute("users", users);
        return "list-usuario";
    }
    
    @GetMapping("add-usuario")
    public String adicionarUsuario() {
        return "add-usuario";
    }

	@RequestMapping("save-usuario")
	public @ResponseBody RedirectView addNewUser(@RequestParam String name, 
				@RequestParam String email, RedirectAttributes redirect) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		redirect.addFlashAttribute("globalMessage","Usuário gravado com sucesso");
		return new RedirectView("list-usuario");
	}

	@GetMapping("delete-usuario/{id}")
	public @ResponseBody RedirectView deleteUser(@PathVariable(name = "id") Integer id,
				RedirectAttributes redirect) {
		userRepository.deleteById(id);
		redirect.addFlashAttribute("globalMessage","Usuário excluído com sucesso");
		return new RedirectView("/" + USUARIO_URI + "list-usuario");
	}
	
	@GetMapping(value = "/read-usuario/{id}")
	public ModelAndView readUser(@PathVariable("id") User user) {
		return new ModelAndView("read-usuario", "user", user);
	}

	@RequestMapping("update-usuario")
	public @ResponseBody RedirectView updateUser(@RequestParam Integer id, @RequestParam String name, 
				@RequestParam String email, RedirectAttributes redirect) {
		Optional<User> optionalUser = userRepository.findById(id);
		User n = optionalUser.get();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		redirect.addFlashAttribute("globalMessage","Usuário alterado com sucesso");
		return new RedirectView("list-usuario");
	}   
}