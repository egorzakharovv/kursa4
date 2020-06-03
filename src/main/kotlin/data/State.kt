package data

class State (
		var products:MutableList<Product>,
		var orders:MutableList<Order>,
		var globalDiscount:Float
)