package com.example.springbootweb.repository;

import com.example.springbootweb.model.File;

public interface FileRepository {

    public void saveFile(File file);

    public File findFileByName(String name);

    public long updateFile(File file);

    public void deleteFileById(Long id);

}