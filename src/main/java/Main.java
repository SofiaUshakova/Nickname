import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger length3 = new AtomicInteger();
    public static AtomicInteger length4 = new AtomicInteger();
    public static AtomicInteger length5 = new AtomicInteger();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        new Thread(() -> {
            for (String text : texts) {
                if (text.equals(new StringBuilder(text).reverse())) {
                    letterCounter(text);
                }
            }

        }).start();

        new Thread(() ->{
            for (String text : texts){
                for (int i = 1; i < text.length(); i++){
                    if(text.charAt(i) == text.charAt(i - 1)){
                        letterCounter(text);
                    }
                }
            }

        }).start();

        new Thread(() -> {
            for (String text : texts){
                for (int i = 1; i < text.length(); i++){
                    if (text.charAt(i) > text.charAt(i - 1)){
                        letterCounter(text);
                    }
                }
            }
        }).start();
        System.out.println("Красивых слов с длиной 3: " + length3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + length4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + length5 + " шт");
    }

    public static void letterCounter(String text) {
        switch (text.length()) {
            case 3:
                length3.incrementAndGet();
                break;
            case 4:
                length4.incrementAndGet();
                break;
            case 5:
                length5.incrementAndGet();
                break;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
