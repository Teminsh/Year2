package OOP.Lab4;

import java.util.ArrayList;
import java.util.List;

public class Event<TEventArgs extends EventArgs>
{
    private final List<EventHandler<TEventArgs>> handlers = new ArrayList<>();

    public void subscribe(EventHandler<TEventArgs> handler)
    {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    public void unsubscribe(EventHandler<TEventArgs> handler)
    {
        handlers.remove(handler);
    }

    public void invoke(Object sender, TEventArgs args) {
        for (EventHandler<TEventArgs> handler : handlers) {
            handler.handle(sender, args);
        }
    }
}