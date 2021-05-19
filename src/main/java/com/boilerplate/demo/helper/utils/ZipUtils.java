package com.boilerplate.demo.helper.utils;

import com.boilerplate.demo.model.common.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtils {

    public static final MediaType ZIP_CONTENT_TYPE = new MediaType("application", "zip");

    @Autowired private Environment environment;

    public List<File> exportZipFile(MultipartFile uploadedZipFile) throws Exception{
        List<File> unzippedFiles = new ArrayList<>(0);
        String outputFilePath = System.getProperty("user.dir") + File.separator + environment.getProperty("project.temp.dir");
        File tempDir = new File(outputFilePath);
        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        ZipInputStream zipFile = new ZipInputStream(uploadedZipFile.getInputStream());
        ZipEntry file;
        while ((file = zipFile.getNextEntry()) != null) {
            File actualFile = new File(outputFilePath + File.separator + file.getName());
            OutputStream outputStream = new FileOutputStream(actualFile);
            byte[] buffer = new byte[2048];
            int length;
            while ((length = zipFile.read(buffer, 0, buffer.length)) >= 0) {
                outputStream.write(buffer, 0, length);
            }
            unzippedFiles.add(actualFile);
            outputStream.close();
        }
        zipFile.close();
        return unzippedFiles;
    }

    public String createZipFile(final String zipName, final String path, final List<File> files) throws CustomException {
        try{
            FileOutputStream fos = new FileOutputStream(path + File.separator + zipName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            for (File fileToZip : files) {
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
            return zipName;
        } catch(Exception exp){
            exp.printStackTrace();
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR).message(exp.getMessage());
        }
    }
}
