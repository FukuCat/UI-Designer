package ui.scene;

import jGame.model.game.GameObject;
import jGame.model.game.GameScene;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.model.input.Mouse;
import jGame.model.math.Vector2f;
import jGame.view.Renderer;
import logic.design.Form;
import logic.design.elements.AbstractUIElement;
import logic.design.elements.ButtonElement;
import logic.design.elements.InputElement;
import logic.design.elements.LabelElement;
import ui.definition.ViewMode;
import utils.CollisionUtils;

import java.awt.event.KeyEvent;

public class DesignScene extends GameScene {

    public static final String ACTION_CREATE_LABLE = "CREATE LABLE";
    public static final String ACTION_CREATE_INPUT = "CREATE INPUT";
    public static final String ACTION_CREATE_BUTTON = "CREATE BUTTON";
    public static final String ACTION_DELETE_ELEMENT = "DELETE ELEMENT";
    public static final String ACTION_LOAD_FORM = "RELOAD FORM";

    private Camera camera;
    private Form form = Form.getInstance();

    private AbstractUIElement draggedElement;
    private Vector2f dragOffset;
    private Vector2f lastPosition;

    public DesignScene(String name){
        super(name);
        camera = new Camera(1000.0f);
        getExternalActions().put(ACTION_CREATE_LABLE, () -> {
            AbstractUIElement e = new LabelElement();
            form.moveElementToTop(e);
            getActors().add(e.getActor());
        });
        getExternalActions().put(ACTION_CREATE_INPUT, () -> {
            AbstractUIElement e = new InputElement();
            form.moveElementToTop(e);
            getActors().add(e.getActor());
        });
        getExternalActions().put(ACTION_CREATE_BUTTON, () -> {
            AbstractUIElement e = new ButtonElement();
            form.moveElementToTop(e);
            getActors().add(e.getActor());
        });

        getExternalActions().put(ACTION_DELETE_ELEMENT, () -> {
            AbstractUIElement e = Form.getInstance().getSelectedElement();
            draggedElement = null;
            form.setSelectedElement(null);
            form.machineProject.redrawPanels();
            getActors().remove(e.getActor());
            form.getElements().remove(e);
        });

        getExternalActions().put(ACTION_LOAD_FORM, this::loadFromForm);

        init();
    }

    public void loadFromForm(){
        dragOffset = null;
        draggedElement = null;
        getActors().clear();
        if(Form.getInstance().getSelectedElement() != null)
            Form.getInstance().getSelectedElement().getActor().reset();
        for(AbstractUIElement e: Form.getInstance().getElements()){
           getActors().add(e.getActor());
        }
        //TODO: validation
    }

    @Override
    public void init() {
        dragOffset = null;
        draggedElement = null;
        lastPosition = null;
        Form.getInstance().reset();
        camera.reset();
        getActors().clear();
        setDoneLoading(true);
    }

