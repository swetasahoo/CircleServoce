package com.stackroute.activitystream.test;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.activitystream.CircleService.CircleServiceApplication;
import com.stackroute.activitystream.dao.CircleDao;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.restcontroller.CircleController;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CircleServiceApplication.class })
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes=CircleServiceApplication.class)
public class CircleControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;

	@Autowired
	    CircleDao circleDAO;
	@Autowired
	Circle circle;
	    
	  @Before
		public void setup() throws Exception {
		    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		}
	   
	    private static String asJsonString(final Object obj) {
		    try {
		        final ObjectMapper mapper = new ObjectMapper();
		        final String jsonContent = mapper.writeValueAsString(obj);
		        return jsonContent;
		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }
		}  
	    
	   
	    @Ignore
	    @Test
		public void testAllCircles_Success() throws Exception {
	    	
	    	this.mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9013/api/circle/allcircles"))
    		.andExpect(status().isOk());
		}
	    @Ignore
	    @Test
		public void testGetCirclesByUser_Success() throws Exception {
	    	
		
			circle.setCreatedBy("sweta@gmail.com");
			mockMvc.perform(post("http://localhost:9013/api/circle/getCirclesByUser").content(asJsonString(circle)).contentType(MediaType.APPLICATION_JSON)
					  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
			
			
			
		}
	    @Ignore
	    @Test
	  		public void testGetCirclesByUser_Error() throws Exception {
	  			
	  			circle.setCreatedBy("xyz@gmail.com");
	  			mockMvc.perform(post("http://localhost:9013/api/circle/getCirclesByUser").content(asJsonString(circle)).contentType(MediaType.APPLICATION_JSON)
	  					  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	  			
	  		}
	    @Ignore
	    @Test
		public void testCreateCircle_Success() throws Exception
		{
	    	//create new circle
			
			circle.setCircleName("circle1");
			circle.setCreatedBy("sofia@gmail.com");
			 

	        mockMvc.perform(
	                post("http://localhost:9013/api/circle/createCircle")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(asJsonString(circle)))
	                .andExpect(status().isOk());          

	       
		}
	    @Ignore  
	    @Test
		public void testCreateCircle_Error() throws Exception
		{
	    	//trying to create existing circle
			
			circle.setCircleName("doc");
			circle.setCreatedBy("rakesh@gmail.com");
			
		        mockMvc.perform(
		                post("http://localhost:9013/api/circle/createCircle")
		                        .contentType(MediaType.APPLICATION_JSON)
		                        .content(asJsonString(circle)))
		        				.andExpect(status().isConflict());
		      
		
			
		}
	    @Ignore
	    @Test
	  		public void testRemoveCircle_Success() throws Exception
	  		{
		       
	  			
	  			circle.setCircleName("newcircle");
	  			mockMvc.perform(post("http://localhost:9013/api/circle/removeCircle").content(asJsonString(circle)).contentType(MediaType.APPLICATION_JSON)
	  					  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	  			
	  		
	  		}
	    @Ignore
	    @Test
	  		public void testRemoveCircle_Error() throws Exception
	  		{
		 	
			circle.setCircleName("Unknown");
	        when(circleDAO.isCircleExist(circle.getCircleName())).thenReturn(false);
	        mockMvc.perform(post("http://localhost:9013/api/circle/removeCircle").content(asJsonString(circle)).contentType(MediaType.APPLICATION_JSON)
	  					  .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	        
  			
	       
	  		}
	    
	 
}
