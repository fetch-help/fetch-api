package com.fetch.file.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(String user, MultipartFile file);

	Stream<Path> loadAll();

	Path load(String user, String filename);

	Resource loadAsResource(String user, String filename);

	void deleteAll();

}
