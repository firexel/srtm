package kernel.pool;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 12.11.11
 * Time: 1:36
 */
public interface Pool<Type>
{
    void enqueue(Type object);
}
