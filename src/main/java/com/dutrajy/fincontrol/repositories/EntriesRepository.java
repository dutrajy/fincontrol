package com.dutrajy.fincontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dutrajy.fincontrol.models.Entry;

public interface EntriesRepository extends JpaRepository<Entry, Long>{
    
}
