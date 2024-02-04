package ru.job4j.dream.service;

import ru.job4j.dream.dto.FileDto;
import ru.job4j.dream.model.File;

import java.util.Optional;

public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);

}
