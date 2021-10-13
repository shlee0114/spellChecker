package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest(classes = [GrammerCheckerApplication::class])
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class GrammarTestGraphql {

    private lateinit var mockMvc : MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc){
        this.mockMvc = mockMvc
    }

    @Test
    @Order(1)
    @DisplayName("검색 성공")
    fun checkSuccess(){
        mockMvc.perform(
            MockMvcRequestBuilders.post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"query\":\"{check(text : \\\"되요\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"))
    }

    @Test
    @Order(2)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){

    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText(){

    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(띄어쓰기 발견)")
    fun checkFailedBecauseWhiteSpace(){

    }
}