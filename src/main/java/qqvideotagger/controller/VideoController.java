package qqvideotagger.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import qqvideotagger.exception.VideoException;
import qqvideotagger.model.Video;
import qqvideotagger.model.operations.UpdateTagsOperation;
import qqvideotagger.service.VideoService;


@RestController
public class VideoController {
	
	@Autowired
	private VideoService videoService;
	
	@PatchMapping(value = "/containers/{container}/videos/{video}/tags", produces = "application/json")
	public ResponseEntity<Video> updateTags(
			@PathVariable("container") String container,
			@PathVariable("video") String video,
			@RequestBody UpdateTagsOperation updateTagsOperation
			) throws IOException, VideoException {
		return new ResponseEntity<>(videoService.updateTags(container, video, updateTagsOperation), HttpStatus.OK);
	}

}
