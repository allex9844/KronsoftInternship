package internship.kronsoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import internship.kronsoft.helper.CSVHelper;
import internship.kronsoft.message.ResponseMessageDTO;
import internship.kronsoft.services.CSVService;

@RestController
public class CSVController {

	@Autowired
	private CSVService csvService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessageDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (CSVHelper.hasCSVFormat(file)) {

			try {
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(csvService.save(file));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				System.out.println(e);
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageDTO(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessageDTO(message));
	}

}
