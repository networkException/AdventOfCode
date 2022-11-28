package de.nwex.adventofcode.year2019.day06.orbit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpaceObject
{
    private List<SpaceObject> trace;
    private String parent;
    private String spaceObject;

    public SpaceObject(String parent, String spaceObject)
    {
        this.trace = new ArrayList<>();
        this.parent = parent;
        this.spaceObject = spaceObject;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(parent, spaceObject);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        SpaceObject that = (SpaceObject) o;
        return Objects.equals(parent, that.parent) &&
            Objects.equals(spaceObject, that.spaceObject);
    }

    @Override
    public String toString()
    {
        return "SpaceObject{" +
            "parent='" + parent + '\'' +
            ", spaceObject='" + spaceObject + '\'' +
            '}';
    }

    public String getParent()
    {
        return parent;
    }

    public String getSpaceObject()
    {
        return spaceObject;
    }

    public List<SpaceObject> getTrace()
    {
        return trace;
    }
}
