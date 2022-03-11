import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://youtrack.jetbrains.com/issue/KTOR-453 is resolved
var shoppingListUrl = endpoint + ShoppingListItem.path

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer()}
}

suspend fun getShoppingList(): List<ShoppingListItem> {
    return jsonClient.get(shoppingListUrl)
}

suspend fun addShoppingListItem(shoppingListItem: ShoppingListItem) {
    jsonClient.post<Unit>(shoppingListUrl) {
        contentType(ContentType.Application.Json)
        body = shoppingListItem
    }
}

suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
    jsonClient.delete<Unit>(shoppingListUrl + "/${shoppingListItem.id}")
}