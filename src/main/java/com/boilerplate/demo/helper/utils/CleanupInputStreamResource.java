package com.boilerplate.demo.helper.utils;

import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CleanupInputStreamResource extends FileSystemResource {

    private File file;
    boolean deleteParent;

    public CleanupInputStreamResource(File file, boolean deleteParent) {
        super(file);
        this.file = file;
        this.deleteParent = deleteParent;
    }
    @Override
    public InputStream getInputStream() throws IOException, IllegalStateException {
        return new FileInputStream(file) {
            @Override
            public void close() throws IOException {
                super.close();
                Files.delete(file.toPath());
                if (deleteParent) {
                    Files.delete(file.getParentFile().toPath());
                }
            }
        };
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (deleteParent ? 1231 : 1237);
        result = prime * result + ((file == null) ? 0 : file.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CleanupInputStreamResource other = (CleanupInputStreamResource) obj;
        if (deleteParent != other.deleteParent)
            return false;
        if (file == null) {
            if (other.file != null)
                return false;
        } else if (!file.equals(other.file))
            return false;
        return true;
    }
}