package dev.jooz.Web.domain.image;

import dev.jooz.Web.domain.image.exception.NoFileUploadException;
import dev.jooz.Web.domain.image.exception.NoImageException;
import dev.jooz.Web.domain.image.exception.TooManyImageException;
import dev.jooz.Web.domain.post.Post;
import dev.jooz.Web.util.ImageFile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageFile imageFile;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<ImageDto.ImageCreateDto> findByPost(Post post) {
        List<ImageDto.ImageCreateDto> images=new ArrayList<>();
        for(Image img:imageRepository.findByPost(post))
           images.add(new ImageDto.ImageCreateDto(img.getUrl()));

        return images;
    }

    public List<ImageDto.ImageCreateDto> saveImages(MultipartFile[] files) throws Exception {
        List<ImageDto.ImageCreateDto> resDtos = new ArrayList<>();

        logger.info("File Length = " + files.length);

        if (files.length > 5)
            throw new TooManyImageException();
        else if (files.length == 0)
            throw new NoFileUploadException();

        for (MultipartFile file : files) {
            String[] nameSplit = file.getOriginalFilename().split("\\.");
            String ext = nameSplit[nameSplit.length - 1];

            logger.info("ext = " + ext);

            if (!imageFile.checkExt(ext)) {
                throw new NoImageException(ext);
            }
        }

        for (int i = 0; i < files.length; i++) {
            String name = imageFile.write(files[i]);
            resDtos.add(new ImageDto.ImageCreateDto(name));
        }

        return resDtos;
    }

    public void save(List<ImageDto.ImageCreateDto> dtos, Post post) {
        for (int i = 0; i < dtos.size(); i++) {
            imageRepository.save(dtos.get(i).toEntity(post));
        }
    }
}
