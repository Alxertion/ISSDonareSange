package JavaResources.View;

import javafx.scene.image.Image;

public class Loader {

    private final Image IMAGE_BACK_BUTTON_UNTOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/back_button_untouched.png").toString(), 64, 64, false, true);
    private final Image IMAGE_BACK_BUTTON_TOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/back_button_touched.png").toString(), 64, 64, false, true);
    private final Image IMAGE_VREAUSADONEZ_UNTOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/circle_vreau_sa_donez_untouched.png").toString(), 220, 200, false, true);
    private final Image IMAGE_VREAUSADONEZ_TOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/circle_vreau_sa_donez_touched.png").toString(), 220, 200, false, true);
    private final Image IMAGE_FORWARD_BUTTON_TOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/forward_button_touched.png").toString(), 64, 64, false, true);
    private final Image IMAGE_FORWARD_BUTTON_UNTOUCHED = new Image(getClass().getClassLoader().getResource("imagesDonator/forward_button_untouched.png").toString(), 64, 64, false, true);

    public Image forward_button_touched(){
        return IMAGE_FORWARD_BUTTON_TOUCHED;
    }

    public Image forward_button_untouched(){
        return IMAGE_FORWARD_BUTTON_UNTOUCHED;
    }

    public Image back_button_untouched(){
        return IMAGE_BACK_BUTTON_UNTOUCHED;
    }

    public Image vreau_sa_donez_touched() {
        return IMAGE_VREAUSADONEZ_TOUCHED;
    }

    public Image vreau_sa_donez_untouched() {
        return IMAGE_VREAUSADONEZ_UNTOUCHED;
    }

    public Image back_button_touched(){
        return IMAGE_BACK_BUTTON_TOUCHED;
    }
}
