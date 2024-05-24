package com.example.demo;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor
{

    private Map<String,Character> reverseMap(Map<Character,String> prefixMap){
        Map<String,Character> map = new HashMap<>();
        for (Character current:prefixMap.keySet()){
            String value = prefixMap.get(current);
            map.put(value,current);
        }
        return map;
    }
    public void writeToFile(String fileContent, Map<Character,String> prefixMap) throws IOException {
        File file = new File("compressed.txt");

        // Initialize a StringBuilder to store the entire bit string
        StringBuilder bitString = new StringBuilder();

        // Convert the fileContent to the corresponding bit string using the prefixMap
        String spaces = new String();
        char[] fileContentArray =fileContent.toCharArray();
        for (int i=0;i<fileContentArray.length;i++) {
            if( Character.isLetter(fileContentArray[i]))
                bitString.append(prefixMap.get(fileContentArray[i]));
            else if( Character.isWhitespace(fileContentArray[i])) {
                spaces += bitString.length();
                spaces+=":";
                //System.out.println(bitString.length());
            }

        }

//        System.out.println("Encoding side: "+bitString);
        //System.out.println("Encoder Side Bitstring :"+bitString);
        BitSet bitSet = new BitSet(bitString.length());
        for (int i = 0; i < bitString.length(); i++) {
            if (bitString.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
//        System.out.println("Encoder side: "+bitSet);
        StringBuilder prefixTable = new StringBuilder();
        Map<String,Character> reversePrefix = reverseMap(prefixMap);
        for (Map.Entry<String, Character> entry : reversePrefix.entrySet()) {
            prefixTable.append(entry.getKey()).append(':').append(entry.getValue()).append(';');
        }

        // Convert BitSet to byte array
        byte[] byteArray = bitSet.toByteArray();




        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(prefixTable.toString());
        dos.writeUTF("//");
        dos.writeUTF( byteArray.length+"");
        dos.writeUTF("//");
        dos.writeUTF( bitString.length()+"");
        dos.writeUTF("//");
        //dos.writeUTF(spaces);



        dos.write(byteArray);



            // Write the final byte array to a file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                baos.writeTo(fos);
            }


    }

    public String readFromFile(String path) throws IOException {

        File file = new File(path);
        FileReader fr = new FileReader(file);
        int ch;
        StringBuilder fileContent = new StringBuilder();
        while ((ch=fr.read()) != -1) {
            fileContent.append((char)ch);
        }
        fr.close();
        return fileContent.toString();


    }

    public void writeToFile(String content) throws IOException {
        File file = new File("Decompressed.txt");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        }

    }
}
