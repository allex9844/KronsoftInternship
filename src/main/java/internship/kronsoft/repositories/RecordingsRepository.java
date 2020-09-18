package internship.kronsoft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import internship.kronsoft.entities.CriminalRecord;

public interface RecordingsRepository extends JpaRepository<CriminalRecord, String>  {
	


}
