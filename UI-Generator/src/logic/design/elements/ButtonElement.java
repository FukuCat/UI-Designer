package logic.design.elements;

import jGame.model.game.GameActor;
import ui.actor.ButtonActor;
import ui.definition.ViewMode;
import utils.CollisionUtils;

import java.awt.*;
import java.util.List;

public class ButtonElement extends AbstractUIElement{

    ButtonActor actor;

    public ButtonElement(){
        super();
    }

    @Override
    public GameActor getActor() {
        return actor == null? (actor = new ButtonActor(this)) : actor;
    }

    @Override
    public ViewMode getViewMode() {
        return actor.getViewMode();
    }

    @Override
    public void hideActor() {
        actor.setViewMode(ViewMode.HIDDEN);
        actor.setPosition(0.0f,0.0f);
    }

    @Override
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("  <type>");
        sb.append("button");
        sb.append("</type>");
        sb.append("\n  ");
        sb.append("<text>");
        sb.append(getText());
        sb.append("</text>");
        sb.append("\n  ");
        //sb.append("<font>");
        //sb.append(getFont());
        //sb.append("</font>");
        //sb.append("\n  ");
        sb.append("<pos-x>");
        sb.append((int)actor.getPosition().getX());
        sb.append("</pos-x>");
        sb.append("\n  ");
        sb.append("<pos-y>");
        sb.append((int)actor.getPosition().getY());
        sb.append("</pos-y>");
        sb.append('\n');
        sb.append("<size-x>");
        sb.append((int)actor.getScale().getX());
        sb.append("</size-x>");
        sb.append("\n  ");
        sb.append("<size-y>");
        sb.append((int)actor.getScale().getY());
        sb.append("</size-y>");
        return sb.toString();
    }

    @Override
    public void setActorViewMode(ViewMode mode) {
        actor.setViewMode(mode);
    }
}
