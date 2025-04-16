// package ro.unibuc.hello.controller;

// import org.junit.jupiter.api.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.http.*;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.containers.MongoDBContainer;
// import ro.unibuc.hello.model.Event;

// import java.util.List;

// import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @Testcontainers
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class EventControllerIntegrationTest {

//     @Container
//     static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.6");

//     @Autowired
//     private TestRestTemplate restTemplate;

//     static String createdEventId;

//     @BeforeAll
//     static void setup() {
//         mongoDBContainer.start();
//         System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
//     }

//     @Test
//     void testAddEvent() {
//         Event event = new Event();
//         event.setUserId("user456");
//         event.setName("Board Game Night");
//         event.setDescription("Fun games with snacks");
//         event.setUsernames(List.of("john", "emma", "alex"));

//         ResponseEntity<Event> response = restTemplate.postForEntity("/api/event/add", event, Event.class);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//         assertThat(response.getBody()).isNotNull();
//         assertThat(response.getBody().getUsernames()).contains("emma");

//         createdEventId = response.getBody().getEventId();
//     }

//     @Test
//     void testGetEventById() {
//         ResponseEntity<Event> response = restTemplate.getForEntity("/api/event/eventId/" + createdEventId, Event.class);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//         assertThat(response.getBody()).isNotNull();
//         assertThat(response.getBody().getName()).isEqualTo("Board Game Night");
//         assertThat(response.getBody().getUsernames().size()).isEqualTo(3);
//     }
// }
