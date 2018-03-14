package com.hooc.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hooc.test.dao.TestDao;

@Service
public class TestService {
	@Autowired 
	private TestDao testDao;
	
	public String getUserName(Integer userId){
		return testDao.getUserName(userId);
	}
}
