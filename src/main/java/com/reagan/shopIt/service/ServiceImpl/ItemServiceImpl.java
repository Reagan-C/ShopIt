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
        Item item = itemRepository.findByName(addItemDTO.getName());
        if (item != null) {
            throw new IllegalArgumentException(addItemDTO.getName()+ " already exists in our record");
        }
        Category itemCategory = categoryRepository.findByName(addItemDTO.getCategoryName());
        if (itemCategory == null) {
            throw new CategoryNotFoundException(addItemDTO.getCategoryName());
        }

        item.setCategory(itemCategory);
        item = mapper.map(addItemDTO, Item.class);
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
        item = mapper.map(updateItemDTO, Item.class);
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
        return ResponseEntity.ok(item);
    }

    @Override
    public List<?> findItemByCategory(ItemCategoryDTO itemCategoryDTO) {

        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return null;
    }

    @Override
    public Item findByName(String itemName) {
        return null;
    }
}
