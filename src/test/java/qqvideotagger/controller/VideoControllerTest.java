package qqvideotagger.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import qqvideotagger.QqvideotaggerApplication;
import qqvideotagger.model.Video;
import qqvideotagger.model.operations.UpdateTagsOperation;
import qqvideotagger.service.FileStorageServiceUtil;

@ExtendWith({ SpringExtension.class })
@SpringBootTest(classes = { QqvideotaggerApplication.class })
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Slf4j
class VideoControllerTest {
	
	@Autowired
	private FileStorageServiceUtil fileStorageServiceUtil;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Test
	void test_updateTags() throws Exception {
		
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

		StringBuilder sbJson = new StringBuilder(256);
		sbJson.append("{");
		sbJson.append("  \"operation\": \"ADD\",");
		sbJson.append("  \"tags\": [");
		sbJson.append("    \"coche\",");
		sbJson.append("    \"mano\"");
		sbJson.append("  ]");
		sbJson.append("}");

		MvcResult mvcResult = mockMvc.perform(patch("/containers/" + container + "/videos/" + video + "/tags")
				.contentType("application/json")
				.content(sbJson.toString()))
				.andExpect(status().isOk())
				.andReturn();
		
		String response = mvcResult.getResponse().getContentAsString();
		Video videoObject = mapper.readValue(response, Video.class);

		assertNotNull(videoObject);
		assertFalse(videoObject.getTags().isEmpty());
		assertEquals(3, videoObject.getTags().size());
		assertTrue(videoObject.getTags().contains("avion"));
		assertTrue(videoObject.getTags().contains("coche"));
		assertTrue(videoObject.getTags().contains("mano"));
		
		//borramos el archivo
		propertiesContainerFile.delete();

		
	}

}
