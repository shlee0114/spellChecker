package grammar

import grammar.type.CheckType
import grammar.type.RequestMethodType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

abstract class TestDefaultSetting : TestInterface<RequestMethodType, CheckType> {
    @Autowired
    override lateinit var context: ApplicationContext

    override val webClient: WebTestClient by lazy {
        WebTestClient.bindToApplicationContext(context).build()
    }

    override fun preWebClient(uri: String, value: String, type: RequestMethodType?): WebTestClient.RequestHeadersSpec<*> =
        when (type) {
            RequestMethodType.POST -> preWebClientPost(uri, value)
            else -> preWebClientGet(uri, value)
        }

    private fun preWebClientGet(uri: String, parameter: String) =
        webClient.get()
            .uri("$uri?$parameter")
            .accept(MediaType.APPLICATION_JSON)

    private fun preWebClientPost(uri: String, bodyValue: String) =
        webClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(bodyValue))

    override fun WebTestClient.RequestHeadersSpec<*>.webClientCheck(type: CheckType?): WebTestClient.BodyContentSpec =
        when (type) {
            CheckType.CLIENT_ERROR -> webClientIs4xxClientError()
            else -> webClientIsOk()
        }

    private fun WebTestClient.RequestHeadersSpec<*>.webClientIsOk(): WebTestClient.BodyContentSpec =
        exchange()
            .expectStatus().isOk
            .expectBody()

    private fun WebTestClient.RequestHeadersSpec<*>.webClientIs4xxClientError(): WebTestClient.BodyContentSpec =
        exchange()
            .expectStatus().is4xxClientError
            .expectBody()
}