package com.example.springbootweb.model;

import com.example.springbootweb.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by summer on 2017/5/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRepositoryTests {

    @Autowired
    private FileRepository fileRepository;

    @Test
    public void testSaveFile() throws Exception {
        File file = new File();
        file.setId(2l);
        file.setName("小明");
        file.setPath("fffooo123");
        fileRepository.saveFile(file);
    }

    @Test
    public void findFileByFileName() {
        File file = fileRepository.findFileByName("小明");
        System.out.println("file is " + file);
    }

    @Test
    public void updateFile() {
        File file = new File();
        file.setId(2l);
        file.setName("天空");
        file.setPath("fffxxxx");
        fileRepository.updateFile(file);
    }

    @Test
    public void deleteFileById() {
        fileRepository.deleteFileById(2l);
    }

}

