package com.cognizant.course.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cognizant.course.services.ParticipantService;

@RestController
@RequestMapping("/participants")
@Api(value = "participants", description = "Participants and course details")
public class CourseController {

	@Autowired
	private ParticipantService participantService;

	@ApiOperation(value = "Bulk upload participant details")
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
	public ResponseEntity bulkUpload(@RequestParam("file") MultipartFile file) {

		boolean uploadStatus = false;

		try {
			uploadStatus = participantService.bulkUploadParticipant(saveUploadedFiles(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (uploadStatus)
			return new ResponseEntity("Participants uploaded successfully", HttpStatus.OK);
		else
			return new ResponseEntity("Participants uploaded successfully", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// save file
	private Path saveUploadedFiles(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		Path path = Paths.get(file.getOriginalFilename());
		Files.write(path, bytes);
		return path;
	}

}
