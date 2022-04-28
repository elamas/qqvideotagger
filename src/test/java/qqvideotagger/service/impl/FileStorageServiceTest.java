package qqvideotagger.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import qqvideotagger.service.FileStorageServiceUtil;

@ExtendWith({ SpringExtension.class })
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class FileStorageServiceTest {
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private FileStorageServiceUtil fileStorageServiceUtil;

	//el archivo no existe
	@Test
	void test_addTagsToVideo_01() throws IOException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		List<String> tags = List.of("tag1", "tag2");
		
		String propertiesContainerPath = fileStorageServiceUtil.getPropertiesContainerPath(container);
		log.info("[FileStorageServiceTest - test_addTagsToVideo_01]propertiesContainerPath: {}", propertiesContainerPath);
		
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
		
		//como no tengo metodo todavia de lectura compruebo el contenido a mano
		assertTrue(propertiesContainerFile.exists());
		String content = FileUtils.readFileToString(propertiesContainerFile, StandardCharsets.UTF_8);
		//log.info("[FileStorageServiceTest - test_addTagsToVideo_01]content: {}", content);
		assertTrue(content.contains("myvideo.mp4=tag1,tag2"));
		
		//borramos el archivo
		propertiesContainerFile.delete();
	}
	
	//existe el archivo pero no tiene ninguna foto (raro creo)
	@Test
	void test_addTagsToVideo_02() throws IOException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		List<String> tags = List.of("tag1", "tag2");
		
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
		
		//anadimos las etiquetas
		fileStorageService.addTagsToVideo(
				container, //String container, 
				video, //String video, 
				tags //List<String> tags
				);
		
		//como no tengo metodo todavia de lectura compruebo el contenido a mano
		assertTrue(propertiesContainerFile.exists());
		String content = FileUtils.readFileToString(propertiesContainerFile, StandardCharsets.UTF_8);
		log.info("[FileStorageServiceTest - test_addTagsToVideo_01]content: {}", content);
		assertTrue(content.contains("myvideo.mp4=tag1,tag2"));
		
		//borramos el archivo
		propertiesContainerFile.delete();
	}
	
	//no existe la foto en el archivo
	@Test
	void test_addTagsToVideo_03() throws IOException {
		String container = "mycontainer";
		String video = "myvideo.mp4";
		List<String> tags = List.of("tag1", "tag2");
		
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
		
		//anadimos las etiquetas
		fileStorageService.addTagsToVideo(
				container, //String container, 
				video, //String video, 
				tags //List<String> tags
				);
		
		//como no tengo metodo todavia de lectura compruebo el contenido a mano
		assertTrue(propertiesContainerFile.exists());
		String content = FileUtils.readFileToString(propertiesContainerFile, StandardCharsets.UTF_8);
		//log.info("[FileStorageServiceTest - test_addTagsToVideo_01]content: {}", content);
		//assertTrue(content.contains("#Thu Apr 28 14:07:37 CEST 2022"));//lo comento porque se ve que cambia al actualizar
		assertTrue(content.contains("lalala.mp4=lalala,lelele"));
		assertTrue(content.contains("myvideo.mp4=tag1,tag2"));
		
		//borramos el archivo
		propertiesContainerFile.delete();
	}
	
	//existe la foto en el archivo y tiene etiquetas
	@Test
	void test_addTagsToVideo_04() throws IOException {
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
		FileUtils.write(propertiesContainerFile, video + "=avion,lampara,vela\n", StandardCharsets.UTF_8, true);
		
		//anadimos las etiquetas
		fileStorageService.addTagsToVideo(
				container, //String container, 
				video, //String video, 
				tags //List<String> tags
				);
		
		//como no tengo metodo todavia de lectura compruebo el contenido a mano
		assertTrue(propertiesContainerFile.exists());
		String content = FileUtils.readFileToString(propertiesContainerFile, StandardCharsets.UTF_8);
		log.info("[FileStorageServiceTest - test_addTagsToVideo_04]content: {}", content);
		//assertTrue(content.contains("#Thu Apr 28 14:07:37 CEST 2022"));//lo comento porque se ve que cambia al actualizar
		assertTrue(content.contains("lalala.mp4=lalala,lelele"));
		assertTrue(content.contains("myvideo.mp4=avion,coche,lampara,mano,vela"));//en orden alfabetico
		
		//borramos el archivo
		propertiesContainerFile.delete();
	}

}
