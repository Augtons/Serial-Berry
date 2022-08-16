# Clion Plugin 模板

## 1. 插件工程结构：

```shell
.
|   # 构建脚本
├─ build.gradle.kts
|
|   # 这个文件包含了插件信息，版本，编译版本范围
|   # 会被build.gradle.kts访问并填入适当位置
├─ gradle.properties  
|
|   # 设置文件，不过这里边好像只有一个工程名
├─ settings.gradle.kts
|
|   # 其余未列出的文件无需特别关注
|
└─src
    └─main
       ├─java #这里包含了所有Java源文件
       │  └─com.template.clionplugin  #包名
       │           └─java程序文件
       ├─kotlin #这里包含了所有Kotlin源文件
       │  └─com.template.clionplugin  #包名
       │           └─ kotlin程序文件
       └─resources  #资源文件
           ├─messages
           │   └─ MyBundle.properties
           └─META-INF
                ├─plugin.xml      #插件清单
                └─pluginIcon.svg  #默认插件图标
```

## 2. 此模板中自带的功能
模板中还自带了几个小功能，分别为

|功能|现有的功能|对应源文件|位置|
|---|---|---|---|
|工程管理器监听器|监听“**打开工程**”这一事件，当打开工程的时候，**获取或创建<font color=ff0000>工程服务</font>**|MyProjectManagerListener.kt|src/main/kotlin/包名/listeners/|
|应用服务|IDE被启动时，输出一句“`applicationService`”|MyApplicationService.kt|src/main/kotlin/包名/services/|
|工程服务|该服务被启动时（由上述的监听器启动），输出一句“`Project service: `+工程名”|MyProjectService.kt|src/main/kotlin/包名/services/|

## 3. 改动

已将平台改为 CLion

修改了 `gradle.properties`
```python
platformType = CL  #将平台改为CLion（'CL'对应CLion）
platformVersion = 2021.2.2 
```
<br>
<hr>
<br><br><br><br>
<center>朴实无华的的分割线</center>
<br><br><br><br>
<hr>

> 以下为`github`工程模板原内容

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [ ] Verify the [pluginGroup](/gradle.properties), [plugin ID](/src/main/resources/META-INF/plugin.xml) and [sources package](/src/main/kotlin).
- [ ] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the Plugin ID in the above README badges.
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
This Fancy IntelliJ Platform Plugin is going to be your implementation of the brilliant ideas that you have.

This specific section is a source for the [plugin.xml](/src/main/resources/META-INF/plugin.xml) file which will be extracted by the [Gradle](/build.gradle.kts) during the build process.

To keep everything working, do not remove `<!-- ... -->` sections. 
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "ClionPlugin"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/Augtons/ClionPlugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
