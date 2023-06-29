package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Category;
import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import com.reagan.shopIt.model.exception.CategoryNotFoundException;
import com.reagan.shopIt.model.exception.ItemNotFoundException;
import com.reagan.shopIt.repository.CategoryRepository;
import com.reagan.shopIt.repository.ItemRepository;
import com.reagan.shopIt.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new IllegalArgumentException(addItemDTO.getName()+ " already exists in our record");
        }
        // check whether category name is valid
        Category itemCategory = categoryRepository.findByName(addItemDTO.getCategoryName());
        if (itemCategory == null) {
            throw new CategoryNotFoundException(addItemDTO.getCategoryName());
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
        return ResponseEntity.ok("Item deleted");
    }

    @Override
    public Item updateItem(UpdateItemDTO updateItemDTO) {
        Item item = itemRepository.findByName(updateItemDTO.getItemName());
        if (item == null) {
            throw  new ItemNotFoundException(updateItemDTO.getItemName());
        }
        item.setDescription(updateItemDTO.getDescription());
        item.setPrice(updateItemDTO.getPrice());
        item.setName(updateItemDTO.getItemNewName());
        itemRepository.save(item);
        return item;
    }

    @Override
    public ResponseEntity<Item> setItemQuantity(SetItemQuantityDTO quantityDTO) {
        Item item = itemRepository.findByName(quantityDTO.getItemName());
        if (item == null) {
            throw new ItemNotFoundException(quantityDTO.getItemName());
        }
        item.setQuantity(quantityDTO.getQuantity());
        itemRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @Override
    public ResponseEntity<Item> setItemNewPrice(SetItemPriceDTO priceDTO) {
        Item item = itemRepository.findByName(priceDTO.getItemName());
        if (item == null) {
            throw new ItemNotFoundException(priceDTO.getItemName());
        }
        item.setPrice(priceDTO.getPrice());
        itemRepository.save(item);
        return ResponseEntity.ok(item);
    }

    @Override
    public List<?> findItemByCategory(ItemCategoryDTO itemCategoryDTO) {
        Category category = categoryRepository.findByName(itemCategoryDTO.getCategoryName());
        if (category == null) {
            throw new CategoryNotFoundException(itemCategoryDTO.getCategoryName());
        }
        //if category exists, return all items in category
        return itemRepository.getItemsByCategoryName(category.getName());
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
    public Item findByName(String itemName) {
        Item item = itemRepository.findByName(itemName);
        if (item != null){
            return item;
        }
        throw new ItemNotFoundException(itemName);
    }
}
