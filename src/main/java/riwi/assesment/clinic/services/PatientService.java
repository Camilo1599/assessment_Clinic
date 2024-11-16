package riwi.assesment.clinic.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityNotFoundException;
import riwi.assesment.clinic.entities.Appointment;
import riwi.assesment.clinic.entities.Patient;
import riwi.assesment.clinic.repositories.AppointmentRepository;
import riwi.assesment.clinic.repositories.PatientRepository;

public class PatientService {
     @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with ID " + patientId + " not found."));
    }

    public List<Appointment> getPatientAppointmentHistory(Long patientId) {
        Patient patient = getPatientById(patientId);
        return appointmentRepository.findByPatientOrderByAppointmentDateTimeDesc(patient);
    }

    public List<Patient> searchPatientsByName(String searchTerm) {
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(searchTerm, searchTerm);
    }
}
