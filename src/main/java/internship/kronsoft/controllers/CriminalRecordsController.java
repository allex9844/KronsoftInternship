package internship.kronsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import internship.kronsoft.message.ResponseMessageDTO;
import internship.kronsoft.services.CriminalRecordsService;

@RestController
@RequestMapping("/criminalrecords")
public class CriminalRecordsController {

	@Autowired
	private CriminalRecordsService crimRecService;

	@PostMapping(value = "/upload")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseMessageDTO> uploadFile(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok(crimRecService.save(file));
	}
}
