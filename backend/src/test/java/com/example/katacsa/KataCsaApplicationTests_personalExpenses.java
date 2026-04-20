package com.example.katacsa;

import com.example.katacsa.personalexpenses.dto.PersonalExpenseDTO;
import com.example.katacsa.personalexpenses.model.Category;
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

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        ResponseEntity<String> response;
        String url = "http://localhost:" + port;

        Date date = new GregorianCalendar(2026, Calendar.APRIL, 5).getTime();
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO(10, Category.Occasional, date, "getting money");

        response = testRestTemplate.postForEntity(url + "/personalexpenses/add",
                new HttpEntity<>(personalExpenseDTO), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        personalExpenseDTO = new PersonalExpenseDTO(850, Category.Fixed, date, "rent");

        response = testRestTemplate.postForEntity(url + "/personalexpenses/add",
                new HttpEntity<>(personalExpenseDTO), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        personalExpenseDTO = new PersonalExpenseDTO(-850, Category.Fixed, date, "rent");

        response = testRestTemplate.postForEntity(url + "/personalexpenses/add",
                new HttpEntity<>(personalExpenseDTO), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        response = testRestTemplate.getForEntity(url + "/personalexpenses/list", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("[{\"amount\":10.0,\"category\":\"Occasional\"," +
                "\"date\":\"Apr 5, 2026, 12:00:00 AM\",\"description\":\"getting money\"}," +
                "{\"amount\":850.0,\"category\":\"Fixed\",\"date\":\"Apr 5, 2026, 12:00:00 AM\"," +
                "\"description\":\"rent\"}]");

        response = testRestTemplate.getForEntity(url + "/personalexpenses/sum?category=Fixed", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("850.0");
    }
}
