package de.nwex.applicationlib.commonutil;

import de.nwex.applicationlib.exceptions.Exceptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for network related actions
 *
 * @author networkException
 *
 * @see #getURLSource(String) (String)
 * @see #getURLSource(URL) (URL)
 */
public class NetUtil
{
    /**
     * Returns the HTML source of a given URL object
     *
     * @param url the given url
     * @return the source as a string
     *
     * @see #getURLSource(String)
     */
    public static String getURLSource(URL url)
    {
        try
        {
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            return FormatUtil.streamToString(urlConnection.getInputStream());
        }
        catch(Exception e)
        {
            Exceptions.handle(e);
        }

        return null;
    }

    /**
     * Returns the HTML source of a given url as text
     *
     * @param url the given url as a string
     * @return the source as a string
     *
     * @see #getURLSource(URL)
     */
    public static String getURLSource(String url)
    {
        try
        {
            return getURLSource(new URL(url));
        }
        catch(MalformedURLException e)
        {
            Exceptions.handle(e);
        }

        return null;
    }
}
