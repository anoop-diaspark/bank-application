package com.diaspark.country.fileserviceetest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.diaspark.country.entity.FileStorageProperties;
import com.diaspark.country.fileservice.FileStorageService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FileStorageServiceTest {

	
	private FileStorageService fileStorageService;
	
	@Mock
	private FileStorageProperties fileStorageProperties;
	@Mock
	 private Path fileStorageLocation;
	@Mock
	private MultipartFile file;
	
	@Mock
	private StringUtils stringUtils;
	
	/*@Mock
	private Files files;*/
	
/*	@Mock
	private StandardCopyOption StandardCopyOption;*/
	
	
	@Before
	public void setUp() throws Exception {  
		MockitoAnnotations.initMocks(this);
		FileStorageService FileStorageService  = new FileStorageService(fileStorageProperties);
	}
	
	@Test
	public void fileStrorageServiceTest(){
		
 String fileName="ap.png";
		
		Mockito.when(fileStorageService.storeFile(file)).thenReturn(fileName);
	}
	
}
