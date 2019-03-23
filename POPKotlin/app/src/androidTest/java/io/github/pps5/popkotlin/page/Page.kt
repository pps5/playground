package io.github.pps5.popkotlin.page

open class Page {

    /**
     * アクションを実行し、ラベルを送信する
     */
    inline fun <T : Page> T.action(label: Label, func: T.() -> Unit): T = apply {
        func()
        label.send()
    }

    /**
     * 次のページを指定してアクションを行う
     */
    inline fun <T : Page, NEXT : Page> T.actionTo(nextPage: NEXT, label: Label, func: T.() -> Unit): NEXT {
        func()
        label.send()
        return nextPage
    }

    inline fun <T: Page> T.assert() {

    }

    /**
     * AppCenterでラベル送信処理を行う（今回はstdoutに出力）
     */
    class Label private constructor(private val label: String?) {
        companion object {
            fun withLabel(name: String) = Label(name)
            fun withoutLabel() = Label(null)
        }

        fun send() {
            if (!label.isNullOrEmpty()) {
                println(label)
            }
        }
    }

}
