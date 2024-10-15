package com.jetbrains.intellij;

import com.jetbrains.intellij.controller.StlFileController;
import com.jetbrains.intellij.service.StlFileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StlFileController.class)
@AutoConfigureMockMvc  // bruges til at simulere HTTP-forespørgsler til controlleren
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(TestSecurityConfig.class)
public class StlFileControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc bruges til at simulere HTTP-anmodninger
    @MockBean // gør det muligt at mock'e StlFileService, så vi ikke rammer den rigtige database.
    private StlFileService stlFileService;

    @Test
    public void testShowUploadForm() throws Exception {
        mockMvc.perform(get("/upload"))
                .andExpect(status().isOk())
                .andExpect(view().name("upload"));
    }
    @Test
    public void testUploadStlFile_Success() throws Exception {
        MockMultipartFile stlFile = new MockMultipartFile("file", "test.stl", "application/octet-stream", "stl content".getBytes());

        mockMvc.perform(multipart("/upload")
                        .file(stlFile))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "STL file uploaded successfully!"))
                .andExpect(view().name("upload"));

        verify(stlFileService, times(1)).saveStlFile(Mockito.any(MultipartFile.class));
    }

    @Test
    public void testUploadStlFile_Failure() throws Exception {
        MockMultipartFile invalidFile = new MockMultipartFile("file", "test.txt", "text/plain", "invalid content".getBytes());

        mockMvc.perform(multipart("/upload")
                        .file(invalidFile))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "Please upload a valid STL file."))
                .andExpect(view().name("upload"));
    }

    @Test
    public void testGetStlFile_Success() throws Exception {
        byte[] stlFileData = "stl data".getBytes(); // Simuleret STL-fil-data
        when(stlFileService.getLatestStlFileFromDatabase()).thenReturn(stlFileData);

        mockMvc.perform(get("/getStlFile"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"latest.stl\""))
                // Brug contentTypeCompatibleWith for at ignorere charset
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(stlFileData));  // Sammenlign med forventet bytearray
    }

    @Test
    public void testGetStlFile_NotFound() throws Exception {
        when(stlFileService.getLatestStlFileFromDatabase()).thenReturn(null);

        mockMvc.perform(get("/getStlFile"))
                .andExpect(status().isNotFound());
    }
}
