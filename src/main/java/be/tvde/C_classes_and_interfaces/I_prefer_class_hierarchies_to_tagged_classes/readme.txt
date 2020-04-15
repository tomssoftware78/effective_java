Item 23: Prefer class hierarchies to tagged classes
===================================================

Occasionally you may run across a class whose instances come in two or more flavors and contain a tag field indicating
the flavor of the instance.

    Example:    consider this class, which is capable of representing a circle or a rectangle:

        // Tagged class - vastly inferior to a class hierarchy!
        class Figure {
            enum Shape { RECTANGLE, CIRCLE };

            // Tag field - the shape of this figure
            final Shape shape;

            // These fields are used only if shape is RECTANGLE
            double length;
            double width;

            // This field is used only if shape is CIRCLE
            double radius;

            // Constructor for circle
            Figure(double radius) {
                shape = Shape.CIRCLE;
                this.radius = radius;
            }

            // Constructor for rectangle
            Figure(double length, double width) {
                shape = Shape.RECTANGLE;
                this.length = length;
                this.width = width;
            }

            double area() {
                switch(shape) {
                    case RECTANGLE:
                        return length * width;
                    case CIRCLE:
                        return Math.PI * (radius * radius);
                    default:
                        throw new AssertionError(shape);
                }
            }
        }

Such tagged classes have numerous shortcomings.

    - Are cluttered with boilerplate, including
            * enum declarations,
            * tag fields, and
            * switch statements.
    - Readability is further harmed because multiple implementations are jumbled together in a single class.
    - Memory footprint is increased because instances are burdened with irrelevant fields belonging to other flavors.
    - Fields can’t be made final unless constructors initialize irrelevant fields, resulting in more boilerplate.
    - Constructors must set the tag field and initialize the right data fields with no help from the compiler:
        if you initialize the wrong fields, the program will fail at runtime.
    - You can’t add a flavor to a tagged class unless you can modify its source file.
    - If you do add a flavor, you must remember to add a case to every switch statement, or the class will fail at
        runtime.
    - The data type of an instance gives no clue as to its flavor.

==> In short, tagged classes are verbose, error-prone, and inefficient.


A FAR BETTER ALTERNATIVE for defining a single data type capable of representing objects of multiple flavors: SUBTYPING


To transform a tagged class into a class hierarchy:

    1. First define an abstract class containing an abstract method for each method in the tagged class whose behavior
        depends on the tag value.

            In the Figure class, there is only one such method, which is area.

    2. This abstract class is the root of the class hierarchy.
        If there are any methods whose behavior does not depend on the value of the tag, put them in this class.

    3. If there are any data fields used by all the flavors, put them also in the abstract class.

            There are no such flavor-independent methods or fields in the Figure class.

    4. Define a concrete subclass of the root class for each flavor of the original tagged class.

            In the Figure class, there are two: circle and rectangle.

    5. Include in each subclass the data fields particular to its flavor.

            In our example:
                - radius is particular to circle
                - length and width are particular to rectangle

    6. Include in each subclass the appropriate implementation of each abstract method in the root class.


    Here is the class hierarchy corresponding to the original Figure class:

        // Class hierarchy replacement for a tagged class
        abstract class Figure {
            abstract double area();
        }

        class Circle extends Figure {

            final double radius;

            Circle(double radius) { this.radius = radius; }

            @Override double area() { return Math.PI * (radius * radius); }
        }

        class Rectangle extends Figure {

            final double length;
            final double width;

            Rectangle(double length, double width) {
                this.length = length;
                this.width = width;
            }

            @Override double area() { return length * width; }
        }

        This class hierarchy corrects every shortcoming of tagged classes noted previously.
        The code is simple and clear, containing none of the boilerplate found in the original.
        The implementation of each flavor is allotted its own class, and none of these classes is encumbered by
        irrelevant data fields.
        All fields are final.
        The compiler ensures that each class’s constructor initializes its data fields and that each class has an
            implementation for every abstract method declared in the root class.

            This eliminates the possibility of a runtime failure due to a missing switch case.

        Multiple programmers can extend the hierarchy independently and interoperably without access to the source for
        the root class.
        There is a separate data type associated with each flavor, allowing programmers to indicate the flavor of a
            variable and to restrict variables and input parameters to a particular flavor.


        ! Note that the fields in the above hierarchy are accessed directly rather than by accessor methods. This was
            done for brevity and would be a poor design if the hierarchy were public (Item 16).

Conclusion:
    - Tagged classes are seldom appropriate.
    - If you’re tempted to write a class with an explicit tag field, think about whether the tag could be eliminated
        and the class replaced by a hierarchy.
    - When you encounter an existing class with a tag field, consider refactoring it into a hierarchy.