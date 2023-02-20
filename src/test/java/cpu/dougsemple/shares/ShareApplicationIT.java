package cpu.dougsemple.shares;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareApplicationIT {

    @Test
    void main() {
        ShareApplication.main(new String[]{});
        boolean isRunning = false;
        for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
            if (s.getClassName().contains("cpu.dougsemple.shares.ShareApplication")) {
                isRunning = true;
            }
        }
        assert(isRunning);
    }

/*    @AfterAll
    static void cleanUP(){
        System.exit(0);
    }*/

}
