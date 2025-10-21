package vn.edu.fpt.elios_user_service.application.usecasehandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.fpt.elios_user_service.application.dto.response.UserProfileResponse;
import vn.edu.fpt.elios_user_service.application.eventpublisher.UserEventPublisher;
import vn.edu.fpt.elios_user_service.application.usecase.GetUserById;
import vn.edu.fpt.elios_user_service.application.usecase.ListUsers;
import vn.edu.fpt.elios_user_service.application.usecase.ProcessUserEvent;
import vn.edu.fpt.elios_user_service.domain.exception.NotFoundException;
import vn.edu.fpt.elios_user_service.enums.EventType;
import vn.edu.fpt.elios_user_service.application.dto.event.EventWrapper;
import vn.edu.fpt.elios_user_service.infra.config.KafkaTopicProperties;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessUserEventHandler implements ProcessUserEvent {
    private final GetUserById getUserById;
    private final ListUsers listUsers;
    private final UserEventPublisher userEventPublisher;
    private final KafkaTopicProperties kafkaTopicProperties;

    @Override
    public EventWrapper processEvent(EventWrapper requestEvent, String sourceService) {
        String responseTopic = kafkaTopicProperties.getResponseTopic(sourceService);

        if (requestEvent == null || requestEvent.eventType() == null) {
            EventWrapper errorResponse = EventWrapper.error(
                UUID.randomUUID(),
                requestEvent != null ? requestEvent.correlationId() : null,
                null,
                "Invalid event: missing eventType"
            );
            userEventPublisher.publishResponse(responseTopic, errorResponse);
            return errorResponse;
        }

        try {
            EventWrapper responseEvent = switch (requestEvent.eventType()) {
                case GET_BY_ID -> handleGetUserById(requestEvent);
                case GET_ALL -> handleGetAllUsers(requestEvent);
                default -> EventWrapper.error(
                        UUID.randomUUID(),
                        requestEvent.correlationId(),
                        requestEvent.eventType(),
                        "Invalid event: unhandled eventType: " + requestEvent.eventType()
                );
            };
            
            userEventPublisher.publishResponse(responseTopic, responseEvent);
            return responseEvent;
        } catch (Exception e) {
            log.error("Error processing event: {}", e.getMessage(), e);
            EventWrapper errorResponse = EventWrapper.error(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                requestEvent.eventType(),
                "Internal server error: " + e.getMessage()
            );
            userEventPublisher.publishResponse(responseTopic, errorResponse);
            return errorResponse;
        }
    }

    private EventWrapper handleGetUserById(EventWrapper requestEvent) {
        try {
            UUID userId = UUID.fromString(requestEvent.payload().toString());
            UserProfileResponse userProfile = getUserById.getById(userId);
            
            return EventWrapper.success(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                EventType.GET_BY_ID,
                "User",
                userProfile
            );
        } catch (IllegalArgumentException e) {
            return EventWrapper.error(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                EventType.GET_BY_ID,
                "Invalid user ID format"
            );
        } catch (NotFoundException e) {
            return EventWrapper.error(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                EventType.GET_BY_ID,
                e.getMessage()
            );
        }
    }

    private EventWrapper handleGetAllUsers(EventWrapper requestEvent) {
        try {
            Pageable pageable = parsePageableFromPayload(requestEvent.payload());
            Page<UserProfileResponse> userList = listUsers.list(pageable, null);
            
            return EventWrapper.success(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                EventType.GET_ALL,
                "User",
                userList
            );
        } catch (Exception e) {
            return EventWrapper.error(
                UUID.randomUUID(),
                requestEvent.correlationId(),
                EventType.GET_ALL,
                "Error retrieving users: " + e.getMessage()
            );
        }
    }

    private Pageable parsePageableFromPayload(Object payload) {
        if (payload instanceof Map<?, ?> map) {
            int page = map.containsKey("page") ? (Integer) map.get("page") : 0;
            int size = map.containsKey("size") ? (Integer) map.get("size") : 10;
            return PageRequest.of(page, size);
        }
        // Default pagination if payload is not a map or doesn't contain pagination info
        return PageRequest.of(0, 10);
    }
}
