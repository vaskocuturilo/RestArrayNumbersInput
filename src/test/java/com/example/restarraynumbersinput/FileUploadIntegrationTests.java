package com.example.restarraynumbersinput;

import com.example.restarraynumbersinput.service.StorageFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private StorageFileService storageService;

    @LocalServerPort
    private int port;

    @Test
    public void shouldUploadFile() {
        ClassPathResource pathResource = new ClassPathResource("upload.json");
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", pathResource);
        ResponseEntity<String> response = this.restTemplate.postForEntity("/api/v1/numbers/upload", map, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.toString()).contains("File upload -> Uploaded the file successfully: upload.json");
        then(storageService).should().saveFile(any(MultipartFile.class));
    }
}
