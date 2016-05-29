package com.weimeng.example.node;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Symbol
{
    public static final int TEMP = -1;
    public static final int SINGLE_INT = 0;
    public static final int SINGLE_FLOAT = 1;

    private String name;
    private int type;
    private Value value;
    private int level;
    private Symbol next;

    public Symbol(String name, int type, int level)
    {
        this.name = name;
        this.type = type;
        this.level = level;
        this.next = null;
        this.value = new Value(type);
    }

    /**
     * 一定要是SINGLE_INT
     *
     * @param name
     * @param type
     * @param level
     * @param value
     */
    public Symbol(String name, int type, int level, int value)
    {
        this(name, type, level);
        this.value.setInt(value);
    }

    /**
     * 一定要是SINGLE_REAL
     *
     * @param name
     * @param type
     * @param level
     * @param value
     */
    public Symbol(String name, int type, int level, double value)
    {
        this(name, type, level);
        this.value.setFloat(value);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Value getValue()
    {
        return value;
    }

    public void setValue(Value value)
    {
        this.value = value;
    }

    public int getLevel()
    {
        return level;
    }

    /**
     * 获取下一个同名Symbol
     *
     * @return
     */
    public Symbol getNext()
    {
        return next;
    }

    /**
     * 设置下一个同名symbol
     *
     * @param next
     */
    public void setNext(Symbol next)
    {
        this.next = next;
    }
}
