package com.adrrriannn.text.analyser.service;

import com.adrrriannn.text.analyser.model.RandomTextAPIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adrian on 26/04/2017.
 */
@Service
public class RandomTextAPIService {
    
    private static final Logger log = LoggerFactory.getLogger(RandomTextAPIService.class);

    private RestTemplate restTemplate;
    private String randomTextAPIUrl;
    
    @Autowired
    public RandomTextAPIService(RestTemplate restTemplate,
                                 @Value("${api.random.text.url}") String randomTextAPIUrl) {
        this.restTemplate = restTemplate;
        this.randomTextAPIUrl = randomTextAPIUrl;
    }

    protected String getRandomText(Integer paragraphs, Integer minWordsSentence, Integer maxWordsSentence) {

        Map<String, Object> params = new HashMap<>();
        params.put("paragraphs", paragraphs);
        params.put("min-words", minWordsSentence);
        params.put("max-words", maxWordsSentence);

        HttpHeaders httpHeaders = new HttpHeaders();
        
        //Requests without User-Agent header were denied by the server. 
        // Hardcoded sample User-Agent in order to make it work
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(httpHeaders);

        String text = "";
        try {
            ResponseEntity<RandomTextAPIResponse> response = restTemplate.exchange(randomTextAPIUrl, HttpMethod.GET, entity, RandomTextAPIResponse.class, params);
            if(response.getStatusCode().is2xxSuccessful()) {
               
                RandomTextAPIResponse randomTextAPIResponse = response.getBody();
                if(randomTextAPIResponse != null) {
                    text = randomTextAPIResponse.getTextOut();                
                }
            }
        } catch (Exception ex) {
            log.error("Error calling Random Text API with parameters -> paragraphs : {}, " +
                "min-words : {}, max-words : {}. Exception {}", 
                paragraphs, minWordsSentence, maxWordsSentence, ex
            );
        }

        return text;
    }
}
