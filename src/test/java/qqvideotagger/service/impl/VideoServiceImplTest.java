package qqvideotagger.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import qqvideotagger.exception.VideoException;
import qqvideotagger.model.Video;
import qqvideotagger.model.operations.UpdateTagsOperation;
import qqvideotagger.service.FileStorageServiceUtil;

@ExtendWith({ SpringExtension.class })
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class VideoServiceImplTest {
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private FileStorageServiceUtil fileStorageServiceUtil;
	
	@Autowired
	private VideoServiceImpl videoService;
	
	@Test
	void test_updateTags_add() throws IOException, VideoException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		List<String> tags = List.of("coche", "mano");
		
		String propertiesContainerPath = fileStorageServiceUtil.getPropertiesContainerPath(container);
		log.info("[FileStorageServiceTest - test_addTagsToVideo_01]propertiesContainerPath: {}", propertiesContainerPath);
		
		//borro el archivo si existia
		File propertiesContainerFile = new File(propertiesContainerPath);
		if (propertiesContainerFile.exists()) {
			propertiesContainerFile.delete();
		}
		assertFalse(propertiesContainerFile.exists());

		//lo creo de nuevo vacio
		propertiesContainerFile.createNewFile();
		assertTrue(propertiesContainerFile.exists());
		
		//le meto un video con etiquetas
		FileUtils.write(propertiesContainerFile, "#Thu Apr 28 14:07:37 CEST 2022\n", StandardCharsets.UTF_8, true);
		FileUtils.write(propertiesContainerFile, "lalala.mp4=lalala,lelele\n", StandardCharsets.UTF_8, true);
		//le meto el video con etiquetas
		FileUtils.write(propertiesContainerFile, video + "=avion\n", StandardCharsets.UTF_8, true);

		assertTrue(propertiesContainerFile.exists());
		
		//anadimos las etiquetas
		UpdateTagsOperation updateTagsOperation = new UpdateTagsOperation();
		updateTagsOperation.setOperation(UpdateTagsOperation.OPERATIONS.ADD);
		updateTagsOperation.setTags(tags);
		Video videoObject = videoService.updateTags(container, video, updateTagsOperation);
		assertNotNull(videoObject);
		assertFalse(videoObject.getTags().isEmpty());
		assertEquals(3, videoObject.getTags().size());
		assertTrue(videoObject.getTags().contains("avion"));
		assertTrue(videoObject.getTags().contains("coche"));
		assertTrue(videoObject.getTags().contains("mano"));
		
		//borramos el archivo
		propertiesContainerFile.delete();
	}

	@Test
	void test_updateTags_remove() throws IOException, VideoException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		String tag = "lampara";
		
		String propertiesContainerPath = fileStorageServiceUtil.getPropertiesContainerPath(container);
		log.info("[FileStorageServiceTest - test_addTagsToVideo_01]propertiesContainerPath: {}", propertiesContainerPath);
		
		//borro el archivo si existia
		File propertiesContainerFile = new File(propertiesContainerPath);
		if (propertiesContainerFile.exists()) {
			propertiesContainerFile.delete();
		}
		assertFalse(propertiesContainerFile.exists());

		//lo creo de nuevo vacio
		propertiesContainerFile.createNewFile();
		assertTrue(propertiesContainerFile.exists());
		
		//le meto un video con etiquetas
		FileUtils.write(propertiesContainerFile, "#Thu Apr 28 14:07:37 CEST 2022\n", StandardCharsets.UTF_8, true);
		FileUtils.write(propertiesContainerFile, "lalala.mp4=lalala,lelele\n", StandardCharsets.UTF_8, true);
		//le meto el video con etiquetas
		FileUtils.write(propertiesContainerFile, video + "=avion,lampara,vela\n", StandardCharsets.UTF_8, true);
		
		//eliminamos una etiqueta
		UpdateTagsOperation updateTagsOperation = new UpdateTagsOperation();
		updateTagsOperation.setOperation(UpdateTagsOperation.OPERATIONS.REMOVE);
		updateTagsOperation.setTags(List.of(tag));
		Video videoObject = videoService.updateTags(container, video, updateTagsOperation);
		assertNotNull(videoObject);
		assertFalse(videoObject.getTags().isEmpty());
		assertEquals(2, videoObject.getTags().size());
		assertTrue(videoObject.getTags().contains("avion"));
		assertTrue(videoObject.getTags().contains("vela"));
		
		//borramos el archivo
		propertiesContainerFile.delete();

	}

	@Test
	void test_getVideo() throws IOException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		List<String> tags = List.of("tag1", "tag2");
		
		String propertiesContainerPath = fileStorageServiceUtil.getPropertiesContainerPath(container);
		log.info("[VideoServiceImplTest - test_getVideo]propertiesContainerPath: {}", propertiesContainerPath);
		
		//borro el archivo si existia
		File propertiesContainerFile = new File(propertiesContainerPath);
		if (propertiesContainerFile.exists()) {
			propertiesContainerFile.delete();
		}
		assertFalse(propertiesContainerFile.exists());
		
		//anadimos las etiquetas
		fileStorageService.addTagsToVideo(
				container, //String container, 
				video, //String video, 
				tags //List<String> tags
				);
		assertTrue(propertiesContainerFile.exists());
		
		Video videoObject = videoService.getVideo(container, video);
		assertNotNull(videoObject);
		assertFalse(videoObject.getTags().isEmpty());
		assertEquals(2, videoObject.getTags().size());
		assertTrue(videoObject.getTags().contains("tag1"));
		assertTrue(videoObject.getTags().contains("tag2"));
		
		//borramos el archivo
		propertiesContainerFile.delete();

	}

}
