package unice.miage.numres.cobuild.servicesImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads");

    public String saveFile(MultipartFile file, String subfolder) {
        Path targetDir = rootLocation.resolve(subfolder);
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try{
            Files.createDirectories(targetDir);
            Path destination = targetDir.resolve(fileName);
    
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            throw new RuntimeException("Impossible de créer le dossier de sauvegarde");
        }

        return fileName;
    }
}

