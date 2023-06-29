package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {

    @Transactional
    ResponseEntity<?> addNewItem(AddItemDTO addItemDTO);

    @Transactional
    ResponseEntity<?> removeItem(DeleteItemDTO itemDTO);

    @Transactional
    Item updateItem(UpdateItemDTO updateItemDTO);

    @Transactional
    ResponseEntity<Item> setItemQuantity(SetItemQuantityDTO quantityDTO);

    ResponseEntity<Item> setItemNewPrice(SetItemPriceDTO priceDTO);

    List<?> findItemByCategory(ItemCategoryDTO itemCategoryDTO);

    List<Item> getAllItems();

    Item findByName(String itemName);
}
