package component

import data.Product
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import react.functionalComponent
import kotlin.browser.document

interface searchProps : RProps {
    var products: MutableList<Product>
}

fun searchtFC() =
    functionalComponent<searchProps> { props ->
        div("container search"){
            h1("head"){+"Поиск"}
            var temporary:Int = -1
            div("searchContainer"){
                input(InputType.text) {
                    attrs.id = "inputSearch"
                    attrs.placeholder = "поиск по названию"
                }
                button {
                    +"Поиск"
                    attrs.id = "searchButton"
                    attrs.onClickFunction = {
                        val value = getValue("inputSearch")
                        render(document.getElementById("cnt")) {
                            div("table-wrapper") {
                            table("fl-table") {
                                thead {tr {
                                        th { +"#" }
                                        th { +"Наименование" }
                                        th { +"Количество" }
                                        th { +"Наличие" }
                                        th { +"Цена" }
                                        th { +"Скидка" }
                                        th { +"Заказать" }
                                    } }
                            props.products.forEachIndexed { i, j ->
                                if (j.name.toLowerCase().contains(value.toLowerCase())) {
                                    temporary++
                                    tbody {
                                        tr {
                                            td { +"$i" }
                                            td { +j.name }
                                            td { +"${j.quantity}" }
                                            td { +"${j.quantity != 0}" }
                                            td { +"${j.price}" }
                                            td { +"${j.discount}" }
                                            td{
                                                a("#/product$i") {
                                                        +"Заказать"
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (temporary == -1) {
                    render(document.getElementById("cnt")) {
                        +"не найдено"
                    }
                }
            }
        }
    }
    div {
        attrs.id = "cnt"
    }
}
}

fun RBuilder.search(
        product: MutableList<Product>
) = child(withDisplayName("search", searchtFC())){
    attrs.products = product
}

