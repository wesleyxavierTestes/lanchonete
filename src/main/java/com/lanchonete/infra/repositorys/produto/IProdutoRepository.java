package com.lanchonete.infra.repositorys.produto;

import java.util.Collection;
import java.util.UUID;

import com.lanchonete.domain.entities.produto.Produto;


import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {

    Collection<Produto> findAllByNomeContaining(String nome);
    Collection<Produto> findAllByCodigo(UUID codigo);
    Produto findByCodigo(UUID codigo);
    
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

    @Query(
        nativeQuery = true, 
            value = "SELECT SUM(e.quantidade) > 0 "
                    +"FROM public.produto as p "
                    +"join public.estoque as e "
                    +"on e.produto_id = p.id "
                    +"where p.ativo = true "
                    +"and p.id = ?1 "
                    +"GROUP BY p.id ")
    boolean countEstoqueByIdBool(long id);

    @Query(
        nativeQuery = true, 
        value = "SELECT SUM(e.quantidade) "
                +"FROM public.produto as p "
                +"join public.estoque as e "
                +"on e.produto_id = p.id "
                +"where p.ativo = true "
                +"and p.codigo = :codigo "
                +"GROUP BY p.id ")
    long countEstoqueByCodigo(@Param("codigo") UUID codigo);

    @Query(
        nativeQuery = true, 
        value = "SELECT SUM(e.quantidade) > 0 "
                +"FROM public.produto as p "
                +"join public.estoque as e "
                +"on e.produto_id = p.id "
                +"where p.ativo = true "
                +"and p.codigo = :codigo "
                +"GROUP BY p.id ")
    boolean _countEstoqueByIdBool(@Param("codigo") UUID codigo);

    @Query(
        value = "FROM Produto as p "
                +"where p.codigo = :codigo ")
    Collection<Produto> _produtoCodigo(@Param("codigo") UUID codigo);
    
    //@Query(
      //  value = "SELECT (c.*) FROM #{#entityName} as c where c.ativo = true and c.id = ?1")
    Produto findByIdAndAtivoIsTrue(long id);
}
