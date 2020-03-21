Prefer try-with-resources to try-finally
========================================

In Java libraries, there are many resources that must be closed manually by a close() method.
    example: InputStream, OutputStream, java.sql.Connection

    This is often overlooked by clients --> poor performance
    These resources often have a safety net by means of finalizers (incertain if they are executed)

try-finally introduced a good construct but was error-prone, unreadable and often implemented wrong

Java 7 introduced try-with-resources construct. For this to work, the resource must implement the
    AutoCloseable interface which consists of a single void clos() method.

Todayn many classes and interfaces in the Java libraries and in third-party libraries now implement or
extend AutoCloseable.
If you write a class that represents a resource that must be closed, your class should implement AutoCloseable
too.

Example:

    // try-with-resources - the the best way to close resources!
    static String firstLineOfFile(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

Another example:

    // try-with-resources on multiple resources - short and sweet
    static void copy(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }

    + shorter
    + more readable
    + far better diagnostics
        -> If exceptions are thrown by both the readLine call and the (invisible) close, the latter exception is
            suppressed in favor of the former.
        -> In fact, multiple exceptions may be suppressed in order to preserve the exception that you actually
            want to see.
            These suppressed exceptions are not merely discarded; they are printed in the stack trace with a
            notation saying that they were suppressed.

            You can also access them programmatically with the getSuppressed method, which was added to Throwable
            in Java 7.

You can put catch clauses on try-with-resources statements, just as you can on regular try-finally statements.
This allows you to handle exceptions without sullying your code with another layer of nesting.

Example that does not throw the exception:

    // try-with-resources with a catch clause
    static String firstLineOfFile(String path, String defaultVal) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return defaultVal;
        }
    }

Conclusion:
    Always use try-with-resources in preference to try finally when working with resources that must be closed.

    The resulting code is shorter and clearer, and the exceptions that it generates are more useful.
    The try-with-resources statement makes it easy to write correct code using resources that must be closed, which
    was practically impossible using try-finally.