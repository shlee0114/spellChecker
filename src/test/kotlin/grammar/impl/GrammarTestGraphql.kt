package grammar.impl

import com.grammer.grammerchecker.GrammerCheckerApplication
import grammar.GraphQLTestDefaultSetting
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.*
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarTestGraphql : GraphQLTestDefaultSetting() {

    @Test
    fun grammarCheck_returnValue_success(){
        preWebClient(value = "check(text:\\\"되요\\\"){errorText fixedText}")
            .webClientCheck()
            .jsonPath("$.data.check.errorText").isEqualTo("되요")
            .jsonPath("$.data.check.fixedText").isEqualTo("돼요")
    }

    @Test
    fun grammarCheck_returnEmptyValue_success(){
        preWebClient(value = "check(text:\\\"돼요\\\"){errorText fixedText}")
            .webClientCheck()
            .jsonPath("$.data.check.errorText").isEqualTo("")
            .jsonPath("$.data.check.fixedText").isEqualTo("")
    }

    @Test
    fun overLengthGrammarCheck_throwException_fail(){
        preWebClient(value = "check(text:\\\"${RandomStringUtils.randomAlphanumeric(501)}\\\"){errorText fixedText}")
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun emptyGrammarCheck_throwException_fail(){
        preWebClient(value = "check(text:\\\"\\\"){errorText fixedText}")
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }
}