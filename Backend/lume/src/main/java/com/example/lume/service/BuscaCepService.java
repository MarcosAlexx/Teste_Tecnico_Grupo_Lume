package com.example.lume.service;

import com.example.lume.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BuscaCepService {

    public Endereco buscarPorCep(String cep) {

            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            return new RestTemplate().getForObject(url, Endereco.class);

    }

}

