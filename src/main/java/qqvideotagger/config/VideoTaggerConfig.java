package qqvideotagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class VideoTaggerConfig {

    @Value("${app.propertiesBasePath}")
    @Getter
    private String propertiesBasePath;
    
    @Value("${app.tagsRelativePath}")
    @Getter
    private String tagsRelativePath;
    
}
