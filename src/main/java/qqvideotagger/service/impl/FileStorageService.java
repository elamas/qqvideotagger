package qqvideotagger.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
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
	
	public enum Operation {
	    ADD,
	    REMOVE; 
	}
	
	@Autowired
	private FileStorageServiceUtil fileStorageServiceUtil;

	@Override
	public void addTagToVideo(String container, String video, String tag) throws IOException {
		addTagsToVideo(
				container, //String container, 
				video, //String video, 
				List.of(tag) //List<String> tags
				);
	}
	
	@Override
	//para no repetir codigo hago un unico metodo para anadir o eliminar
	public void addTagsToVideo(String container, String video, List<String> tags) throws IOException {
		addOrRemoveTagsToVideo(
				container, //String container, 
				video, //String video, 
				tags, //List<String> tags, 
				Operation.ADD //Operation operation
				);
	}


	//para no repetir codigo hago un unico metodo para anadir o eliminar
	private void addOrRemoveTagsToVideo(String container, String video, List<String> tags, Operation operation) throws IOException {
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
		
		if (operation.equals(Operation.ADD)) {
			//anadimos las nuevas
			tagSet.addAll(tags);
		} else if (operation.equals(Operation.REMOVE)) {
			tagSet.removeAll(tags);
		}
		 
		//si un video se queda sin etiquetas borro la property
		if (tagSet.isEmpty() && props.getProperty(video) != null) {
			props.remove(video);
		} else {
			props.setProperty(video, CSVUtil.setToCSV(tagSet));
		}
		
		//guardamos el properties
		fProps.getParentFile().mkdirs();
		try (OutputStream os = new FileOutputStream(fProps)) {
			props.store(
					os, 
					null //comments
					);
		} 
	}
	
	public void removeTagFromVideo(String container, String video, String tag) throws IOException {
		addOrRemoveTagsToVideo(
				container, //String container, 
				video, //String video, 
				List.of(tag), //List<String> tags, 
				Operation.REMOVE //Operation operation
				);
	}

	@Override
	public List<String> getTagsFromVideo(String container, String video) throws IOException {
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
		if (videoTags != null) {
			return CSVUtil.csvToList(videoTags);
		} else {
			return Collections.emptyList();//segun el sonarlint es mejor devolver una lista vacia que u null
		}
		
	}

}
