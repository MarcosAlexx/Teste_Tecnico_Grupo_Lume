package br.com.grupolume.service;

import br.com.grupolume.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class BuscaCepService {

    public Endereco buscarPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            Endereco endereco = new RestTemplate().getForObject(url, Endereco.class);

            if (endereco == null || endereco.getCep() == null) {
                throw new IllegalArgumentException("CEP não encontrado: " + cep);
            }

            return endereco;
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao consultar CEP: " + cep, e);
        }
    }

}

