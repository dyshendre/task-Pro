package com.masai.service;

import com.masai.exception.BookException;
import com.masai.exception.CartException;
import com.masai.exception.LoginException;
import com.masai.exception.UserException;
import com.masai.model.Book;
import com.masai.model.LogInDTO;
import com.masai.model.User;

public interface UserService {
	public User registerUser(User user) throws UserException;
	
	public String logIn(LogInDTO dto) throws LoginException;
	
	public String logOut(String uId) throws LoginException;
	
//	public Book addBookInCart(String uId,Integer bookId) throws LoginException,BookException;
//	
//	public String purchaseBooks(String uId) throws LoginException,CartException;
//	
//	public String maxmBookAdd(String uId) throws LoginException;
}
