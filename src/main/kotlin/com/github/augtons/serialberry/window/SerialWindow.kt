package com.github.augtons.serialberry.window

import com.fazecast.jSerialComm.SerialPort
import com.github.augtons.serialberry.core.SerialCore
import com.github.augtons.serialberry.localization.MyBundle
import com.github.augtons.serialberry.utils.installValidator
import com.github.augtons.serialberry.utils.validator
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.DocumentAdapter
import java.io.Closeable
import javax.swing.text.JTextComponent

class SerialWindow(
    private val name: String,
    private val parentToolWindow: ToolWindow
): Closeable {
    private val application = ApplicationManager.getApplication()
    private val logger = logger<SerialWindow>()

    val runAction = object : DumbAwareAction(AllIcons.Actions.Execute) {
        override fun actionPerformed(e: AnActionEvent) {
            println("1")
        }
    }

    val settingAction = object : DumbAwareAction(AllIcons.Actions.Suspend) {
        override fun actionPerformed(e: AnActionEvent) {
            println("2")
        }
    }

    val ui = SerialWindowUI().apply {
        // 这里创建左侧工具按钮
        createToolWindowPanel(
            runAction,
            Separator.create(),
            settingAction.apply { templatePresentation.icon = AllIcons.General.Add } // 动态更改图标的方法
        )
        serialWindowLeft.scroolPane.border = null
    } // 去掉边框

    var serialPort: SerialPort? = null
        set(value) {
            serialPort?.closePort()
            field = value
        }


    init {
        println("$name, created")
        // 加载一次端口下拉框
        reloadPortList()

        // 波特率
        with(ui.serialWindowLeft) {
            // 波特率下拉框
            initBaudrateComboBox()
            // 波特率刷新键
            setOnRefresh {
                reloadPortList()
            }
        }

        // 数据位长
        with(ui.serialWindowLeft) {
            SerialCore.DATA_BIT_ITEMS.forEach {
                databitComboBox.addItem(it)
                databitComboBox.selectedItem = "8"
            }
        }

        // 停止位长
        with(ui.serialWindowLeft) {
            SerialCore.STOP_BIT_ITEMS.forEach { (label, _) ->
                stopbitComboBox.addItem(label)
            }
            stopbitComboBox.selectedItem = "1"
        }

        // 奇偶校验
        with(ui.serialWindowLeft) {
            SerialCore.PARITY_ITEM.forEach { (label, _) ->
                parityComboBox.addItem(label)
            }
            parityComboBox.selectedItem = SerialCore.PARITY_ITEM[0].first
        }

        ui.serialWindowLeft.run {
            // 需要检查的：端口 和 波特率
            // 端口需要检查“①未找到” ②找到但连接时已经拔出
            // 波特率 检查是否有红色提示框弹出
            button1.addActionListener {
                println(
                    buildString {
                        appendLine()
                        appendLine("baudrate = ${baudrateComboBox.selectedItem}")
                        appendLine("databit = ${databitComboBox.selectedItem}")
                        appendLine("stopbit = ${stopbitComboBox.selectedItem}")
                        appendLine("parity = ${parityComboBox.selectedItem}")
                        appendLine("RTS = ${rtsCheckBox.isSelected}")
                        appendLine("DTR = ${dtrCheckBox.isSelected}")
                        appendLine()
                    }
                )
            }
        }
    }

    /**
     * 初始化波特率下拉框
     */
    private fun SerialWindowLeft.initBaudrateComboBox() {
        // 加入默认常用波特率
        SerialCore.BAUDRATE_ITEMS.forEach {
            baudrateComboBox.addItem(it)
        }
        // 选上115200
        baudrateComboBox.selectedItem = "115200"

        // 输入验证：打开值更改的监听
        val editorComponent = baudrateComboBox.editor.editorComponent as JTextComponent
        editorComponent.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: javax.swing.event.DocumentEvent) {
                baudrateComboBox.validator.ifPresent { it.revalidate() }
            }
        })
        // 输入验证：验证的逻辑
        baudrateComboBox.installValidator(parentToolWindow.contentManager) {
            val value = editorComponent.text

            if (value == null || value.isBlank()) { // 未填
                ValidationInfo(MyBundle.message("notBlack"), baudrateComboBox)
            } else if(value.toLongOrNull() == null) { // 填的值不为整数
                ValidationInfo(MyBundle.message("invalidInput", value), baudrateComboBox)
            } else {
                null
            }
        }

    }

    /**
     * 重新加载端口列表
     */
    private fun reloadPortList() {
        with(ui.serialWindowLeft.portComboBox) {
            removeAllItems()
            SerialPort.getCommPorts().forEach {
                addItem(it.systemPortName)
            }
            if(itemCount <= 0) {
                addItem(MyBundle.message("notfound"))
            }
        }
    }

    /**
     * 释放资源
     */
    override fun close() {
        println("$name, closed")
    }
}