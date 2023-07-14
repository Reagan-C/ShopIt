package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import com.reagan.shopIt.model.exception.CategoryNameNotFoundException;
import com.reagan.shopIt.model.exception.InsufficientItemQuantityException;
import com.reagan.shopIt.model.exception.ItemNotFoundException;
import com.reagan.shopIt.repository.CategoryRepository;
import com.reagan.shopIt.repository.ItemRepository;
import com.reagan.shopIt.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private ModelMapper mapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> addNewItem(AddItemDTO addItemDTO) {
        boolean itemExists = itemRepository.existsByName(addItemDTO.getName());
        if (itemExists) {
            throw new IllegalArgumentException(addItemDTO.getName()+ " already exists in the record");
        }

        // check whether category name is valid
        Category itemCategory = categoryRepository.findByName(addItemDTO.getCategoryName());
        if (itemCategory == null) {
            throw new CategoryNameNotFoundException(addItemDTO.getCategoryName());
        }

        Item item = new Item();
        item.setCategory(itemCategory);
        item.setName(addItemDTO.getName());
        item.setPrice(addItemDTO.getPrice());
        item.setQuantity(addItemDTO.getQuantity());
        item.setDescription(addItemDTO.getDescription());
        item.setPicture(addItemDTO.getPicture());

        itemRepository.save(item);
        return ResponseEntity.ok("Item added");
    }

    @Override
    public ResponseEntity<?> removeItem(DeleteItemDTO itemDTO) {
        Item item = itemRepository.findByName(itemDTO.getItemName());
        if (item == null) {
            throw new ItemNotFoundException(itemDTO.getItemName());
        }

        itemRepository.delete(item);
        return ResponseEntity.ok(  itemDTO.getItemName() + " entry deleted");
    }

    @Override
    public ResponseEntity<?> updateItem(UpdateItemDTO updateItemDTO) {
        Item item = itemRepository.findByName(updateItemDTO.getItemOldName());
        if (item == null) {
            throw  new ItemNotFoundException(updateItemDTO.getItemOldName());
        }

        item.setDescription(updateItemDTO.getDescription());
        item.setPrice(updateItemDTO.getPrice());
        item.setName(updateItemDTO.getItemNewName());
        itemRepository.save(item);
        return ResponseEntity.ok("Item updated");
    }

    @Override
    public ResponseEntity<String> setItemQuantity(SetItemQuantityDTO quantityDTO) {
        Item item = itemRepository.findByName(quantityDTO.getItemName());
        if (item == null) {
            throw new ItemNotFoundException(quantityDTO.getItemName());
        }

        item.setNewQuantity(quantityDTO.getQuantity());
        itemRepository.save(item);
        return ResponseEntity.ok("Quantity of " + quantityDTO.getItemName() + " updated");
    }

    @Override
    public ResponseEntity<String> setItemNewPrice(SetItemPriceDTO priceDTO) {
        Item item = itemRepository.findByName(priceDTO.getItemName());
        if (item == null) {
            throw new ItemNotFoundException(priceDTO.getItemName());
        }

        item.setPrice(priceDTO.getPrice());
        itemRepository.save(item);
        return ResponseEntity.ok("Price of item updated");
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> items = itemRepository.getAllItems();
        if (items.isEmpty()) {
            throw new IllegalArgumentException("No items in the records yet");
        }
        return items;
    }

    @Override
    public List<Map<String, Object>> findByName(ItemNameDTO itemName) {
        List<Map<String, Object>> itemList = new ArrayList<>();
        Map<String, Object> itemMap = new LinkedHashMap<>();

        Item item = itemRepository.findByName(itemName.getItemName());
        if (item == null){
            throw new ItemNotFoundException(itemName.getItemName());
        }

        itemMap.put("name", item.getName());
        itemMap.put("description", item.getDescription());
        itemMap.put("price", item.getPrice());
        itemMap.put("picture", item.getPicture());
        itemMap.put("category", item.getCategory().getName());

        itemList.add(itemMap);
        return itemList;
    }
}
