package grammar.impl

import com.grammer.grammerchecker.GrammerCheckerApplication
import grammar.TestDefaultSetting
import grammar.type.CheckType
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.junit.jupiter.api.*
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarTest : TestDefaultSetting() {

    override val uri: String = "/api/check"

    @Test
    @Order(1)
    @DisplayName("검색 성공")
    fun checkSuccess() {
        preWebClient(value = "grammar=이거이렇게 안돼나요")
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty
    }

    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull() {
        preWebClient(value = "grammar=돼요")
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isEmpty
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded() {
        preWebClient(value = "grammar=${randomAlphanumeric(501)}")
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText() {
        preWebClient(value = "grammar=")
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }
}