package ru.yandex.practicum.javafilmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class JavaFilmorateApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {

    }

}
