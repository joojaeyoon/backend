package dev.jooz.Web.domain.image;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageRestControllerTest {
    @Autowired
    MockMvc mvc;

    List<String> fileList = new ArrayList<>();

    @AfterAll
    public void cleanUp() {
        String path = "src/main/resources/static/images/";

        for (String file : fileList) {
            File f = new File(path + file);

            if (f.exists() && f.delete()) {
                System.out.println(f.getPath() + " - deleted");
            }
        }
    }

    @Test
    @DisplayName("이미지 업로드 테스트")
    public void upload_image() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "test2.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());

        MvcResult result = mvc.perform(multipart("/api/image")
                .file(file)
                .file(file2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").exists())
                .andDo(print())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        fileList.add(JsonPath.parse(content).read("$[0].name"));
        fileList.add(JsonPath.parse(content).read("$[1].name"));
    }

    @Test
    @DisplayName("5장 초과 이미지 업로드")
    public void upload_over_five_images() throws Exception {
        MockMultipartFile[] files = new MockMultipartFile[6];

        for (int i = 0; i < 6; i++)
            files[i] = new MockMultipartFile("files", "test" + Integer.toString(i) + ".png", MediaType.IMAGE_PNG_VALUE, "Test".getBytes());

        mvc.perform(multipart("/api/image")
                .file(files[0])
                .file(files[1])
                .file(files[2])
                .file(files[3])
                .file(files[4])
                .file(files[5])
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("파일을 업로드 하지 않을 때")
    public void no_file_upload() throws Exception{

        MockMultipartFile file=new MockMultipartFile("f","test.png",MediaType.IMAGE_PNG_VALUE,"test".getBytes());

        mvc.perform(multipart("/api/image")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("이미지 형식이 아닌 파일 업로드")
    public void upload_invalid_image() throws Exception {
        MockMultipartFile file = new MockMultipartFile("files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World!".getBytes());

        mvc.perform(multipart("/api/image")
                .file(file)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
