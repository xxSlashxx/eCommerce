package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemControllerTest extends AbstractControllerTest {

    private ItemController itemController;

    @Autowired
    public void setItemController(ItemController itemController) {
        this.itemController = itemController;
    }

    @Test
    public void testGetItems() {
        ResponseEntity<List<Item>> getItemsResponse = itemController.getItems();
        assertNotNull(getItemsResponse);
        assertEquals(200, getItemsResponse.getStatusCodeValue());
        List<Item> items = getItemsResponse.getBody();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals(Long.valueOf(1), items.get(0).getId());
        assertEquals(Long.valueOf(2), items.get(1).getId());
    }

    @Test
    public void testGetItemById() {
        ResponseEntity<Item> getItemByIdResponse = itemController.getItemById(1L);
        assertNotNull(getItemByIdResponse);
        assertEquals(200, getItemByIdResponse.getStatusCodeValue());
        Item item = getItemByIdResponse.getBody();
        assertNotNull(item);
        assertEquals(Long.valueOf(1), item.getId());
    }

    @Test
    public void testGetItemsByName() {
        ResponseEntity<List<Item>> getItemsByNameResponse = itemController.getItemsByName("Round Widget");
        assertNotNull(getItemsByNameResponse);
        assertEquals(200, getItemsByNameResponse.getStatusCodeValue());
        List<Item> items = getItemsByNameResponse.getBody();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(Long.valueOf(1), items.get(0).getId());
    }
}