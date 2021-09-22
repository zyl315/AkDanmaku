/*
 * The MIT License (MIT)
 *
 * Copyright 2021 Kwai, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.kuaishou.akdanmaku.library.layout.locator

import com.kuaishou.akdanmaku.library.DanmakuConfig
import com.kuaishou.akdanmaku.library.data.DanmakuItem
import com.kuaishou.akdanmaku.library.data.ItemState
import com.kuaishou.akdanmaku.library.layout.retainer.DanmakuRetainer
import com.kuaishou.akdanmaku.library.ui.DanmakuDisplayer

internal class RollingLocator : DanmakuRetainer.Locator {
  override fun layout(
    item: DanmakuItem,
    currentTimeMills: Long,
    displayer: DanmakuDisplayer,
    config: DanmakuConfig
  ) {
    val drawState = item.drawState
    if (item.state >= ItemState.Measured) {
      val deltaTime = (currentTimeMills - item.timePosition).toFloat()
      // FIXME optimize constraint pre calculate
      val x =
        displayer.width - (deltaTime / config.rollingDurationMs) * (displayer.width + drawState.width)
      drawState.positionX = x
      drawState.visibility = true
    } else {
      drawState.visibility = false
    }
  }
}
