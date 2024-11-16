package riwi.assesment.clinic.repositories;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.assesment.clinic.entities.Doctor;
import riwi.assesment.clinic.entities.DoctorSchedules;

@Repository 
public interface DoctorSchedulesRepository extends JpaRepository<DoctorSchedules, Long>{
    List<DoctorSchedulesRepository> findByDoctorAndDate(Doctor doctor, LocalDate fecha);
    
}
