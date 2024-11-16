package riwi.assesment.clinic.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import riwi.assesment.clinic.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
