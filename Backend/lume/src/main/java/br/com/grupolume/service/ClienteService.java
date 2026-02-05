package br.com.grupolume.service;

import br.com.grupolume.dto.ClienteRequestDTO;
import br.com.grupolume.model.Cliente;
import br.com.grupolume.model.Endereco;
import br.com.grupolume.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BuscaCepService buscaCepService;

    public Cliente criarCliente(ClienteRequestDTO dto) {

        Endereco enderecoCompleto = buscaCepService.buscarPorCep(dto.cep());

        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEndereco(enderecoCompleto);

        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(Long id, ClienteRequestDTO dto) {
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Endereco enderecoAtualizado = buscaCepService.buscarPorCep(dto.cep());

        clienteExistente.setNome(dto.nome());
        clienteExistente.setCpf(dto.cpf());
        clienteExistente.setEndereco(enderecoAtualizado);

        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("ID não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}