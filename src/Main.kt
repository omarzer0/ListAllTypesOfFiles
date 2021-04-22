import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.util.*


val imagesExtensions = listOf("jpg", "jpeg")
val audioExtensions = listOf("mp3", "m4a")
val videoExtensions = listOf("mp4", "mkv")

val allExtensions = imagesExtensions + audioExtensions + videoExtensions

val filePaths = mutableListOf<File>()
val reports = mutableListOf<String>()


fun main(args: Array<String>) {
    print("Please enter the input file path: ")
    val inputFile = Scanner(System.`in`).nextLine()
    print("please enter the output file path: ")
    val outputFile = Scanner(System.`in`).nextLine()
    val extensionsChoice = chooseTypeOfFiles()
    showFilesContent(File(inputFile).listFiles(), extensionsChoice)
    copyFilesToDestination(filePaths, File(outputFile))

    when {
        reports.isEmpty() -> print("No error happen :)")
        else -> reports.forEach { println(it) }
    }
}

fun showFilesContent(files: Array<File>?, extensions: List<String>) {
    if (files != null) {
        for (file in files) {
            if (file.isDirectory) {
                showFilesContent(file.listFiles(), extensions)
            } else {
                if (extensions.contains(file.extension.toLowerCase()))
                    filePaths.add(File(file.absolutePath))
            }
        }
    } else println("No Files")
}

fun copyFilesToDestination(filesToBeCopiedPaths: List<File>, outputFile: File) {
    val outputFileWithMilSec = File(outputFile.absolutePath.plus("/output${System.currentTimeMillis()}"))
    if (!outputFileWithMilSec.exists()) {
        outputFileWithMilSec.mkdirs()
    }

    for (file in filesToBeCopiedPaths) {
        try {
            Files.copy(
                file.toPath(),
                File("${outputFileWithMilSec.absolutePath}/${file.name}").toPath(), REPLACE_EXISTING
            )
            println("${outputFileWithMilSec.absolutePath}/${file.name}")
        } catch (e: Exception) {
            reports.add(e.message.toString())
        }
    }
}


fun chooseTypeOfFiles(): List<String> {
    print(
        "please choose the number of the file type you want to get: \n"
                + "1 for images\n"
                + "2 for audios\n"
                + "3 for videos\n"
                + "4 for all types\n"
                + ": "
    )
    return when (Scanner(System.`in`).next()) {
        "1" -> imagesExtensions
        "2" -> audioExtensions
        "3" -> videoExtensions
        "4" -> allExtensions
        else -> {
            println("please choose valid number ")
            chooseTypeOfFiles()
        }
    }
}
