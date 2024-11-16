package riwi.assesment.clinic.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import riwi.assesment.clinic.entities.Appointment;
import riwi.assesment.clinic.entities.Patient;
import riwi.assesment.clinic.exceptions.ConflictException;
import riwi.assesment.clinic.repositories.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment scheduleAppointment(Appointment appointment){
        validateAvailability(appointment);
        return appointmentRepository.save(appointment);
    }

    public Appointment validateAvailability(Appointment appointment) {
        boolean conflict = appointmentRepository.existsByDoctorAndDateTimeAfter(
            appointment.getDoctor(),
            appointment.getDateTime()
        );
        if (conflict) {
            throw new ConflictException("The doctor is not available at this time.");
        }
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findAll().stream()
                .filter(appointment -> appointment.getDoctor().getId().equals(doctorId))
                .collect(Collectors.toList());
    }

      public List<Appointment> getAppointmentHistory(Patient patient) {
        return appointmentRepository.findByPacienteOrderByDateTime(patient);
    }

    public List<Appointment> getAppointmentHistoryByDates(Patient patient, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByPatientAndDateTimeBetweenOrderByDateTime(patient, start, end);
    }

    
    public List<Appointment> getAppointmentHistoryByIssue(Patient paciente, String reason) {
        return appointmentRepository.findByPatientAndReasonDoctorContainingIgnoreCaseOrderByDateTime(
                paciente, reason);
    }
}
