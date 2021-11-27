package grammar.impl

import com.grammer.grammerchecker.GrammerCheckerApplication
import grammar.GraphQLTestDefaultSetting
import grammar.type.GraphQLType
import org.apache.commons.lang3.RandomStringUtils.randomNumeric
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.junit.jupiter.api.*
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest(
    classes = [GrammerCheckerApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient(timeout = "10000")
class GrammarLogTestGraphql : GraphQLTestDefaultSetting() {

    @Test
    fun insertLog_returnTrue_success() {
        preWebClient(
            value = "log(input:{errorText:\\\"테스트\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"test\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data.log").isEqualTo(true)
    }

    @Test
    fun insertOverLengthLog_returnFalse_fail() {
        preWebClient(
            value = "log(input:{errorText:\\\"${randomAlphanumeric(501)}\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"test\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        preWebClient(
            value = "log(input:{errorText:\\\"테스트}\\\", fixedText:\\\"${randomAlphanumeric(501)}\\\", fixedCount:0, ip:\\\"test\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        preWebClient(
            value = "log(input:{errorText:\\\"테스트\\\", fixedText:\\\"테스트\\\", fixedCount:${randomNumeric(11)}, ip:\\\"test\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        preWebClient(
            value = "log(input:{errorText:\\\"테스트\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"${randomAlphanumeric(21)}\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun insertEmptyLog_returnFalse_fail() {
        preWebClient(
            value = "log(input:{errorText:\\\"\\\", fixedText:\\\"테스트\\\", fixedCount:0, ip:\\\"테스트\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()

        preWebClient(
            value = "log(input:{errorText:\\\"테스트\\\", fixedText:\\\"\\\", fixedCount:0, ip:\\\"테스트\\\"})",
            type = GraphQLType.MUTATION
        )
            .webClientCheck()
            .jsonPath("$.data").doesNotExist()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun searchLog_returnValue_success() {
        preWebClient(value = "log{error,fixed,count,fixedTime}")
            .webClientCheck()
            .jsonPath("$.data.log").isArray
    }
}