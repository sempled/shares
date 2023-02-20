package cpu.dougsemple.shares.controller;

import cpu.dougsemple.shares.ShareApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShareApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShareControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test Stock Data - IBM")
    @Order(1)
    void testStockData() throws Exception {
        this.mvc.perform(post("/api/shares/StockData")
                        .param("symbol", "IBM"))
                .andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Stock data for IBM in week ending")));
    }

    @Test
    @DisplayName("Test export JSON - IBM")
    @Order(2)
    void testExportJSON() throws Exception {
        this.mvc.perform(get("/api/shares/exportJSON"))
                .andDo(print()).andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string(containsString("date")))
                .andExpect(content().string(containsString("symbol")))
                .andExpect(content().string(containsString("high")))
                .andExpect(content().string(containsString("low")))
                .andExpect(content().string(containsString("avg")));
    }

    @Test
    @DisplayName("Test Stock Data - Empty Symbol")
    @Order(3)
    void testStockDataEmptySymbol() {
        try {
            this.mvc.perform(post("/api/shares/StockData")
                    .param("symbol", ""));
        }catch (Exception e) {
            assert(e.getMessage().contains("Stock Symbol can not be blank"));
        }
    }

    @Test
    @DisplayName("Test Stock Data - Incorrect Symbol")
    @Order(4)
    void testStockDataIncorrectSymbol() {
        try {
            this.mvc.perform(post("/api/shares/StockData")
                    .param("symbol", "?"));
        }catch (Exception e) {
            assert(e.getMessage().contains("Unable to get stock data for"));
        }
    }
}