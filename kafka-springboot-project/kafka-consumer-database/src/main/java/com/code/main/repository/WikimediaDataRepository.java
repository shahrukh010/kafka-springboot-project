package com.code.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.main.entity.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}
