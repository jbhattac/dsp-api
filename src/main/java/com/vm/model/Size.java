package com.vm.model;

import lombok.Data;

@Data
public class Size
{
    private int height;
    private int width;
    
    
    
    
    @Override
    public String toString()
    {
        return "ClassPojo [height = "+height+", width = "+width+"]";
    }


    
    


    public Size(int height, int width)
    {
        super();
        this.height = height;
        this.width = width;
    }



    public Size(String height, String width)
    {
        super();
        this.height = Integer.parseInt(height);
        this.width = Integer.parseInt(width);
    }


    public Size()
    {
        super();
    }






    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Size other = (Size) obj;
        if (height != other.height)
            return false;
        if (width != other.width)
            return false;
        return true;
    }






    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }

}
