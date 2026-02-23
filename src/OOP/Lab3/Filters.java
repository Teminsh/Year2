package OOP.Lab3;

import java.util.regex.Pattern;

class SimpleLogFilter implements ILogFilter
{
    private final String keyword;

    public SimpleLogFilter(String keyword)
    {
        this.keyword = keyword;
    }

    @Override
    public boolean match(LogLevel level, String message)
    {
        return message.contains(keyword);
    }
}

class ReLogFilter implements ILogFilter
{
    private final Pattern pattern;

    public ReLogFilter(String regex)
    {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean match(LogLevel level, String message)
    {
        return pattern.matcher(message).find();
    }
}

class LevelFilter implements ILogFilter
{
    private final LogLevel minLevel;

    public LevelFilter(LogLevel minLevel)
    {
        this.minLevel = minLevel;
    }

    @Override
    public boolean match(LogLevel level, String message)
    {
        return level == minLevel;
    }
}