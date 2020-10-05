package internship.kronsoft.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
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
import internship.kronsoft.message.ResponseMessageDTO;
import internship.kronsoft.repositories.RecordingsRepository;

@Service
public class CriminalRecordsService {

	@Autowired
	private RecordingsRepository recordingsRepository;

	public ResponseMessageDTO save(MultipartFile file, String message) {
		
		ResponseMessageDTO response = new ResponseMessageDTO(message);

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<CriminalRecord> records = new ArrayList<CriminalRecord>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {

				YearMonth yearMonth = YearMonth.parse(csvRecord.get("Month"));

				CriminalRecord criminalRecord = recording(csvRecord, yearMonth);

				if (criminalRecord.getCrimeId().isEmpty()) {
					response.incrementNullId();
				}

				if (!CSVHelper.checkLSOACode(criminalRecord.getLsoaCode())) {
					response.incrementInvalidLSOA();
				}

				if (!criminalRecord.getCrimeId().isEmpty() && !criminalRecord.getLsoaCode().isEmpty()
						&& CSVHelper.checkLSOACode(criminalRecord.getLsoaCode())
						&& !idAlreadyExists(records, criminalRecord)) {
					records.add(criminalRecord);
					response.incrementSuccessfullEntries();
				}

			}
			recordingsRepository.saveAll(records);
			return response;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse or store CSV file: " + e.getMessage());
		}
	}

	public List<CriminalRecord> getAllRecords() {
		return recordingsRepository.findAll();
	}

	private boolean idAlreadyExists(List<CriminalRecord> list, CriminalRecord criminalRecord) {
		for (CriminalRecord record : list) {
			if (record.getCrimeId().equals(criminalRecord.getCrimeId())) {
				return true;
			}
		}
		return false;
	}
	
	private String verifyValues(String value) {
		if(value.isEmpty()) {
			return "0";
		}
		return value;
	}

	private CriminalRecord recording(CSVRecord csvRecord, YearMonth yearMonth) {
		CriminalRecord criminalRecord = new CriminalRecord(
				csvRecord.get("Crime ID"),
				LocalDate.from(yearMonth.atDay(1)),
				csvRecord.get("Reported by"),
				csvRecord.get("Falls within"),
				new BigDecimal(verifyValues(csvRecord.get("Longitude"))),
				new BigDecimal(verifyValues(csvRecord.get("Latitude"))),
				csvRecord.get("Location"),
				csvRecord.get("LSOA code"),
				csvRecord.get("LSOA name"),
				csvRecord.get("Crime type"),
				csvRecord.get("Last outcome category"),
				csvRecord.get("Context"));
		return criminalRecord;
	}

}
