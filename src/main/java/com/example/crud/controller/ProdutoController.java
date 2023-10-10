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

import com.example.crud.model.Produto;
import com.example.crud.repository.ProdutoRepository;

@Controller
@RequestMapping(path = "/produtos")
public class ProdutoController {

    @Autowired
	private ProdutoRepository produtoRepository;
    private final String PRODUTO_URI = "produtos/";

    @RequestMapping("list-produto")
    public String listarProdutos(Model model) {
    	List<Produto> produtos = (List<Produto>) produtoRepository.findAll();
    	model.addAttribute("produtos", produtos);
        return "produto/list-produto";
    }
    
    @GetMapping("add-produto")
    public String adicionarProduto() {
        return "produto/add-produto";
    }

	@RequestMapping("save-produto")
	public @ResponseBody RedirectView addNewProduto(@RequestParam String descricao, 
				@RequestParam Double valor, RedirectAttributes redirect) {
		Produto n = new Produto();
		n.setDescricao(descricao);
		n.setValor(valor);
		produtoRepository.save(n);
		redirect.addFlashAttribute("globalMessage","Produto gravado com sucesso");
		return new RedirectView("list-produto");
	}

	@GetMapping("delete-produto/{id}")
	public @ResponseBody RedirectView deleteProduto(@PathVariable(name = "id") Integer id,
				RedirectAttributes redirect) {
		produtoRepository.deleteById(id);
		redirect.addFlashAttribute("globalMessage","Produto excluído com sucesso");
		return new RedirectView("/" + PRODUTO_URI + "list-produto");
	}
	
	@GetMapping(value = "/read-produto/{id}")
	public ModelAndView readUser(@PathVariable("id") Produto produto) {
		return new ModelAndView("produto/read-produto", "produto", produto);
	}

	@RequestMapping("update-produto")
	public @ResponseBody RedirectView updateProduto(@RequestParam Integer id, @RequestParam String descricao, 
				@RequestParam Double valor, RedirectAttributes redirect) {
		Optional<Produto> optionalProduto = produtoRepository.findById(id);
		Produto n = optionalProduto.get();
		n.setDescricao(descricao);
		n.setValor(valor);
		produtoRepository.save(n);
		redirect.addFlashAttribute("globalMessage","Produto alterado com sucesso");
		return new RedirectView("list-produto");
	}   
}