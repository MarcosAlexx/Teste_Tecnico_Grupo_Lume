package com.example.lume.controller;

import com.example.lume.dto.ClienteRequestDTO;
import com.example.lume.model.Cliente;
import com.example.lume.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Criar um novo cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "nome": "João Silva",
                      "cpf": "12345678901",
                      "endereco": {
                        "logradouro": "Rua das Flores",
                        "bairro": "Centro",
                        "localidade": "São Paulo",
                        "estado": "São Paulo",
                        "cep": "01001000",
                        "uf": "SP"
                      }
                    }
                    """))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos (CPF, CEP ou nome)", content = @Content),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteRequestDTO cliente) {
        Cliente novoCliente = clienteService.criarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "nome": "João Silva",
                      "cpf": "12345678901",
                      "endereco": {
                        "logradouro": "Rua das Flores",
                        "bairro": "Centro",
                        "localidade": "São Paulo",
                        "estado": "São Paulo",
                        "cep": "01001000",
                        "uf": "SP"
                      }
                    }
                    """))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Buscar cliente por CPF")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 2,
                      "nome": "Maria Souza",
                      "cpf": "98765432100",
                      "endereco": {
                        "logradouro": "Avenida Paulista",
                        "bairro": "Bela Vista",
                        "localidade": "São Paulo",
                        "estado": "São Paulo",
                        "cep": "01310100",
                        "uf": "SP"
                      }
                    }
                    """))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@Valid @PathVariable String cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Listar todos os clientes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": 1,
                        "nome": "João Silva",
                        "cpf": "12345678901",
                        "endereco": {
                          "logradouro": "Rua das Flores",
                          "bairro": "Centro",
                          "localidade": "São Paulo",
                          "estado": "São Paulo",
                          "cep": "01001000",
                          "uf": "SP"
                        }
                      },
                      {
                        "id": 2,
                        "nome": "Maria Souza",
                        "cpf": "98765432100",
                        "endereco": {
                          "logradouro": "Avenida Paulista",
                          "bairro": "Bela Vista",
                          "localidade": "São Paulo",
                          "estado": "São Paulo",
                          "cep": "01310100",
                          "uf": "SP"
                        }
                      }
                    ]
                    """))),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarTodos() {
        var clientes = clienteService.listarTodos();
        return ResponseEntity.ok().body(clientes);
    }

    @Operation(summary = "Atualizar um cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "nome": "João Silva Atualizado",
                      "cpf": "12345678901",
                      "endereco": {
                        "logradouro": "Rua Nova",
                        "bairro": "Jardins",
                        "localidade": "São Paulo",
                        "estado": "São Paulo",
                        "cep": "01453000",
                        "uf": "SP"
                      }
                    }
                    """))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO cliente){
        Cliente clienteatt = clienteService.atualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteatt);
    }

    @Operation(summary = "Deletar um cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
        @ApiResponse(responseCode = "403", description = "Token inválido ou ausente", content = @Content)
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
