package com.example.restarraynumbersinput;

import com.example.restarraynumbersinput.service.StorageFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StorageFileService storageFileService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void whenFileUpload_thenCheckStatusIsOk() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "upload.json",
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(Paths.get("src/main/resources/upload.json")));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/api/v1/numbers/upload").file(mockMultipartFile))
                .andExpect(status().isOk());

        then(this.storageFileService).should().saveFile(mockMultipartFile);
    }
}
