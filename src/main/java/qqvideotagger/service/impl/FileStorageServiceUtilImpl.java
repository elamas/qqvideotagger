package qqvideotagger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import qqvideotagger.config.VideoTaggerConfig;
import qqvideotagger.service.FileStorageServiceUtil;

@Service
public class FileStorageServiceUtilImpl implements FileStorageServiceUtil {

	@Autowired
	private VideoTaggerConfig videoTaggerConfig;
	
	@Override
	//TODO a lo mejor en el futuro no leo del application.yml sino que lo configurara el usuario de alguna manera
	public String getPropertiesContainerPath(String container) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(videoTaggerConfig.getPropertiesBasePath())
		.append("/")
		.append(videoTaggerConfig.getTagsRelativePath())
		.append("/")
		.append(container)
		.append(".txt")
		;
		return sb.toString();
	}


}
