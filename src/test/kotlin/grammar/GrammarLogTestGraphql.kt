package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters



@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarLogTestGraphql {
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
    fun checkSuccess(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"테스트\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"test\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data.log").isEqualTo(true)
    }

    @Test
    @Order(2)
    @DisplayName("등록 실패(errorText 혹은 fixedText 최대 길이 500자, count 10자, ip 20자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "${RandomStringUtils.randomAlphanumeric(501)}\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"test\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "테스트}\\\", fixedText:\\\"${RandomStringUtils.randomAlphanumeric(501)}\\\", fixedCount:0, ip:\\\"test\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "테스트\\\", fixedText:\\\"테스트\\\", fixedCount:${RandomStringUtils.randomNumeric(11)}, ip:\\\"test\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "테스트\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"${RandomStringUtils.randomAlphanumeric(21)}\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    @Order(3)
    @DisplayName("등록 실패(errorText 혹은 fixedText 빈 값)")
    fun checkFailedBecauseEmpty(){
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"테스트\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()


        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"mutation{log(input:{errorText:\\\"" +
                        "테스트\\\", fixedText:\\\"\\\", fixedCount:0, ip:\\\"테스트\\\"})}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    @Order(4)
    @DisplayName("로그 조회 성공")
    fun checkSuccessSearchLogList() {
        webclient.post().uri("/graphql")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue("{ \"query\" : \"{log{error,fixed,count,fixedTime}}\"}")
            ).exchange()
            .expectBody()
            .jsonPath("$.data.log").isArray
    }
}