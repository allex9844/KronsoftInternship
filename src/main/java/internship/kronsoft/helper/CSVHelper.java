package internship.kronsoft.helper;

import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		return TYPE.equals(file.getContentType());
	}
	
	public static boolean noFileSelected(MultipartFile file) {
		return file.getContentType() == null;
	}
}
