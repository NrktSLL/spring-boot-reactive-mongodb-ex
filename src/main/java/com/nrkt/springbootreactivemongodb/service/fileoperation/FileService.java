package com.nrkt.springbootreactivemongodb.service.fileoperation;

import com.nrkt.springbootreactivemongodb.dto.response.FileResponse;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileService {
    Mono<FileResponse> uploadFile(String id, Mono<FilePart> filePart);

    Flux<FileResponse> getFiles();

    Flux<FileResponse> getFiles(String employeeId);

    Mono<ReactiveGridFsResource> getFile(String id);

    Mono<Void> deleteFile(String id);
}
