package com.github.augtons.serialberry.window;

import com.github.augtons.serialberry.localization.MyBundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class SerialWindowLeft {
    private JPanel leftPanel;

    private JPanel configPanel;
    private JComboBox portComboBox;
    private JComboBox baudrateComboBox;
    private JComboBox databitComboBox;
    private JComboBox stopbitComboBox;
    private JComboBox parityComboBox;
    private JCheckBox RTSCheckBox;
    private JCheckBox DTRCheckBox;
    private JPanel controlPanel;
    private JButton button1;
    private JPanel root;
    private ActionButton actionButton1;
    private JTextField textField1;
    private JBScrollPane scroolPane;

    private OnClickListener onRefresh;

    public void createUIComponents() {
        root = new JPanel(new GridLayoutManager(1, 1));

        var presentation = new Presentation(MyBundle.message("refresh"));
        presentation.setIcon(AllIcons.Actions.Refresh);
        presentation.setEnabled(true);
        actionButton1 = new ActionButton(new DumbAwareAction() {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {
                if (onRefresh != null) { onRefresh.onClick(); }
            }
        }, presentation, "refresh", new Dimension(26, 24));
    }

    public interface OnClickListener {
        void onClick();
    }

    public OnClickListener getOnRefresh() {
        return onRefresh;
    }

    public void setOnRefresh(OnClickListener onRefresh) {
        this.onRefresh = onRefresh;
    }

    public JPanel getRoot() {
        return root;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public JPanel getConfigPanel() {
        return configPanel;
    }

    public JComboBox getPortComboBox() {
        return portComboBox;
    }

    public JComboBox getBaudrateComboBox() {
        return baudrateComboBox;
    }

    public JComboBox getDatabitComboBox() {
        return databitComboBox;
    }

    public JComboBox getStopbitComboBox() {
        return stopbitComboBox;
    }

    public JComboBox getParityComboBox() {
        return parityComboBox;
    }

    public JCheckBox getRTSCheckBox() {
        return RTSCheckBox;
    }

    public JCheckBox getDTRCheckBox() {
        return DTRCheckBox;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public JButton getButton1() {
        return button1;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JBScrollPane getScroolPane() {
        return scroolPane;
    }
}
