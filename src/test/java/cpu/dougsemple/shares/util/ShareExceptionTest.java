package cpu.dougsemple.shares.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShareExceptionTest {

    @Test
    @DisplayName("Test standard exception")
    void standardException(){
        String expected = "Error in shares application";
        ShareException instance = new ShareException();
        String result = instance.getMessage();

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test custom exception")
    void customException(){
        String expected = "test";
        ShareException instance = new ShareException(expected);
        String result = instance.getMessage();

        assertEquals(expected, result);
    }

}
