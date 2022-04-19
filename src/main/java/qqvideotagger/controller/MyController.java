package qqvideotagger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import qqvideotagger.service.MyService;

@RestController
public class MyController {
	
	@Autowired
	private MyService myService;
	
	@GetMapping(value = "/get-string/", produces = "application/json")
	public ResponseEntity<String> getString() {
		return new ResponseEntity<>(myService.getString(), HttpStatus.OK);
	}
	

}
