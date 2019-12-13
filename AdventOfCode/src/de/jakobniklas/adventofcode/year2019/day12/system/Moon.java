package de.jakobniklas.adventofcode.year2019.day12.system;

public class Moon
{
    private Position position;
    private Velocity velocity;

    public Moon(String input)
    {
        position = new Position();
        position.setX(Integer.parseInt(input.split(", ")[0].split("x=")[1]));
        position.setY(Integer.parseInt(input.split(", ")[1].split("y=")[1]));
        position.setZ(Integer.parseInt(input.split(", ")[2].split("z=")[1]));

        velocity = new Velocity();
    }

    public Integer getEnergy()
    {
        return position.getPotentialEnergy() * velocity.getKineticEnergy();
    }

    public void applyGravity()
    {
        position.addToX(velocity.getX());
        position.addToY(velocity.getY());
        position.addToZ(velocity.getZ());
    }

    public void applyVelocity(Moon otherMoon)
    {
        if(!position.getX().equals(otherMoon.position.getX()))
        {
            if(position.getX() > otherMoon.position.getX())
            {
                //otherMoon.velocity.addToX(1);
                velocity.addToX(-1);
            }
            else
            {
                //otherMoon.velocity.addToX(-1);
                velocity.addToX(1);
            }
        }

        if(!position.getY().equals(otherMoon.position.getY()))
        {
            if(position.getY() > otherMoon.position.getY())
            {
                //otherMoon.velocity.addToY(1);
                velocity.addToY(-1);
            }
            else
            {
                //otherMoon.velocity.addToY(-1);
                velocity.addToY(1);
            }
        }

        if(!position.getZ().equals(otherMoon.position.getZ()))
        {
            if(position.getZ() > otherMoon.position.getZ())
            {
                //otherMoon.velocity.addToZ(1);
                velocity.addToZ(-1);
            }
            else
            {
                //otherMoon.velocity.addToZ(-1);
                velocity.addToZ(1);
            }
        }
    }

    @Override
    public String toString()
    {
        return "Moon{" +
            "position=" + position +
            ", velocity=" + velocity +
            '}';
    }
}
