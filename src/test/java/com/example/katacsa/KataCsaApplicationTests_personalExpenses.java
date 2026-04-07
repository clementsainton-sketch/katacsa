package com.example.katacsa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.ObjectMapper;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KataCsaApplicationTests_personalExpenses {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addTwoExpenses_listIt_sumIt() {
        String url = "http://localhost:" + port;
        ResponseEntity<String> response;

        HttpHeaders headers = new HttpHeaders();
        headers.add("amount", "10");
        headers.add("category", "OCCASIONAL");
        headers.add("date", "2026-04-05");
        headers.add("description", "getting money");
        response = testRestTemplate.postForEntity(url + "/personalexpenses/add",
                        new HttpEntity<>(null, headers), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        headers.set("amount", "850");
        headers.set("category", "FIXED");
        headers.set("date", "2026-04-07");
        headers.set("description", "rent");
        response = testRestTemplate.postForEntity(url + "/personalexpenses/add",
                new HttpEntity<>(null, headers), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        response = testRestTemplate.getForEntity(url + "/personalexpenses/list", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("[{\"amount\":10.0,\"category\":\"OCCASIONAL\"," +
                "\"date\":\"2026-04-05T00:00:00.000Z\",\"description\":\"getting money\"},{\"amount\":850.0," +
                "\"category\":\"FIXED\",\"date\":\"2026-04-07T00:00:00.000Z\",\"description\":\"rent\"}]");

        response = testRestTemplate.getForEntity(url + "/personalexpenses/sum?category=FIXED", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("850.0");


    }

}
