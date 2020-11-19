package dev.jooz.Web.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageFile {
    private final String[] imgExt;

    public ImageFile(){
        imgExt=new String[]{"png","jpg","jpeg"};
    }

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
}
