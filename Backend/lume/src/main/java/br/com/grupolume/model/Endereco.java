package br.com.grupolume.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Endereco {

    @JsonProperty("logradouro")
    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @JsonProperty("bairro")
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @JsonProperty("localidade")
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @JsonProperty("estado")
    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @JsonProperty("cep")
    @NotBlank(message = "Cep é obrigatório")
    private String cep;

    @JsonProperty("uf")
    @NotBlank(message = "UF é obrigatório")
    private String uf;

}
