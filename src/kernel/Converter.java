package kernel;

/**
* Created by IntelliJ IDEA.
* User: alex
* Date: 28.10.11
* Time: 11:21
*/
public interface Converter<Request, Return>
{
    Return convert(Request string);
}
