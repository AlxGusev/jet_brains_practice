package com.example.demo.repository;

import com.example.demo.model.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {

    Page<Result> findAllByCompletedBy(String email, Pageable pageable);
}
