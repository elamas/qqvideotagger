package qqvideotagger.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qqvideotagger.service.FileStorageServiceUtil;
import qqvideotagger.service.StorageService;
import qqvideotagger.util.CSVUtil;

@Service
public class FileStorageService implements StorageService {
	
	@Autowired
	private FileStorageServiceUtil fileStorageServiceUtil;

	@Override
	//TODO implementar
	public void addTagToVideo(String container, String video, String tag) {
		
	}

	@Override
	public void addTagsToVideo(String container, String video, List<String> tags) throws IOException {
		String propsPath = fileStorageServiceUtil.getPropertiesContainerPath(container);
		File fProps = new File(propsPath);
		Properties props = new Properties();
		
		//cargamos el properties del container si existe
		if (fProps.exists()) {
			try (InputStream is = new FileInputStream(fProps)) {
				props.load(is);
			}
		}
		
		//obtenemos la property del video
		String videoTags = props.getProperty(video);
		
		Set<String> tagSet = new TreeSet<>();
		
		//meto las anteriores si existian
		if (videoTags != null) {
			CSVUtil.loadCsvToSet(tagSet, videoTags);
		}
		
		//anadimos las nuevas
		tagSet.addAll(tags);
		props.setProperty(video, CSVUtil.setToCSV(tagSet));
		
		//guardamos el properties
		fProps.getParentFile().mkdirs();
		try (OutputStream os = new FileOutputStream(fProps)) {
			props.store(
					os, 
					null //comments
					);
		} 
	}
	
	//TODO implementar
	//TODO si un video se queda sin etiquetas borrar la property
	public void removeTagFromVideo(String container, String video, String tag) {
		
	}

}
