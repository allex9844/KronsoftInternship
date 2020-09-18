package internship.kronsoft.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CriminalRecord {
	
	@Id
	@Column(updatable = false, nullable = false, unique=true)
	private String crimeId;
	
	@Column(name = "MONTH")
	private String month;
	
	@Column(name = "REPORTED_BY")
	private String reportedBy;
	
	@Column(name = "FALLS_WITHIN")
	private String fallsWithin;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;
	
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "LSOA_CODE")
	private String lsoaCode;
	
	@Column(name = "LSOA_NAME")
	private String lsoaName;
	
	@Column(name = "CRIME_TYPE")
	private String crimeType;
	
	@Column(name = "OUTCOME_CATEGORY")
	private String outcomeCategory;
	
	@Column(name = "CONTEXT")
	private String context;

	public String getCrimeId() {
		return crimeId;
	}

	public void setCrimeId(String crimeId) {
		this.crimeId = crimeId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getFallsWithin() {
		return fallsWithin;
	}

	public void setFallsWithin(String fallsWithin) {
		this.fallsWithin = fallsWithin;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLsoaCode() {
		return lsoaCode;
	}

	public void setLsoaCode(String lsoaCode) {
		this.lsoaCode = lsoaCode;
	}

	public String getLsoaName() {
		return lsoaName;
	}

	public void setLsoaName(String lsoaName) {
		this.lsoaName = lsoaName;
	}

	public String getCrimeType() {
		return crimeType;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public String getOutcomeCategory() {
		return outcomeCategory;
	}

	public void setOutcomeCategory(String outcomeCategory) {
		this.outcomeCategory = outcomeCategory;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public CriminalRecord() { }

	public CriminalRecord(
			String crimeId,
			String month,
			String reportedBy,
			String fallsWithin,
			String longitude,
			String latitude,
			String location,
			String lsoaCode,
			String lsoaName,
			String crimeType,
			String outcomeCategory,
			String context
			) {
		super();
		this.crimeId = crimeId;
		this.month = month;
		this.reportedBy = reportedBy;
		this.fallsWithin = fallsWithin;
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = location;
		this.lsoaCode = lsoaCode;
		this.lsoaName = lsoaName;
		this.crimeType = crimeType;
		this.outcomeCategory = outcomeCategory;
		this.context = context;
	}

	@Override
	public String toString() {
		return "CriminalRecord [crimeId=" + crimeId + ", month=" + month + ", reportedBy=" + reportedBy
				+ ", fallsWithin=" + fallsWithin + ", longitude=" + longitude + ", latitude=" + latitude + ", location="
				+ location + ", lsoaCode=" + lsoaCode + ", lsoaName=" + lsoaName + ", crimeType=" + crimeType
				+ ", outcomeCategory=" + outcomeCategory + ", context=" + context + "]";
	}
	
	

}