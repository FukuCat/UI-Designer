package ui.actor;

import jGame.factory.TextureFactory;
import jGame.model.game.GameActor;
import jGame.model.graphics.Camera;
import jGame.model.graphics.Texture;
import jGame.model.input.Input;
import jGame.view.Renderer;
import logic.design.elements.AbstractUIElement;
import ui.definition.ViewMode;

import java.awt.*;

public class ButtonActor extends GameActor implements AbstractUIActor{

    public static final Color BORDER_SELECTED = Color.green;
    public static final Color BORDER_DEFAULT = Color.lightGray;
    public static final Color BORDER_TAKEN = Color.red;
    private AbstractUIElement parent;
    private ViewMode mode;

    private String font;
    private int fontSize;
    private Color border;
    private Texture texture;
    private String text;


    private int fontWidthOffset, fontHeightOffset;

    public ButtonActor(AbstractUIElement parent){
        super();
        this.parent = parent;
        /*
        try {
            texture = new Texture(TEXTURE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        reset();
    }
    @Override
    public void setViewMode(ViewMode mode){
        this.mode = mode;
        switch(mode){
            case NORMAL:
                border = BORDER_DEFAULT;
                break;
            case HIGHLIGHT:
                border = BORDER_SELECTED;
                break;
            case TAKEN:
                border = BORDER_TAKEN;
                break;
        }
    }

    @Override
    public void reset(){
        text = parent.getText();
        texture = TextureFactory.createVerticalGradient((int)parent.getScale().getX(), (int)parent.getScale().getY(), Color.WHITE, Color.LIGHT_GRAY);
        setViewMode(ViewMode.NORMAL);
        setScale(parent.getScale());
        setPosition(parent.getPosition());
        fontWidthOffset = fontHeightOffset = -1;
        fontSize = parent.getFontSize();
        font = parent.getFont();
    }

    @Override
    public void input(Input input, long deltaTime, Camera camera){

    }

    @Override
    public void logic(long deltaTime){

    }

    @Override
    public void render(Renderer renderer, Camera camera){
        if(border == BORDER_DEFAULT)
            renderer.getRendIn().setStroke(new BasicStroke(1));
        else
            renderer.getRendIn().setStroke(new BasicStroke(3));

        // draw image
        renderer.getRendIn().drawImage(texture.getImage(),
                (int)(position.getX()),
                (int)(position.getY()),
                null);

        // outline
        renderer.getRendIn().setColor(border);
        renderer.getRendIn().drawRect((int)position.getX(), (int)position.getY(), (int)scale.getX(), (int)scale.getY());

        // Text
        //TODO: show text fix
        if(fontWidthOffset == -1)
            fontWidthOffset = renderer.getRendIn().getFontMetrics().stringWidth(text);
        if(fontHeightOffset == -1)
            fontHeightOffset = fontSize
                    * (renderer.getRendIn().getFontMetrics().getAscent()
                    + renderer.getRendIn().getFontMetrics().getDescent())
                    / renderer.getRendIn().getFontMetrics().getAscent();
        renderer.getRendIn().setStroke(new BasicStroke(1));
        renderer.getRendIn().setFont(new Font(font, Font.PLAIN, fontSize));
        renderer.getRendIn().setColor(Color.BLACK);
        renderer.getRendIn().drawString(text, (int) position.getX() + 3, (int) position.getY() + fontHeightOffset + 3);


    }

    @Override
    public void close(){

    }
    @Override
    public ViewMode getViewMode(){return mode;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