    @Override
    public void input(Input input, long deltaTime) {


        if(input.getMouse().isPressed(Mouse.LEFT_BUTTON)){
            if(draggedElement == null){ // not currently selecting an element
                boolean isSelected = false;
                for(int i = 0; i < form.getElements().size(); i++){
                    AbstractUIElement e = form.getElements().get(i);
                    // is cursor inside element
                    isSelected = CollisionUtils.pointCollision2D(
                            e.getActor(),
                            (float) input.getMouse().getX() - e.getActor().getScale().getX()/2,
                            (float) input.getMouse().getY() - e.getActor().getScale().getY()/2);
                    if(isSelected) { // select new element
                            draggedElement = e;
                            lastPosition = draggedElement.getActor().getPosition().getValue();
                            Form.getInstance().moveElementToTop(e);
                            Form.getInstance().setSelectedElement(e);
                            Form.getInstance().machineProject.loadStatsFromActor();
                            Form.getInstance().machineProject.redrawPanels();
                            if(getActors().indexOf(e) != getActors().size() - 1) {
                                getActors().remove(e.getActor());
                                getActors().add(e.getActor());
                            }
                        break;
                    }
                }
                if(!isSelected){ // clicked white space
                    // return position to last position if current position is not allowed
                    if(Form.getInstance().getSelectedElement() != null){

                        if(Form.getInstance().getSelectedElement().isOutOfBounds(getWindowWidth(), getWindowHeight())){
                            Form.getInstance().getSelectedElement().getActor().setPosition(lastPosition);
                        }

                        Form.getInstance().getSelectedElement().syncPositionWithActor();
                        Form.getInstance().machineProject.loadStatsFromActor();
                        Form.getInstance().machineProject.redrawPanels();
                    }
                    // unselect element and reload side menu
                    draggedElement = null;
                    lastPosition = null;
                    form.setSelectedElement(null);
                    form.machineProject.redrawPanels();
                }

            } else { // currently selected element
                // set cursor offset
                boolean isCursorInsideElement = CollisionUtils.pointCollision2D(
                        draggedElement.getActor(),
                        (float) input.getMouse().getX() - draggedElement.getActor().getScale().getX()/2,
                        (float) input.getMouse().getY() - draggedElement.getActor().getScale().getY()/2);
                if ( dragOffset != null || isCursorInsideElement) {
                    if(dragOffset == null)
                        dragOffset = new Vector2f(
                                ((float) input.getMouse().getX()) - draggedElement.getActor().getPosition().getX(),
                                ((float) input.getMouse().getY()) - draggedElement.getActor().getPosition().getY());
                    draggedElement.getActor().setPosition(
                            ((float) input.getMouse().getX()) - dragOffset.getX(),
                            ((float) input.getMouse().getY()) - dragOffset.getY());
                }
            }
        } else { // Left mouse button is not pushed down
            if(draggedElement != null){
                // return position to last position if current position is not allowed
                if(Form.getInstance().getSelectedElement() != null){

                    if(Form.getInstance().getSelectedElement().isOutOfBounds(getWindowWidth(), getWindowHeight())){
                        Form.getInstance().getSelectedElement().getActor().setPosition(lastPosition);
                    }

                    Form.getInstance().getSelectedElement().syncPositionWithActor();
                    Form.getInstance().machineProject.loadStatsFromActor();
                    Form.getInstance().machineProject.redrawPanels();
                }
                // clear dragged element because you are not holding the mouse button
                draggedElement = null;
                dragOffset = null;
            }
        }

        if(input.getMouse().isPressed(Mouse.RIGHT_BUTTON)){
            if(draggedElement == null){ // not currently selecting an element
                for(int i = 0; i < form.getElements().size(); i++){
                    AbstractUIElement e = form.getElements().get(i);
                    // is cursor inside element
                    boolean isSelected = CollisionUtils.pointCollision2D(
                            e.getActor(),
                            (float) input.getMouse().getX() - e.getActor().getScale().getX()/2,
                            (float) input.getMouse().getY() - e.getActor().getScale().getY()/2);
                    if(isSelected) { // removing element
                        lastPosition = null;
                        draggedElement = null;
                        form.setSelectedElement(null);
                        form.machineProject.redrawPanels();
                        getActors().remove(e.getActor());
                        form.getElements().remove(e);
                        break;
                    }
                }

            }
        }

        for(GameObject o : getActors())
            o.input(input, deltaTime, camera);
    }

    @Override
    public void logic(long deltaTime) {

        // calculate all borders
        for(AbstractUIElement e : Form.getInstance().getElements()){
            if(e.isOutOfBounds(getWindowWidth(), getWindowHeight()))
                e.setActorViewMode(ViewMode.TAKEN);
            else {
                if(e.equals(Form.getInstance().getSelectedElement()))
                    e.setActorViewMode(ViewMode.HIGHLIGHT);
                else
                    e.setActorViewMode(ViewMode.NORMAL);
            }
        }

        // default logic
        camera.logic(deltaTime);
        for(GameObject o : getActors())
            o.logic(deltaTime);
    }

    @Override
    public void render(Renderer renderer) {
        for(int i =  0; i < getActors().size(); i++)
            getActors().get(i).render(renderer, camera);
    }

    @Override
    public void close() {
        for(GameObject o : getActors())
            o.close();
        setDoneLoading(false);
    }
}
