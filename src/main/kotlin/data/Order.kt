package data

data class Order(
		var productID:Int,
		var quantity:Int,
		var userName:String,
		var userSName:String,
		var userEMail:String
){
	override fun toString(): String  = this.productID.toString()
}

var orders:MutableList<Order> = mutableListOf(
		Order(
			0,
			2,
			"Сергей",
			"Брин",
			"sergeybrrin@gmail.ru"
		),
		Order(
			0,
			0,
			"[EXAMPLE_NAME1]",
			"[EXAMPLE_SNAME1]",
			"[@]"
		)
)
