package pi.naut.github.model;

public class PikachuMood {

    public static PikachuMood MAX_HAPPY = new PikachuMood("MAX HAPPY", new String[]{"pikachu-max-happy-0.png", "pikachu-max-happy-1.png"});
    public static PikachuMood VERY_HAPPY = new PikachuMood("VERY HAPPY", new String[]{"pikachu-very-happy-0.png", "pikachu-very-happy-1.png", "pikachu-very-happy-2.png", "pikachu-very-happy-3.png"});
    public static PikachuMood HAPPY = new PikachuMood("HAPPY", new String[]{"pikachu-happy-0.png", "pikachu-happy-1.png"});
    public static PikachuMood NEUTRAL = new PikachuMood("NEUTRAL", new String[]{"pikachu-neutral-0.png", "pikachu-neutral-1.png"});
    public static PikachuMood SAD = new PikachuMood("SAD", new String[]{"pikachu-sad-0.png", "pikachu-sad-1.png"});
    public static PikachuMood DEPRESSED = new PikachuMood("DEPRESSED", new String[]{"pikachu-depressed-0.png", "pikachu-depressed-1.png"});
    public static PikachuMood SICK = new PikachuMood("SICK", new String[]{"pikachu-sick-0.png", "pikachu-sick-1.png", "pikachu-sick-2.png", "pikachu-sick-3.png", "pikachu-sick-4.png", "pikachu-sick-5.png", "pikachu-sick-6.png", "pikachu-sick-7.png"});
    public static PikachuMood CONFUSED = new PikachuMood("CONFUSED", new String[]{"pikachu-confused-0.png", "pikachu-confused-1.png"});
    public static PikachuMood SHOCKED = new PikachuMood("SHOCKED", new String[]{"pikachu-shocked-0.png", "pikachu-shocked-1.png"});

    private String displayText;
    private String[] images;
    private int currentImageIndex;

    public PikachuMood(String displayText, String[] images) {
        this.displayText = displayText;
        this.images = images;
        this.currentImageIndex = 0;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getNextImage() {
        currentImageIndex = currentImageIndex == images.length - 1 ? 0 : currentImageIndex + 1;
        return images[currentImageIndex];
    }
}