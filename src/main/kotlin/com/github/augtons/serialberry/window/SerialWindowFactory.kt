package com.github.augtons.serialberry.window

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.ContentManager
import com.intellij.ui.content.ContentManagerEvent
import com.intellij.ui.content.ContentManagerListener

class SerialWindowFactory : ToolWindowFactory, DumbAware {
    private val logger = logger<SerialWindowFactory>()
    val contentFactory = ContentFactory.SERVICE.getInstance()

    var toolWindow: ToolWindow? = null

    /**
     * 保存已经被占用的窗口名
     */
    private val names: Set<String> get() = windows.keys

    private val windows = mutableMapOf<String, SerialWindow>()

    /**
     * 获取一个合适的窗口序号
     */
    private val fitName: String get() {
            var index = 1
            // 增加index直至orders中没有
            while ("Serial$index" in names) {
                index ++
            }
            return "Serial$index"
        }

    /**
     * 当前Tab是否可以关闭
     *
     * @see changeClosable
     */
    private var canClose = false

    /**
     * ToolWindow被创建之后执行此方法
     *
     * 功能：
     * - 添加Add按钮的行为
     */
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        this.toolWindow = toolWindow
        // 添加Add按钮的行为
        toolWindow.setTitleActions(listOf(
            object : DumbAwareAction(AllIcons.General.Add) {
                override fun actionPerformed(e: AnActionEvent) {
                    val content = newTabContent(fitName)
                    toolWindow.contentManager.addContent(content)
                }
            }
        ))

        val content = newTabContent(fitName)
        toolWindow.contentManager.run {
            addContent(content)
            changeClosable(false)
        }

        toolWindow.contentManager.run {
            addContentManagerListener(object : ContentManagerListener {
                override fun contentAdded(event: ContentManagerEvent) {
                    // 功能：添加新Tab，若当前状态为不可关闭，则更改为可关闭
                    if (canClose.not()) {
                        canClose = true
                        changeClosable(true)
                    }
                    setSelectedContent(event.content)
                }

                override fun contentRemoved(event: ContentManagerEvent) {
                    // 功能：当只剩下最后一个Tab时，隐藏关闭按钮
                    if (contentCount <= 1) {
                        canClose = false
                        changeClosable(false)
                    }
                    // 功能：移出name
                    // TODO 异步化释放资源
                    event.content.displayName.let { name ->
                        windows[name]?.close()
                        windows.remove(name)
                    }
                }
            })
        }
    }

    /**
     * 创建一个新的Tab
     */
    private fun newTabContent(name: String): Content {
        val newWindow = SerialWindow(name, toolWindow!!)
//        val content = contentFactory.createContent(newWindow.ui.root, name, false)
        val content = contentFactory.createContent(newWindow.ui.toolWindowPanel, name, false)
        // 加入map
        windows[name] = newWindow
        return content
    }

    /**
     * 更改toolwindow内所有content的Tab的可关闭性
     *
     * @see canClose
     */
    private fun ContentManager.changeClosable(closeable: Boolean) {
        contents.forEach {
            it.isCloseable = closeable
        }
    }

    @Suppress("unused")
    inline fun <T> List<T>.firstFitIndex(predicate: (T) -> Boolean): Int {
        forEachIndexed { index, t ->
            if (predicate(t)) {
                return index
            }
        }
        return size
    }

}