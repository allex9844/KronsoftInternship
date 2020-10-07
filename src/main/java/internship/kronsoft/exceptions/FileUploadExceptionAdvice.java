package internship.kronsoft.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class FileUploadExceptionAdvice {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File too large!");
	}
	
	@ExceptionHandler(FileExtensionNotCSVException.class)
    public ResponseEntity<Object> handleFileExtensionNotCSVException(
    		FileExtensionNotCSVException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "The file is not a CSV!");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NoFileSelectedException.class)
    public ResponseEntity<Object> handleNoFileSelectedException(
    		NoFileSelectedException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "You haven't selected any files!");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
