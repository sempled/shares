package cpu.dougsemple.shares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ShareApplication {

    private static String homepage;
    @Value("${homepage}")
    public void setHomepage(String uri){homepage = uri;}
    private static final Logger log = LoggerFactory.getLogger(ShareApplication.class);

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(ShareApplication.class, args);
        try {
            log.info("Opening homepage");
            openHomePage();
        } catch (IOException e) {
            log.error(e.toString());
        }
        log.info("Application closing");
    }

    private static void openHomePage() throws IOException {

        if (System.getProperty("os.name").contains("Windows")) {
            // Windows
            Runtime rt = Runtime.getRuntime();
            rt.exec("rundll32 url.dll,FileProtocolHandler " + homepage);
        } else {
            // Linux
            Runtime.getRuntime().exec("google-chrome " + homepage);
        }
    }
}
