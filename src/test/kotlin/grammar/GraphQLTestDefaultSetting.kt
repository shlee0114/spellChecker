package grammar

import grammar.type.GraphQLType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

abstract class GraphQLTestDefaultSetting : TestInterface<GraphQLType, Any?> {
    @Autowired
    override lateinit var context: ApplicationContext

    override val webClient: WebTestClient by lazy {
        WebTestClient.bindToApplicationContext(context).build()
    }

    override val uri: String = "/graphql"

    override fun preWebClient(uri: String, value: String, type: GraphQLType?): WebTestClient.RequestHeadersSpec<*> =
        when (type) {
            GraphQLType.MUTATION -> preWebClientDo("mutation{$value}")
            else -> preWebClientDo("{$value}")
        }

    private fun preWebClientDo(bodyValue: String) =
        webClient.post()
            .uri(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue("{ \"query\" : \"$bodyValue\"}"))

    override fun WebTestClient.RequestHeadersSpec<*>.webClientCheck(type: Any?): WebTestClient.BodyContentSpec =
        exchange()
            .expectBody()
}