//package com.googledrive.api.test;
//
//import java.net.URI;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.Assert;
//import org.springframework.web.client.RestTemplate;
//
//import com.googledrive.api.controller.DriveFilesController;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(DriveFilesController.class)
//public class DriveFilesControllerTest {
//	
//	@Autowired
//		private MockMvc mvc;
//
//	@MockBean
//	   private DriveFilesController filesController;
//	
////	@BeforeEach
////	void setup() {
////		fileController = new DriveFilesController();
////	}
////	
////	@AfterEach
////	void tearDown() {
////		fileController = null;
////	}
//	
//	@Test
//	public void testFilesList() {
//		
//	    final String baseUrl = "http://localhost:8080/Files";
//	    
//	    URI uri = new URI(baseUrl);
//	 
//	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//	     
//	    //Verify request succeed
//	    Assert.assertEquals(200, result.getStatusCodeValue());
//	    Assert.assertEquals(true, result.getBody().contains("files"));
//	}
//
//}
