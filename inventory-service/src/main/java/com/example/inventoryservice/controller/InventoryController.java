package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    @PostMapping
    public Inventory add(@RequestBody Inventory inventory) {
        return repository.save(inventory);
    }

    @GetMapping
    public List<Inventory> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{flightNumber}/{flightDate}")
    public Optional<Inventory> getInventory(
            @PathVariable String flightNumber,
            @PathVariable String flightDate
    ) {
        return repository.findByFlightNumberAndFlightDate(flightNumber, flightDate);
    }

    // 新增：用于减少 availableSeats 的更新接口
    @PutMapping("/update")
    public ResponseEntity<String> updateSeats(@RequestBody Map<String, String> request) {
        String flightNumber = request.get("flightNumber");
        String flightDate = request.get("flightDate");

        Optional<Inventory> optional = repository.findByFlightNumberAndFlightDate(flightNumber, flightDate);

        if (optional.isPresent()) {
            Inventory inventory = optional.get();

            if (inventory.getAvailableSeats() <= 0) {
                return ResponseEntity.badRequest().body("No seats available to reduce.");
            }

            inventory.setAvailableSeats(inventory.getAvailableSeats() - 1);
            repository.save(inventory);
            return ResponseEntity.ok("Seat count updated successfully.");
        }

        return ResponseEntity.notFound().build();
    }
}
