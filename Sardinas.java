import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class Sardinas {
    public static void main(String[] args) {
    List<String> langage=new ArrayList<>();
    langage.add("0");
    langage.add("01");
    langage.add("1101");
    langage.add("110");

    List<String> residu= getresidusL1(langage,langage);
        System.out.println(residu);

        System.out.println("iscode:"+isCode(langage));
        testerListe(langage);
    }




    public static List<String> getresidus(List<String> langage,List<String>langmoins){
        List<String> rep=new ArrayList<>();
        for ( String valmoins :langmoins ){
            for(String val: langage){
                if(val.startsWith(valmoins) && !val.equals(valmoins)){
                    rep.add(val.substring(valmoins.length()));
                } else if (val.equals(valmoins)) {
                    rep.add("e");

                }
            }
        }
        return rep;
    }

    public static List<String> getresidusL1(List<String> langage,List<String>langmoins){
        List<String> rep=new ArrayList<>();
        for ( String valmoins :langmoins ){
            for(String val: langage){
                if(val.startsWith(valmoins) && !val.equals(valmoins)){
                    rep.add(val.substring(valmoins.length()));
                }
            }
        }
        return rep;
    }

    public static List<String>getNextL(List<String>L1,List<String>L2){
        List<String>NextL=new ArrayList<>();
        NextL=getresidus(L1,L2);
        NextL.addAll(getresidus(L2,L1));
        return NextL;
    }

    public static boolean isCode(List<String>langage){
        List<List<String>>listeresidus=new ArrayList<>();
        List<String>L1=getresidusL1(langage,langage);
        List<String>lnext=getNextL(L1,langage);
        System.out.println(lnext);

        while(!lnext.contains("e"))
        {
            listeresidus.add(lnext);

            lnext=getNextL(lnext,langage);
            if (listeresidus.contains(lnext)){
                System.out.println(lnext);

                return true;
            }
            System.out.println(lnext);

        }

return false;    }


    public static void testerListe(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            String removed = strings.remove(i);
            if (isCode(strings))
            {System.out.println("code vrai retirer:"+removed);
            } else
            {System.out.println("code faux retirer:"+ removed);
            }
            strings.add(i, removed);
            for (int j = i+1; j < strings.size(); j++) {
                if (i < strings.size() - 1) {
                    String removed2 = strings.remove(j);
                    if (isCode(strings))
                    {System.out.println("code vrai retirer:"+removed +" et "+removed2);
                    } else
                    {System.out.println("code faux retirer:"+removed+" et "+removed2);
                    }
                    strings.add(j, removed2);
                } else
                {System.out.println("nulll");
                }
            }

        }
    }




}

