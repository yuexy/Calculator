package com.weimeng.example.node;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Token
{
    // 初始化类型为NULL
    public static final int EOF = 0;

    // ident 第一个字母不能是数字
    public static final int IDENT = 1;

    // int
    public static final int INT = 2;

    // float
    public static final int FLOAT = 3;

    // +
    public static final int PLUS = 4;

    // -
    public static final int MINUS = 5;

    // *
    public static final int MUL = 6;

    // /
    public static final int DIV = 7;

    // (
    public static final int LPAR = 8;

    // )
    public static final int RPAR = 9;

    // ;
    public static final int SEMI = 10;

    // .
    public static final int DOT = 11;

    // =
    public static final int ASSIGN = 12;

    // int 字面值
    public static final int INT_REAL = 13;

    // float 字面值
    public static final int FLOAT_REAL = 14;

    // write
    public static final int WRITE = 15;

    public static final int ADDTIVE_EXP = 16;

    public static final int TERM_EXP = 17;

    private int type;
    private String value;
    private int line;

    public Token(int line)
    {
        type = EOF;
    }

    public Token(int t, int l)
    {
        this(t, null, l);
    }

    public Token(int t, String v, int l)
    {
        type = t;
        value = v;
        line = l;
    }

    public void setType(int t)
    {
        type = t;
    }

    public int getType()
    {
        return type;
    }

    public void setValue(String v)
    {
        value = v;
    }

    public String getValue()
    {
        return value;
    }

    public int getLine()
    {
        return line;
    }
}
