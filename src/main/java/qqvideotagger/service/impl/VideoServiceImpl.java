package qqvideotagger.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qqvideotagger.exception.VideoException;
import qqvideotagger.model.Video;
import qqvideotagger.model.operations.UpdateTagsOperation;
import qqvideotagger.service.StorageService;
import qqvideotagger.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {
	
	@Autowired
	private StorageService storageService;

	@Override
	public Video updateTags(String container, String video, UpdateTagsOperation updateTagsOperation) throws IOException, VideoException {
		if (updateTagsOperation.getOperation().equals(UpdateTagsOperation.OPERATIONS.ADD)) {
			storageService.addTagsToVideo(container, video, updateTagsOperation.getTags());
		} else if (updateTagsOperation.getOperation().equals(UpdateTagsOperation.OPERATIONS.REMOVE)) {
			//solo permito borrar una tag
			if (updateTagsOperation.getTags().size() != 1) {
				throw new VideoException("Invalid tags: " + updateTagsOperation.getTags());
			}
			storageService.removeTagFromVideo(container, video, updateTagsOperation.getTags().get(0));
		} else {
			throw new VideoException("Invalid operation: " + updateTagsOperation.getOperation());
		}
		return getVideo(container, video);
	}

	@Override
	public Video getVideo(String container, String video) throws IOException {
		Video videoObject = new Video();
		videoObject.setTags(storageService.getTagsFromVideo(container, video));
		return videoObject;
	}

}
