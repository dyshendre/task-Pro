package com.masai.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.BookException;
import com.masai.exception.CartException;
import com.masai.exception.LoginException;
import com.masai.exception.UserException;
import com.masai.model.Book;
import com.masai.model.Cart;
import com.masai.model.LogInDTO;
import com.masai.model.User;
import com.masai.model.UserCurrentSession;
import com.masai.repository.BookDao;
import com.masai.repository.CartDao;
import com.masai.repository.UserDao;
import com.masai.repository.UserSessionDao;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao uDao;
	
	@Autowired
	private UserSessionDao sessionDao;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private CartDao cartDao;
	
	

	@Override
	public User registerUser(User user) throws UserException {
		User existingUser = uDao.findByMobileNumber(user.getMobileNumber());
		
		if(existingUser!=null) 
		{
			throw new UserException("User already registered with this mobile number");
		}
		
		User registeredUser = uDao.save(user);
		
		Cart cart = new Cart();
		cart.setUser(registeredUser);
		cart.setTotal(0);
		
		cartDao.save(cart);
		
		return registeredUser;
		
	}

	@Override
	public String logIn(LogInDTO dto) throws LoginException {
		User existingUser = uDao.findByMobileNumber(dto.getMobileNumber());
		
		if(existingUser == null) {
			throw new LoginException("Please register your self first!");
		}
		
		Optional<UserCurrentSession> validUser = sessionDao.findById(existingUser.getUserId());
		
		if(validUser.isPresent()) {
			throw new LoginException("User already logged in");
		}
		
		if(existingUser.getPassword().equals(dto.getPassword())) {
			
			String key = RandomString.make(6);
			
			UserCurrentSession userCurrentSession = new UserCurrentSession(existingUser.getUserId(),key,LocalDateTime.now());
			
			sessionDao.save(userCurrentSession);
			
			return userCurrentSession.toString();
		}
		else {
			throw new LoginException("Please enter a valid password!");
		}
	}

	@Override
	public String logOut(String uId) throws LoginException {
		UserCurrentSession validUser = (UserCurrentSession) sessionDao.findByUniqueId(uId);
		
		if(validUser == null) {
			throw new LoginException("User haven't logged in with this number");
		}
		
		sessionDao.delete(validUser);
		
		return "User logged out successfully!;";
	}

	@Override
	public Book addBookInCart(String uId, Integer bookId) throws LoginException, BookException {
		UserCurrentSession loggedInUser = sessionDao.findByUniqueId(uId);
		
		if(loggedInUser==null) 
		{
			throw new LoginException("Please provide a correct key.");
		}
		Integer userId=loggedInUser.getUserId();
		Optional<User> userOpt=uDao.findById(userId);
		User user=userOpt.get();
		
		Optional<Cart> cartOpt=cartDao.findById(user.getCart().getCartId());
		Cart cart=cartOpt.get();
		List<Book> booklist=cart.getBooks();
		
		Optional<Book> bookOpt=bookDao.findById(bookId);
		Book book=bookOpt.get();
		
		for(Book el: booklist)
		{
			if(el.getBookId()==bookId)
			{
				throw new BookException("Given book already present in cart");
			}
			else {
				cart.getBooks().add(book);
				cart.setTotal(cart.getTotal()+book.getPrice());
				cartDao.save(cart);
			}
		}
		return book;
	}

	@Override
	public String purchaseBooks(String uId) throws LoginException, CartException {
		UserCurrentSession loggedInUser = sessionDao.findByUniqueId(uId);
		
		if(loggedInUser==null) 
		{
			throw new LoginException("Please provide a correct key.");
		}
		Integer userId=loggedInUser.getUserId();
		Optional<User> userOpt=uDao.findById(userId);
		User user=userOpt.get();
		
		Optional<Cart> cartOpt=cartDao.findById(user.getCart().getCartId());
		Cart cart=cartOpt.get();
		if(user.getBudget()>=cart.getTotal())
		{
			user.setBudget(user.getBudget()-cart.getTotal());
			uDao.save(user);
			
			cart.setBooks(null);	
			cartDao.save(cart);
			return "Books purchased successfully..!!!";
		}
		else {
			throw new CartException("Cart books are out of budget...!!!");
		}
		
	}

	@Override
	public String maxmBookAdd(String uId) throws LoginException {
		UserCurrentSession loggedInUser = sessionDao.findByUniqueId(uId);
		
		if(loggedInUser==null) 
		{
			throw new LoginException("Please provide a correct key.");
		}
		Integer userId=loggedInUser.getUserId();
		Optional<User> userOpt=uDao.findById(userId);
		User user=userOpt.get();

		List<Book> bookList=bookDao.findAll();
		Collections.sort(bookList, Comparator.comparingDouble(Book::getPrice));
		
		Optional<Cart> cartOpt=cartDao.findById(user.getCart().getCartId());
		Cart cart=cartOpt.get();
		

		
		
		List<Book> cartBook=new ArrayList<>();
		for(Book el:bookList)
		{
			
			if(user.getBudget()-cart.getTotal()>=el.getPrice())
			{
			cartBook.add(el);
			cart.setTotal(cart.getTotal()+el.getPrice());
			}
			
		}
		
		return "added";
	}

	

}
