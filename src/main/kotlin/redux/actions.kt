package redux

import data.Order

class MakeOrder(var order: Order) : RAction

class ChangeDiscount(val discount:Float) : RAction