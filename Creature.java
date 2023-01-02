public abstract class Creature {

    public boolean hasMovedorBreed= false; // member varible to pass on to the child classes
    public abstract int Move(String d); // move abstract class
    public abstract Creature Breed();  // breed abstract class
}
