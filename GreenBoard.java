import java.awt.*;

public class GreenBoard implements Design {

    private final Color color;

    public GreenBoard() {
        color = Color.GREEN.darker();
    }


    public Color getColor() {
        return color;
    }
}
