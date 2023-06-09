package com.example.demo.services;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> loadById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> loadAll() {
        return itemRepository.findAll();
    }

    public List<Item> loadByName(String name) {
        return itemRepository.findByName(name);
    }
}