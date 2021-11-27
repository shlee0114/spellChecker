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
    fun grammarCheck_returnValue_success() {
        preWebClient(value = "grammar=이거이렇게 안돼나요")
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty
    }

    @Test
    fun grammarCheck_returnEmptyValue_success() {
        preWebClient(value = "grammar=돼요")
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isEmpty
    }

    @Test
    fun overLengthGrammarCheck_throwException_fail() {
        preWebClient(value = "grammar=${randomAlphanumeric(501)}")
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }

    @Test
    fun emptyGrammarCheck_throwException_fail() {
        preWebClient(value = "grammar=")
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.response").isEmpty
    }
}