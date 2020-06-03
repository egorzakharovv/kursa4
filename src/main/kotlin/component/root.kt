package component

import data.*
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.*
import react.router.dom.*
import redux.*
import react.RBuilder
import store

interface RootProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}
fun rootFC() =
    functionalComponent<RootProps> {
        input(type = InputType.checkBox) { attrs.id = "side-checkbox"; }
        div("side-panel") {
            label("side-button-2") {
                attrs.htmlFor = "side-checkbox";
                +"+"
            }
            div("side-title"){ navLink(""){ +"GLB" } }
            ul("widget-list") {
                li { navLink("/productlist"){ +"products"} }
                li { navLink("/search") { +"search" } }
                li { navLink("/orders") { +"orders" } }
                li { navLink("/admin") { +"admin" } }
            }
        }
        div("side-button-1-wr") {
            label("side-button-1"){
                div("side-b side-open"){+"<<>>"}
                div("side-b side-close"){+">><<"}
                attrs.htmlFor = "side-checkbox"
            }
        }
        switch{
            store.getState().products.mapIndexed { productsIndex, products ->
                route("/product$productsIndex",
                    exact = true,
                    render = {
                        productPageOrder(products, productsIndex)
                    }
                )
            }
            route("/orders",
                exact = true,
                render = {
                    orderPage(store.getState().orders, store.getState().products )
                }
            )
            route("/productlist",
                exact = true,
                render = {
                    productList(store.getState().products)
                }
            )
            route("/admin",
                exact = true,
                render = {
                    admin(store.getState().products)
            }
            )
            route("/search",
                exact = true,
                render = {
                    search(store.getState().products)
                }
            )
        }
        }


fun RBuilder.root(store: Store<State, RAction, WrapperAction>) =
    child(withDisplayName("Root", rootFC())){
        attrs.store = store
    }






