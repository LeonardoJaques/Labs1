package br.com.leonardo.jaques.ZipJava;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String[] data = {

                "line 1",
                "line 2 2",
                "line 3 3 3 3",
                "line 4 4 4 4 4",
                "line 5 5 5 5 5 5",
        };

        try(FileSystem zipFs = openZip(Paths.get("MyData.zip"))){
            copyToZip(zipFs);

            writeToFileInZip1(zipFs, data);
            writeToFileInZip2(zipFs, data);

        } catch (Exception e){
            System.out.println(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }



    private static java.nio.file.FileSystem openZip( Path zipPath) throws IOException, URISyntaxException{

        Map<String, String> providerProps = new HashMap <>();
        providerProps.put("create", "true");

        URI zipUri = new URI("Jar:File",zipPath.toUri().getPath(),null);
        java.nio.file.FileSystem zipFs = FileSystems.newFileSystem(zipUri, providerProps);

        return zipFs;
    }

    private static void copyToZip ( java.nio.file.FileSystem zipFs) throws IOException {
//          Path sourcefile = FileSystems.getDefault().getPath("file1.txt");
        Path sourceFile = Paths.get("file1.txt");
        Path destFile = zipFs.getPath("/file1Copy.txt");

        Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void writeToFileInZip1 (FileSystem zipFs, String[] data) throws IOException{

        try (BufferedWriter writer = Files.newBufferedWriter(zipFs.getPath("/newFile1.txt"))){

            for(String d:data){
                writer.write(d);
                writer.newLine();
            }

        }

    }

    private static void writeToFileInZip2 (FileSystem zipFs, String[] data) throws IOException{

        Files.write(zipFs.getPath("/newFile2.txt"), Arrays.asList(data),
                Charset.defaultCharset(), StandardOpenOption.CREATE);


    }
}
