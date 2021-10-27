package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.io.IOException
import kotlin.jvm.Throws


@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarTestGraphql {
    @Autowired
    private lateinit var context: ApplicationContext

    private lateinit var webclient: WebTestClient

    @BeforeEach
    fun setUp() {
        webclient = WebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    @Order(1)
    @DisplayName("검색 성공")
    @Throws(IOException::class)
    fun checkSuccess(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"{check(text:\\\"되요\\\"){errorText fixedText}}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data.check.errorText").isEqualTo("되요")
            .jsonPath("$.data.check.fixedText").isEqualTo("돼요")
    }

    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"{check(text:\\\"돼요\\\"){errorText fixedText}}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data.check.errorText").isEqualTo("")
            .jsonPath("$.data.check.fixedText").isEqualTo("")
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"{check(text:\\\"${RandomStringUtils.randomAlphanumeric(501)}\\\"){errorText fixedText}}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"{check(text:\\\"\\\"){errorText fixedText}}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }
}