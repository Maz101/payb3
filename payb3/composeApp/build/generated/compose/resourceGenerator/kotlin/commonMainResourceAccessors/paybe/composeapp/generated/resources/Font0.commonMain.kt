@file:OptIn(InternalResourceApi::class)

package paybe.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/paybe.composeapp.generated.resources/"

internal val Res.font.inter_24pt_regular: FontResource by lazy {
      FontResource("font:inter_24pt_regular", setOf(
        ResourceItem(setOf(), "${MD}font/inter_24pt_regular.ttf", -1, -1),
      ))
    }

internal val Res.font.inter_medium: FontResource by lazy {
      FontResource("font:inter_medium", setOf(
        ResourceItem(setOf(), "${MD}font/inter_medium.ttf", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainFont0Resources(map: MutableMap<String, FontResource>) {
  map.put("inter_24pt_regular", Res.font.inter_24pt_regular)
  map.put("inter_medium", Res.font.inter_medium)
}
