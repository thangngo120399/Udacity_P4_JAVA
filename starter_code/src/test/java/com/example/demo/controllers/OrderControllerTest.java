package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	@InjectMocks
	OrderController orderController;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	OrderRepository orderRepository;
	
	@Test
	void requestNotFoundUser() {
		Mockito.doReturn(null).when(userRepository).findByUsername("thangnx2");
		ResponseEntity<UserOrder> response = orderController.submit("thangnx2");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void reqeustSuccess() {
		User user = createUser();
		Cart cart = createCart();
		user.setCart(cart);
		
		Mockito.doReturn(user).when(userRepository).findByUsername("thangnx2");
		ResponseEntity<UserOrder> response = orderController.submit("thangnx2");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
		
	@Test
	void getOrdersForUserSuccess() {
		Mockito.doReturn(new User()).when(userRepository).findByUsername("thangnx2");
		Mockito.doReturn(new ArrayList<User>()).when(orderRepository).findByUser(any());
		ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("thangnx2");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void getOrdersForUserWhenNotFoundUser() {
		Mockito.doReturn(null).when(userRepository).findByUsername("thangnx2");
		ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("thangnx2");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	private User createUser() {
		User user = new User();
		user.setId(1L);
		user.setUsername("thangnx2");
		user.setPassword("thangnx2password");
		return user;
	}
	
	private Cart createCart() {
		Cart cart = new Cart();
		cart.setId(1L);
		cart.setTotal(BigDecimal.ONE);
		
		Item item = new Item();
		item.setId(1L);
		item.setName("New Item");
		item.setPrice(BigDecimal.ONE);
		item.setDescription("New Item description");
		
		List<Item> items = new ArrayList<>();
		items.add(item);
		
		cart.setItems(items);
		return cart;
	}
}
