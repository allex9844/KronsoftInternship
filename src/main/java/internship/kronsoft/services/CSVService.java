package internship.kronsoft.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import internship.kronsoft.entities.CriminalRecord;
import internship.kronsoft.helper.CSVHelper;
import internship.kronsoft.repositories.RecordingsRepository;

@Service
public class CSVService {

	int nullId = 0;

	@Autowired
	private RecordingsRepository recordingsRepository;

	public void save(MultipartFile file) {
		try {
			List<CriminalRecord> records = CSVHelper.csvToRecords(file.getInputStream());
			recordingsRepository.saveAll(records);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<CriminalRecord> getAllTutorials() {
		return recordingsRepository.findAll();
	}

}
