package com.event.service;

import com.event.entity.Event;
import com.event.entity.UploadedFile;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

@ApplicationScoped
public class FileUploadService {

    @Inject
    EntityManager em;

    private static final String UPLOAD_DIR = "C:/uploads/tango-events/";

    @Transactional
    public Response uploadFile(Long eventId, String fileName, 
                               InputStream fileStream) throws IOException {

        Event event = em.find(Event.class, eventId);
        if (event == null) {
            throw new WebApplicationException(
                "Event with id " + eventId + " not found",
                Response.Status.NOT_FOUND);
        }

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = UPLOAD_DIR + fileName;
        File targetFile = new File(filePath);

        if (targetFile.exists()) {
            UploadedFile existing = em.createQuery(
                "SELECT u FROM UploadedFile u WHERE u.filename = :filename",
                UploadedFile.class)
                .setParameter("filename", filePath)
                .getResultStream()
                .findFirst()
                .orElse(null);

            if (existing != null) {
                return Response.status(Response.Status.CONFLICT)
                    .entity("File already exists at: " + filePath)
                    .build();
            }
        }

        Files.copy(fileStream, Paths.get(filePath), 
                   StandardCopyOption.REPLACE_EXISTING);

        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.filename = filePath;
        em.persist(uploadedFile);

        if (event.uploadedFiles == null) {
            event.uploadedFiles = new ArrayList<>();
        }
        event.uploadedFiles.add(uploadedFile);
        em.merge(event);

        return Response.ok(uploadedFile).status(201).build();
    }

    public Event getEventWithFiles(Long eventId) {
        Event event = em.find(Event.class, eventId);
        if (event == null) {
            throw new WebApplicationException(
                "Event with id " + eventId + " not found",
                Response.Status.NOT_FOUND);
        }

        if (event.uploadedFiles != null) {
            for (UploadedFile uf : event.uploadedFiles) {
                File file = new File(uf.filename);
                if (file.exists()) {
                    uf.file = file;
                }
            }
        }

        return event;
    }
}