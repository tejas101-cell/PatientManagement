package com.pm.patientservice.Repository;

import com.pm.patientservice.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    public boolean existsPatientByEmail(String email);
    public boolean existsByEmailAndIdNot(String email, UUID id);

    List<Patient> id(UUID id);
}
