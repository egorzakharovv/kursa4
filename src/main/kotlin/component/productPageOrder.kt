package component

import data.Order
import data.Product
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSpanElement
import react.*
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink
import redux.MakeOrder
import store
import kotlin.browser.document

interface ProductPageOrderProps : RProps {
}

fun productPageOrderFC(product:Product, productIndex:Int) =
    functionalComponent<ProductPageOrderProps> { props ->
        div("container1") {
            navLink("/productlist"){
                button(classes = "button first") {
                    +"<-"
                }
            }

            div("floatContainer") {
                div("imageHolder"){ img(src = "imgs/$productIndex.jpg",
                        classes = "imageContainer") { } }
                div("textHolder"){
                    h2 { +product.name }
                    p("description") {
                        +product.description
                    }
                    p ("price"){ +"Цена: ${product.price}" }
                    if(product.discount){p("DPrice"){
                        +" Цена со скидкой: ${product.price *(1f - store.getState().globalDiscount)}"}}
                    p ("quantity"){ +"Количество: ${product.quantity}" }
                }
            }
            div("container order"){
                h3{+"Заказ"}
                div{
                    input(InputType.number) {
                        attrs.id = "orderQuantityInput"
                        attrs.placeholder = "Количество"
                        attrs.defaultValue = "0"
                    }
                    span("wrongInput"){
                        attrs.id = "wrongQuantity"
                        +"<<"
                    }
                }
                div{
                    div{
                        input(InputType.text) {
                            attrs.id = "orderNameInput"
                            attrs.placeholder = "Имя"
                        }
                        span("wrongInput"){
                            attrs.id = "wrongName"
                            +"<<"
                        }
                    }
                    div{
                        input(InputType.text) {
                            attrs.id = "orderSNameInput"
                            attrs.placeholder = "Фамилия"
                        }
                        span("wrongInput"){
                            attrs.id = "wrongSName"
                            +"<<"
                        }
                    }
                }

                div{
                    input(InputType.text) {
                        attrs.id = "orderAddressInput"
                        attrs.placeholder = "почтовый адрес"
                    }
                    span("wrongInput"){
                        attrs.id = "wrongMail"
                        +"<<"
                    }
                }
                var zxc = false
                button {
                    +"Поддтвердить"
                    attrs.onClickFunction = {
                        val quantity = getValue("orderQuantityInput").toInt()
                        val name = getValue("orderNameInput")
                        val sName = getValue("orderSNameInput")
                        val address = getValue("orderAddressInput")
                        var check = Array<Boolean>(4){ false }
/*                        console.log("quantity = ${quantity}\n" +
                                "name = ${name}\n" +
                                " sMame = ${sName}\n " +
                                "address = ${address}\n")*/
                        if(quantity > product.quantity || quantity < 0){
                            (document.getElementById("wrongQuantity") as HTMLSpanElement).className = "THIS"
                            check[0] = false
                        }else{
                            (document.getElementById("wrongQuantity") as HTMLSpanElement).className = "wrongInput"
                            check[0] = true
                        }
                        if(name.length < 2){
                            (document.getElementById("wrongName") as HTMLSpanElement).className = "THIS"
                            check[1] = false
                        }else{
                            (document.getElementById("wrongName") as HTMLSpanElement).className = "wrongInput"
                            check[1] = true
                        }
                        if(sName.length < 2){
                            (document.getElementById("wrongSName") as HTMLSpanElement).className = "THIS"
                            check[2] = false
                        }else{
                            (document.getElementById("wrongSName") as HTMLSpanElement).className = "wrongInput"
                            check[2] = true
                        }
                        if(!address.contains('@')){
                            (document.getElementById("wrongMail") as HTMLSpanElement).className = "THIS"
                            check[3] = false
                        }else{
                            (document.getElementById("wrongMail") as HTMLSpanElement).className = "wrongInput"
                            check[3] = true
                        }
                        console.log(check)
                        if(check[0] && check[1] && check[2] && check[3]){
                            render(document.getElementById("cnt")){
                                val discount:Float = if(product.discount){ (1 - store.getState().globalDiscount) } else{ 1f }
                                val finalPrice:Float = (quantity * product.price) * discount
                                div { span{ +"Цена: $finalPrice" } }
                                button {
                                    +"submit"
                                    attrs.onClickFunction = {
                                        console.log("[SUBMIT]quantity = ${quantity}\nname = ${name}\n sName = ${sName}\n address = ${address}\n")
                                        store.dispatch(
                                            MakeOrder(
                                                Order(
                                                    productIndex,
                                                    quantity,
                                                    name,
                                                    sName,
                                                    address
                                                )
                                            )
                                        )
                                    }
                                }
                            }
                        }else{
                            render( document.getElementById("cnt") ){ +"" }
                        }
                    }
                }
                div { attrs.id = "cnt" }
            }
        }
    }

fun getValue(str:String):String = (document.getElementById(str) as HTMLInputElement).value

fun RBuilder.productPageOrder(
        product:Product,
        productIndex: Int
) = child(
    withDisplayName("productPageOrderFC",  productPageOrderFC(product,productIndex))
){
}
