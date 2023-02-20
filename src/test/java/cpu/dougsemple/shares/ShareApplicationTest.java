package cpu.dougsemple.shares;

import cpu.dougsemple.shares.controller.ShareController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ShareApplicationTest {

    @Autowired
    private ShareController testController;

    @Test
    void contextLoads() {
        assertNotNull(testController);
    }

}
