package qqvideotagger.service.impl;

import org.springframework.stereotype.Service;

import qqvideotagger.service.MyService;

@Service
public class MyServiceImpl implements MyService {

	@Override
	public String getString() {
		return "hola";
	}

}
