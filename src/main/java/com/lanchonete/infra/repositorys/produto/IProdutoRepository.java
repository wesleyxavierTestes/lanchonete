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
        value = "SELECT (c.*) FROM produto as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM produto as c where c.ativo = false")
    Page<Produto> listDesactive(PageRequest of);
    
    @Query(
        nativeQuery = true, 
            value = "SELECT (p.*), SUM(e.quantidade) as estoque_atual "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"GROUP BY p.id "
                    +"HAVING SUM(e.quantidade) >= ?1 and SUM(e.quantidade) < ?2",
                    
        countQuery = "SELECT (p.*), SUM(e.quantidade) as estoque_atual "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"GROUP BY p.id "
                    +"HAVING SUM(e.quantidade) >= ?1 and SUM(e.quantidade) < ?2")
    Page<Produto> listEstoque(
    int inicio, int fim,    
    PageRequest pageRequest);

    @Query(
        nativeQuery = true, 
            value = "SELECT SUM(e.quantidade) "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"and p.id = ?1 "
                    +"GROUP BY p.id ")
    double countEstoqueById(long id);
}
