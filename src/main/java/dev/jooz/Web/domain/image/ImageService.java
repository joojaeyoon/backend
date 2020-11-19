package dev.jooz.Web.domain.image;

import dev.jooz.Web.domain.image.exception.NoFileUploadException;
import dev.jooz.Web.domain.image.exception.NoImageException;
import dev.jooz.Web.domain.image.exception.TooManyImageException;
import dev.jooz.Web.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final String[] imgExt= {"png","jpg"};

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public boolean checkExt(String ext){
        for(String e : imgExt) {
            if (ext.equals(e))
                return true;
        }
        return false;
    }

    public String write(MultipartFile file) throws IOException {
        UUID uuid=UUID.randomUUID();
        String name=file.getOriginalFilename();
        name=uuid.toString()+"-"+name;
        Path path= Paths.get("src/main/resources/static/images/",name);

        File f=new File(path.toString());
        f.createNewFile();

        try(OutputStream os= Files.newOutputStream(path)){
            os.write(file.getBytes());
        }
        return name;
    }

    public List<ImageDto.ImageCreateDto> saveImages(MultipartFile[] files) throws Exception{
        List<ImageDto.ImageCreateDto> resDtos=new ArrayList<>();

        logger.info("File Length = "+files.length);

        if(files.length >5)
            throw new TooManyImageException();
        else if(files.length==0)
            throw new NoFileUploadException();

        for(MultipartFile file : files){
            String[] nameSplit=file.getOriginalFilename().split("\\.");
            String ext=nameSplit[nameSplit.length-1];

            logger.info("ext = "+ext);

            if(!checkExt(ext)) {
                throw new NoImageException(ext);
            }
        }

        for(int i=0;i<files.length;i++){
            String name=write(files[i]);
            resDtos.add(new ImageDto.ImageCreateDto(name));
        }

        return resDtos;
    }

    public void save(List<ImageDto.ImageCreateDto> dtos, Post post){
        logger.info(dtos.stream().toArray().toString());
        for(int i=0;i<dtos.size();i++){
            imageRepository.save(dtos.get(i).toEntity(post));
        }
    }
}
