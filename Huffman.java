import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    public static void main(String[] args) throws Exception {
        /////////////prendre le file
        String filePath = "C:\\Users\\Jeddy\\Documents\\Codage\\test.txt"; // Remplacez ceci par le chemin de votre fichier
        String fileContent = readFileToString(filePath);
        if (fileContent != null) {
            System.out.println("Contenu du fichier :\n" + fileContent);
        } else {
            System.out.println("Im\npossible de lire le fichier.");
        }

        //////////////encodage



        String text = fileContent;
        Map<Character, Integer> frequencyMap = countFrequencies(text);
        HuffmanNode root = buildHuffmanTree(frequencyMap);
        Map<Character, String> huffmanCodes = generateHuffmanCodes(root);
        writeObjectTofile(huffmanCodes,"C:\\Users\\Jeddy\\Documents\\Codage\\huffmancode.txt");
        System.out.println("Huffman Codes: " + huffmanCodes);


//        Map<Character, String> huffmanCodes = Map.of(
//                'a', "00",
//                'b', "01",
//                'c', "10",
//                'd', "11"
//        );

        // Mot à encoder
        String text2 = "abcd";
        // Encodage du texte
        Object huffObject=readObjectFromFile("C:\\Users\\Jeddy\\Documents\\Codage\\huffmancode.txt");
        Map<Character, String> huff= (Map<Character, String>) huffObject;
        String encodedString = encode(text, huff);
        System.out.println("Encoded String: " + encodedString);
        ////conversion en byte
        String filePathcomp = "C:\\Users\\Jeddy\\Documents\\Codage\\testcompressed.bin"; // Remplacez ceci par le chemin où vous souhaitez enregistrer le fichier

        byte[] lbyte=convertToBytes(encodedString);
        saveByteToFile(lbyte,filePathcomp);
        // Reconstruction de l'arbre de Huffman
        HuffmanNode root2 = buildHuffmanTree2(huff);
        // Décodage de la chaîne encodée
        String decodedString = decode(encodedString, root2);
        System.out.println("Decoded String: " + decodedString);

        ///////////decompression du fichier
        String filePathcompressed = "C:\\Users\\Jeddy\\Documents\\Codage\\testcompressed.bin"; // Remplacez ceci par le chemin de votre fichier
        byte[] bytes=readFileToByte(filePathcompressed);
        String rep=bytesToString(bytes);
        System.out.println("rep: " + rep);
        String decodedrep = decode(rep, root2);
        System.out.println("Decoded rep: " + decodedrep);
        writeStringToFile(decodedrep,"C:\\\\Users\\\\Jeddy\\\\Documents\\\\Codage\\\\testcompresseddecoded.txt");


        displayHuffmanTree(root);



    }

    public static Map<Character, Integer> countFrequencies(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
//            if (c != ' ') { // Exclure les espaces
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
//            }
        }
        return frequencyMap;
    }


    ///////////creation de l arbre
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        // Créer un nœud pour chaque caractère avec sa fréquence
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getValue(), entry.getKey()));
//            System.out.println(new HuffmanNode(entry.getValue(), entry.getKey()).data);
        }

        // Construire l'arbre de Huffman
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            // Créer un nouveau nœud avec une fréquence somme des deux nœuds les plus petits
            HuffmanNode newNode = new HuffmanNode(left.frequency + right.frequency, '-');
            newNode.left = left;
            newNode.right = right;
            pq.add(newNode);
        }

        return pq.poll(); // Renvoie le nœud racine de l'arbre de Huffman
    }

    /////encodage des valeurs
    public static Map<Character, String> generateHuffmanCodes(HuffmanNode root) {
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateCodesRecursive(root, "", huffmanCodes);
        return huffmanCodes;
    }

    private static void generateCodesRecursive(HuffmanNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) return;
        // Si le nœud est une feuille, associez le code de Huffman au caractère correspondant
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.data, code);
//            System.out.println(root.data + code);
            return;
        }
        // Sinon, continuez à parcourir l'arbre, en ajoutant 0 pour le sous-arbre gauche et 1 pour le sous-arbre droit
        generateCodesRecursive(root.left, code + "1", huffmanCodes);
        generateCodesRecursive(root.right, code + "0", huffmanCodes);
    }





    //////////////////////////decodage///////////////////////////
    public static HuffmanNode buildHuffmanTree2(Map<Character, String> huffmanCodes) {
        HuffmanNode root = new HuffmanNode('-');
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            char character = entry.getKey();
            String code = entry.getValue();
            HuffmanNode current = root;
            for (char bit : code.toCharArray()) {
                if (bit == '0') {
                    if (current.left == null) {
                        current.left = new HuffmanNode('-');
                    }
                    current = current.left;
                } else if (bit == '1') {
                    if (current.right == null) {
                        current.right = new HuffmanNode('-');
                    }
                    current = current.right;
                }
            }
            current.data = character;
        }
        return root;
    }

    public static String decode(String encodedString, HuffmanNode root) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode current = root;
        for (char bit : encodedString.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else if (bit == '1') {
                current = current.right;
            }
            // Si nous avons atteint une feuille, ajoutons son caractère à la chaîne décodée et réinitialisons le nœud actuel au nœud racine
            if (current.left == null && current.right == null) {
                decodedString.append(current.data);
                current = root;
            }
        }
        return decodedString.toString();
    }


    ///////////////encodage du texte / mot
    public static String encode(String text, Map<Character, String> huffmanCodes) {
        StringBuilder encodedString = new StringBuilder();
        for (char c : text.toCharArray()) {
            String code = huffmanCodes.get(c);
            if (code != null) {
                encodedString.append(code);
            } else {
                System.err.println("Character '" + c + "' not found in Huffman codes.");
            }
        }
        return encodedString.toString();
    }





    /////////////////////////////////////////////lecture par fichier
    public static String readFileToString(String filePath) {
        try {
            byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
            return new String(encodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeStringToFile(String content, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
            System.out.println("Fichier créé avec succès : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création du fichier.");
        }
    }
    ////////////////////// conversion byte
    public static byte[] convertToBytes(String encodedText) {
        char[] charArray = encodedText.toCharArray();
        int byteLength = (int) Math.ceil(charArray.length / 8.0);
        byte[] bytes = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            int value = 0;
            for (int j = 0; j < 8; j++) {
                int index = i * 8 + j;
                if (index < charArray.length && charArray[index] == '1') {
                    value |= (1 << (7 - j));
                }
            }
            bytes[i] = (byte) value;
        }
        return bytes;
    }
    public static String bytesToString(byte[] bytes) {
        // Convert each byte to its binary representation and append it to the StringBuilder
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            // Convert byte to binary and append it to the StringBuilder
            builder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return builder.toString();
    }

    public static  void saveByteToFile(byte[]bytes,String filepath){
        Path path1=Paths.get(filepath);
        try{
            Files.write(path1,bytes);

        } catch (Exception e) {
            throw new RuntimeException("errorwedfnouewdfew"+e);
        }
    }

    public static byte[] readFileToByte(String filepath){
        Path path=Paths.get(filepath);
        byte[] bytes=null;
        try{
            bytes=Files.readAllBytes(path);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static void writeObjectTofile(Object object,String filepath) throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
    }

    public static Object readObjectFromFile(String filepath) throws Exception {
        FileInputStream fileInputStream=new FileInputStream(filepath);
        ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
        return objectInputStream.readObject();
    }


    public static void displayHuffmanTree(HuffmanNode root) {
        if (root == null) return;
        displayHuffmanTreeUtil(root, "");
    }

    private static void displayHuffmanTreeUtil(HuffmanNode root, String indent) {
        if (root == null) return;

        System.out.println(indent + "mot: " + root.data + ", frequence: " + root.frequency);

        displayHuffmanTreeUtil(root.left, indent + "\t");

        displayHuffmanTreeUtil(root.right, indent + "\t");
    }




}
