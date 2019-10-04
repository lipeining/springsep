package com.example.springbootweb.repository.impl;

import com.mongodb.client.result.UpdateResult;
import com.example.springbootweb.repository.FileRepository;
import com.example.springbootweb.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class FileRepositoryImpl implements FileRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     * @param file 自定义的 file DTO
     */
    @Override
    public void saveFile(File file) {
        mongoTemplate.save(file);
    }

    @Override
    public File findFileByName(String name) {
        Query query = new Query(Criteria.where("fileName").is(name));
        File file = mongoTemplate.findOne(query, File.class);
        return file;
    }


    /**
     * 更新对象
     *
     * @param file 自定义的 file DTO
     */
    @Override
    public long updateFile(File file) {
        Query query = new Query(Criteria.where("id").is(file.getId()));
        Update update = new Update().set("name", file.getName()).set("path", file.getPath());
        // 更新查询返回结果集的第一条
        UpdateResult result = mongoTemplate.updateFirst(query, update, File.class);
        // 更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,FileEntity.class);
        if (result != null) {
            return result.getMatchedCount();
        } else {
            return 0;
        }

    }

    /**
     * 删除对象
     *
     * @param id 自定义的 file DTO.id
     */
    @Override
    public void deleteFileById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, File.class);
    }
}
