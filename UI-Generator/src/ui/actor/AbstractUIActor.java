package ui.actor;

import ui.definition.ViewMode;

public interface AbstractUIActor {

    void setViewMode(ViewMode mode);
    ViewMode getViewMode();
    String getText();
    void setText(String text);
}
