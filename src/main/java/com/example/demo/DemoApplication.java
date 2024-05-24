package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SpringApplication.run(DemoApplication.class, args);
        FileProcessor fileProcessor = new FileProcessor();
        String fileContent = fileProcessor.readFromFile("sample.txt");
        CompressionAlgorithm compressionAlgorithm = new CompressionAlgorithm();
        Map<Character,String> prefixValue =  compressionAlgorithm.compress(fileContent);
        fileProcessor.writeToFile(fileContent,prefixValue);
        Decompress decompress = new Decompress();
        String filePath = new String("compressed.txt");
        String decompressedText = decompress.decompress(filePath);
        fileProcessor.writeToFile(decompressedText);
        //System.out.println(decompressedText);

    }

}
