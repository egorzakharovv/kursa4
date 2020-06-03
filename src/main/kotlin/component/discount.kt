package component

import data.Product
import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.*
import react.functionalComponent
import react.router.dom.navLink
import redux.ChangeDiscount
import store
import kotlin.browser.document
import kotlin.js.Date

interface AdminProps : RProps {
	var products: MutableList<Product>
}

fun adminFC() =
		functionalComponent<AdminProps> { props ->
			div("container admin") {
				h1("head") { +"discountPanel" }
				div("adminContainer"){
					input(InputType.number) {
						attrs.id = "discountInput"
						attrs.placeholder = "Введите скидку в процентах"
					}
					button {
						+"Submit"
						attrs.onClickFunction = {
							var newDiscount = (document.getElementById("discountInput")
									as HTMLInputElement).value.toInt().toFloat()
							newDiscount /= 100f
							console.log("newDiscount = $newDiscount")
							store.dispatch(ChangeDiscount(newDiscount))
						}
					}
				}
			}
		}

fun RBuilder.admin(
		product: MutableList<Product>
) = child(withDisplayName("AdminFC", adminFC())){
	attrs.products = product
}

