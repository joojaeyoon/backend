package dev.jooz.Web.domain.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/image")
@RestController
@RequiredArgsConstructor
public class ImageRestController {
    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<ImageDto.ImageCreateResDto> uploadImage(@RequestParam MultipartFile[] files) throws Exception {

        return imageService.saveImages(files);
    }
}
