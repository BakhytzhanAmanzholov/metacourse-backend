package kz.metanit.metacourse.services;

import kz.metanit.metacourse.models.Lesson;
import kz.metanit.metacourse.models.Module;

import java.util.List;

public interface ModuleService {
    Module saveModule(Module module);
    Module getModule(String title);
    List<Module> getModules();
    Module getModule(Long id);
    void deleteModule(Long id);
    Module update(Long id, Module module);
    void addLessonToModule(Lesson lesson, Module module);
}
