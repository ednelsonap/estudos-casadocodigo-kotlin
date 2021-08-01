package br.com.zup.ednelson.autor

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable

@Controller("/autor/{id}")
class DeletaAutorController(val autorRepository: AutorRepository) {
    @Delete
    fun deleta(@PathVariable id: Long) : HttpResponse<Any>{
        val possivelAutor = autorRepository.findById(id)
        if (possivelAutor.isEmpty) {
            return HttpResponse.notFound()
        }
        //autorRepository.deleteById(id)
        val autor = possivelAutor.get()
        autorRepository.delete(autor)

        return HttpResponse.ok()
    }
}