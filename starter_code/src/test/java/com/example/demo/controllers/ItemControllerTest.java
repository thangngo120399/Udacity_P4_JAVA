package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
	@InjectMocks
	ItemController itemController;
	
	@Mock
	ItemRepository itemRepository;
	
	@Test
	void getAllItem() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findAll();
		ResponseEntity<List<Item>> responseEntity = itemController.getItems();
		assertEquals(0, responseEntity.getBody().size());
	}
	
	@Test
	void getItemByIdSuccess() {
		Item item  = new Item();
		Mockito.doReturn(Optional.of(item)).when(itemRepository).findById(1L);
		ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
		assertEquals(new Item(), responseEntity.getBody());
	}
	@Test
	void getItemsByNameWhenNull() {
		Mockito.doReturn(null).when(itemRepository).findByName("thangnx2");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("thangnx2");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void getItemsByNameWhenListEmpty() {
		Mockito.doReturn(new ArrayList<Item>()).when(itemRepository).findByName("thangnx2");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("thangnx2");
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
		
	@Test
	void getItemsByNameWhenContainsData() {
		List<Item> listItem = new ArrayList<>();
		Item item = new Item();
		item.setId(1L);
		item.setName("New Item");
		item.setPrice(BigDecimal.ONE);
		item.setDescription("New Item description");
		listItem.add(item);
		Mockito.doReturn(listItem).when(itemRepository).findByName("thangnx2");
		ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("thangnx2");
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
}
