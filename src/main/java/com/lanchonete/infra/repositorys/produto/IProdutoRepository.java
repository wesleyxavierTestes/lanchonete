package com.lanchonete.infra.repositorys.produto;

import com.lanchonete.domain.entities.produto.entities.Produto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long>  {

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM produto as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM produto as c where c.ativo = true")
    Page<Produto> listActive(PageRequest of);

    @Query(
        nativeQuery = true, 
        value = "SELECT * FROM produto WHERE lower(nome) like lower(concat ('%',?1,'%'))",
        countQuery = "SELECT * FROM produto WHERE lower(nome) like lower(concat ('%',?1,'%'))")
    Page<Produto> listByName(String nome, PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM produto as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM produto as c where c.ativo = false")
    Page<Produto> listDesactive(PageRequest of);
    
    @Query(
        nativeQuery = true, 
            value = "SELECT (p.*) "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"GROUP BY p.id "
                    +"HAVING SUM(e.quantidade) < 1",
                    
        countQuery = "SELECT (p.*) "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"GROUP BY p.id "
                    +"HAVING SUM(e.quantidade) < 1")
    Page<Produto> listEstoqueZero(PageRequest pageRequest);
}
