package cpu.dougsemple.shares.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cpu.dougsemple.shares.model.ShareData;
import cpu.dougsemple.shares.util.ShareException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/shares")
public class ShareController {

    private static final Logger log = LoggerFactory.getLogger(ShareController.class);
    @Autowired
    ObjectMapper mapper;
    @Value("${shareApiUrl}")
    private String apiUrl;
    @Value("${shareApiKey}")
    private String apiKey;
    private ShareData theData;

    @Bean
    private RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping("/StockSelection")
    public ModelAndView submitSymbol(@ModelAttribute("symbol") String symbol) throws ShareException {
        log.info("Displaying stock selection form.  ");
        try {
            ModelAndView mav = new ModelAndView("StockSelection");
            mav.addObject("symbol", symbol);
            return mav;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ShareException("Unable to display stock selection form.");
        }
    }

    @PostMapping(value="/StockData")
    public ModelAndView getStockData(@RequestParam(value="symbol") String symbol , RestTemplate restTemplate) throws ShareException {
        log.info("Received request for share data for {}" , symbol);
        if (symbol.isBlank() || symbol.isEmpty()) {
            log.error("Stock Symbol can not be blank");
            throw new ShareException("Stock Symbol can not be blank");
        }
        try {
            ModelAndView mav = new ModelAndView("StockData");
            mav.addObject("stockdata", getShareData(restTemplate, symbol));
            log.info("Displaying share data for {}", symbol);
            return mav;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ShareException("Unable to get stock data for " + symbol);
        }
    }

    private ShareData getShareData(RestTemplate restTemplate, String symbol) {

        String url = MessageFormat.format(apiUrl, symbol, apiKey);

        ResponseEntity<String> response = restTemplate.getForEntity(
                url, String.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            log.error("Error accessing share api");
            return null;
        }

        ShareData data = null;
        try {
            data = getPreviousWeeksData(response.getBody());
        } catch (JsonProcessingException | ShareException e) {
            log.error("Unable to get share data for last week.\nMalformed JSON in response body.\n{}", e.toString());
            return null;
        }

        return data;

    }

    private ShareData getPreviousWeeksData(String responseBody) throws JsonProcessingException, ShareException {

        JsonNode root = mapper.readTree(responseBody);

        String key = root
                .get("Meta Data")
                .get("3. Last Refreshed")
                .toString()
                .replace("\"", "");

        String symbol = root
                .get("Meta Data")
                .get("2. Symbol")
                .toString()
                .replace("\"", "");

        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.parse(key);
        } catch (ParseException e) {
            log.error("Unable to parse date\n{}", e.getMessage());
            throw new ShareException("Unable to parse date");
        }

        float high = Float.parseFloat(root
                .get("Weekly Time Series")
                .get(key).get("2. high")
                .toString()
                .replace("\"", ""));

        float low = Float.parseFloat(root
                .get("Weekly Time Series")
                .get(key)
                .get("3. low")
                .toString()
                .replace("\"", ""));

        float avg = (high + low)/2f;

        theData = new ShareData(symbol,date,high,low,avg);

        return theData;
    }

    @GetMapping("/exportJSON")
    public ModelAndView exportJSON() throws ShareException {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        log.info("Downloading data as json.  ");
        try {
        mav.addObject("symbol",theData.symbol());
        mav.addObject( "date",theData.date().toString());
        mav.addObject("high", theData.high());
        mav.addObject("low", theData.low());
        mav.addObject("avg", theData.avg());
        return mav;
        } catch (Exception e) {
            log.error(e.toString());
            throw new ShareException("Unable to download data as json.");
        }
    }

}
