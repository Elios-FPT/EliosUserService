package old.elios_user_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import old.elios_user_service.dto.EventWrapper;
import old.elios_user_service.dto.UserProfile;
import old.elios_user_service.enums.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class KafkaListenerService {
    private final UserService userService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "users")
    public void handleUserEvents(EventWrapper requestEvent) {
        if (requestEvent == null || requestEvent.eventType() == null) return;

        switch (requestEvent.eventType()) {
            case GET_USER_BY_ID:
                handleGetUserById(requestEvent);
                break;
            case GET_ALL_USER:
                handleGetAllUsers(requestEvent);
                break;
            default:
                // Log or handle unknown event types
                break;
        }
    }

    private void handleGetUserById(EventWrapper requestEvent) {
        UUID userId = UUID.fromString(requestEvent.payload().toString());
        UserProfile userProfile = userService.get(userId);

        EventWrapper responseEvent = new EventWrapper(
                UUID.randomUUID(),
                requestEvent.eventId(),
                EventType.GET_USER_BY_ID,
                "UserProfile",
                userProfile
        );

        kafkaTemplate.send("users-response", responseEvent);
    }

    private void handleGetAllUsers(EventWrapper requestEvent) {
        Pageable pageable = PageRequest.of(0, 10); // Or parse from requestEvent.payload()
        Page<UserProfile> userList = userService.list(pageable);

        EventWrapper responseEvent = new EventWrapper(
                UUID.randomUUID(),
                requestEvent.eventId(),
                EventType.GET_ALL_USER,
                "Page<UserProfile>",
                userList
        );

        kafkaTemplate.send("users-response", responseEvent);
    }

}
