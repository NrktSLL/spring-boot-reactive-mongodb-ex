package com.nrkt.springbootreactivemongodb.decarator;

import com.nrkt.springbootreactivemongodb.api.FileController;
import com.nrkt.springbootreactivemongodb.dto.response.FileResponse;
import com.nrkt.springbootreactivemongodb.mapper.FileMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

public abstract class FileDecorator implements FileMapper {

    @Setter(onMethod = @__({@Autowired}))
    FileMapper fileMapper;

    @Override
    public FileResponse fileResponse(String id, Date date) {
        var fileResponse = new FileResponse();
        fileResponse.setId(id);
        fileResponse.setUploadDate(date);

        return getFileResponseWithLinks(fileResponse);
    }

    @Override
    public FileResponse fileResponse(String id) {
        var fileResponse = new FileResponse();
        fileResponse.setId(id);

        return getFileResponseWithLinks(fileResponse);
    }

    private FileResponse getFileResponseWithLinks(FileResponse fileResponse) {
        WebFluxLink[] links = new WebFluxLink[]{
                linkTo(methodOn(FileController.class).viewFile(fileResponse.getId(), null))
                        .withRel("file").map(x -> x.withDeprecation("view file")),
                linkTo(methodOn(FileController.class).downloadFile(fileResponse.getId(), null))
                        .withRel("file").map(x -> x.withDeprecation("file download")),
                linkTo(methodOn(FileController.class).deleteFile(fileResponse.getId()))
                        .withRel("file").map(x -> x.withDeprecation("delete file"))
        };

        Arrays.stream(links).forEach(link -> link.toMono().map(fileResponse::add).subscribe());

        return fileResponse;
    }
}
