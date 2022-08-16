@file:Suppress("nothing_to_inline")

package com.github.augtons.serialberry.utils

import com.intellij.openapi.Disposable
import com.intellij.openapi.ui.ComponentValidator
import com.intellij.openapi.ui.ValidationInfo
import java.util.*
import java.util.function.Supplier
import javax.swing.JComponent

inline fun JComponent.installValidator(disposable: Disposable, validator: Supplier<ValidationInfo?>) =
    ComponentValidator(disposable).withValidator(validator).installOn(this)


val JComponent.validator: Optional<ComponentValidator>
    get() = ComponentValidator.getInstance(this)