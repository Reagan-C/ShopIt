package com.reagan.shopIt.controllers.item;

import com.reagan.shopIt.model.domain.Item;
import com.reagan.shopIt.model.dto.itemdto.*;
import com.reagan.shopIt.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService   ) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody AddItemDTO addItemDTO) {
        return itemService.addNewItem(addItemDTO);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
//        ProductDTO product = itemService.findItemByCategory()
//        return ResponseEntity.ok(product);
//    }
//
    @PutMapping("/update")
    public ResponseEntity<Item> updateItem(@RequestBody UpdateItemDTO updateItemDTO) {
        return ResponseEntity.ok(itemService.updateItem(updateItemDTO));
    }

    @PutMapping("/set-quantity")
    public ResponseEntity<Item> increaseQuantity(@RequestBody SetItemQuantityDTO itemQuantityDTO) {
        return itemService.setItemQuantity(itemQuantityDTO);
    }

    @PutMapping("/set-price")
    public ResponseEntity<Item> setQuantity(@RequestBody SetItemPriceDTO itemPriceDTO) {
        return itemService.setItemNewPrice(itemPriceDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestBody DeleteItemDTO itemName) {
        return itemService.removeItem(itemName);
    }
}

