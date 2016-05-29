package com.weimeng.example.node;

import com.weimeng.example.exception.InterpretException;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by weimeng on 2016/5/29.
 */
public class SymbolTable
{
    private static final String TEMP_PREFIX = "*temp";

    private static SymbolTable symbolTable;

    private static LinkedList<Symbol> tempNames;

    private ArrayList<Symbol> symbolList;

    private SymbolTable()
    {

    }

    public static SymbolTable getSymbolTable()
    {
        if (symbolTable == null)
            symbolTable = new SymbolTable();

        return symbolTable;
    }

    public void newTable()
    {
        symbolList = new ArrayList<Symbol>();
        tempNames = new LinkedList<Symbol>();
    }

    public void deleteTable()
    {
        if (symbolList != null)
        {
            symbolList.clear();
            symbolList = null;
        }
        if (tempNames != null)
        {
            tempNames.clear();
            tempNames = null;
        }
    }

    public void register(Symbol symbol) throws InterpretException
    {
        for (int i = 0; i < symbolList.size(); i++)
        {
            if (symbolList.get(i)
                    .getName()
                    .equals(symbol.getName()))
            {
                if (symbolList.get(i)
                        .getLevel() < symbol.getLevel())
                {
                    symbol.setNext(symbolList.get(i));
                    symbolList.set(i, symbol);
                    return;
                } else
                {
                    throw new InterpretException("变量 <" + symbol.getName() + "> 重复声明");
                }
            }
        }
        symbolList.add(symbol);
    }

    public void deregister(int level)
    {
        for (int i = 0; i < symbolList.size(); i++)
        {
            if (symbolList.get(i)
                    .getLevel() == level)
            {
                symbolList.set(i,
                        symbolList.get(i)
                                .getNext());
            }
        }
        for (int i = symbolList.size() - 1; i >= 0; i--)
        {
            if (symbolList.get(i) == null)
            {
                symbolList.remove(i);
            }
        }
    }

    public void setSymbolValue(String name, Value value) throws InterpretException
    {
        getSymbol(name).setValue(value);
    }

    /**
     * 返回Symbol中的类型
     *
     * @param name
     * @return
     * @throws InterpretException
     */
    public int getSymbolType(String name) throws InterpretException
    {
        return getSymbol(name).getType();
    }

    /**
     * 取单值用这个函数
     *
     * @param name
     * @return
     */
    public Value getSymbolValue(String name) throws InterpretException
    {
        Symbol s = getSymbol(name);
        return s.getValue();
    }

    private Symbol getSymbol(String name) throws InterpretException
    {
        for (Symbol s : symbolList)
        {
            if (s.getName()
                    .equals(name))
            {
                return s;
            }
        }
        for (Symbol s : tempNames)
        {
            if (s.getName()
                    .equals(name))
            {
                return s;
            }
        }
        if (name.startsWith(TEMP_PREFIX))
        {
            Symbol s = new Symbol(name, Symbol.TEMP, -1);
            tempNames.add(s);
            return s;
        }
        throw new InterpretException("变量 <" + name + "> 不存在");
    }

    /**
     * 获取一个没有使用的临时符号名
     *
     * @return
     */
    public Symbol getTempSymbol()
    {
        String temp = null;
        for (int i = 1; ; i++)
        {
            temp = TEMP_PREFIX + i;
            boolean exist = false;
            for (Symbol s : tempNames)
            {
                if (s.getName()
                        .equals(temp))
                {
                    exist = true;
                    break;
                }
            }
            for (Symbol s : symbolList)
            {
                if (s.getName()
                        .equals(temp))
                {
                    exist = true;
                    break;
                }
            }
            if (exist)
            {
                continue;
            }
            Symbol s = new Symbol(temp, Symbol.TEMP, -1);
            tempNames.add(s);
            return s;
        }
    }

    /**
     * 清空临时符号名
     */
    public void clearTempNames()
    {
        tempNames.clear();
    }
}
