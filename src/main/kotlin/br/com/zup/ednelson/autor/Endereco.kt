package br.com.zup.ednelson.autor

import javax.persistence.Embeddable

@Embeddable
class Endereco(enderecoResponse: EnderecoResponse,
               val numero: String) {

    val cep = enderecoResponse.cep
    val logradouro = enderecoResponse.logradouro
    val bairro = enderecoResponse.bairro
    val localidade = enderecoResponse.localidade
    val uf = enderecoResponse.uf

}
