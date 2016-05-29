package com.weimeng.example.node;

import com.weimeng.example.exception.InterpretException;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Value
{
    private int type;

    private int mInt;
    private double mFloat;

    public Value(int t)
    {
        type = t;
    }

    public void setInt(int i)
    {
        mInt = i;
    }

    public int getInt()
    {
        return mInt;
    }

    public void setFloat(double d)
    {
        mFloat = d;
    }

    public double getFloat()
    {
        return mFloat;
    }

    public Value PLUS(Value v) throws InterpretException
    {
        if (this.type == Symbol.SINGLE_INT)
        {
            Value rv = new Value(Symbol.SINGLE_INT);
            if (v.type == Symbol.SINGLE_INT)
            {
                rv.setInt(this.getInt() + v.getInt());
            }
            else
            {
                rv.setInt(this.getInt() + (int) v.getFloat());
            }

            return rv;
        }
        else if (this.type == Symbol.SINGLE_FLOAT)
        {
            if (v.type == Symbol.SINGLE_FLOAT)
            {
                Value rv = new Value(Symbol.SINGLE_FLOAT);
                rv.setFloat(this.getFloat() + v.getFloat());
                return rv;
            }
            else
            {
                Value rv = new Value(Symbol.SINGLE_INT);
                rv.setInt(v.getInt() + (int) this.getFloat());
                return rv;
            }
        }
        throw new InterpretException("算数运算非法");
    }

    public Value MINUS(Value v) throws InterpretException
    {
        if (this.type == Symbol.SINGLE_INT)
        {
            Value rv = new Value(Symbol.SINGLE_INT);
            if (v.type == Symbol.SINGLE_INT)
            {
                rv.setInt(this.getInt() - v.getInt());
            }
            else
            {
                rv.setInt(this.getInt() - (int) v.getFloat());
            }

            return rv;
        }
        else if (this.type == Symbol.SINGLE_FLOAT)
        {
            if (v.type == Symbol.SINGLE_FLOAT)
            {
                Value rv = new Value(Symbol.SINGLE_FLOAT);
                rv.setFloat(this.getFloat() - v.getFloat());
                return rv;
            }
            else
            {
                Value rv = new Value(Symbol.SINGLE_INT);
                rv.setInt((int) this.getFloat() - v.getInt());
                return rv;
            }
        }
        throw new InterpretException("算数运算非法");
    }

    public Value MUL(Value v) throws InterpretException
    {
        if (this.type == Symbol.SINGLE_INT)
        {
            Value rv = new Value(Symbol.SINGLE_INT);
            if (v.type == Symbol.SINGLE_INT)
            {
                rv.setInt(this.getInt() * v.getInt());
            }
            else
            {
                rv.setInt(this.getInt() * (int) v.getFloat());
            }

            return rv;
        }
        else if (this.type == Symbol.SINGLE_FLOAT)
        {
            if (v.type == Symbol.SINGLE_FLOAT)
            {
                Value rv = new Value(Symbol.SINGLE_FLOAT);
                rv.setFloat(this.getFloat() * v.getFloat());
                return rv;
            }
            else
            {
                Value rv = new Value(Symbol.SINGLE_INT);
                rv.setInt(v.getInt() * (int) this.getFloat());
                return rv;
            }
        }
        throw new InterpretException("算数运算非法");
    }

    public Value DIV(Value v) throws InterpretException
    {
        if (this.type == Symbol.SINGLE_INT)
        {
            Value rv = new Value(Symbol.SINGLE_INT);
            if (v.type == Symbol.SINGLE_INT)
            {
                if (v.getInt() == 0)
                    throw new InterpretException("算数运算非法");
                rv.setInt(this.getInt() / v.getInt());
            }
            else
            {
                if (v.getFloat() == 0)
                    throw new InterpretException("算数运算非法");
                rv.setInt(this.getInt() / (int) v.getFloat());
            }

            return rv;
        }
        else if (this.type == Symbol.SINGLE_FLOAT)
        {
            if (v.type == Symbol.SINGLE_FLOAT)
            {
                if (v.getFloat() == 0)
                    throw new InterpretException("算数运算非法");
                Value rv = new Value(Symbol.SINGLE_FLOAT);

                rv.setFloat(this.getFloat() / v.getFloat());
                return rv;
            }
            else
            {
                if (v.getInt() == 0)
                    throw new InterpretException("算数运算非法");
                Value rv = new Value(Symbol.SINGLE_INT);
                rv.setInt((int) this.getFloat() / v.getInt());
                return rv;
            }
        }
        throw new InterpretException("算数运算非法");
    }
}
