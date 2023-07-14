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
import java.util.Map;

@RestController
@RequestMapping(value = "items", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService   ) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('Administrator')")
    @PostMapping("/add")
    public ResponseEntity<?> addItem(@Validated @RequestBody AddItemDTO addItemDTO) {
        return itemService.addNewItem(addItemDTO);
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping("/update")
    public ResponseEntity<?> updateItem(@Validated @RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(itemService.updateItem(updateItemDTO));
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping("/set-quantity")
    public ResponseEntity<String> increaseQuantity(@Validated @RequestBody SetItemQuantityDTO itemQuantityDTO) {
        return itemService.setItemQuantity(itemQuantityDTO);
    }

    @PreAuthorize("hasRole('Administrator')")
    @PutMapping("/set-price")
    public ResponseEntity<String> setPrice(@Validated @RequestBody SetItemPriceDTO itemPriceDTO) {
        return itemService.setItemNewPrice(itemPriceDTO);
    }

    @PreAuthorize("hasRole('Administrator')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@Validated @RequestBody DeleteItemDTO itemName) {
        return itemService.removeItem(itemName);
    }

    @PreAuthorize("hasRole('Administrator')")
    @GetMapping("/get-all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/get-by-name")
    public List<Map<String, Object>> getByName(@Validated @RequestBody ItemNameDTO name) {
        return itemService.findByName(name);
    }
}

