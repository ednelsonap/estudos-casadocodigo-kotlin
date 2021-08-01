package br.com.zup.ednelson.autor

data class EnderecoResponse(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
)
