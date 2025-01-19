package de.rechergg.ktimer

import com.github.ajalt.mordant.animation.animation
import com.github.ajalt.mordant.animation.asRefreshable
import com.github.ajalt.mordant.rendering.BorderType
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.rendering.TextStyles.*
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.rendering.Widget
import com.github.ajalt.mordant.table.table
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun main() = runBlocking {
    val terminal = Terminal()
    print("\u001B[?25l") // hide courser

    Runtime.getRuntime().addShutdownHook(Thread {
        print("\u001B[?25h")  // Ensure cursor is visible again on exit
    })

    val animation = terminal.animation<Unit> {
        createWorldClockTable()
    }.asRefreshable(fps = 1)

    animation.refresh()
    while (true) {
        delay(1000)
        animation.refresh()
    }
}

data class CityInfo(
    val name: String,
    val zoneId: String,
    val continent: String
)

val cities = listOf(
    CityInfo("Berlin", "Europe/Berlin", "Europe"),
    CityInfo("New York", "America/New_York", "North America"),
    CityInfo("Tokyo", "Asia/Tokyo", "Asia"),
    CityInfo("Sydney", "Australia/Sydney", "Australia"),
    CityInfo("Moscow", "Europe/Moscow", "Europe"),
    CityInfo("Dubai", "Asia/Dubai", "Asia"),
    CityInfo("Los Angeles", "America/Los_Angeles", "North America"),
    CityInfo("Cape Town", "Africa/Johannesburg", "Africa"),
    CityInfo("Buenos Aires", "America/Argentina/Buenos_Aires", "South America"),
    CityInfo("Delhi", "Asia/Kolkata", "Asia"),
    CityInfo("London", "Europe/London", "Europe"),
    CityInfo("Chicago", "America/Chicago", "North America"),
    CityInfo("Beijing", "Asia/Shanghai", "Asia"),
    CityInfo("Rio de Janeiro", "America/Sao_Paulo", "South America")
).sortedBy { ZoneId.of(it.zoneId).rules.getOffset(ZonedDateTime.now().toLocalDateTime()).totalSeconds }

fun createWorldClockTable(): Widget {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val style = (gray + bold)

    return table {
        borderType = BorderType.ROUNDED
        borderStyle = style

        captionTop("World clock")
        header { row("🌍 City", "🌐 Continent", "⏰ Time", "📅 Date", "🕰️ Time Zone") }
        body {
            cities.forEach { city ->
                val now = ZonedDateTime.now(ZoneId.of(city.zoneId))
                row(
                    blue(city.name),
                    brightBlue(city.continent),
                    magenta(now.format(timeFormatter)),
                    brightMagenta(now.format(dateFormatter)),
                    green(city.zoneId),
                )
            }
        }

        captionBottom(dim("Last refresh ${ZonedDateTime.now(ZoneId.of("UTC")).format(timeFormatter)} — by RECHERGG"))
    }
}