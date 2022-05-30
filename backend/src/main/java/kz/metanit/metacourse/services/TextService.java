package kz.metanit.metacourse.services;

import kz.metanit.metacourse.models.Text;

import java.util.List;

public interface TextService {
    Text saveText(Text text);
    Text getText(String title);
    List<Text> getTexts();
    Text getText(Long id);
    void deleteText(Long id);
    Text update(Long id, Text text);
}
