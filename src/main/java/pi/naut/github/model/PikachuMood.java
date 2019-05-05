package pi.naut.github.model;

public class PikachuMood {

    public static PikachuMood HAPPY = new PikachuMood("pikachu-happy-0.png", "pikachu-happy-1.png");

    private String firstImage;
    private String secondImage;
    private String currentImage;

    public PikachuMood(String firstImage, String secondImage) {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        this.currentImage = firstImage;
    }

    public String getNextImage() {
        if(currentImage.equals(firstImage)) {
            currentImage = secondImage; 
        } else {
            currentImage = firstImage;
        }
        return currentImage;
    }
}