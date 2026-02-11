package com.pm.patientservice.PatientDTO;

import com.pm.patientservice.PatientDTO.Validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDTO {
    @NotBlank
    @Size(max = 100, message = "Names cannot be over 100 charecters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of birth is required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatientValidationGroup.class, message =  "Registered date is required")
    private String registeredDate;


}
