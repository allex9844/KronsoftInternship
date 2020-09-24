package internship.kronsoft.helper;

import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {

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

}
