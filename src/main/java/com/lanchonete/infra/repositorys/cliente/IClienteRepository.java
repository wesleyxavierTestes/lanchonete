package com.lanchonete.infra.repositorys.cliente;

import java.util.List;

import com.lanchonete.domain.entities.cliente.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>  {

    @Query(
        nativeQuery = true, 
        value = "SELECT * FROM cliente WHERE tipo_cliente like ?1 limit 1")
    Cliente findByTipoCliente(String tipoCliente);

    /**
     * Listagem dos clientes que mais gastam
     * @param pageRequest
     * @return
     */
    @Query(
        nativeQuery = true, 
            value = "SELECT c.id, c.nome, Sum(v.valor_total) as valor "
                    +"FROM public.cliente c "
                    +"join public.venda as v "
                    +"on v.cliente_id = c.id "
                    +"where v.cancelado = false "
                    +"and c.ativo = true"
                    +"group by c.id "
                    +"order by valor desc",
                    
        countQuery = "SELECT c.id, c.nome, Sum(v.valor_total) as valor "
                    +"FROM public.cliente c "
                    +"join public.venda as v "
                    +"on v.cliente_id = c.id "
                    +"where v.cancelado = false "
                    +"and c.ativo = true"
                    +"group by c.id "
                    +"order by valor desc")
    Page<Cliente> listSpendMore(PageRequest pageRequest);

    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cliente as c where c.ativo = true",
        countQuery = "SELECT (c.*) FROM cliente as c where c.ativo = true")
    Page<Cliente> listActive(PageRequest pageRequest);
    
    @Query(
        nativeQuery = true, 
        value = "SELECT (c.*) FROM cliente as c where c.ativo = false",
        countQuery = "SELECT (c.*) FROM cliente as c where c.ativo = false")
    Page<Cliente> listDesactive(PageRequest pageRequest);
    
}
