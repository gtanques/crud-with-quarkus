package org.acme.Controller;


import org.acme.dto.ProdutoDTO;
import org.acme.entity.Produto;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;


@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoController {

    @GET
    public List<Produto> buscarTodos(){
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void inserirProduto(ProdutoDTO dto){
        Produto p = new Produto();
        p.nome = dto.nome;
        p.valor = dto.valor;
        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void inserirProduto(@PathParam("id") Long id, ProdutoDTO dto){
        Optional<Produto> p = Produto.findByIdOptional(id);
        if (p.isPresent()){
            Produto produto = p.get();
            produto.nome = dto.nome;
            produto.valor = dto.valor;
            produto.persist();
        }else{
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletaProduto(@PathParam("id") Long id){
        Optional<Produto> produto = Produto.findByIdOptional(id);
        produto.ifPresentOrElse(
            Produto::delete, () -> {
                throw new NotFoundException();
            }
        );
    }
}
