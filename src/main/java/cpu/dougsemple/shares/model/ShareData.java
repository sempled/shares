package cpu.dougsemple.shares.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShareData(
        String symbol,
        @DateTimeFormat(pattern = "dd-MM-YYYY")
        Date date,
        float high,
        float low,
        float avg
){

}
