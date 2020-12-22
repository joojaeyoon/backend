package dev.jooz.Web.util;

import org.springframework.stereotype.Component;

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
}
