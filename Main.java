import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        String rep = ConvertionBase10enBaseb("721", 2);
//        System.out.println("reponse: " + rep);
//        String nb="010";
//        String rep = ConvertionBase10("1011010001", 2);
        System.out.println("reponse: " + rep);
//        String bob = ConvertionBineaireHexadeciamle(1011010001);
//        System.out.println("bob:" + bob);
//        String haha = ConvertionBaseBenBaseB1(2, 16, "1011010001");
//        System.out.println("haha" + haha);

        String huhu=ConvertionBinaireAvecsigne("128");
        System.out.println("huhu:"+huhu);



    }

    public static String convertAlpha(int nombre) {
        String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String rep = null;
        rep = String.valueOf(alpha.charAt(nombre));

        return rep;
    }

    public static int convertAlphatonombre(String nombre) {
        String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String rep = null;

        return alpha.indexOf(nombre);
    }

    public static String ConvertionBase10enBaseb(String n, int base) {
        String reponse = "";
        int quotient = Integer.parseInt(n);
        while (quotient >= 1) {
            int reste = quotient % base;
            reponse = convertAlpha(reste) + reponse;
            quotient = quotient / base;

        }
        return reponse;
    }

    public static String ConvertionBase10(String n, int base) {
        int reponse = 0;
        String nbchar = String.valueOf(n);

        int puissance = nbchar.length() - 1;

        for (int i = 0; i < nbchar.length(); i++) {
            int nb = convertAlphatonombre(String.valueOf(nbchar.charAt(i)));
            int puiss = (int) Math.pow(base, puissance);
            reponse = (reponse + nb * puiss);
            puissance = puissance - 1;

        }
        String repstring = String.valueOf(reponse);


        return repstring;

    }

    public static String ConvertionBineaireHexadeciamle(int n) {
        String nbString = String.valueOf(n);
        String paddedString = padWithZeroes(nbString, 4);
        String[] parts = splitString(paddedString, 4);
        String rep = "";
        for (int i = 0; i < parts.length; i++) {
            rep = rep + convertAlpha(Integer.parseInt(ConvertionBase10((parts[i]), 2)));
        }

        return rep;
    }

    public static String padWithZeroes(String input, int partLength) {
        int remainder = input.length() % partLength;
        if (remainder != 0) {
            int zeroesToAdd = partLength - remainder;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < zeroesToAdd; i++) {
                sb.append("0");
            }
            sb.append(input);
            return sb.toString();
        }
        return input;
    }

    public static String[] splitString(String input, int partLength) {
        int length = input.length();
        int numParts = (length + partLength - 1) / partLength;
        String[] parts = new String[numParts];
        for (int i = 0; i < numParts; i++) {
            parts[i] = input.substring(i * partLength, Math.min((i + 1) * partLength, length));
        }
        return parts;
    }


    public static String ConvertionBineaireOctale(String n) {
        String nbString = String.valueOf(n);
        String paddedString = padWithZeroes(nbString, 3);
        String[] parts = splitString(paddedString, 3);
        String rep = "";
        for (int i = 0; i < parts.length; i++) {
            rep = rep + ConvertionBase10((parts[i]), 2);
        }

        return rep;
    }

    public static String ConvertionBaseBenBaseB1(int b, int b1, String nb) {
        String rep = "";
        rep = ConvertionBase10(nb, b);
        rep = ConvertionBase10enBaseb(rep, b1);
        return rep;
    }

    public static String ConvertionBinaireapresvirgule(String nb) {
        String rep = ".";
        double repdouble = Double.parseDouble(nb);
        int repInt = (int) repdouble;
        while (repdouble - repInt != 0) {
            repdouble=repdouble*2;
            String repString= String.valueOf(repdouble);
                if(Integer.valueOf(String.valueOf(repString.charAt(0)))<=1){
                    rep = rep + repString.charAt(0);
                    if(Integer.valueOf(String.valueOf(repString.charAt(0)))==1){
                        repdouble=repdouble-1;
                    }
                }
                else {
                    rep = rep+"0";
                    repdouble=repdouble-Integer.valueOf(String.valueOf(repString.charAt(0)));

                }

            repInt= (int) repdouble;
        }

        return rep;
    }

    public static String ConvertionBinaireDecimale(String nb) {
    String rep="";
    double nbdouble= Double.parseDouble(nb);
    int nbInt = ((int) nbdouble);

    double nbapresvirgule=nbdouble-nbInt;

    rep=rep+ConvertionBase10enBaseb(String.valueOf(nbInt),2);
    rep=rep+ConvertionBinaireapresvirgule(String.valueOf(nbapresvirgule));
    return rep;
    }

    public static String ConvertionBinaireAvecsigne(String nb) {
        String rep="";
        if (nb.contains("-")){
            nb=nb.substring(1);
            System.out.println("nb:"+nb);
            rep="1"+ConvertionBinaireDecimale(nb);
        }
        else {
            rep="0"+ConvertionBinaireDecimale(nb);
        }
        return rep;
    }
}
