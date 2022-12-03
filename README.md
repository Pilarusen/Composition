This project is done in order to solve the task:

interface Structure {
//returns any element with provided color
Optional<Block> findBlockByColor(String color);

//returns all elements with provided material
List<Block> findBlocksByMaterial(String material);

//returns number of all elements in structure
int count();
}

public class Wall implements Structure {
private List<Block> blocks;
}

interface Block {
String getColor();
String getMaterial();
}

interface CompositeBlock extends Block {
List<Block> getBlocks();
}

Using unit tests I tested and created solution free from StackOverflowError.
I focused to test every case that could happen during code run.
Application is using Gradle, Spring Boot, Lombok, JUnit 5.