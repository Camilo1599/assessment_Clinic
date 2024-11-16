package riwi.assesment.clinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.assesment.clinic.entities.Appointment;
import riwi.assesment.clinic.entities.Doctor;
import riwi.assesment.clinic.entities.Patient;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorIdAndDateTimeAfter(Long doctorId, LocalDateTime dateTime);
    boolean existsByDoctorAndDateTimeAfter(Doctor doctor, LocalDateTime dateTime);
    boolean existsByPatientAndReasonDoctorAndDateTime(Patient patient, String reason, LocalDateTime dateTime);
    List<Appointment> findByPacienteOrderByDateTime(Patient patient);
    List<Appointment> findByPatientAndDateTimeBetweenOrderByDateTime(Patient patient, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByPatientAndReasonDoctorContainingIgnoreCaseOrderByDateTime(
        Patient patient, String reason);
    List<Appointment> findByPatientOrderByAppointmentDateTimeDesc(Patient patient);
}
