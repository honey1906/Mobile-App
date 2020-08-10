package com.mobile.ws.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.ws.model.request.UserDetails;
import com.mobile.ws.model.response.UserRest;


@RestController
@RequestMapping("/users")
public class UserController // http://localhost:8080/users
{
	Map<String, UserRest> users;

	@GetMapping()
	public String getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "50") int limit) {
		return "Get users was called= " + page + " and limit= " + limit;
	}

	@GetMapping(path = "/{userId}", 
			produces = { 
					MediaType.APPLICATION_XML_VALUE, 
			        MediaType.APPLICATION_JSON_VALUE 
			  })
	public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
		UserRest returnValue = new UserRest();
		
		if(users.containsKey(userId)) {
		return new ResponseEntity<>(users.get(userId), HttpStatus.OK);
		}else
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@PostMapping(
			consumes = { 
					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE 
						},
			produces = { 
					MediaType.APPLICATION_XML_VALUE, 
				    MediaType.APPLICATION_JSON_VALUE 
				    })
	public ResponseEntity<UserRest> createUser(@RequestBody UserDetails userDetails) {
		UserRest returnValue = new UserRest();
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		returnValue.setEmail(userDetails.getEmail());
		
		String userId = UUID.randomUUID().toString();
		returnValue.setUserId(userId);
		
		if(users==null) users = new HashMap<>();
		users.put(userId, returnValue);
		
		
		return new ResponseEntity<UserRest>(returnValue, HttpStatus.OK);
		
	}

	@PutMapping(path="/{userId}",
			consumes = { 
					MediaType.APPLICATION_XML_VALUE, 
					MediaType.APPLICATION_JSON_VALUE 
						},
			produces = { 
					MediaType.APPLICATION_XML_VALUE, 
				    MediaType.APPLICATION_JSON_VALUE 
				    }
			)
	public UserRest updateUser(@PathVariable String userId, @RequestBody UserDetails userDetails) {
		UserRest storedValue = users.get(userId);
		storedValue.setFirstName(userDetails.getFirstName());
		storedValue.setLastName(userDetails.getLastName());
		storedValue.setEmail(userDetails.getEmail());
		
		users.put(userId, storedValue);
		return storedValue;
	}

	@DeleteMapping(path="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id) {
		
		users.remove(id);		
		return ResponseEntity.noContent().build();
	}

}
