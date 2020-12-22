package dev.jooz.Web.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String path="src/main/resources/temp/";

    private Optional<File> convert(MultipartFile file) throws IOException{
        UUID uuid=UUID.randomUUID();
        String name=file.getOriginalFilename();
        name=path+uuid.toString()+"-"+name;
        File convertFile=new File(name);

        if(convertFile.createNewFile()){
            try(FileOutputStream fos=new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public String upload(MultipartFile multipartFile) throws IOException{
        File uploadFile=convert(multipartFile)
                .orElseThrow(()->new IllegalArgumentException("Failed to convert multipartfile"));
        return upload(uploadFile,uploadFile.getName());
    }

    public String upload(File uploadFile,String fileName){
        fileName="static/"+fileName;

        amazonS3Client.putObject(new PutObjectRequest(bucket,fileName,uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

    public void delete(String key){
        amazonS3Client.deleteObject(bucket,key);
    }
}
