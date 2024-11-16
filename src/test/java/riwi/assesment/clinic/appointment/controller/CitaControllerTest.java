package riwi.assesment.clinic.appointment.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import riwi.assesment.clinic.dto.AppointmentDTO;
import riwi.assesment.clinic.exceptions.ConflictException;
import riwi.assesment.clinic.services.AppointmentService;
import riwi.assesment.clinic.entities.Appointment;

@SpringBootTest
public class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Para simular peticiones HTTP

    @MockBean
    private AppointmentService appointmentService; // El servicio que maneja la lógica de negocio

    @Test
    public void testAgendarCitaSinConflictos() throws Exception {
        // Crear un CitaDTO de ejemplo
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateTime(LocalDateTime.parse("2024-11-15T10:00:00"));
        appointmentDTO.setReason("Consulta general");

        // Simulamos que el servicio devuelve la cita agendada correctamente
        Appointment appointment = new Appointment();
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment.setReason(appointmentDTO.getReason());
        
        when(appointmentService.scheduleAppointment(any(Appointment.class))).thenReturn(appointment);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/citas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(appointmentDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Esperamos que la respuesta sea 200 OK
    }

    @Test
    public void testAgendarCitaConConflicto() throws Exception {
        // Crear un CitaDTO de ejemplo
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDateTime(LocalDateTime.parse("2024-11-15T10:00:00"));
        appointmentDTO.setReason("Consulta general");

        // Simulamos que el servicio lanza una ConflictException
        doThrow(new ConflictException("El médico no está disponible en este horario."))
                .when(appointmentService).scheduleAppointment(any(Appointment.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/appointment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(appointmentDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict()); // Esperamos que la respuesta sea 409 (Conflicto)
    }
}

