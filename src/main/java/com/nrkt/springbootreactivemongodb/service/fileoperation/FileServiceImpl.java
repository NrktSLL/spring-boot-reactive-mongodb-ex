package com.nrkt.springbootreactivemongodb.service.fileoperation;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.nrkt.springbootreactivemongodb.dto.response.FileResponse;
import com.nrkt.springbootreactivemongodb.exception.GridFsException;
import com.nrkt.springbootreactivemongodb.mapper.FileMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileServiceImpl implements FileService {

    ReactiveGridFsOperations gridFsOperations;
    FileMapper fileMapper;

    @Override
    public Mono<FileResponse> uploadFile(String employeeId, Mono<FilePart> filePart) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("employeeId",employeeId);
        return filePart
                .flatMap(part -> gridFsOperations.store(part.content(), part.filename(), dbObject)).log()
                .map(id -> fileMapper.fileResponse(id.toHexString()));
    }

    @Override
    public Flux<FileResponse> getFiles() {
        return gridFsOperations
                .find(query(Criteria.where("metadata.type").is("data")))
                .map(x -> fileMapper.fileResponse(x.getObjectId().toHexString(), x.getUploadDate()));
    }

    @Override
    public Flux<FileResponse> getFiles(String employeeId) {
        return gridFsOperations
                .find(query(Criteria.where("metadata.employeeId").is(employeeId)))
                .map(x -> fileMapper.fileResponse(x.getObjectId().toHexString(), x.getUploadDate()));
    }

    @Override
    public Mono<ReactiveGridFsResource> getFile(String id) {
        return gridFsOperations.findOne(query(where("_id").is(id)))
                .switchIfEmpty(Mono.error(new GridFsException("File not found to id: " + id)))
                .log()
                .flatMap(gridFsOperations::getResource);
    }

    @Override
    public Mono<Void> deleteFile(String id) {
        Query query = query(where("_id").is(id));
        return gridFsOperations.findOne(query)
                .switchIfEmpty(Mono.error(new GridFsException("File not found to id: " + id)))
                .log()
                .flatMap(file -> gridFsOperations.delete(query));
    }
}
