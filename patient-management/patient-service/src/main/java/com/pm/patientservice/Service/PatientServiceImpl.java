package com.pm.patientservice.Service;

import com.pm.patientservice.Exception.EmailAlreadyExistsException;
import com.pm.patientservice.Exception.PatientNotFoundException;
import com.pm.patientservice.Mapper.PatientMapper;
import com.pm.patientservice.Model.Patient;
import com.pm.patientservice.PatientDTO.PatientRequestDTO;
import com.pm.patientservice.PatientDTO.PatientResponseDTO;
import com.pm.patientservice.Repository.PatientRepository;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService{
    private final KafkaProducer kafkaProducer;
    private PatientRepository patientRepository;
    private BillingServiceGrpcClient billingServiceGrpcClient;

    // for single constructor there is no need to use the @Autowired anotation
    // but we are using it
    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(
                patient -> PatientMapper.toDTO(patient)).toList();
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsPatientByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Patient with this email exists");
        }
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());

        kafkaProducer.sendEvent(patient);

        return PatientMapper.toDTO(patient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO requestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()->new PatientNotFoundException("Patient not found with id: "+id));

        if (patientRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("Patient with this email exists");
        }
        patient.setName(requestDTO.getName());
        patient.setEmail(requestDTO.getEmail());
        patient.setAddress(requestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    @Override
    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }

}
