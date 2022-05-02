package com.metanit.metacourse.services.implementation;

import com.metanit.metacourse.models.Lesson;
import com.metanit.metacourse.models.Module;
import com.metanit.metacourse.repositories.ModuleRepository;
import com.metanit.metacourse.services.ModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ModuleImplementation implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Override
    public Module saveModule(Module module) {
        log.info("Saving new module by name {}", module.getTitle());
        return moduleRepository.save(module);
    }

    @Override
    public Module getModule(String title) {
        log.info("Get module by title {}", title);
        return moduleRepository.findByTitle(title);
    }

    @Override
    public List<Module> getModules() {
        log.info("Get modules");
        return moduleRepository.findAll();
    }

    @Override
    public Module getModule(Long id) {
        log.info("Get module by id {}", id);
        Optional<Module> module = moduleRepository.findById(id);
        return module.orElseGet(Module::new);
    }

    @Override
    public void deleteModule(Long id) {
        log.info("Delete module by id {}", id);
        Module module = getModule(id);
        moduleRepository.delete(module);
    }

    @Override
    public void addLessonToModule(Lesson lesson, Module module) {
        module.getLessons().add(lesson);
    }

    @Override
    public Module update(Long id, Module module) {
        log.info("Update module by id {} ", id);
        Module moduleOld = getModule(id);
        moduleOld.setTitle(module.getTitle());
        moduleOld.setDescription(module.getDescription());
        return moduleOld;
    }
}
