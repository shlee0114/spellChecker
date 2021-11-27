package grammar

import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient

interface TestInterface<T1, T2> {
    val context: ApplicationContext
    val webClient: WebTestClient
    val uri: String

    fun preWebClient(
        uri: String = this.uri,
        value: String = "",
        type: T1? = null
    ): WebTestClient.RequestHeadersSpec<*>


    fun WebTestClient.RequestHeadersSpec<*>.webClientCheck(type: T2? = null): WebTestClient.BodyContentSpec
}