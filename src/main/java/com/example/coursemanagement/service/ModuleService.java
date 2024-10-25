package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.ModuleDTO;

import java.util.List;

public interface ModuleService {
    List<ModuleDTO> getAllModule();
    ModuleDTO addModule(ModuleDTO moduleDTO);
    boolean deleteModule(Integer moduleId);
    ModuleDTO updateModule(Integer moduleId, ModuleDTO moduleDTO);
}
