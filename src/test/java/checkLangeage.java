public class checkLangeage {
   static  boolean checker (String teststring)

    {

        String[] test = teststring.trim().split("\\s+");
    int size = test.length;

    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',};
    int amountofletters = alphabet.length;
    int contains = 0;
        for (int j = 0; j < size; j++) {
        char[] word = test[j].toCharArray();
        for (int i = 0; i < amountofletters; i++) {
        if (word[0] == alphabet[i])
        contains++;
        }
        }
        if (contains == size)
        return true;
        else
        return false;
        }
    static void lehapidor() {
        int a;
        for (a=0; a<10; a++){
            System.out.println(a);
        }
return;
    }
        }