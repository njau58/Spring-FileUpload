package org.spu.Uploads;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "UPLOADS")
public class Uploads {


	@Id
	@Column(name = "UPLOADS_METADATA_ID" , nullable=false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UPLOADS_METADATA_ID_SEQ")
	@SequenceGenerator(sequenceName = "UPLOADS_METADATA_ID_SEQ", allocationSize = 1, name = "UPLOADS_METADATA_ID_SEQ")
	private BigDecimal uploadsMetadataId;

	@Column(name = "UPLOADS_METADATA_FILE_NAME")
	private String  uploadsMetadataFileName;
	
	@Column(name = "UPLOADS_METADATA_LOCATION")
	private String  uploadsMetadataLocation;
	
@Lob
	@Column(name = "UPLOADS_IMAGE")
	private byte[] image;
	
	@Column(name = "UPLOADS_METADATA_REF_NO")
	private String   uploadsMetadataRefNo;
	
	@Column(name = "UPLOADS_METADATA_REF_TYPE")
	private String   uploadsMetadataRefType;

	public BigDecimal getUploadsMetadataId() {
		return uploadsMetadataId;
	}

	public void setUploadsMetadataId(BigDecimal uploadsMetadataId) {
		this.uploadsMetadataId = uploadsMetadataId;
	}

	
	public String getUploadsMetadataFileName() {
		return uploadsMetadataFileName;
	}

	public void setUploadsMetadataFileName(String uploadsMetadataFileName) {
		this.uploadsMetadataFileName = uploadsMetadataFileName;
	}


	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getUploadsMetadataRefNo() {
		return uploadsMetadataRefNo;
	}

	public void setUploadsMetadataRefNo(String uploadsMetadataRefNo) {
		this.uploadsMetadataRefNo = uploadsMetadataRefNo;
	}

	public String getUploadsMetadataRefType() {
		return uploadsMetadataRefType;
	}

	public void setUploadsMetadataRefType(String uploadsMetadataRefType) {
		this.uploadsMetadataRefType = uploadsMetadataRefType;
	}

	public String getUploadsMetadataLocation() {
		return uploadsMetadataLocation;
	}

	public void setUploadsMetadataLocation(String uploadsMetadataLocation) {
		this.uploadsMetadataLocation = uploadsMetadataLocation;
	}

	

	

}