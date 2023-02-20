package cpu.dougsemple.shares.model;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShareDataTest {

    private static ShareData testData;
    private static final String testSymbol = "IBM";
    private static final Date testDate = new Date();
    private static final float testHigh = 213.99f;
    private static final float testLow = 191.55f;
    private static final float testAvg = 202.77f;

    @Test
    @DisplayName("Test create share data")
    @Order(1)
    void shouldCreateShareData(){
        testData = new ShareData(testSymbol, testDate, testHigh, testLow, testAvg);
        assertNotNull(testData);
    }

    @Test
    @DisplayName("Test get symbol")
    @Order(2)
    void shouldGetSymbol() {
        assertEquals(testSymbol, testData.symbol());
    }

    @Test
    @DisplayName("Test get date")
    @Order(3)
    void shouldGetDate() {
        assertEquals(testDate, testData.date());
    }

    @Test
    @DisplayName("Test get high")
    @Order(4)
    void shouldGetHigh() {
        assertEquals(testHigh, testData.high());
    }

    @Test
    @DisplayName("Test get low")
    @Order(5)
    void shouldGetLow() {
        assertEquals(testLow, testData.low());
    }

    @Test
    @DisplayName("Test get avg")
    @Order(6)
    void shouldGetAvg() {
        assertEquals(testAvg, testData.avg());
    }
}