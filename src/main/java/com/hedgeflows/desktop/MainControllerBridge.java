package com.hedgeflows.desktop;

import com.hedgeflows.desktop.controller.MainController;

public class MainControllerBridge {
    private final MainController controller;

    public MainControllerBridge(MainController controller) {
        this.controller = controller;
    }

    public MainController getController() {
        return controller;
    }
}

