package com.rest.fileprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestfileprocessingApplicationTests {
	
	private static final String DOWNLOAD_PATH = "C:\\Users\\TEJO SAI\\Desktop\\uploads\\";
	private static final String FILE_DOWNLOAD_URL = "http://localhost:8080/download/";
	private static final String FILE_UPLOAD_URL = "http://localhost:8080/upload";
	@Autowired
	RestTemplate restTemplate;
	
	
	@Test
	void testUpload() {
		//Headers
		HttpHeaders  headers = new HttpHeaders();  // step 1
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		//Body
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>(); // step 2
		body.add("file", new ClassPathResource("img.jpg"));
		
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body,headers); // step 3
		
		ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, entity, Boolean.class);
		
		System.out.println(response.getBody());
		 
	}
	
	@Test
	void testDownload() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
		
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String fileName = "img.jpg";
		
		ResponseEntity<byte[]> response = restTemplate.exchange(FILE_DOWNLOAD_URL+fileName, HttpMethod.GET,entity,byte[].class);
		
		
		
		Files.write(Paths.get(DOWNLOAD_PATH + fileName), response.getBody());
	}

}
