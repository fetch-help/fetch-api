package com.fetch.file.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(String user, MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			Path storageLocation = createDirectoryIfNotExists(user);

			Files.copy(file.getInputStream(), storageLocation.resolve(
					file.getOriginalFilename()
			));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 2)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}
/*
	private String resolveName(String fileName){
		String md5Hex = DigestUtils.md5DigestAsHex(fileName.getBytes()).toLowerCase();
		return md5Hex;
	}
*/
	private synchronized Path createDirectoryIfNotExists(String user)throws IOException{
		File directory = new File(rootLocation.toFile(), user);
		if (! directory.exists()) {
			Files.createDirectory(directory.toPath());
		}
		return directory.toPath();
	}

	@Override
	public Path load(String user, String filename) {
		return new File(rootLocation.toFile(), user).toPath().resolve(filename);
	}

	@Override
	public Resource loadAsResource(String user, String filename) {
		try {
			Path file = load(user, filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			if (! rootLocation.toFile().exists()) {
				Files.createDirectory(rootLocation);
			}
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
