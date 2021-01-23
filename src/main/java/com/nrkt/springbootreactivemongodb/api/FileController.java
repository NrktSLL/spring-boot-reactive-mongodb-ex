package com.nrkt.springbootreactivemongodb.api;

import com.nrkt.springbootreactivemongodb.dto.response.FileResponse;
import com.nrkt.springbootreactivemongodb.service.fileoperation.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/versions/1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    FileService fileService;

    @PostMapping(value = "/files/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<FileResponse> uploadFile(@PathVariable String id,
                                         @RequestPart Mono<FilePart> file) {
        return fileService.uploadFile(id, file);
    }

    @GetMapping("/files")
    @ResponseStatus(HttpStatus.OK)
    public Flux<FileResponse> getFiles() {
        return fileService.getFiles();
    }

    @GetMapping("/files/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Void> viewFile(@PathVariable String id, ServerWebExchange exchange) {
        return fileService.getFile(id)
                .flatMapMany(resource -> {
                    exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"");
                    return exchange.getResponse().writeWith(resource.getDownloadStream());
                });
    }

    @GetMapping("/files/download/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Void> downloadFile(@PathVariable String id, ServerWebExchange exchange) {
        return fileService.getFile(id)
                .flatMapMany(resource -> {
                    exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"");
                    return exchange.getResponse().writeWith(resource.getDownloadStream());
                });
    }

    @DeleteMapping("/files/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteFile(@PathVariable String id) {
        return fileService.deleteFile(id);
    }
}
