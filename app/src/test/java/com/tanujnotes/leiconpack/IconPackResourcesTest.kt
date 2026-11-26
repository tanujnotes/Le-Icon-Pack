package com.tanujnotes.leiconpack

import org.junit.Assert.assertTrue
import org.junit.Test
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Checks that every icon the launcher-facing XML files point to actually exists. A missing or
 * misspelled drawable doesn't fail the build: the app just silently gets no icon.
 */
class IconPackResourcesTest {

    // Unit tests run with the module directory (app/) as the working directory.
    private val main = File("src/main")

    private val drawables: Set<String> = main.resolve("res")
        .listFiles { file -> file.isDirectory && file.name.startsWith("drawable") }!!
        .flatMap { dir -> dir.listFiles()!!.map { it.nameWithoutExtension } }
        .toSet()

    private fun attributeValues(path: String, tag: String, attribute: String): List<String> {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(main.resolve(path))
        val nodes = document.getElementsByTagName(tag)
        return (0 until nodes.length).map { (nodes.item(it) as Element).getAttribute(attribute) }
    }

    private fun assertAllExist(source: String, names: List<String>) {
        assertTrue("$source references no icons", names.isNotEmpty())
        val missing = names.filterNot { it in drawables }.distinct()
        assertTrue("$source references missing drawables: $missing", missing.isEmpty())
    }

    @Test
    fun appfilterDrawablesExist() {
        assertAllExist(
            "res/xml/appfilter.xml",
            attributeValues("res/xml/appfilter.xml", "item", "drawable")
        )
    }

    @Test
    fun appfilterSpecialImagesExist() {
        for (tag in listOf("iconback", "iconmask", "iconupon")) {
            val images = attributeValues("res/xml/appfilter.xml", tag, "img1").filter { it.isNotEmpty() }
            val missing = images.filterNot { it in drawables }
            assertTrue("appfilter <$tag> references missing drawables: $missing", missing.isEmpty())
        }
    }

    @Test
    fun themeResourcesImagesExist() {
        assertAllExist(
            "res/xml/theme_resources.xml",
            attributeValues("res/xml/theme_resources.xml", "AppIcon", "image")
        )
    }

    @Test
    fun iconPickerDrawablesExist() {
        assertAllExist(
            "assets/drawable.xml",
            attributeValues("assets/drawable.xml", "item", "drawable")
        )
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(main.resolve("res/values/iconpack.xml"))
        val items = document.getElementsByTagName("item")
        assertAllExist(
            "res/values/iconpack.xml",
            (0 until items.length).map { items.item(it).textContent.trim() }
        )
    }

    @Test
    fun everyIconHasDayAndNightVersions() {
        fun names(dir: String) = main.resolve("res/$dir").listFiles()!!
            .map { it.nameWithoutExtension }.toSet()
        val day = names("drawable-nodpi")
        val night = names("drawable-night-nodpi")
        assertTrue("Icons without a night version: ${day - night}", (day - night).isEmpty())
        assertTrue("Icons without a day version: ${night - day}", (night - day).isEmpty())
    }

    @Test
    fun componentsAreWellFormed() {
        val components = attributeValues("res/xml/appfilter.xml", "item", "component") +
                attributeValues("res/xml/theme_resources.xml", "AppIcon", "component")
        val malformed = components.filterNot { it.startsWith(":") || COMPONENT.matches(it) }
        assertTrue("Malformed components: $malformed", malformed.isEmpty())
    }

    private companion object {
        // Class names may be activity-alias names, which can contain characters like '-'.
        val COMPONENT = Regex("""ComponentInfo\{[\w.]+/[^/{}\s]+}""")
    }
}
