package com.example.restarraynumbersinput;

import com.example.restarraynumbersinput.service.StorageFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
public class FileDownloadUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StorageFileService storageFileService;

    @BeforeTestMethod
    public void init() {
        storageFileService.init();
    }

    @Test
    public void testExportFile() throws Exception {
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/numbers/export")
                                .contentType(MediaType.APPLICATION_OCTET_STREAM))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());
        assertEquals(0, result.getResponse().getContentAsByteArray().length);
        assertEquals("application/csv", result.getResponse().getContentType());
    }
}
