package com.example.demo;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Decompress
{
    public String decompress(String filePath) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(filePath))) {

//            StringBuilder result = new StringBuilder();
//            while (true) {
//                try {
//                    String utfString = dataInputStream.readUTF();
//                    result.append(utfString);
//                } catch (EOFException eof) {
//                    // End of stream reached
//                    break;
//                }
//            }
//            //System.out.println(reverseMap);
//            String[] splitter = result.toString().split("//");
//
//            Map<String, String> map = parseReverseMap(splitter[0]);
//
//
//
//            // Read the length of the bit string
//            int byteArrayLength = Integer.parseInt(splitter[1]);
//            int bitStringLength = Integer.parseInt(splitter[2]);

            String reverseMap = dataInputStream.readUTF();
            Map<String, String> map = parseReverseMap(reverseMap);
            dataInputStream.readUTF();
            int byteArrayLength = Integer.parseInt(dataInputStream.readUTF());
            dataInputStream.readUTF();
            int bitStringLength = Integer.parseInt(dataInputStream.readUTF());
            dataInputStream.readUTF();
//            String[] spacesArray = dataInputStream.readUTF().toString().split(":");
//            Set<Integer> spacesSet = new HashSet<>();
//            for (String c:spacesArray){
//                spacesSet.add(Integer.parseInt(c)+1);
//            }




            // Read the compressed data
            byte[] byteArray = new byte[byteArrayLength]; // Calculate byte array size based on bitStringLength
            dataInputStream.readFully(byteArray);

            BitSet bitSet = BitSet.valueOf(byteArray);
            int[] bits = bitSet.stream().flatMap(a-> IntStream.of(a)).toArray();
            Set<Integer> set = new HashSet<>();
            for (int val:bits)
                set.add(val);
//            System.out.println("Deocder side: "+bitSet);
            StringBuilder bitString = new StringBuilder();
            for (int i = 0; i < bitStringLength; i++) {
                if( set.contains(i) )
                    bitString.append('1');
                else
                    bitString.append('0');
                //bitString.append(bitSet.get(i) ? '1' : '0');

            }
//            System.out.println("BitString:"+bitString);
            // Convert the byte array to a bit string
//            StringBuilder bitString = new StringBuilder();
//            for (byte b : byteArray) {
//                for (int i = 7; i >= 0; i--) {
//                    bitString.append((b >> i) & 1);
//                }
//            }

            // Truncate the bit string to the specified length
            if (bitString.length() > bitStringLength) {
                bitString.setLength(bitStringLength);
            }
//            System.out.println("Decoding side: "+bitString);
            // Decode the bit string using the reverse map
            StringBuilder originalString = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            StringBuilder forSpace = new StringBuilder();
            for (int i = 0; i < bitString.length(); i++) {
                temp.append(bitString.charAt(i));
                forSpace.append(bitString.charAt(i));
//                if( spacesSet.contains(forSpace.length())) {
//                    originalString.append(" ");
//
//                }
                if (map.containsKey(temp.toString())) {
                    originalString.append(map.get(temp.toString()));
                    temp.setLength(0);
                }
            }

            return originalString.toString();
        }
    }

    private Map<String, String> parseReverseMap(String reverseMapString) {

        //11:b;00:e;01:a;100:d;101:c;
        Map<String, String> reverseMap = new HashMap<>();
        String[] pairs = reverseMapString.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            reverseMap.put(keyValue[0], keyValue[1]);
        }
        return reverseMap;
    }
}
