import extensions.asArgs
import extensions.hasGreaterSizeThan
import extensions.isSizeOf
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

internal class Parser {

    @BeforeEach
    @AfterEach
    fun setUp() {
        File("./src/test/resources/exported").deleteRecursively()
    }

    //files for data
    private val exportedResiduosJsonFile = File("./src/test/resources/exported/residuos.json")
    private val exportedResiduosXmlFile = File("./src/test/resources/exported/residuos.xml")
    private val exportedResiduosCsvFile = File("./src/test/resources/exported/residuos.csv")

    private val exportedContenedoresJsonFile = File("./src/test/resources/exported/contenedores.json")
    private val exportedContenedoresXmlFile = File("./src/test/resources/exported/contenedores.xml")
    private val exportedContenedoresCsvFile = File("./src/test/resources/exported/contenedores.csv")

    //bitacora
    private val exportedBitacoraXmlFile = File("./src/test/resources/exported/bitacora.xml")

    //files for data
    private val residuosJsonFile = File("./src/test/resources/data/residuos.json")
    private val residuosXmlFile = File("./src/test/resources/data/residuos.xml")
    private val residuosCsvFile = File("./src/test/resources/data/residuos.csv")

    private val contenedoresJsonFile = File("./src/test/resources/data/contenedores.json")
    private val contenedoresXmlFile = File("./src/test/resources/data/contenedores.xml")
    private val contenedoresCsvFile = File("./src/test/resources/data/contenedores.csv")

    @Test
    fun `should import csv and export json,xml and csv`() {
        main("parser ./src/test/resources/data ./src/test/resources/exported".asArgs())

        assert(exportedContenedoresCsvFile isSizeOf contenedoresCsvFile)
        assert(exportedContenedoresJsonFile isSizeOf contenedoresJsonFile)
        assert(exportedContenedoresXmlFile isSizeOf contenedoresXmlFile)

        assert(exportedResiduosCsvFile isSizeOf residuosCsvFile)
        assert(exportedResiduosJsonFile isSizeOf residuosJsonFile)
        assert(exportedResiduosXmlFile isSizeOf residuosXmlFile)

        assert(exportedBitacoraXmlFile hasGreaterSizeThan 0)

    }

    @Test
    fun `should import json and export json,xml and csv`() {
        main(
            "parser -residuos=residuos.json -contenedores=contenedores.json ./src/test/resources/data ./src/test/resources/exported".asArgs()
        )

        assert(exportedContenedoresCsvFile isSizeOf contenedoresCsvFile)
        assert(exportedContenedoresJsonFile isSizeOf contenedoresJsonFile)
        assert(exportedContenedoresXmlFile isSizeOf contenedoresXmlFile)

        assert(exportedResiduosCsvFile isSizeOf residuosCsvFile)
        assert(exportedResiduosJsonFile isSizeOf residuosJsonFile)
        assert(exportedResiduosXmlFile isSizeOf residuosXmlFile)

        assert(exportedBitacoraXmlFile hasGreaterSizeThan 0)
    }

    @Test
    fun `should import xml and export json,xml and csv`() {
        main(
            "parser -residuos=residuos.xml -contenedores=contenedores.xml ./src/test/resources/data ./src/test/resources/exported".asArgs()
        )

        assert(exportedContenedoresCsvFile isSizeOf contenedoresCsvFile)
        assert(exportedContenedoresJsonFile isSizeOf contenedoresJsonFile)
        assert(exportedContenedoresXmlFile isSizeOf contenedoresXmlFile)

        assert(exportedResiduosCsvFile isSizeOf residuosCsvFile)
        assert(exportedResiduosJsonFile isSizeOf residuosJsonFile)
        assert(exportedResiduosXmlFile isSizeOf residuosXmlFile)

        assert(exportedBitacoraXmlFile hasGreaterSizeThan 0)
    }

    @Test
    fun `should not export`() {
        main(
            "parser ./src/test/resources/wrongData ./src/test/resources/exported".asArgs()
        )

        assert(residuosJsonFile isSizeOf File("./src/test/resources/data/residuos.json"))
        assert(residuosXmlFile isSizeOf File("./src/test/resources/data/residuos.xml"))
        assert(residuosCsvFile isSizeOf File("./src/test/resources/data/residuos.csv"))

        assert(exportedBitacoraXmlFile hasGreaterSizeThan 0)
    }


}