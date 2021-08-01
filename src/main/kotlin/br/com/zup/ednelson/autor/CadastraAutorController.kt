package br.com.zup.ednelson.autor

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/autor")
class CadastraAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient
) {

    @Post
    fun cadastra(@Body @Valid request: NovoAutorRequest) : HttpResponse<Any>{

        val enderecoResponse = enderecoClient.consultaJson(request.cep)

        val autor = request.paraAutor(enderecoResponse.body())
        autorRepository.save(autor)
        val uri = UriBuilder.of("/autor/{id}")
            .expand(mutableMapOf(Pair("id", autor.id)))
        return HttpResponse.created(uri)
    }

}