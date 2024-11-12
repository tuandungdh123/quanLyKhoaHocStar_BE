package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.ModuleDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.ModuleEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.ModuleRepository;
import com.example.coursemanagement.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleServiceImplement implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public List<ModuleDTO> getAllModule() {
        return moduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModuleDTO getModuleById(Integer moduleId) {
        return moduleRepository.findByModuleId(moduleId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND, "Không tìm thấy chương học nào."));
    }

    @Override
    public List<ModuleDTO> getModulesByCourseId(Integer courseId) {
        return moduleRepository.findByCourse_CourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ModuleDTO addModule(ModuleDTO moduleDTO) {
        CourseEntity courseEntity = moduleDTO.getCourse();
        ModuleEntity moduleEntity = convertToEntity(moduleDTO, courseEntity);
        moduleEntity.setCreatedAt(LocalDateTime.now());
        ModuleEntity savedModule = moduleRepository.save(moduleEntity);
        return convertToDTO(savedModule);
    }

    @Override
    @Transactional
    public boolean deleteModule(Integer moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new AppException(ErrorCode.MODULE_NOT_FOUND, "Không tìm thấy chương học nào.");
        }
        moduleRepository.deleteById(moduleId);
        return true;
    }

    @Override
    public ModuleDTO updateModule(Integer moduleId, ModuleDTO moduleDTO) {
        ModuleEntity existingModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND, "Không tìm thấy chương học nào."));

        existingModule.setTitle(moduleDTO.getTitle());
        existingModule.setOrderNumber(moduleDTO.getOrderNumber());
        existingModule.setCourse(moduleDTO.getCourse());
        ModuleEntity updatedModule = moduleRepository.save(existingModule);
        return convertToDTO(updatedModule);
    }

    private ModuleDTO convertToDTO(ModuleEntity moduleEntity) {
        return ModuleDTO.builder()
                .moduleId(moduleEntity.getModuleId())
                .course(moduleEntity.getCourse())
                .title(moduleEntity.getTitle())
                .orderNumber(moduleEntity.getOrderNumber())
                .createdAt(moduleEntity.getCreatedAt())
                .build();
    }

    private ModuleEntity convertToEntity(ModuleDTO moduleDTO, CourseEntity courseEntity) {
        ModuleEntity moduleEntity = new ModuleEntity();
        moduleEntity.setModuleId(moduleDTO.getModuleId());
        moduleEntity.setCourse(courseEntity);
        moduleEntity.setTitle(moduleDTO.getTitle());
        moduleEntity.setOrderNumber(moduleDTO.getOrderNumber());
        moduleEntity.setCreatedAt(moduleDTO.getCreatedAt() != null ? moduleDTO.getCreatedAt() : LocalDateTime.now());
        return moduleEntity;
    }
}
