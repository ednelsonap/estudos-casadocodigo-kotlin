package br.com.zup.ednelson.autor

import br.com.zup.ednelson.autor.EnderecoResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Consumes
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws/")
interface EnderecoClient {

    @Get("{cep}/json")
    fun consultaJson(cep: String) : HttpResponse<EnderecoResponse>

    @Get(value = "{cep}/xml")
    @Consumes(MediaType.APPLICATION_XML)
    fun consultaXml(@PathVariable cep: String) : HttpResponse<EnderecoResponse>
}