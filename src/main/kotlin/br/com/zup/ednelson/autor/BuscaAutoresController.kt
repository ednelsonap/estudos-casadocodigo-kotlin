package br.com.zup.ednelson.autor

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/autores")
class BuscaAutoresController(val autorRepository: AutorRepository) {

    @Get
    fun lista(@QueryValue(defaultValue = "") email: String) : HttpResponse<Any> {

        if (email.isBlank()) {
            val autores = autorRepository.findAll()
            val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor.nome, autor.email, autor.descricao) }
            return HttpResponse.ok(resposta)
        }
        val possivelAutor = autorRepository.buscaPorEmail(email)
        //val possivelAutor = autorRepository.findByEmail(email)

        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor.nome, autor.email, autor.descricao))

    }
}