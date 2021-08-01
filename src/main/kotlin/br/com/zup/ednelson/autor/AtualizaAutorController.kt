package br.com.zup.ednelson.autor

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import javax.transaction.Transactional

@Controller("/autor/{id}")
@Transactional
class AtualizaAutorController(val autorRepository: AutorRepository) {
    @Put
    fun atualiza(@PathVariable id: Long, descricao: String): HttpResponse<Any> {
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty){
            return HttpResponse.notFound()
        }
        val autor = possivelAutor.get()
        autor.descricao = descricao
        //autorRepository.update(autor) //não é necessário quando o método está anotado com @Transactional

        return HttpResponse.ok(DetalhesDoAutorResponse(autor.nome, autor.email, autor.descricao))
    }
}