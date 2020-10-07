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
import internship.kronsoft.exceptions.FileExtensionNotCSVException;
import internship.kronsoft.exceptions.NoFileSelectedException;
import internship.kronsoft.helper.CSVHelper;
import internship.kronsoft.message.ResponseMessageDTO;
import internship.kronsoft.repositories.RecordingsRepository;

@Service
public class CriminalRecordsService {

	@Autowired
	private RecordingsRepository recordingsRepository;

	public ResponseMessageDTO save(MultipartFile file) {

		ResponseMessageDTO response = new ResponseMessageDTO();

		if (CSVHelper.hasCSVFormat(file)) {

			try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
					CSVParser csvParser = new CSVParser(fileReader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				List<CriminalRecord> records = new ArrayList<CriminalRecord>();
				Iterable<CSVRecord> csvRecords = csvParser.getRecords();

				for (CSVRecord csvRecord : csvRecords) {
					
					boolean noProblemFound=true;

					CriminalRecord criminalRecord = csvToCriminalRecord(csvRecord);

					if (criminalRecord.getCrimeId().isEmpty()) {
						response.incrementNullId();
						noProblemFound=false;
					}

					if (!criminalRecord.getLsoaCode().matches("[A-Z]{1}[0-9]{8}")) {
						response.incrementInvalidLSOA();
						noProblemFound=false;
					}

					if (noProblemFound && !criminalRecord.getLsoaCode().isEmpty()) {
						records.add(criminalRecord);
						response.incrementSuccessfullEntries();
					}
				}
				recordingsRepository.saveAll(records);
				response.setMessage("Uploaded the file successfully: " + file.getOriginalFilename());
				return response;
			} catch (IOException e) {
				throw new RuntimeException("fail to parse or store the CSV file: " + e.getMessage());
			}
		}
		
		else if (CSVHelper.noFileSelected(file)) {
			throw new NoFileSelectedException();
		}
		
		else {
			throw new FileExtensionNotCSVException();
		}
		
	}

	public List<CriminalRecord> getAllRecords() {
		return recordingsRepository.findAll();
	}

//	private boolean idAlreadyExists(List<CriminalRecord> list, CriminalRecord criminalRecord) {
//		for (CriminalRecord record : list) {
//			if (record.getCrimeId().equals(criminalRecord.getCrimeId())) {
//				return true;
//			}
//		}
//		return false;
//	}

	private BigDecimal verifyIfNull(String value) {
		if (value.isEmpty()) {
			return null;
		}
		return new BigDecimal(value);
	}

	private CriminalRecord csvToCriminalRecord(CSVRecord csvRecord) {
		YearMonth yearMonth = YearMonth.parse(csvRecord.get("Month"));
		CriminalRecord criminalRecord = new CriminalRecord(csvRecord.get("Crime ID"),
				LocalDate.from(yearMonth.atDay(1)), csvRecord.get("Reported by"), csvRecord.get("Falls within"),
				verifyIfNull(csvRecord.get("Longitude")),
				verifyIfNull(csvRecord.get("Latitude")), csvRecord.get("Location"),
				csvRecord.get("LSOA code"), csvRecord.get("LSOA name"), csvRecord.get("Crime type"),
				csvRecord.get("Last outcome category"), csvRecord.get("Context"));
		return criminalRecord;
	}

}
