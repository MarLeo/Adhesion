package com.tchokonthe.adhesion.controller;

import com.tchokonthe.adhesion.model.Authority;
import com.tchokonthe.adhesion.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;

@RestController
@RequestMapping("api/authorities")
public class RoleController {


    public static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    private final AuthorityRepository authorityRepository;


    @Autowired
    public RoleController(final AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/v1.0/{name}")
    public Authority roleName(@PathVariable("name") String name) {
        return authorityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException(name + " doesn't exist"));
    }

    @GetMapping("/v1.0/authorities")
    public List<Authority> authorities() {
        return authorityRepository.findAll(sortByDayAsc());
    }

    private Sort sortByDayAsc() {
        return new Sort(DEFAULT_DIRECTION, "id");
    }



}
