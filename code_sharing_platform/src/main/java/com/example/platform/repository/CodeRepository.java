package com.example.platform.repository;

import com.example.platform.model.Code;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepository extends CrudRepository<Code, UUID> {

    Optional<Code> findById(UUID id);
    void deleteById(UUID id);
    List<Code> findTop10ByTimeEqualsAndViewsEqualsOrderByDateDesc(int time, int views);

}
