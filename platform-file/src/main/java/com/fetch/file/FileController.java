package com.fetch.file;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fetch.file.storage.StorageFileNotFoundException;
import com.fetch.file.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("api/v1/files")
public class FileController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/list")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam("user") String user,
            @RequestParam("filename") String filename) {

        Resource file = storageService.loadAsResource(user, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("redirUrl") String redirectUrl,
                                   @RequestParam("user") String user,
                                   @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(user, file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:"+redirectUrl;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
