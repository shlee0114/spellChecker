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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest(classes = [GrammerCheckerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class GrammarLogTest {

    private lateinit var mockMvc : MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc){
        this.mockMvc = mockMvc
    }

    @Test
    @Order(1)
    @DisplayName("등록 성공")
    fun checkSuccess(){
        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/log")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"errorText\":\"되요\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
        )
        result.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(true)))
    }

    @Test
    @Order(2)
    @DisplayName("등록 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){
        var result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/log")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"errorText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedText\":\"돼요\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
        )
        result.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(false)))

        result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/log")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"errorText\":\"되요\",\"fixedText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
        )
        result.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(false)))

        result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/log")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"errorText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedText\":\"${RandomStringUtils.randomAlphanumeric(501)}\",\"fixedCount\":1,\"ip\":\"123.123.123.123\"}")
        )
        result.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.`is`(false)))
    }
}