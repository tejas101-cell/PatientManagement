package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaConsumer {

    // defining that this method will listen to the events sent by the
    // patient-service
    @KafkaListener(topics = "Patient", groupId = "analytics-service")
    public void consumeEvent(byte[]event) {
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            // perform business logic
            log.info("Recieved patient info: [PatientId: {}, Patient name: {}, Patient email: {}]",
                    patientEvent.getPatientId(),
                    patientEvent.getName(),
                    patientEvent.getEmail());

        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing the data {}", e.getMessage());
        }

    }
}
