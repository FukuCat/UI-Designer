package logic.design.elements;

import jGame.model.game.GameActor;
import jGame.model.math.Vector2f;
import ui.definition.ViewMode;
import utils.CollisionUtils;

import java.awt.*;
import java.util.List;

public abstract class AbstractUIElement {

    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 22;
    public static final String DEFAULT_FONT = Font.SANS_SERIF;
    public static final int DEFAULT_FONT_SIZE = 13;

    private String text;
    private String font;
    private int fontSize;
    private Vector2f position;
    private Vector2f scale;

    public AbstractUIElement(){
        text = "New Element";
        font = DEFAULT_FONT;
        setFontSize(DEFAULT_FONT_SIZE);
        position = new Vector2f();
        scale = new Vector2f(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    public void syncPositionWithActor(){
        position.setValue(getActor().getPosition());
    }
    public abstract GameActor getActor();
    public abstract ViewMode getViewMode();
    public abstract void hideActor();
    // every split second and on drop
    public boolean isOutOfBounds(float width, float height){
        if(Math.abs(width/2 - getActor().getPosition().getX())<= width/2
                && Math.abs(width/2 - (getActor().getPosition().getX() + getScale().getX()))< width/2){
            if(Math.abs(height/2 - getActor().getPosition().getY())<= height/2
                    && Math.abs(height/2 - (getActor().getPosition().getY() + getScale().getY()))< height/2){
                return false;
            }
        }

        return true;
    }
    public boolean isOverlappingElements(List<AbstractUIElement> elements){
        for(AbstractUIElement e: elements){
            if(!this.equals(e))
            if(CollisionUtils.aabbCollision2D(this.getActor(), e.getActor()))
                return true;
        }
        return false;
    }
    // sterilize inputs too
    public abstract String toXML();
    public abstract void setActorViewMode(ViewMode mode);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(Vector2f scale) {
        this.scale = scale;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
