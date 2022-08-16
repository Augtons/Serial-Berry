package com.github.augtons.serialberry.window;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.OnePixelSplitter;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SerialWindowUI {
    private JPanel root;
    private SerialWindowLeft serialWindowLeft;
    private SerialWindowRight serialWindowRight;
    private OnePixelSplitter onePixelSplitter1;

    private SimpleToolWindowPanel toolWindowPanel;

    private void createUIComponents() {
        root = new JPanel(new GridLayoutManager(1, 1));

        onePixelSplitter1 = new OnePixelSplitter();
        onePixelSplitter1.setHonorComponentsMinimumSize(true);

        serialWindowLeft = new SerialWindowLeft();
        serialWindowRight = new SerialWindowRight();

        onePixelSplitter1.setFirstComponent(serialWindowLeft.getRoot());
        onePixelSplitter1.setSecondComponent(serialWindowRight.getRoot());

    }

    public void createToolWindowPanel(AnAction... actions) {
        toolWindowPanel = new SimpleToolWindowPanel(false, true);

        var group = new DefaultActionGroup(actions);
        var toolBar = ActionManager.getInstance().createActionToolbar("asd", group, false);

        toolBar.setTargetComponent(toolWindowPanel);
        toolWindowPanel.setToolbar(toolBar.getComponent());
        toolWindowPanel.setContent(root);
    }

    public float getSplitterProportion() {
        return onePixelSplitter1.getProportion();
    }

    public SerialWindowLeft getSerialWindowLeft() {
        return serialWindowLeft;
    }

    public SerialWindowRight getSerialWindowRight() {
        return serialWindowRight;
    }

    public JPanel getRoot() {
        return root;
    }

    public SimpleToolWindowPanel getToolWindowPanel() {
        return toolWindowPanel;
    }
}
