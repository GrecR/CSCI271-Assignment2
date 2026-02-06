public class FractionDemo
{
    /*****************************main************************************
    * Description: Runs a set of test cases for the Fraction data type.
    *
    * Parameters:
    *   args (input) - command line arguments (unused)
    *
    * Pre: none
    * Post: prints test output to console
    *
    * Returns: none
    *
    * Called by: JVM
    * Calls: runBasicToStringTests, runSpecialValueTests, runOperationTests
    ************************************************************************/
    public static void main(String[] args)
    {
        runBasicToStringTests();
        runSpecialValueTests();
        runOperationTests();
    }

    /*****************************runBasicToStringTests********************
    * Description: Tests constructors and toString formatting for normal
    * fractions and integers.
    *
    * Parameters: none
    * Pre: none
    * Post: prints results
    *
    * Returns: none
    *
    * Called by: main
    * Calls: printCase
    ************************************************************************/
    private static void runBasicToStringTests()
    {
        System.out.println("=== Basic toString / Normalization Tests ===");

        Fraction f1 = new Fraction();          // 0
        Fraction f2 = new Fraction(5);         // 5
        Fraction f3 = new Fraction(6, 8);      // should reduce to 3/4
        Fraction f4 = new Fraction(-2, -3);    // should become 2/3
        Fraction f5 = new Fraction(2, -3);     // should become -2/3

        printCase("new Fraction()", f1.toString(), "0");
        printCase("new Fraction(5)", f2.toString(), "5");
        printCase("new Fraction(6,8)", f3.toString(), "3/4");
        printCase("new Fraction(-2,-3)", f4.toString(), "2/3");
        printCase("new Fraction(2,-3)", f5.toString(), "-2/3");
        System.out.println();
    }

    /*****************************runSpecialValueTests*********************
    * Description: Tests creation and printing of Infinity, -Infinity, NaN.
    *
    * Parameters: none
    * Pre: none
    * Post: prints results
    *
    * Returns: none
    *
    * Called by: main
    * Calls: printCase
    ************************************************************************/
    private static void runSpecialValueTests()
    {
        System.out.println("=== Special Value Tests ===");

        Fraction posInf = new Fraction(1, 0);   // Infinity
        Fraction negInf = new Fraction(-1, 0);  // -Infinity
        Fraction nanVal = new Fraction(0, 0);   // NaN

        printCase("new Fraction(1,0)", posInf.toString(), "Infinity");
        printCase("new Fraction(-1,0)", negInf.toString(), "-Infinity");
        printCase("new Fraction(0,0)", nanVal.toString(), "NaN");
        System.out.println();
    }

    /*****************************runOperationTests************************
    * Description: Runs operation tests. These will PASS once we implement
    * add/subtract/multiply/divide/negate/pow in Fraction.
    *
    * Parameters: none
    * Pre: none
    * Post: prints results
    *
    * Returns: none
    *
    * Called by: main
    * Calls: printExpression
    ************************************************************************/
    private static void runOperationTests()
    {
        System.out.println("=== Operation Tests (will match after implementation) ===");

        Fraction a = new Fraction(1, 2);  // 1/2
        Fraction b = new Fraction(1, 3);  // 1/3

        // Normal arithmetic expected results
        printExpression("1/2 + 1/3", a.add(b), "5/6");
        printExpression("1/2 - 1/3", a.subtract(b), "1/6");
        printExpression("1/2 * 1/3", a.multiply(b), "1/6");
        printExpression("1/2 / 1/3", a.divide(b), "3/2");
        printExpression("negate(1/2)", a.negate(), "-1/2");
        printExpression("(1/2)^3", a.pow(3), "1/8");
        printExpression("(1/2)^0", a.pow(0), "1");
        printExpression("(1/2)^-2", a.pow(-2), "4");

        // Special value operations (expected per assignment rules)
        Fraction inf = new Fraction(1, 0);
        Fraction ninf = new Fraction(-1, 0);
        Fraction nan = new Fraction(0, 0);
        Fraction zero = new Fraction(0, 1);

        printExpression("Infinity + 1/2", inf.add(a), "Infinity");
        printExpression("-Infinity + 1/2", ninf.add(a), "-Infinity");
        printExpression("Infinity + -Infinity", inf.add(ninf), "NaN");
        printExpression("Infinity - Infinity", inf.subtract(inf), "NaN");
        printExpression("0 * Infinity", zero.multiply(inf), "NaN");
        printExpression("Infinity / Infinity", inf.divide(inf), "NaN");
        printExpression("NaN + 1/2", nan.add(a), "NaN");

        System.out.println();
    }

    /*****************************printCase********************************
    * Description: Prints a simple test case line.
    *
    * Parameters:
    *   label (input) - what is being tested
    *   actual (input) - actual result string
    *   expected (input) - expected result string
    *
    * Pre: none
    * Post: prints one line
    *
    * Returns: none
    *
    * Called by: runBasicToStringTests, runSpecialValueTests
    * Calls: none
    ************************************************************************/
    private static void printCase(String label, String actual, String expected)
    {
        System.out.println(label + " => " + actual + " (expected: " + expected + ")");
    }

    /*****************************printExpression**************************
    * Description: Prints an expression result and expected output.
    *
    * Parameters:
    *   label (input) - expression label
    *   result (input) - Fraction result
    *   expected (input) - expected string form
    *
    * Pre: none
    * Post: prints one line
    *
    * Returns: none
    *
    * Called by: runOperationTests
    * Calls: toString
    ************************************************************************/
    private static void printExpression(String label, Fraction result, String expected)
    {
        System.out.println(label + " = " + result.toString() + " (expected: " + expected + ")");
    }
}
