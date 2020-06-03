package component

import react.*
import react.dom.*
import data.*
import hoc.withDisplayName
import react.router.dom.navLink


interface ProductListProps : RProps {
}

fun productListFC(products:MutableList<Product>) =
    functionalComponent<ProductListProps> {props ->
	    div("container"){
		    h1("head"){+"Товары"}
		    div("table-wrapper") {
			    table("fl-table") {
				    thead {
					    tr {
						    th { +"#" }
						    th { +"Наименование" }
						    th { +"Количество" }
						    th { +"Наличие" }
						    th { +"Цена" }
						    th { +"Скидка" }
						    th { +"Заказать" }
					    }
				    }
				    tbody {
						products.mapIndexed{ productIndex, product ->
								tr {
									td { +"$productIndex" }
									td { +product.name }
									td { +"${product.quantity}" }
									td { +"${product.quantity != 0}" }
									td { +"${product.price}" }
									td { +"${product.discount}" }
									td { navLink("/product$productIndex") { +"Заказать" } }
								}
							}
						}
			        }
			    }
            }
		}

fun RBuilder.productList(
		products: MutableList<Product>
) = child(withDisplayName("productList", productListFC(products))){
}
