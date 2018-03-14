package com.hooc.test.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface TestDao {
	public String getUserName(Integer userId);
}
