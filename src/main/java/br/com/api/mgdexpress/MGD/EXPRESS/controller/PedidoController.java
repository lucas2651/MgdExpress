package br.com.api.mgdexpress.MGD.EXPRESS.controller;

import br.com.api.mgdexpress.MGD.EXPRESS.model.historico.Historico;
import br.com.api.mgdexpress.MGD.EXPRESS.model.pedido.*;
import br.com.api.mgdexpress.MGD.EXPRESS.model.records.DadosIdPedidoIdMotoboy;
import br.com.api.mgdexpress.MGD.EXPRESS.repository.*;
import br.com.api.mgdexpress.MGD.EXPRESS.services.RegrasService;
import br.com.api.mgdexpress.MGD.EXPRESS.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private MotoboyRepository motoboyRepository;
    @Autowired
    private HistoricoRepository historicoRepository;
    @Autowired
    private GerenteRepository gerenteRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_USER_MASTER') OR hasRole('ROLE_USER_GERENTE')")
    @PostMapping
    @Transactional
    public ResponseEntity criar(@Valid @RequestBody DadosPedido dadosPedido, UriComponentsBuilder uriComponentsBuilder){

        var gerente = gerenteRepository.getReferenceById(dadosPedido.GerenteId());
        var pedido = pedidoRepository.save(new Pedido(dadosPedido,gerente));

        var uri = uriComponentsBuilder.path("/pedidos/{id}").buildAndExpand("id",pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_USER_MOTOBOY')")
    @PutMapping
    @Transactional
    public ResponseEntity MudarStadoDoPedidoParaAndamento(@Valid @RequestBody DadosIdPedidoIdMotoboy dadosId){
        var pedido = pedidoRepository.getReferenceById(dadosId.idPedido());
        var motoboy = motoboyRepository.getReferenceById(dadosId.idMotoboy());
        var regras = new RegrasService();
        regras.verificarSeMotoboyDisponivel(motoboy);
        pedido.setStatus(Status.ANDAMENTO);
        pedido.setMotoboy(motoboy);
        motoboy.setDisponivel(false);

        pedidoRepository.save(pedido);
        motoboyRepository.save(motoboy);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_USER_MASTER') OR hasRole('ROLE_USER_GERENTE')")
    @GetMapping("/pendente/gerente")
    public ResponseEntity<List<DadosPedidoPage>> listar(@RequestHeader("Authorization") String header){
        var token = header.replace("Bearer ","");
        var subject = tokenService.getSubject(token);

        return ResponseEntity.ok(pedidoRepository.findAllWhereStatusINICIARByLogin(subject).stream().map(DadosPedidoPage::new).toList());

    }

    @GetMapping("/pendente")
    public ResponseEntity<List<DadosPedidoPage>> listarPedidodsByMotoboy(){
        return ResponseEntity.ok(pedidoRepository.findAllWhereStatusINICIAR().stream().map(DadosPedidoPage::new).toList());
    }

    @PreAuthorize("hasRole('ROLE_USER_MASTER') OR hasRole('ROLE_USER_GERENTE')")
    @GetMapping("/EmAndamento")
    public ResponseEntity<Page<DadosPedidoPageEmandamento>> listarPedidosEmAndamento(@PageableDefault(size = 10)Pageable page){
        return ResponseEntity.ok(pedidoRepository.findAllWhereStatusANDAMENTO(page).map(DadosPedidoPageEmandamento::new));

    }


    @GetMapping("/{id}")
    public ResponseEntity detalherPedido(@PathVariable Long id){
        var pedido = pedidoRepository.getReferenceByIdAndNotinHistorico(id);
        if(pedido != null) {
            if (pedido.getMotoboy() != null) {
                return ResponseEntity.ok(new DadosPedidoCompleto(pedido));
            }
            return ResponseEntity.ok(new DadosPedido(pedido));
        }else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity finalizarpedido(@PathVariable Long id){
        var pedido = pedidoRepository.getReferenceById(id);
        var motoboy = motoboyRepository.getReferenceById(pedido.getMotoboy().getId());
        pedidoRepository.save(pedido);
        historicoRepository.save(new Historico(pedido));
        motoboy.setDisponivel(true);
        return ResponseEntity.noContent().build();
    }




}
