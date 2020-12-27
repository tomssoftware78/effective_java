joining()
   concatenates the input elements into a String, in encountered order

   Stream.of("E", "F", "G", "H", "I").collect(Collectors.joining()); //EFGHI

joining(CharSequence delimiter)
   concatenates the input elements into a String, separated by the specified delimiter, in encountered order

   Stream.of("E", "F", "G", "H", "I").collect(Collectors.joining(",")); //E,F,G,H,I

joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
   concatenates the input elements, separated by the specified delimiter, with the specified prefix and suffix,
   in encountered order

   Stream.of("E", "F", "G", "H", "I").collect(Collectors.joining(",", "{", "}")); //{E,F,G,H,I}