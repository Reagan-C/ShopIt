package com.reagan.shopIt.service;

import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ItemService {

    @Transactional
    ResponseEntity<?> addNewItem(AddItemDTO addItemDTO);

    @Transactional
    ResponseEntity<?> removeItem(DeleteItemDTO itemDTO);

    @Transactional
    ResponseEntity<?> updateItem(UpdateItemDTO updateItemDTO);

    @Transactional
    ResponseEntity<String> setItemQuantity(SetItemQuantityDTO quantityDTO);

    ResponseEntity<String> setItemNewPrice(SetItemPriceDTO priceDTO);

    List<Item> getAllItems();

    List<Map<String, Object>> findByName(ItemNameDTO itemName);
}
