package com.example.markethibernate.gui.state;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface State {
    void renderView(VBox content);

    void initButton(HBox leftToolbar , HBox rightToolbar);

    void refresh();
}
