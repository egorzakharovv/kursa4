package component

import data.Order
import data.Product
import hoc.withDisplayName
import react.*
import react.dom.*
import react.functionalComponent
import store

interface OrderPageProps : RProps {
    var orders:MutableList<Order>
    var products:MutableList<Product>
}

fun orderPageFC() =
    functionalComponent<OrderPageProps> { props ->
        div("container") {
            h1("head"){+"Заказы"}
            div("table-wrapper"){
                table("fl-table"){
                    thead{
                        tr {
                            th { +"#" }
                            th { +"ID продукта" }
                            th { +"Количество" }
                            th { +"Почта" }
                            th { +"Имя" }
                            th { +"Фамилия" }
                            th { +"Стоимость заказа" }
                        }
                    }
                    tbody {
                        props.orders.forEachIndexed { orderIndex, order ->
                            val d  = if(props.products[order.productID].discount){1f-store.getState().globalDiscount}
                            else{1f}
                            tr {
                                td {+"$orderIndex" }
                                td {+"${order.productID}"}
                                td {+"${order.quantity}" }
                                td {+order.userEMail }
                                td {+order.userName}
                                td {+order.userSName }
                                td{ +"${(order.quantity * props.products[order.productID].price)*d}" }
                            }
                        }
                    }
                }
            }
        }
    }

fun RBuilder.orderPage(
    orders:MutableList<Order>,
    products:MutableList<Product>
) = child(
    withDisplayName("OrderPageFC",  orderPageFC())
){
    attrs.orders = orders
    attrs.products = products
}
