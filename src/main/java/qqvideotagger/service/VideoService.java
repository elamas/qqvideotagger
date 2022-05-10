package qqvideotagger.service;

import java.io.IOException;

import qqvideotagger.exception.VideoException;
import qqvideotagger.model.Video;
import qqvideotagger.model.operations.UpdateTagsOperation;

public interface VideoService {
	
	public Video updateTags(String container, String video, UpdateTagsOperation updateTagsOperation) throws IOException, VideoException;
	public Video getVideo(String container, String video) throws IOException;
	
}
