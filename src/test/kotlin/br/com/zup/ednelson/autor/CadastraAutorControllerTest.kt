package br.com.zup.ednelson.autor

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject

@MicronautTest
internal class CadastraAutorControllerTest {

    @field:Inject
    lateinit var enderecoClient: EnderecoClient

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    //nesse teste o Yuri usou banco H2
    @Test
    fun deveCadastrarUmNovoAutor() {

        val novoAutorRequest = NovoAutorRequest(
            nome = "Fulano",
            email = "fulano@gmail.com",
            descricao = "teste",
            cep = "11705420",
            numero = "817"
        )

        val enderecoResponse = EnderecoResponse(
            cep = "11705420",
            logradouro = "Avenida Carlos Alberto Perrone",
            bairro = "Maracanã",
            localidade = "Praia Grande",
            uf = "SP"
        )

        Mockito.`when`(enderecoClient.consultaJson(novoAutorRequest.cep))
            .thenReturn(HttpResponse.ok(enderecoResponse))

        val request = HttpRequest.POST("/autor", novoAutorRequest)

        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        //assertTrue(response.header("Location")!!.matches("/autor/\\d".toRegex()))
    }

    @MockBean(EnderecoClient::class)
    fun enderecoMock(): EnderecoClient {
        return Mockito.mock(EnderecoClient::class.java)
    }

}