package com.example.platform.service;

import com.example.platform.model.Code;
import com.example.platform.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppService {

    private final CodeRepository repository;

    @Autowired
    public AppService(CodeRepository repository) {
        this.repository = repository;
    }

    public UUID saveCode(Code codeToAdd) {

        if (codeToAdd.getTime() > 0) {
            codeToAdd.setHasTimeLimit(true);
        }
        if (codeToAdd.getViews() > 0) {
            codeToAdd.setHasViewLimit(true);
        }
        return repository.save(codeToAdd).getId();
    }

    public Code findCodeById(UUID id) {
        Optional<Code> byId = repository.findById(id);

        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Code curCode = byId.get();

        if (curCode.isHasViewLimit()) {
            curCode.updateViews();
            if (curCode.getViews() == 0) {
                repository.deleteById(id);
                return curCode;
            }
        }

        if (curCode.isHasTimeLimit() && curCode.getTimeLeft() <= 0) {
            repository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return repository.save(curCode);
    }

    public List<Code> findRecent(){
        return repository.findTop10ByTimeEqualsAndViewsEqualsOrderByDateDesc(0,0);
    }
}
