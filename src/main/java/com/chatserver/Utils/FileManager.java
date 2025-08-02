package com.chatserver.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    public enum Context {
        SERVER("server"),
        CLIENT("client");

        private final String path;

        Context(String path) {
            this.path = path;
        }

        public String getPath() {
            return this.path;
        }
    }

    public enum Directory {
        LOGS("logs", "Archivos de registro");

        private final String path;
        private final String description;

        Directory(String path, String description) {
            this.path = path;
            this.description = description;
        }

        public String getPath() {
            return this.path;
        }

        public String getDescription() {
            return this.description;
        }

        public Path getFullPath(Context context) {
            return FileManager.resolve(context, this);
        }
    }

    private static final String RESOURCE_DIR = "src/main/resources";
    private final Context context;

    public FileManager(Context context) {
        this.context = context;

        createDirectoryStructure();
    }

    private static Path resolve(String relativePath) {
        Path projectRoot = Paths.get("").toAbsolutePath();
        return projectRoot.resolve(RESOURCE_DIR).resolve(relativePath);
    }

    public static Path resolve(Context context, Directory directory) {
        return resolve(context.getPath() + "/" + directory.getPath());
    }

    public Path resolve(Directory directory, String fileName) {
        return resolve(this.context.getPath() + "/" + directory.getPath() + "/" + fileName);
    }

    public Path resolve(Directory directory) {
        return resolve(this.context.getPath() + "/" + directory.getPath());
    }

    private void createDirectoryStructure() {
        System.out.println("Iniciando estructura de directorios...");

        for (var dir : Directory.values()) {

            try {
                var dirPath = resolve(dir);
                if (!Files.exists(dirPath)) {
                    Files.createDirectories(dirPath);
                    System.out.println("Creado: " + dirPath);
                }
            } catch (IOException e) {
                System.err.println("Erro al crear directorios " + this.context + "/" + dir + ": " + e.getMessage());
            }
        }
    }

    public boolean createDirectory(Directory dir) {
        var path = resolve(dir);

        if (Files.exists(path)) {
            return false;
        }

        try {
            Files.createDirectories(path);
            return true;
        } catch (IOException e) {
            System.err.println("Error al crear el directorio: " + e.getMessage());
            return false;
        }
    }
}
