package hr.fer.oprpp1.hw05;

public class Util {
    public static byte[] hextobyte(String keyText) {
        keyText = keyText.toLowerCase();
        byte[] b = new byte[keyText.length() % 2 == 0 ? keyText.length() / 2 : keyText.length() / 2 + 1];
        for (int i = 0; i < keyText.length(); i++) {
            int x;
            String regex = "[0-9]";
            String v = Character.toString(keyText.charAt(i));
            if (v.matches(regex)) {
                x = Integer.parseInt(v);
            } else {
                switch (keyText.charAt(i)) {
                    case 'a':
                        x = 10;
                        break;
                    case 'b':
                        x = 11;
                        break;
                    case 'c':
                        x = 12;
                        break;
                    case 'd':
                        x = 13;
                        break;
                    case 'e':
                        x = 14;
                        break;
                    case 'f':
                        x = 15;
                        break;
                    default:
                        throw new IllegalArgumentException("Krivo uneseni hex");
                }
            }
            if (i % 2 == 0)
                x = x << 4;
            b[i/2]+= x;
        }
        return b;
    };

    public static String bytetohex(byte[] b){
        StringBuilder h = new StringBuilder(b.length * 2);
        for(var i : b){
            h.append(String.format("%02x",i));
        }
        return h.toString();
    }

    
    public static void main(String[] args) {
        byte[] b = {17,11,0};
        System.out.println(bytetohex(b));
    }
}
