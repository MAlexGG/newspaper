package com.newspaper.newspaper.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.newspaper.newspaper.exception.EntityNotFoundException;
import com.newspaper.newspaper.model.Image;
import com.newspaper.newspaper.repository.ImageRepository;

@Service
public class ImageService {

    ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get("uploads/"));
        Path path = Paths.get("uploads/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Image image = imageRepository.save(Image.builder()
            .name(file.getOriginalFilename())
            .path(path.toString())
            .build()
        );

        if (image != null) {
            return image;
        }
        
        return null;
    }

    public void deleteImage(int imageId) {
        Image image = getImage(imageId);
        Path path = Paths.get(image.getPath());
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo eliminar el archivo: " + path, e);
        }
        imageRepository.deleteById(imageId); 
    }
    

    public Image getImage(int id){
        Optional<Image> image = imageRepository.findById(id);
        return unwrapImage(image);
    }

    public static Image unwrapImage(Optional<Image> entity){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(Image.class);
    }
}
