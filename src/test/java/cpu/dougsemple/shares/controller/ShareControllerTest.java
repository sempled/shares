package cpu.dougsemple.shares.controller;

import cpu.dougsemple.shares.ShareApplication;
import cpu.dougsemple.shares.util.ShareException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
class ShareControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test Stock Selection")
    void testStockSelection() throws Exception {
        this.mvc.perform(get("/api/shares/StockSelection")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Stock Selection")));
    }

    @Test
    @DisplayName("Test Stock Data - No Symbol")
    void testStockDataNoSymbol() throws Exception {
        this.mvc.perform(post("/api/shares/StockData")).andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(status().reason(containsString("Required parameter 'symbol' is not present.")))
                .andExpect(content().string(containsString("")));
    }

    @Test
    @DisplayName("Test Export Json - Error")
    void testExportJsonError() {
        try {
            this.mvc.perform(get("/api/shares/exportJSON")).andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ShareException))
                    .andReturn();
        } catch (Exception e){
            assert(e.getMessage().contains("Unable to download data as json."));
        }
    }

    @Test
    @DisplayName("Test Error")
    void shouldError() throws Exception {
        this.mvc
                .perform(get("/api/shares/error"))
                .andExpect(status().is4xxClientError());
    }
}