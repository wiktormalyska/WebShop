package com.wiktormalyska.backend.controllers;

import com.wiktormalyska.backend.dto.CreateItemDto;
import com.wiktormalyska.backend.dto.ItemDto;
import com.wiktormalyska.backend.model.Item;
import com.wiktormalyska.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    @Autowired

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    private final ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<Collection<ItemDto>> getItems() {
        Collection<ItemDto> items = itemService.getItems();
        return ResponseEntity.ok(items);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addItem(@RequestBody CreateItemDto item) {
        Item newItem = new Item(
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getQuantity()
        );
        switch (itemService.addItem(newItem)) {
            case "item already exists" -> {
                return ResponseEntity.badRequest().body("Item already exists");
            }
            case "item added successfully" -> {
                return ResponseEntity.ok("Item added successfully");
            }
        }
        return ResponseEntity.status(500).body("An unexpected error occurred");
    }

    @PostMapping("/get/{item}")
    public ResponseEntity<ItemDto> getItem(@PathVariable int item) {
        ItemDto itemdto = itemService.getItem(item);
        if (itemdto != null) {
            return ResponseEntity.ok(itemdto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @PostMapping("/remove/{item}")
    public ResponseEntity<String> removeItem(@PathVariable int item) {
        ItemDto itemToRemove = itemService.getItem(item);
        if (itemToRemove== null) return ResponseEntity.badRequest().body("item does not exist");
        itemService.removeItem(itemToRemove.getItem());
        return ResponseEntity.ok("successfully removed item");
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String addItem(Model model) {
        model.addAttribute("item", new Item());
        return "item-form";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addItem(@ModelAttribute Item item) {
        itemService.saveOrUpdate(item);
        return "redirect:/items";
    }

    @RequestMapping(path = {"/main", "/", "/index"}, method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("items", itemService.getItems());
        return "index";
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
    public String updateItem(@PathVariable int id, Model model) {
        Item item = itemService.getItem(id).getItem();
        if (item == null) {
            return "redirect:/main";
        }
        model.addAttribute("item", item);
        return "item-form";
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST)
    public String updateItem(@PathVariable int id, @ModelAttribute Item item) {
        itemService.saveOrUpdate(item);
        return "redirect:/main";
    }

    @RequestMapping(path = "/delete")
    public String deleteItem(@RequestParam int id) {
        itemService.delete(id);
        return "redirect:/main";
    }

}
