package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [GrammerCheckerApplication::class])
class GrammarLogTestGraphql {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    @Test
    @Order(1)
    @DisplayName("등록 성공")
    fun checkSuccess(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/log.graphql")
        val result = response.readTree().get("data").get("log")

        Assertions.assertEquals(result.toString(), "true")
        Assertions.assertNotNull(result.asText())
    }

    @Test
    @Order(2)
    @DisplayName("등록 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/logOverLength.graphql")
        val result = response.readTree()


        Assertions.assertNull(result.get("data").get("log"))
        Assertions.assertNotNull(result.get("errors"))
    }
}