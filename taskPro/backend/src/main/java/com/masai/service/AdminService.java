package com.masai.service;

import com.masai.model.LogInDTO;
import com.masai.model.User;

public interface AdminService {
	public User registerAdmin(User user) throws AdminException;
	
	public String logIn(LogInDTO dto) throws LoginException;
	
	public String logOut(String uId) throws LoginException;
	
	public String addSprintName(String uId,String name) throws LoginException;
	
	public String addTask(String uId,Task task) throws LoginException;
	
	public String updateTask(String uId,Task task) throws LoginException;
}
