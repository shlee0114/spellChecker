package grammar

import com.grammer.grammerchecker.GrammerCheckerApplication
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

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
}