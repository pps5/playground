package io.github.pps5.popkotlin

import io.github.pps5.popkotlin.page.Page

inline fun <T: Page> T.assert(func: () -> Unit): T {
    func()
    return this
}
