package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(classes = [GrammerCheckerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class GrammarTest{

    private lateinit var mockMvc : MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc){
        this.mockMvc = mockMvc
    }

    @Test
    @Order(1)
    @DisplayName("검색 성공")
    fun checkSuccess(){
        val result = mockMvc.perform(
            get("/api/check")
                .param("grammar", "이거이렇게 안돼나요")
        )
        result.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(jsonPath("$.response").isNotEmpty)
    }
    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull(){
        val result = mockMvc.perform(
            get("/api/check")
                .param("grammar", "돼요")
        )
        result.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success", Matchers.`is`(true)))
            .andExpect(jsonPath("$.response").isEmpty)
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        val result = mockMvc.perform(
            get("/api/check")
                .param("grammar", RandomStringUtils.randomAlphanumeric(501))
        )
        result.andDo(print())
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.success", Matchers.`is`(false)))
    }
    @Test
    @Order(4)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText(){
        val result = mockMvc.perform(
            get("/api/check")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("grammar", "")
        )
        result.andDo(print())
            .andExpect(status().is5xxServerError)
            .andExpect(jsonPath("$.success", Matchers.`is`(false)))
    }



}