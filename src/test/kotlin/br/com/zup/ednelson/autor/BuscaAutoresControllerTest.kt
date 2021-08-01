package br.com.zup.ednelson.autor

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest {

    @field:Inject
    lateinit var autorRepository: AutorRepository

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    lateinit var autor: Autor

    @BeforeEach
    internal fun setUp() {
        val enderecoResponse = EnderecoResponse(
            cep = "11705420",
            logradouro = "Avenida Carlos Alberto Perrone",
            bairro = "Maracanã",
            localidade = "Praia Grande",
            uf = "SP"
        )

        val endereco = Endereco(enderecoResponse = enderecoResponse, numero = "817")

        autor = Autor(
            nome = "Ednelson",
            email = "ednelson@gmail.com",
            descricao = "teste",
            endereco = endereco
        )

        autorRepository.save(autor)
    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.delete(autor)
    }

    @Test
    internal fun deveRetornarOsDetalhesDeUmAutor() {

        //por padrão esse client do micronaut é reativo, quero um clint blocante, que será executado
        //na mesma tread da execução
        var response = client.toBlocking().exchange("/autores?email=${autor.email}", DetalhesDoAutorResponse::class.java)
        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.nome, response.body().nome)
        assertEquals(autor.email, response.body().email)
        assertEquals(autor.descricao, response.body().descricao)
    }
}