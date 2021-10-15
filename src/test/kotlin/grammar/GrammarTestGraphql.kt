package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.json.JSONException
import org.json.JSONObject
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


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

        val query = "{\"query\":\"{check(text : \\\"되요\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"

        val mvcResult = mockMvc.perform(
            post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andExpect(request().asyncResult(CoreMatchers.notNullValue()))
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.check").isNotEmpty)
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    @Order(2)
    @DisplayName("검색 성공(수정 없음)")
    fun checkSuccessReturnIsNull(){

        val query = "{\"query\":\"{check(text : \\\"돼요\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"

        val mvcResult = mockMvc.perform(
            post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andExpect(request().asyncResult(CoreMatchers.notNullValue()))
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.data.check.errorText").isEmpty)
            .andExpect(jsonPath("$.errors").doesNotExist())
    }

    @Test
    @Order(2)
    @DisplayName("검색 실패(최대 길이 500자 초과)")
    fun checkFailedBecauseMaxLengthExceeded(){

        val query = "{\"query\":\"{check(text : \\\"${RandomStringUtils.randomAlphanumeric(501)}\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"

        val mvcResult = mockMvc.perform(
            post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andExpect(request().asyncResult(CoreMatchers.notNullValue()))
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andDo(print())
            .andExpect(jsonPath("$.data.check").isEmpty)
            .andExpect(jsonPath("$.errors").exists())
    }

    @Test
    @Order(3)
    @DisplayName("검색 실패(빈 텍스트 전달)")
    fun checkFailedBecauseEmptyText(){

        val query = "{\"query\":\"{check(text : \\\"\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"

        val mvcResult = mockMvc.perform(
            post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andExpect(request().asyncResult(CoreMatchers.notNullValue()))
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andDo(print())
            .andExpect(jsonPath("$.data.check").isEmpty)
            .andExpect(jsonPath("$.errors").exists())
    }

    @Test
    @Order(4)
    @DisplayName("검색 실패(띄어쓰기 발견)")
    fun checkFailedBecauseWhiteSpace(){

        val query = "{\"query\":\"{check(text : \\\"이거 돼요\\\") { errorText fixedText }}\",\"variables\":null,\"operationName\":null}"

        val mvcResult = mockMvc.perform(
            post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(request().asyncStarted())
            .andExpect(request().asyncResult(CoreMatchers.notNullValue()))
            .andReturn()

        mockMvc.perform(asyncDispatch(mvcResult))
            .andDo(print())
            .andExpect(jsonPath("$.data.check").isEmpty)
            .andExpect(jsonPath("$.errors").exists())
    }

}