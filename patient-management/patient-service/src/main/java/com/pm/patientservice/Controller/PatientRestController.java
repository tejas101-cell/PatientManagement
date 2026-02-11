package com.pm.patientservice.Controller;

import com.pm.patientservice.PatientDTO.PatientRequestDTO;
import com.pm.patientservice.PatientDTO.PatientResponseDTO;
import com.pm.patientservice.PatientDTO.Validators.CreatePatientValidationGroup;
import com.pm.patientservice.Service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "Api for managing patients")
public class PatientRestController {
    private final PatientService patientService;

    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }
    // ResponseEntity creates an HTTP response
    // with ResponseEntity we can manipulate the HTTP response
    @GetMapping
    @Operation(summary = "Get patients")
    public ResponseEntity<List<PatientResponseDTO>> gotPatients(){
        List<PatientResponseDTO> patients = patientService.getPatients();
        // .ok means we are sending the success status
        // .body means we are adding the list of patients into the response body
        return ResponseEntity.ok().body(patients);
    }

    // @Valid will avail all the annotations which are being implemented inside the patientRequestDTO method
    @PostMapping
    @Operation(summary = "Post patients")
    public ResponseEntity<PatientResponseDTO> createdPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO requestDTO){
        PatientResponseDTO responseDTO = patientService.createPatient(requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patients")
    public ResponseEntity<PatientResponseDTO> updatedPatient(@PathVariable("id")UUID patientId,
                                                             @Validated({Default.class}) @RequestBody PatientRequestDTO requestDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(patientId, requestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletedPatient(@PathVariable("id") UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
