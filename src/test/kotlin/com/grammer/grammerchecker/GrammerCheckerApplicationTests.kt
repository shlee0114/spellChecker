package com.grammer.grammerchecker

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [GrammerCheckerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GrammerCheckerApplicationTests {

    @Test
    fun contextLoads() {
    }

}
