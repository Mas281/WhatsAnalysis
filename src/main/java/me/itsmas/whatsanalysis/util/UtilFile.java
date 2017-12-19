package me.itsmas.whatsanalysis.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * File utilities
 */
public final class UtilFile
{
    private UtilFile() {}

    /**
     * Reads the lines from a {@link File}
     *
     * @param file The file
     *
     * @return The file lines
     */
    public static List<String> readLines(File file)
    {
        assert file.exists() : "File does not exist";

        try
        {
            return Files.readAllLines(file.toPath());
        }
        catch (IOException ex)
        {
            return null;
        }
    }
}
