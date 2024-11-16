package riwi.assesment.clinic.repositories;
import java.util.List;
import java.util.Optional;

import riwi.assesment.clinic.entities.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

    Optional<Patient> getPatientById(Long id);


    public List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String searchTerm, String searchTerm0);
    
}
