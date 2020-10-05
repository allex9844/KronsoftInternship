package internship.kronsoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import internship.kronsoft.entities.CriminalRecord;

@Repository
public interface RecordingsRepository extends JpaRepository<CriminalRecord, String>  {
	
}
