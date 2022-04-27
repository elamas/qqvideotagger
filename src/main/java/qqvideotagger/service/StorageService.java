package qqvideotagger.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface StorageService {

	//TODO ya veremos los parametros
	public void addTagToVideo(String container, String video, String tag);
	
	//TODO ya veremos los parametros
	/*
	 * A lo que representaria el dvdX le llamo "container" porque a lo mejor en el futuro no guardo en dvds, vete a saber.
	 */
	public void addTagsToVideo(String container, String video, List<String> tags) throws IOException;
	
	//TODO ya veremos los parametros
	public void removeTagFromVideo(String container, String video, String tag);


}
