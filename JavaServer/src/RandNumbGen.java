/**
 * Created by txc37 on 02.08.2016.
 */
public class RandNumbGen {

    private static String code = null;

    public static void main(String[] args){

        String randCode = getCode();
        System.out.println(randCode);


    }

    public static String generateCode(){
        int randCode = (int) (Math.random() * 9000) + 1000;
        String codeString = String.valueOf(randCode);
        return codeString;
    }

    public static String getCode(){
        if (code == null){

            code = generateCode();

        }
        return code;
    }

}
