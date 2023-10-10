package com.example.crud.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.crud.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
    
}
