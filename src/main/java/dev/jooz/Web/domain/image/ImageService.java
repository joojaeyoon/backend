package dev.jooz.Web.domain.image;

import dev.jooz.Web.exception.image.NoFileUploadException;
import dev.jooz.Web.exception.image.NoImageException;
import dev.jooz.Web.exception.image.TooManyImageException;
import dev.jooz.Web.domain.post.Post;
import dev.jooz.Web.util.ImageFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageFile imageFile;
    private String path="src/main/resources/static/images/";

    public ImageDto.ImageCreateDto findByPost(Post post){
        Image image=imageRepository.findByPost(post);
        if(image==null)
            return null;
        return new ImageDto.ImageCreateDto(imageRepository.findByPost(post));
    }

    public List<ImageDto.ImageCreateDto> findAllByPost(Post post) {
        List<ImageDto.ImageCreateDto> images=new ArrayList<>();
        for(Image img:imageRepository.findAllByPost(post))
           images.add(new ImageDto.ImageCreateDto(img.getUrl()));

        return images;
    }

    public List<ImageDto.ImageCreateDto> saveImages(MultipartFile[] files) throws Exception {
        List<ImageDto.ImageCreateDto> resDtos = new ArrayList<>();

        if (files.length > 5)
            throw new TooManyImageException();
        else if (files.length == 0)
            throw new NoFileUploadException();

        for (MultipartFile file : files) {
            String ext=file.getContentType().split("/")[1];

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
            String name=dtos.get(i).getName();
            imageRepository.save(dtos.get(i).toEntity(post));
            File img=new File(path+name);
            File to=new File(path+post.getId()+"/"+name);

            img.renameTo(to);

        }
    }

    public ImageDto.ImageCreateDto findFirstByPost(Post post){
        Image image=imageRepository.findFirstByPost(post);

        ImageDto.ImageCreateDto img=new ImageDto.ImageCreateDto(image);

        return img;
    }
}
