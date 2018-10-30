package org.spu.Uploads;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;



import org.spu.Repository.*;




@RestController
@RequestMapping(value = "/upload-api/files")

public class UploadController {

	@Value("${DEFAULT_FILE_UPLOADS_LOCATION}")
	String pathOfFiles;

 RandomNumberGenerator randomNumberGenerator;

	private final RepositoryLog  repositoryLog ;

	String name = null;
	long timeStamp;
	String newName = null;
	String location = null;

	@Autowired
	public UploadController(RandomNumberGenerator randomNumberGenerator,RepositoryLog  repositoryLog ) {
		this.randomNumberGenerator = randomNumberGenerator;
		this.repositoryLog = repositoryLog;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String refNo,
			@RequestParam String refType) {
	     String returnMessage;

			name = file.getOriginalFilename();
			timeStamp = randomNumberGenerator.generate();
			newName = timeStamp + "_" + name;

			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();

					String rootPath = this.pathOfFiles;

					File dir = new File(rootPath + File.separator);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + newName);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					System.out.println("Server File Location=" + serverFile.getAbsolutePath());
					location = serverFile.getAbsolutePath();

					returnMessage = "You successfully uploaded file>> " + name + "  as >> " + newName;

					Uploads uploads = new Uploads();
					uploads.setUploadsMetadataFileName(newName);
					uploads.setUploadsMetadataLocation(location);
					uploads.setUploadsMetadataRefNo(refNo);
					uploads.setUploadsMetadataRefType(refType);
					uploads.setImage(file.getBytes());
				repositoryLog.save(uploads);
		
			} catch (Exception e) {
			    return ResponseEntity.badRequest().body("You failed to upload " + name + " => " + e.getMessage());
			}
		} else {
			returnMessage = "You failed to upload " + name + " because the file was empty.";
		}

		return ResponseEntity.ok((returnMessage));
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public HttpEntity<byte[]> download(@RequestParam BigDecimal uploadsMetadataId) {
		String filename = null;
		HttpHeaders header = new HttpHeaders();
		List<Uploads> uploadsList = repositoryLog.findByUploadsMetadataId(uploadsMetadataId);
		System.out.print("Uploads List Location>>>>" + uploadsList.get(0).getUploadsMetadataLocation());

		Path path = Paths.get(uploadsList.get(0).getUploadsMetadataLocation());
		byte[] bytes = null;
		try {
			bytes = Files.readAllBytes(path);

			filename = uploadsList.get(0).getUploadsMetadataFileName();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bytes == null) {
			return null;
		}

		header.setContentType(new MediaType("application", "pdf"));
		header.setContentType(new MediaType("application", "jpg"));
		header.setContentType(new MediaType("application", "doc"));
		header.setContentType(new MediaType("application", "csv"));
		header.setContentType(new MediaType("application", "pptx"));
		header.set("Content-Disposition", "inline; filename=" + filename);
		header.setContentLength(bytes.length);

		return new HttpEntity<byte[]>(bytes, header);

	

	}
	
	@RequestMapping(method=RequestMethod.GET ,value = "/fetchFile")
	public List<Uploads> fetchFile(@RequestParam String refno) {

		return repositoryLog.findByUploadsMetadataRefNo(refno);

	}

	
}