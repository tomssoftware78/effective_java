Always override hashCode when you override equals
=================================================

!!! You must override hashCode in every class that overrides equals.

Here is the contract, adapted from the Object specification :
    • When the hashCode method is invoked on an object repeatedly during an execution of an application, it must
        consistently return the same value, provided no information used in equals comparisons is modified.
        This value need not remain consistent from one execution of an application to another.

    • If two objects are equal according to the equals(Object) method, then calling hashCode on the two objects must
        produce the same integer result.

    • If two objects are unequal according to the equals(Object) method, it is not required that calling hashCode on
        each of the objects must produce distinct results.
        However, the programmer should be aware that producing distinct results for unequal objects may improve the
        performance of hash tables.

A good hash function tends to produce unequal hash codes for unequal instances.

Ideally, a hash function should distribute any reasonable collection of unequal instances uniformly across all int
values.
Achieving this ideal can be difficult. Luckily it’s not too hard to achieve a fair approximation. Here is a simple
recipe:

    1. Declare an int variable named result, and initialize it to the hash code c for the first significant field in
        your object, as computed in step 2.a.
        (Recall from Item 10 that a significant field is a field that affects equals comparisons.)

    2. For every remaining significant field f in your object, do the following:

        a. Compute an int hash code c for the field:

            i. If the field is of a primitive type, compute
                    Type.hashCode(f),
                where Type is the boxed primitive class corresponding to f’s type.
            ii. - If the field is an object reference and this class’s equals method compares the field by recursively
                invoking equals, recursively invoke hashCode on the field.
                - If a more complex comparison is required, compute a “canonical representation” for this field and
                invoke hashCode on the canonical representation.
                - If the value of the field is null, use 0 (or some other constant, but 0 is traditional).
            iii. If the field is an array, treat it as if each significant element were a separate field.
                 That is, compute a hash code for each significant element by applying these rules recursively, and
                 combine the values per step 2.b.
                 If the array has no significant elements, use a constant, preferably not 0. If all elements are
                 significant, use Arrays.hashCode.
        b. Combine the hash code c computed in step 2.a into result as follows:

            result = 31 * result + c;

    3. Return result.

When you are finished writing the hashCode method, ask yourself whether equal instances have equal hash codes.
Write unit tests to verify your intuition (unless you used AutoValue to generate your equals and hashCode methods, in
which case you can safely omit these tests).
If equal instances have unequal hash codes, figure out why and fix the problem.
You may exclude derived fields from the hash code computation. In other words, you may ignore any field whose value can
be computed from fields included in the computation.
You must exclude any fields that are not used in equals comparisons, or you risk violating the second provision of the
hashCode contract.

The multiplication in step 2.b makes the result depend on the order of the fields, yielding a much better hash function
if the class has multiple similar fields.

For example, if the multiplication were omitted from a String hash function, all
anagrams would have identical hash codes. The value 31 was chosen because it is
an odd prime. If it were even and the multiplication overflowed, information
would be lost, because multiplication by 2 is equivalent to shifting. The advantage
of using a prime is less clear, but it is traditional. A nice property of 31 is that the
multiplication can be replaced by a shift and a subtraction for better performance
on some architectures: 31 * i == (i << 5) - i. Modern VMs do this sort of optimization automatically.
Let’s apply the previous recipe to the PhoneNumber class:
// Typical hashCode method
@Override public int hashCode() {
int result = Short.hashCode(areaCode);
result = 31 * result + Short.hashCode(prefix);
result = 31 * result + Short.hashCode(lineNum);
return result;
}
Because this method returns the result of a simple deterministic computation
whose only inputs are the three significant fields in a PhoneNumber instance, it is
clear that equal PhoneNumber instances have equal hash codes. This method is, in
fact, a perfectly good hashCode implementation for PhoneNumber, on par with
those in the Java platform libraries. It is simple, is reasonably fast, and does a
reasonable job of dispersing unequal phone numbers into different hash buckets.
While the recipe in this item yields reasonably good hash functions, they are
not state-of-the-art. They are comparable in quality to the hash functions found in
the Java platform libraries’ value types and are adequate for most uses. If you have
a bona fide need for hash functions less likely to produce collisions, see Guava’s
com.google.common.hash.Hashing [Guava]