package internship.kronsoft.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import internship.kronsoft.entities.CriminalRecord;
import internship.kronsoft.helper.CSVHelper;
import internship.kronsoft.repositories.RecordingsRepository;

@Service
public class CSVService {

	@Autowired
	private RecordingsRepository recordingsRepository;
	
	public int nullId=0;
	public int invalidLSOA=0;
	public int successfullEntries=0;

	public void save(MultipartFile file) {
		try {
			List<CriminalRecord> records = csvToRecords(file.getInputStream());
			recordingsRepository.saveAll(records);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	public List<CriminalRecord> getAllRecords() {
		return recordingsRepository.findAll();
	}
	
	private boolean alreadyExists(List<CriminalRecord> list, CriminalRecord criminalRecord) {
		for (CriminalRecord record : list) {
			if (record.getCrimeId().equals(criminalRecord.getCrimeId())) {
				return true;
			}
		}
		return false;
	}
	
	private List<CriminalRecord> csvToRecords(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<CriminalRecord> records = new ArrayList<CriminalRecord>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				CriminalRecord criminalRecord = new CriminalRecord(
						csvRecord.get("Crime ID"),
						csvRecord.get("Month"),
						csvRecord.get("Reported by"),
						csvRecord.get("Falls within"),
//	              Float.parseFloat(csvRecord.get("Longitude")),
						csvRecord.get("Longitude"),
//	              Float.parseFloat(csvRecord.get("Latitude")),
						csvRecord.get("Latitude"),
						csvRecord.get("Location"),
						csvRecord.get("LSOA code"),
						csvRecord.get("LSOA name"),
						csvRecord.get("Crime type"),
						csvRecord.get("Last outcome category"),
						csvRecord.get("Context"));

				// @@@@@@@@@@@@@@@@ needs much more improvement @@@@@@@@@@@@@@

				if (criminalRecord.getCrimeId().isEmpty()) {
					nullId++;
				}

				if (!CSVHelper.checkLSOACode(criminalRecord.getLsoaCode())) {
					invalidLSOA++;
				}

				if (!criminalRecord.getCrimeId().isEmpty() && !criminalRecord.getLsoaCode().isEmpty()
						&& CSVHelper.checkLSOACode(criminalRecord.getLsoaCode()) && !alreadyExists(records, criminalRecord)) {
					records.add(criminalRecord);
					successfullEntries++;
				}
				// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

			}

			return records;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}
