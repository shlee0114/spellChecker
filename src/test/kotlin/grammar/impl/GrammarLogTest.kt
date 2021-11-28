package grammar.impl

import com.grammer.grammerchecker.GrammerCheckerApplication
import grammar.TestDefaultSetting
import grammar.type.CheckType
import grammar.type.GraphQLType
import grammar.type.RequestMethodType
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarLogTest : TestDefaultSetting() {

    override val uri: String = "/api/log"

    @Test
    fun insertLog_returnTrue_success() {
        preWebClient(
            value = "{\"errorText\":\"되요\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
    }

    @Test
    fun insertOverLengthLog_throwException_fail() {
        preWebClient(
            value = "{\"errorText\":\"${randomAlphanumeric(501)}\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.error.message").isEqualTo("'errorText' must be less than 500")

        preWebClient(
            value = "{\"errorText\":\"돠요\",\"fixedText\":\"${randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.error.message").isEqualTo("'fixedText' must be less than 500")

        preWebClient(
            value = "{\"errorText\":\"${randomAlphanumeric(501)}\",\"fixedText\":\"${randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.error.message").isEqualTo("'errorText' must be less than 500")
    }

    @Test
    fun insertEmptyLog_throwException_fail() {
        preWebClient(
            value = "{\"errorText\":\"\",\"fixedText\":\"테스트\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.error.message").isEqualTo("invalid value 'errorText'")

        preWebClient(
            value = "{\"errorText\":\"테스트\",\"fixedText\":\"\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}",
            type = RequestMethodType.POST
        )
            .webClientCheck(CheckType.CLIENT_ERROR)
            .jsonPath("$.success").isEqualTo(false)
            .jsonPath("$.error.message").isEqualTo("invalid value 'fixedText'")
    }


    @Test
    fun searchLog_returnValue_success() {
        preWebClient()
            .webClientCheck()
            .jsonPath("$.success").isEqualTo(true)
            .jsonPath("$.response").isNotEmpty
    }
}