package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.ModuleDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.ModuleEntity;
import com.example.coursemanagement.repository.ModuleRepository;
import com.example.coursemanagement.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImplement implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public List<ModuleDTO> getAllModule(){
        List<ModuleEntity> modules = moduleRepository.findAll();
        return modules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ModuleDTO addModule(ModuleDTO moduleDTO) {
        CourseEntity courseEntity = moduleDTO.getCourse();
        ModuleEntity moduleEntity = convertToEntity(moduleDTO, courseEntity);
        moduleEntity.setCreatedAt(LocalDateTime.now()); // Set creation date
        ModuleEntity savedModule = moduleRepository.save(moduleEntity);
        return convertToDTO(savedModule);
    }

    @Override
    @Transactional
    public boolean deleteModule(Integer moduleId) {
        if (moduleRepository.existsById(moduleId)) {
            moduleRepository.deleteById(moduleId);
            return true;
        }
        return false;
    }

    @Override
    public ModuleDTO updateModule(Integer moduleId, ModuleDTO moduleDTO) {
        Optional<ModuleEntity> existingModuleOpt = moduleRepository.findById(moduleId);
        if (existingModuleOpt.isPresent()) {
            ModuleEntity existingModule = existingModuleOpt.get();
            existingModule.setTitle(moduleDTO.getTitle());
            existingModule.setOrderNumber(moduleDTO.getOrderNumber());
            existingModule.setCourse(moduleDTO.getCourse());
            ModuleEntity updatedModule = moduleRepository.save(existingModule);
            return convertToDTO(updatedModule);
        }
        return null;
    }

    public ModuleDTO convertToDTO(ModuleEntity moduleEntity) {
        if (moduleEntity == null) {
            return null;
        }

        return ModuleDTO.builder()
                .moduleId(moduleEntity.getModuleId())
                .course(moduleEntity.getCourse())
                .title(moduleEntity.getTitle())
                .orderNumber(moduleEntity.getOrderNumber())
                .createdAt(moduleEntity.getCreatedAt())
                .build();
    }
    public ModuleEntity convertToEntity(ModuleDTO moduleDTO, CourseEntity courseEntity) {
        if (moduleDTO == null) {
            return null;
        }

        ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setModuleId(moduleDTO.getModuleId());
        moduleEntity.setCourse(courseEntity);
        moduleEntity.setTitle(moduleDTO.getTitle());
        moduleEntity.setOrderNumber(moduleDTO.getOrderNumber());
        moduleEntity.setCreatedAt(moduleDTO.getCreatedAt() != null ? moduleDTO.getCreatedAt() : LocalDateTime.now());

        return moduleEntity;
    }
}
