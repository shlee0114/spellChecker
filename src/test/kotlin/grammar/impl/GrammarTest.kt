package grammar.impl

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarTest {
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
    fun checkSuccess() {
        webclient.get().uri { builder ->
            builder
                .path("/api/check")
                .queryParam("grammar", "이거이렇게 안돼나요")
                .build()
        }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty
    }

    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull() {
        webclient.get().uri { builder ->
            builder
                .path("/api/check")
                .queryParam("grammar", "돼요")
                .build()
        }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isEmpty
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded() {
        webclient.get().uri { builder ->
            builder
                .path("/api/check")
                .queryParam("grammar", RandomStringUtils.randomAlphanumeric(501))
                .build()
        }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText() {
        webclient.get().uri { builder ->
            builder
                .path("/api/check")
                .queryParam("grammar", "")
                .build()
        }
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is4xxClientError
            .expectBody()
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }
}