package com.pm.patientservice.kafka;

import com.pm.patientservice.Model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

// responsible for sending data

@Service
public class KafkaProducer {
    // string is sent into the byte array
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("Patient_created")
                .build();

        // using kafkaTemplate to send the
        try {
            kafkaTemplate.send("Patient", patientEvent.toByteArray());
            log.info("Patient event sent successfully");
        }catch (Exception e){
            log.error("Error sending patient created {}", patientEvent);
        }
    }
}
