package com.fetch.file;

import com.fetch.file.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StorageService storageService;

    @LocalServerPort
    private int port;

    @Test
    public void shouldUploadFile() throws Exception {
        ClassPathResource resource = new ClassPathResource("uploadtest.txt");

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("redirUrl", "http://localhost:" + this.port + "/api/v1/files/uploaded.html");
        map.add("user", "username");
        map.add("file", resource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/api/v1/files/upload",
                map,
                String.class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);
        assertThat(response.getHeaders().getLocation().toString())
                .startsWith("http://localhost:" + this.port + "/api/v1/files/uploaded.html");
        then(storageService).should().store(eq("username"), any(MultipartFile.class));
    }


    @Test
    public void shouldDownloadFile() throws Exception {
        ClassPathResource resource = new ClassPathResource("uploadtest.txt");
        given(this.storageService.loadAsResource("username",
                "uploadtest.txt")).willReturn(resource);
        ResponseEntity<Resource> response = this.restTemplate
                .getForEntity("/api/v1/files/download?user=username&filename=uploadtest.txt", Resource.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION))
                .isEqualTo("attachment; filename=\"uploadtest.txt\"");
    }
}
