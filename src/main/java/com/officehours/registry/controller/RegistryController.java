package com.officehours.registry.controller;

import com.officehours.registry.model.Registry;
import com.officehours.registry.services.RegistryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registry")
public class RegistryController {
    private RegistryService registryService;

    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping
    public Registry create(@RequestBody Registry newRegistry) {
        return registryService.save(newRegistry);
    }


}
