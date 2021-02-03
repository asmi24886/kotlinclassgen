import wu.seal.jsontokotlin.library.JsonToKotlinBuilder
import wu.seal.jsontokotlin.model.DefaultValueStrategy
import wu.seal.jsontokotlin.model.PropertyTypeStrategy
import java.io.File
import java.nio.file.Paths

fun main(args: Array<String>) {

    val packageName     = "com.my.pkg" //can optimize later for edge cases where no package name is provided
    val className       = "DemoDataClass"

    val projectDir      = Paths.get("./").toAbsolutePath().parent.toAbsolutePath().toString()
    val kotlinDir       = "${projectDir + File.separator}src${File.separator}main${File.separator}kotlin"
    val resourceDir     = "${projectDir + File.separator}src${File.separator}main${File.separator}resources"
    val containingDir   = kotlinDir + File.separator + packageName.split(".").joinToString(File.separator)

    File(containingDir).mkdirs()

    val inputSchemaFilePath  =   "${resourceDir + File.separator}DemoDataClassSchema.json"
    val jsonStr              =     File(inputSchemaFilePath).readText(Charsets.UTF_8)

    val output = JsonToKotlinBuilder()
        .setPackageName(packageName)
        .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot)
        .setDefaultValueStrategy(DefaultValueStrategy.AvoidNull)
        .enableComments(true)
        .enableMapType(true)
        .setIndent(4)
        .build(jsonStr, className)

    //println(output)

    println(containingDir)

    val outputFile = File(containingDir + File.separator + className + ".kt")
    outputFile.createNewFile()
    outputFile.writeText(output, Charsets.UTF_8)
}