package com.github.mmaltsev.intellijplugintest.startup

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class MyProjectActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
        
        // Register our custom ProgressBarUI
        javax.swing.UIManager.put("ProgressBarUI", com.github.mmaltsev.intellijplugintest.ui.MyProgressBarUI::class.java.name)
        javax.swing.UIManager.getDefaults()[com.github.mmaltsev.intellijplugintest.ui.MyProgressBarUI::class.java.name] = com.github.mmaltsev.intellijplugintest.ui.MyProgressBarUI::class.java
    }
}