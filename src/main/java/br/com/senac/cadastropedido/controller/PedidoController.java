package br.com.senac.cadastropedido.controller;


import br.com.senac.cadastropedido.dto.PedidoRequest;
import br.com.senac.cadastropedido.dto.PedidoResponse;
import br.com.senac.cadastropedido.modelo.Pedido;
import br.com.senac.cadastropedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/pedido"})
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Void> criarPedido(@RequestBody PedidoResponse pedidoRequest) {
        Pedido pedidoModel = new Pedido();

        pedidoModel.setNomeCliente(pedidoRequest.getNomeCliente());
        pedidoModel.setRua(pedidoRequest.getRua());
        pedidoModel.setBairro(pedidoRequest.getBairro());
        pedidoModel.setCidade(pedidoRequest.getCidade());
        pedidoModel.setEstado(pedidoRequest.getEstado());
        pedidoModel.setValorPedido(pedidoRequest.getValorPedido());
        try {

            pedidoRepository.save(pedidoModel);

            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> retornarPedidos() {
        List<Pedido> pedidoList;

        pedidoList = pedidoRepository.findAll();
        List<PedidoResponse> pedidoResponseList = new ArrayList<>();

        for (Pedido dadoPedido : pedidoList) {
            pedidoResponseList.add(new PedidoResponse(dadoPedido.getId(), dadoPedido.getNomeCliente(), dadoPedido.getRua(), dadoPedido.getBairro(), dadoPedido.getCidade(), dadoPedido.getEstado(), dadoPedido.getValorPedido() ));
        }

        return ResponseEntity.ok().body(pedidoResponseList);

    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<Void> removerPedido(@PathVariable Long id) {

        pedidoRepository.deleteById(id);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(path="/todos")
    public ResponseEntity<Void> removerPedidoAll(){

        pedidoRepository.deleteAll();

        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<Void> atualizarPedido(@RequestBody PedidoRequest pedidoRequest, @PathVariable Long id) {
        Optional<Pedido> pedido;
                pedido = pedidoRepository.findById(id)
                .map(record -> {
                    record.setValorPedido(pedidoRequest.getValorPedido());
                    record.setEstado(pedidoRequest.getEstado());
                    record.setCidade(pedidoRequest.getCidade());
                    record.setBairro(pedidoRequest.getBairro());
                    record.setRua(pedidoRequest.getRua());
                    record.setNomeCliente(pedidoRequest.getNomeCliente());
                    return pedidoRepository.save(record);
                });

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<PedidoResponse> carregarPedidoById(@PathVariable Long id) {

        Optional<Pedido> pedido = pedidoRepository.findById(id);

        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setId(pedido.get().getId());
        pedidoResponse.setNomeCliente(pedido.get().getNomeCliente());
        pedidoResponse.setRua(pedido.get().getRua());
        pedidoResponse.setBairro(pedido.get().getBairro());
        pedidoResponse.setCidade(pedido.get().getCidade());
        pedidoResponse.setEstado(pedido.get().getEstado());
        pedidoResponse.setValorPedido(pedido.get().getValorPedido());

        return ResponseEntity.ok().body(pedidoResponse);
    }

}