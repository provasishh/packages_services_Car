/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.car.customization.tool.features.rro.systemui.systembarpresets

import android.content.Context
import android.content.om.OverlayManager
import com.android.car.customization.tool.di.MenuActionKey
import com.android.car.customization.tool.di.UIContext
import com.android.car.customization.tool.domain.menu.MenuActionReducer
import com.android.car.customization.tool.domain.menu.MenuItem
import com.android.car.customization.tool.features.rro.systemui.submenu.RroSystemUiMenu
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

/**
 * SystemBar DropDown.
 *
 * Adds a [MenuItem.DropDown] to the menu that shows preconfigured RROs that change the position
 * and style of the System Bars.
 * The module provides a [MenuItem.DropDown] and the reducer for the item.
 */
@Module
internal class SystemBarPresetsModule {

    @Provides
    @RroSystemUiMenu
    @IntoSet
    fun provideSystemBarItem(overlayManager: OverlayManager): MenuItem =
        systemBarPresetsDropDownItem(overlayManager)

    @Provides
    @IntoMap
    @MenuActionKey(SelectSystemBarPresetAction::class)
    fun provideSelectPresetsReducer(
        overlayManager: OverlayManager,
        @UIContext context: Context,
    ): MenuActionReducer = SystemBarPresetsReducer(overlayManager, context)
}