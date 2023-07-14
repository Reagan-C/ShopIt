package com.reagan.shopIt.controllers.item;

import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import com.reagan.shopIt.service.ItemService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "items", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService   ) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@Validated @RequestBody AddItemDTO addItemDTO) {
        return itemService.addNewItem(addItemDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateItem(@Validated @RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(itemService.updateItem(updateItemDTO));
    }

    @PutMapping("/set-quantity")
    public ResponseEntity<String> increaseQuantity(@Validated @RequestBody SetItemQuantityDTO itemQuantityDTO) {
        return itemService.setItemQuantity(itemQuantityDTO);
    }

    @PutMapping("/set-price")
    public ResponseEntity<Item> setQuantity(@Validated @RequestBody SetItemPriceDTO itemPriceDTO) {
        return itemService.setItemNewPrice(itemPriceDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@Validated @RequestBody DeleteItemDTO itemName) {
        return itemService.removeItem(itemName);
    }

    @GetMapping("/get-by-category")
    public List<?> getAllItemsByCategory(@Validated @RequestBody ItemCategoryDTO itemCategoryDTO) {
        return itemService.findItemByCategory(itemCategoryDTO);
    }

    @GetMapping("/get-all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/get-by-name")
    public Item getByName(@Validated @RequestBody String itemName) {
        return itemService.findByName(itemName);
    }
}

