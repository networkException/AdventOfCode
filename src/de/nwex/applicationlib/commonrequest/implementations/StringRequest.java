package de.nwex.applicationlib.commonrequest.implementations;

import de.nwex.applicationlib.commonrequest.HandleableRequest;
import de.nwex.applicationlib.commonrequest.Request;
import de.nwex.applicationlib.commonrequest.RequestRegistry;
import de.nwex.applicationlib.commonrequest.Response;
import de.nwex.applicationlib.commonutil.FormatUtil;
import de.nwex.applicationlib.exceptions.RequestTokenException;
import de.nwex.applicationlib.exceptions.WrongHandableRequestTypeException;
import de.nwex.applicationlib.exceptions.WrongResponseTypeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class which extends {@link Request Request}. Can be registered into a {@link RequestRegistry RequestRegistry}
 *
 * @author networkException
 *
 * @see #StringRequest(String, StringResponse)
 * @see #handle(HandleableRequest)
 * @see #matches(HandleableRequest)
 *
 * @see #requestPatternTokens
 * @see #response
 */
public class StringRequest extends Request
{
    /**
     * The tokens defined by the String pattern given in {@link #StringRequest(String, StringResponse)} ({@code Arrays.asList(requestPattern.split(" "));})
     */
    private List<String> requestPatternTokens;

    /**
     * The response given in {@link #StringRequest(String, StringResponse)}
     */
    private StringResponse response;

    /**
     * The constructor accepting arguments to register the request
     *
     * @param requestPattern a string, which defines if strings are matching this request and what the response will be:<br><br>
     *
     *                       The pattern is space separated. Four arguments '#string', '#int', '#boolean' and '#regex' can be used to mark parameters in the pattern.<br>
     *                       Parameters will ignore if the given input at the index is the same as the pattern. The parameter will, depending on its type add an argument to a list.<br>
     *                       This argument can be used in the response.<br><br>
     *
     *                       The types of arguments correspond to the following methods in {@link FormatUtil FormatUtil}:<br><br>
     *
     *                              - '#string' {@link FormatUtil#isText(String) isText(String)}<br>
     *                              - '#int' {@link FormatUtil#isNumeric(String) isNumeric(String)}<br>
     *                              - '#boolean' {@link FormatUtil#isBoolean(String) isBoolean(String)}<br>
     *                              - '#regex' {@link FormatUtil#matchesRegex(String, String) matchesRegex(String, String)}<br><br>
     *
     *                       For a more descriptive syntax, a variable name can be added to an argument by using () and naming it inside<br><br>
     *
     *                       ----- !the name cannot contain any spaces!<br><br>
     *
     *                       Example:<br><br>
     *
     *                              - '#string(name)'<br><br>
     *
     *                       ----- Spaces in the arguments of an {@link StringHandleableRequest} have to be escaped using '' to put the argument in<br><br>
     *
     *                       ----- The '#regex' has to be used in a special way:<br><br>
     *
     *                              - '#regex[regularExpression]'<br><br>
     *
     *                              or<br><br>
     *
     *                              - '#regex[regularExpression](name)'<br><br>
     *
     *                       Spaces in the regular expression have to be escaped using the {@link FormatUtil#spaceEscape spaceEscape}<br><br>
     *
     * @param response a class that has to be implemented, containing the processing code to respond to the request
     */
    public StringRequest(String requestPattern, StringResponse response)
    {
        requestPatternTokens = Arrays.asList(requestPattern.split("\\s(?=(?:[^\\']*\\'[^\\']*\\')*[^\\']*$)"));

        this.response = response;
    }

