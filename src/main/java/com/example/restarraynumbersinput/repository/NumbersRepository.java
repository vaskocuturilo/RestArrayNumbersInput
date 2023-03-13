package com.example.restarraynumbersinput.repository;

import com.example.restarraynumbersinput.entity.NumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumbersRepository extends JpaRepository<NumberEntity, Long> {
}
