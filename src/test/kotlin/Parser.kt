import org.junit.jupiter.api.Test

internal class Parser {
    @Test
    fun `should import csv and export json,xml and csv`() {
        main("asd asd".trim().split(" ").toTypedArray())
    }
}