    /**
     * Called when the {@link #requestPatternTokens} are {@link #matches(HandleableRequest) matching} the given request
     *
     * Parses the tokens and calls the implemented {@link #response}. Arguments specified in the requestPattern ({@link #StringRequest(String, StringResponse)}) can be accessed by calling {@code getArgs.get(index)}.
     * Arguments will have an increasing index starting from the first argument on the left
     *
     *          Example:
     *
     *          pattern: '#string(message) #string(planet)'
     *          input: 'hello earth'
     *
     *          response code: {@code return "Message: '" + getArgs().get(0) + "' was send to planet '" + getArgs().get(1) + "'.";}
     *
     *          response output: "Message: 'hello' was send to planet 'earth'."
     *
     *
     * @param incomingRequest the request which contains the input
     *
     * @return the response to the given incomingRequest
     */
    @Override
    public Response handle(HandleableRequest incomingRequest)
    {
        if(incomingRequest instanceof StringHandleableRequest)
        {
            StringHandleableRequest request = (StringHandleableRequest) incomingRequest;

            List<String> requestTokens = Arrays.asList(request.getRequest().split("\\s(?=(?:[^\\']*\\'[^\\']*\\')*[^\\']*$)"));
            List<String> parsedArguments = new ArrayList<>();

            boolean tokensFullyParsed = true;

            if(requestPatternTokens.size() == requestTokens.size())
            {
                for(int i = 0; i < requestTokens.size(); i++)
                {
                    if(requestPatternTokens.get(i).equalsIgnoreCase(requestTokens.get(i)))
                    {
                        continue;
                    }
                    else if(requestPatternTokens.get(i).startsWith("#string"))
                    {
                        if(FormatUtil.isText(requestTokens.get(i)))
                        {
                            if(requestTokens.get(i).startsWith("'") && requestTokens.get(i).endsWith("'"))
                            {
                                requestTokens.set(i, requestTokens.get(i).substring(1, requestTokens.get(i).length() - 1));
                            }

                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching text, which its not");

                            tokensFullyParsed = false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#int"))
                    {
                        if(FormatUtil.isNumeric(requestTokens.get(i)))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching a number, which its not");

                            tokensFullyParsed = false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#boolean"))
                    {
                        if(FormatUtil.isBoolean(requestTokens.get(i)))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching true or false, which its not");

                            tokensFullyParsed = false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#regex"))
                    {
                        if(FormatUtil.matchesRegex(requestTokens.get(i), requestPatternTokens.get(i).substring(requestPatternTokens.get(i).indexOf("[") + 1, requestPatternTokens.get(i).substring(requestPatternTokens.get(i).indexOf("[") + 1).indexOf("]")).replace(FormatUtil.spaceEscape, " ")))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching given regex, which its not");

                            tokensFullyParsed = false;
                        }
                    }
                    else
                    {
                        tokensFullyParsed = false;
                    }
                }

                if(tokensFullyParsed)
                {
                    if(response instanceof StringResponse)
                    {
                        StringResponse stringResponse = (StringResponse) response;

                        stringResponse.setArgs(parsedArguments);

                        return stringResponse;
                    }
                    else
                    {
                        new WrongResponseTypeException();

                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
            else
            {
                new RequestTokenException("Length of of request '" + request.getRequest() + "' (" + requestTokens.size() + ") should be matching the expected token count of '" +  requestPatternTokens.size() + "'");

                return null;
            }
        }
        else
        {
            new WrongHandableRequestTypeException();

            return null;
        }
    }

    /**
     * Called to validate if the requirements for a saved request are given in a HandableRequest<br>
     *
     * The same rules for a HandableRequest apply as in {@link #handle(HandleableRequest)}
     *
     * @param incomingRequest the request to be matched with
     *
     * @return if the HandableRequest matches the requirements
     */
    @Override
    public boolean matches(HandleableRequest incomingRequest)
    {
        if(incomingRequest instanceof StringHandleableRequest)
        {
            StringHandleableRequest request = (StringHandleableRequest) incomingRequest;

            List<String> requestTokens = Arrays.asList(request.getRequest().split("\\s(?=(?:[^\\']*\\'[^\\']*\\')*[^\\']*$)"));
            List<String> parsedArguments = new ArrayList<>();

            if(requestPatternTokens.size() == requestTokens.size())
            {
                for(int i = 0; i < requestTokens.size(); i++)
                {
                    if(requestPatternTokens.get(i).equals(requestTokens.get(i)))
                    {
                        continue;
                    }
                    else if(requestPatternTokens.get(i).startsWith("#string"))
                    {
                        if(FormatUtil.isText(requestTokens.get(i)))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            //new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching text, which its not");

                            return false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#int"))
                    {
                        if(FormatUtil.isNumeric(requestTokens.get(i)))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            //new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching a number, which its not");

                            return false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#boolean"))
                    {
                        if(FormatUtil.isBoolean(requestTokens.get(i)))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            //new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching true or false, which its not");

                            return false;
                        }
                    }
                    else if(requestPatternTokens.get(i).startsWith("#regex"))
                    {
                        if(FormatUtil.matchesRegex(requestTokens.get(i), requestPatternTokens.get(i).substring(requestPatternTokens.get(i).indexOf("[") + 1, requestPatternTokens.get(i).substring(requestPatternTokens.get(i).indexOf("[") + 1).indexOf("]")).replace(FormatUtil.spaceEscape, " ")))
                        {
                            parsedArguments.add(requestTokens.get(i));

                            continue;
                        }
                        else
                        {
                            //new RequestTokenException("Token at index '" + i + "' of request '" + request.getRequest() + "' (" + requestTokens.get(i) + ") should be matching given regex, which its not");

                            return false;
                        }
                    }
                    else
                    {
                        return false;
                    }
                }

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            new WrongHandableRequestTypeException();

            return false;
        }
    }
}
