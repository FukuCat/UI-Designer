package logic.design;

import logic.design.elements.AbstractUIElement;
import ui.MachineProject;

import java.util.ArrayList;
import java.util.List;

public class Form {

    public MachineProject machineProject;
    private AbstractUIElement selectedElement;
    private static Form instance = null;
    private List<AbstractUIElement> elements;

    private Form(){
        reset();
    }

    public static Form getInstance(){
        return instance == null? (instance = new Form()) : instance;
    }

    public void reset(){
        // TODO: run close of all actors
        selectedElement = null;
        setElements(new ArrayList<>());
    }

    public String generateXML(){
        StringBuilder sb = new StringBuilder();
        sb.append("<form>\n");
        for(AbstractUIElement e: getElements()){
            sb.append(e.toXML());
            sb.append('\n');
        }
        sb.append("</form>");
        return sb.toString();
    }

    public boolean isValid(){
        for(AbstractUIElement e : Form.getInstance().getElements()){
            if(e.isOverlappingElements(Form.getInstance().getElements()))
                return false;
        }
        return true;
    }

    public boolean isValid(int width, int height){
        for(AbstractUIElement e : Form.getInstance().getElements()){
            if(e.isOverlappingElements(Form.getInstance().getElements()) || e.isOutOfBounds(width, height))
                return false;
        }
        return true;
    }

    public void moveElementToTop(AbstractUIElement element){
        elements.remove(element);
        elements.add(element);
    }

    public List<AbstractUIElement> getElements() {
        return elements;
    }

    public void setElements(List<AbstractUIElement> elements) {
        this.elements = elements;
    }

    public AbstractUIElement getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(AbstractUIElement selectedElement) {
        this.selectedElement = selectedElement;
    }
}
