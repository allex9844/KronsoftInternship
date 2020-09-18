package internship.kronsoft.message;

public class ResponseMessage {

	private String message;
	private int nullId;
	private int invalidLSOA;
	private int successfullEntries;
	
	public ResponseMessage(String message, int nullId, int invalidLSOA, int successfullEntries) {
		this.message = message;
		this.nullId = nullId;
		this.invalidLSOA = invalidLSOA;
		this.successfullEntries = successfullEntries;
	}
	
	public ResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getNullId() {
		return nullId;
	}
	
	public void setNullId(int nullId) {
		this.nullId = nullId;
	}
	
	public int getInvalidLSOA() {
		return invalidLSOA;
	}
	
	public void setInvalidLSOA(int invalidLSOA) {
		this.invalidLSOA = invalidLSOA;
	}
	
	public int getSuccessfullEntries() {
		return successfullEntries;
	}
	
	public void setSuccessfullEntries(int successfullEntries) {
		this.successfullEntries = successfullEntries;
	}

	

}
