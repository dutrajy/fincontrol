package com.dutrajy.fincontrol.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dutrajy.fincontrol.models.Entry;
import com.dutrajy.fincontrol.repositories.EntriesRepository;

@RestController
@RequestMapping("/api/entries")
public class EntriesController {

    @Autowired
    private EntriesRepository entriesRepository;

    @GetMapping
    public ResponseEntity<List<Entry>> getAllEntries() {
        List<Entry> entries = entriesRepository.findAll();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entry> getEntryById(@PathVariable Long id) {
        Entry entry = entriesRepository.findById(id).orElse(null);
        return (entry != null) ?
                new ResponseEntity<>(entry, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        entry.setCreatedAt(LocalDateTime.now());
        Entry savedEntry = entriesRepository.save(entry);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody Entry updatedEntry) {
        Entry existingEntry = entriesRepository.findById(id).orElse(null);

        if (existingEntry != null) {
            existingEntry.setDescription(updatedEntry.getDescription());
            existingEntry.setAmount(updatedEntry.getAmount());
            existingEntry.setUpdatedAt(LocalDateTime.now());

            Entry savedEntry = entriesRepository.save(existingEntry);
            return new ResponseEntity<>(savedEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        if (entriesRepository.existsById(id)) {
            entriesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
