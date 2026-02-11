package com.pm.patientservice.Mapper;

import com.pm.patientservice.Model.Patient;
import com.pm.patientservice.PatientDTO.PatientRequestDTO;
import com.pm.patientservice.PatientDTO.PatientResponseDTO;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientResponseDTO = PatientResponseDTO.builder()
                .id(patient.getId().toString())
                .name(patient.getName())
                .email(patient.getAddress())
                .address(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .build();
        return patientResponseDTO;
    }
    public static Patient toModel(PatientRequestDTO patientRequestDTO){
        Patient patient = Patient.builder()
                .name(patientRequestDTO.getName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .dateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()))
                .registeredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()))
                .build();
        return patient;
    }
}
