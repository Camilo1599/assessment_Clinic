package riwi.assesment.clinic.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import riwi.assesment.clinic.dto.AppointmentDTO;
import riwi.assesment.clinic.entities.Appointment;
import riwi.assesment.clinic.entities.Patient;
import riwi.assesment.clinic.services.AppointmentService;
import riwi.assesment.clinic.services.PatientService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;
    
    @Autowired
    private PatientService PatientService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> scheduleAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        // Convert DTO to entity
        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment.setReason(appointmentDTO.getReason());
        // Here we should assign doctor and patient using received IDs
        // Assuming they are already loaded

        Appointment scheduledAppointment = appointmentService.scheduleAppointment(appointment);

        // Convert saved appointment back to DTO
        AppointmentDTO response = new AppointmentDTO();
        response.setId(scheduledAppointment.getId());
        response.setDateTime(scheduledAppointment.getDateTime());
        response.setReason(scheduledAppointment.getReason());

        return ResponseEntity.ok(response);
    }

    

    // Opcional: Filtrar el historial por fechas
    @GetMapping("/record/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentHistory(@PathVariable Long patientId) {
        Patient patient = PatientService.getPatientById(patientId);

        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Appointment> historial = appointmentService.getAppointmentHistory(patient);
        return ResponseEntity.ok(historial);
    }


    @GetMapping("/record/{patientId}/fechas")
    public ResponseEntity<List<Appointment>> getAppointmentHistoryByDates(
            @PathVariable Long patientId,
            @RequestParam String start,
            @RequestParam String end) {

        Patient patient = PatientService.getPatientById(patientId);
        if (patient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        LocalDateTime dateStart = LocalDateTime.parse(start);
        LocalDateTime dateEnd = LocalDateTime.parse(end);

        List<Appointment> record = appointmentService.getAppointmentHistoryByDates(patient, dateStart, dateEnd);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/record/{patientId}/problema")
    public ResponseEntity<List<Appointment>> getAppointmentHistoryByIssue(
            @PathVariable Long patientId,
            @RequestParam String problemaMedico) {

        Patient paciente = PatientService.getPatientById(patientId);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Appointment> record = appointmentService.getAppointmentHistoryByIssue(paciente, problemaMedico);
        return ResponseEntity.ok(record);
    }
}

