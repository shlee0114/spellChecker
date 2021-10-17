package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import com.graphql.spring.boot.test.GraphQLTestTemplate
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import java.io.IOException
import kotlin.jvm.Throws


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [GrammerCheckerApplication::class])
@AutoConfigureMockMvc
class GrammarTestGraphql {

    @Autowired
    private lateinit var graphQLTestTemplate: GraphQLTestTemplate

    private lateinit var mockMvc : MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc){
        this.mockMvc = mockMvc
    }


    @Test
    @Order(1)
    @DisplayName("검색 성공")
    @Throws(IOException::class)
    fun checkSuccess(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/grammar.graphql")
        val result = response.readTree().get("data").get("query")

        Assertions.assertEquals(result.get("errorText").toString(), "\"되요\"")
        Assertions.assertEquals(result.get("fixedText").toString(), "\"돼요\"")
        Assertions.assertNotNull(result.asText())
    }

    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/grammarNoFix.graphql");
        val result = response.readTree().get("data").get("query")

        Assertions.assertEquals(result.get("errorText").toString(), "\"\"")
        Assertions.assertEquals(result.get("fixedText").toString(), "\"\"")
        Assertions.assertNotNull(result.asText())
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/grammarOverLength.graphql")
        val result = response.readTree()

        Assertions.assertNull(result.get("data").get("query"))
        Assertions.assertNotNull(result.get("errors"))
    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText(){
        val response = this.graphQLTestTemplate.postForResource("graphQL/grammarEmptyError.graphql");
        val result = response.readTree()

        Assertions.assertNull(result.get("data").get("query"))
        Assertions.assertNotNull(result.get("errors"))
    }
}