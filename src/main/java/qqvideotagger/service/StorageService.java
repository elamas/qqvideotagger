package qqvideotagger.service;

import java.io.IOException;
import java.util.List;

public interface StorageService {

	public void addTagToVideo(String container, String video, String tag) throws IOException;
	
	/*
	 * A lo que representaria el dvdX le llamo "container" porque a lo mejor en el futuro no guardo en dvds, vete a saber.
	 */
	public void addTagsToVideo(String container, String video, List<String> tags) throws IOException;
	
	public void removeTagFromVideo(String container, String video, String tag) throws IOException;

	public List<String> getTagsFromVideo(String container, String video) throws IOException;

}
