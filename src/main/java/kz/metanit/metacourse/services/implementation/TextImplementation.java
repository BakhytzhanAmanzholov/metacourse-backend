package kz.metanit.metacourse.services.implementation;

import kz.metanit.metacourse.models.Text;
import kz.metanit.metacourse.repositories.TextRepository;
import kz.metanit.metacourse.services.TextService;
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
public class TextImplementation implements TextService {
    private final TextRepository textRepository;


    @Override
    public Text saveText(Text text) {
        return textRepository.save(text);
    }

    @Override
    public Text getText(String title) {
        return textRepository.findByHeading(title);
    }

    @Override
    public List<Text> getTexts() {
        return textRepository.findAll();
    }

    @Override
    public Text getText(Long id) {
        Optional<Text> text = textRepository.findById(id);
        return text.orElseGet(Text::new);
    }

    @Override
    public void deleteText(Long id) {
        Text text = getText(id);
        textRepository.delete(text);
    }

    @Override
    public Text update(Long id, Text text) {
        Text textOld = getText(id);
        textOld.setText(text.getText());
        return textOld;
    }
}
