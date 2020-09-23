package internship.kronsoft.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import internship.kronsoft.entities.CriminalRecord;

public class CSVHelper {

	public static int nullId = 0;
	public static int invalidLSOA = 0;
	public static int successfullEntries = 0;

	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Crime ID", "Month", "Reported by", "Falls within", "Longitude", "Latitude", "Location",
			"LSOA code", "LSOA name", "Crime type", "Last outcome category", "Context" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static boolean checkLSOACode(String s) {
		return s.matches("[A-Z]{1}[0-9]{8}");
	}

	public static boolean alreadyExists(List<CriminalRecord> list, CriminalRecord criminalRecord) {
		for (CriminalRecord record : list) {
			if (record.getCrimeId().equals(criminalRecord.getCrimeId())) {
				return true;
			}
		}
		return false;
	}

	public static List<CriminalRecord> csvToRecords(InputStream is) {
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

				if (!checkLSOACode(criminalRecord.getLsoaCode())) {
					invalidLSOA++;
				}

				if (!criminalRecord.getCrimeId().isEmpty() && !criminalRecord.getLsoaCode().isEmpty()
						&& checkLSOACode(criminalRecord.getLsoaCode()) && !alreadyExists(records, criminalRecord)) {
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
