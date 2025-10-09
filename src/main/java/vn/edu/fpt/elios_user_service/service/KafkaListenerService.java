package vn.edu.fpt.elios_user_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.dto.EventWrapper;
import vn.edu.fpt.elios_user_service.dto.UserProfile;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class KafkaListenerService {
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "users")
    public void getUserById(EventWrapper requestEvent) {
        UserProfile userProfile = userService.get(UUID.fromString(requestEvent.payload().toString()));

        EventWrapper responseEvent = new EventWrapper(
                UUID.randomUUID(),
                "GET_USER_BY_ID",
                "User",
                userProfile

        );
        kafkaTemplate.send("users-response", responseEvent);
    }
}
