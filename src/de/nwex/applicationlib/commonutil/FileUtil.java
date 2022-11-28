package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.commonutil.subclasses.FileInputIterator;
import de.nwex.applicationlib.commonutil.subclasses.FileOutputIterator;
import de.nwex.applicationlib.commonutil.subclasses.IteratorOutputRegistry;
import de.nwex.applicationlib.exceptions.Exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to interact with the file system
 *
 * @author networkException
 * @see #getTextContent(File)
 * @see #getTextContent(String)
 * @see #fileInputIterator(File, FileInputIterator, IteratorOutputRegistry)
 * @see #fileInputIterator(String, FileInputIterator, IteratorOutputRegistry)
 * @see #fileOutputIterator(File, List, boolean)
 * @see #fileOutputIterator(String, List, boolean)
 * @see #setTextContent(File, String)
 * @see #setTextContent(String, String)
 * @see #getAbsolutePath(String)
 */
public class FileUtil
{
    /**
     * Returns the full content of a text file, separated by {@link System#lineSeparator()}
     *
     * @param file The file to read from
     * @return The full content of the given text file
     * @see #getTextContent(String)
     */
    public static String getTextContent(File file)
    {
        String content = "";

        IteratorOutputRegistry outputRegistry = new IteratorOutputRegistry<String>();

        fileInputIterator(file, new FileInputIterator()
        {
            @Override
            public void action(String line, int lineNumber, IteratorOutputRegistry iteratorOutputRegistry)
            {
                iteratorOutputRegistry.store(line + System.lineSeparator());
            }
        }, outputRegistry);

        for(String string : (List<String>) outputRegistry.getAll())
        {
            content = content.concat(string);
        }

        return content;
    }

    /**
     * Returns the full content of a text file, separated by {@link System#lineSeparator()}
     *
     * @param filepath The path and name of the file to read from
     * @return The full content of the given text file
     * @see #getTextContent(File)
     */
    public static String getTextContent(String filepath)
    {
        return getTextContent(new File(filepath));
    }

    /**
     * Iterates over a given file and executes {@link FileInputIterator#action(String, int, IteratorOutputRegistry)}
     *
     * @param file The given file to be iterated over
     * @param iterator The implemented iterator to be called
     * @param iteratorOutputRegistry Stores output from the iterator
     *
     * @see #fileInputIterator(String, FileInputIterator, IteratorOutputRegistry)
     */
    public static void fileInputIterator(File file, FileInputIterator iterator, IteratorOutputRegistry iteratorOutputRegistry)
    {
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count = 0;

            while((line = br.readLine()) != null)
            {
                iterator.action(line, count, iteratorOutputRegistry);

                count++;
            }

            fr.close();
            br.close();
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }
    }

    /**
     * Iterates over a given file and executes {@link FileInputIterator#action(String, int, IteratorOutputRegistry)}
     *
     * @param filepath The given path and name of the file to be iterated over
     * @param iterator The implemented iterator to be called
     * @param iteratorOutputRegistry Stores output from the iterator
     *
     * @see #fileInputIterator(File, FileInputIterator, IteratorOutputRegistry)
     */
    public static void fileInputIterator(String filepath, FileInputIterator iterator, IteratorOutputRegistry iteratorOutputRegistry)
    {
        fileInputIterator(new File(filepath), iterator, iteratorOutputRegistry);
    }

    /**
     * Iterates over the given iterators and appends the return value to a given file
     *
     * @param file The given file to append to
     * @param iterators The implemented list of {@link FileOutputIterator}
     * @param overwrite Toggles to overwrite the files content
     * @see #fileOutputIterator(String, List, boolean)
     */
    public static void fileOutputIterator(File file, List<FileOutputIterator> iterators, boolean overwrite)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(file);
            int count = 0;

            if(overwrite)
            {
                fileWriter.write("");
            }

            for(FileOutputIterator iterator : iterators)
            {
                fileWriter.append(iterator.action(count));

                count++;
            }

            fileWriter.close();
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }
    }

    /**
     * Iterates over the given iterators and appends the return value to a given file
     *
     * @param filepath The given path and name of the file to append to
     * @param iterators The implemented list of {@link FileOutputIterator}
     * @param overwrite Toggles to overwrite the files content
     * @see #fileOutputIterator(String, List, boolean)
     */
    public static void fileOutputIterator(String filepath, List<FileOutputIterator> iterators, boolean overwrite)
    {
        fileOutputIterator(new File(filepath), iterators, overwrite);
    }

    /**
     * Overwrites and appends the given content to a file
     *
     * @param file The file to write to
     * @param content The content to write to the file
     * @see #setTextContent(String, String)
     */
    public static void setTextContent(File file, String content)
    {
        fileOutputIterator(file, new ArrayList<>(Collections.singleton(new FileOutputIterator()
        {
            @Override
            public String action(int lineNumber)
            {
                return content;
            }
        })), true);
    }

    /**
     * Overwrites and appends the given content to a file
     *
     * @param filepath The path and name of the file to write to
     * @param content The content to write to the file
     * @see #setTextContent(File, String)
     */
    public static void setTextContent(String filepath, String content)
    {
        setTextContent(new File(filepath), content);
    }

    /**
     * Returns the running directory + {@link File#separator} + the specified filename + {@link File#separator}
     *
     * @param fileName The specified fileName
     * @return The path as a string
     */
    public static String getAbsolutePath(String fileName)
    {
        return System.getProperty("user.dir") + File.separator + fileName + File.separator;
    }
}
