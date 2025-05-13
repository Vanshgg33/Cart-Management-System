package com.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ResourceController {


    private static final Map<String, String> contentTypeMap = new HashMap<>();

    static {
        // CSS mappings
        contentTypeMap.put("css", "text/css");

        // Image mappings
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("jpeg", "image/jpeg");
        contentTypeMap.put("gif", "image/gif");
        contentTypeMap.put("svg", "image/svg+xml");
        contentTypeMap.put("webp", "image/webp");
        contentTypeMap.put("ico", "image/x-icon");

        // Add other mappings as needed
        contentTypeMap.put("js", "application/javascript");
        contentTypeMap.put("json", "application/json");
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("txt", "text/plain");
    }

    // Handle root level resources (like styles.css)
    @GetMapping(value = "/styles.css")
    @ResponseBody
    public ResponseEntity<Resource> serveStylesCss() throws IOException {
        return serveStaticResource("static/styles.css", "text/css");
    }

    // Handle CSS files with a specific endpoint pattern
    @GetMapping(value = "/**/*.css")
    @ResponseBody
    public ResponseEntity<Resource> serveCssFiles(HttpServletRequest request) throws IOException {
        String path = request.getRequestURI();
        // Remove leading slash if present
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return serveStaticResource("static/" + path, "text/css");
    }

    // Handle resources in subfolders with proper content types
    @GetMapping(value = "/{folder}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveResource(
            @PathVariable String folder,
            @PathVariable String filename) throws IOException {

        String path = "static/" + folder + "/" + filename;
        String contentType = getContentTypeForFile(filename);

        return serveStaticResource(path, contentType);
    }

    // Generic method to serve a static resource with the right content type
    private ResponseEntity<Resource> serveStaticResource(String resourcePath, String contentType) throws IOException {
        ClassPathResource resource = new ClassPathResource(resourcePath);

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(resource.getInputStream()));
    }

    // Determine content type based on file extension
    private String getContentTypeForFile(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            String extension = filename.substring(dotIndex + 1).toLowerCase();
            return contentTypeMap.getOrDefault(extension, "application/octet-stream");
        }
        return "application/octet-stream";
    }
}