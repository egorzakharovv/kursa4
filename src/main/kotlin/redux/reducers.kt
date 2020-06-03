package redux

import data.*

fun changeReducer(state: State, action: RAction) =
    when (action) {
        is MakeOrder -> State(
                decreaseQuantity(state.products,
                        action.order.productID,
                        action.order.quantity),
                addOrder(state.orders, action.order),
                state.globalDiscount
        )
        is ChangeDiscount -> State(
                state.products,
                state.orders,
                action.discount
        )
        else -> state
    }

fun decreaseQuantity(
     products:MutableList<Product>,
     productID:Int,orderQuantity:Int): MutableList<Product>{
    products[productID].quantity -= orderQuantity
    return products
}

fun addOrder(
     orders: MutableList<Order>,
     newOrder: Order): MutableList<Order>{
    val temp = orders
    temp.add(newOrder)
    return temp
}