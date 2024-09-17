package com.github.devricks.bugzapperj.microservice.bug;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BugServiceApplicationTests {

    @Test
    void main_None_ShouldRunWithoutThrowingAnyExceptions() {
        assertDoesNotThrow(() -> BugServiceApplication.main(new String[]{}));
    }
}