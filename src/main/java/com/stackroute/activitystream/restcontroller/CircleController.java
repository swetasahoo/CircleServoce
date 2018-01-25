package com.stackroute.activitystream.restcontroller;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.stackroute.activitystream.dao.CircleDao;
import com.stackroute.activitystream.model.Circle;
import com.stackroute.activitystream.model.UserCircle;



//http://localhost:9013/api/circle/

@RestController
@RequestMapping("/api/circle")
@EnableWebMvc
@CrossOrigin(origins="http://localhost:4200")
public class CircleController {
	
	@Autowired
	 private CircleDao circleDAO;

	@Autowired
	 private Circle circle;
	
	
	//http://localhost:9013/ActivityStreamRest/api/circle/allCircles
	@GetMapping("/allcircles")
	public ResponseEntity<List<Circle>> getAllCircles() {
		
	
		List<Circle> allCircles=circleDAO.getAllCircles();
		for(Circle circle:allCircles)
		{			
			Link link=linkTo(CircleController.class).slash(circle.getCircleName()).withSelfRel();
			circle.add(link);
		}
		if( allCircles==null || allCircles.isEmpty())
		{
			 return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

        return new ResponseEntity<List<Circle>>(allCircles, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getCirclesByUser",method=RequestMethod.POST)
	public ResponseEntity getCirclesByUser(@RequestBody Circle circle) {
	
		List<Circle> circleList=circleDAO.getCircleCreatedByMe(circle.getCreatedBy());
		
		if (circleList.isEmpty() || circleList==null) {
			return new ResponseEntity<>("No Circle found for -- "+circle.getCreatedBy(), HttpStatus.NOT_FOUND);
		}
	
		for(Circle usercircle:circleList)
		{			
			Link link=linkTo(CircleController.class).slash(usercircle.getCircleName()).withSelfRel();
			usercircle.add(link);
		}
		return new ResponseEntity<List<Circle>>(circleList, HttpStatus.OK);
}
	
/*
	@SuppressWarnings("unchecked")
	@GetMapping(value="/getCirclesByUser/{emailId}")
	public ResponseEntity getCirclesByUser(@PathVariable("emailId") String emailId) 
	{
	
		List<Circle> circleList=circleDAO.getCircleByUser(emailId);
		
		if (circleList.isEmpty() || circleList==null) {
			return new ResponseEntity<>("No Circle found for -- "+circle.getCreatedBy(), HttpStatus.NOT_FOUND);
		}
		
		for(Circle usercircle:circleList)
		{			
			Link link=linkTo(CircleController.class).slash(usercircle.getCircleName()).withSelfRel();
			usercircle.add(link);
		}
		return new ResponseEntity<List<Circle>>(circleList, HttpStatus.OK);
	}

	*/
	@RequestMapping(value="/createCircle",method=RequestMethod.POST)
	public ResponseEntity createCircle(@RequestBody Circle circle) {

		
		if(circleDAO.isCircleExist(circle.getCircleName()))
		{
			
			return new ResponseEntity("Circle already exist", HttpStatus.CONFLICT);
			
		}
		
	
			circleDAO.addCircle(circle);
			return new ResponseEntity<Circle>(circle, HttpStatus.OK);
			
		
		  
	}
	
	@RequestMapping(value="/removeCircle",method=RequestMethod.POST)
	public ResponseEntity removeCircle(@RequestBody Circle circle) {

		if(circleDAO.isCircleExist(circle.getCircleName()))
		{
			
		circleDAO.removeCircle(circle);
		return new ResponseEntity("Circle is inactive", HttpStatus.OK);
		}
		
			return new ResponseEntity("Circle doesnot exist", HttpStatus.NOT_FOUND);
				
		
		
	}
	
	

}
