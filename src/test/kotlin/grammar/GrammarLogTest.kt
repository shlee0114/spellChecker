package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@SpringBootTest(classes = [GrammerCheckerApplication::class],webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarLogTest {
    @Autowired
    private lateinit var context: ApplicationContext

    private lateinit var webclient: WebTestClient

    @BeforeEach
    fun setUp() {
        webclient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    @Order(1)
    @DisplayName("등록 성공")
    fun checkSuccess() {
        webclient.post().uri("/api/log")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{\"errorText\":\"되요\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
            ).exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
    }

    @Test
    @Order(2)
    @DisplayName("등록 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded() {
        webclient.post().uri("/api/log")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{\"errorText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
            ).exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("$.success").isEqualTo(false)

        webclient.post().uri("/api/log")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{\"errorText\":\"돠요\",\"fixedText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
            ).exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("$.success").isEqualTo(false)

        webclient.post().uri("/api/log")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{\"errorText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
            ).exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("$.success").isEqualTo(false)
    }

    @Test
    @Order(3)
    @DisplayName("로그 조회 성공")
    fun checkSuccessSearchLogList() {
        webclient.get().uri("/api/log")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty
    }
}