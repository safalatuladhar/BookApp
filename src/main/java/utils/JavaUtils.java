package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

public class JavaUtils {
    public static String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("cont-disp-header" + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    public static String fileUpload(HttpServletRequest request) throws ServletException, IOException {
        final Part filePart = request.getPart("file");
        String uploadDir = "images";
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + uploadDir;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()){
            fileSaveDir.mkdir();
        }
        System.out.println("image added" + fileSaveDir.getAbsolutePath());
        String filename = getFileName(filePart);
        filePart.write(uploadFilePath + File.separator + filename);
        return filename;
    }
}
