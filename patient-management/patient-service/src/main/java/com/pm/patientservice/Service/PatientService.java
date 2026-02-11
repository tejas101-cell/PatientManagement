package com.pm.patientservice.Service;

import com.pm.patientservice.PatientDTO.PatientRequestDTO;
import com.pm.patientservice.PatientDTO.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    public List<PatientResponseDTO> getPatients();
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO requestDTO);
    public void deletePatient(UUID id);
}
