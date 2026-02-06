/*******************************************************************
* I declare and confirm the following:
* - I have not discussed this program code with anyone other than my
* instructor or the teaching assistants assigned to this course.
* - I have not used programming code obtained from someone else,
* or any unauthorised sources, including the Internet, either
* modified or unmodified.
* - If any source code or documentation used in my program was
* obtained from other sources, like a text book or course notes,
* I have clearly indicated that with a proper citation in the
* comments of my program.
* - I have not designed this program in such a way as to defeat or
* interfere with the normal operation of the supplied grading code.
*
* Rayane Tellas
* W30780780
********************************************************************/


/*************************************************************************
* Assignment 2 for CSCI 271-001 Spring 2026
*
* Author: Rayane Tellas
* OS: Windows
* Compiler: javac
* Date: February 6, 2026
*
* Purpose
* This file defines a Fraction data type that supports arithmetic
* operations (add, subtract, multiply, divide, negate, pow) and handles
* special values Infinity, -Infinity, and NaN as described in the
* assignment specification.
*
* Method / Algorithm
* - Fractions are stored as numerator/denominator in lowest terms.
* - Denominator is kept positive for normal fractions.
* - Special cases are represented with denominator == 0:
*     numerator > 0 => +Infinity
*     numerator < 0 => -Infinity
*     numerator == 0 => NaN
*
* Data Structures
* - Uses primitive long fields for numerator and denominator.
*************************************************************************/

/*************************************************************************
* Class: Fraction
*
* Purpose
* Represents a rational number as a numerator/denominator pair, including
* special values Infinity, -Infinity, and NaN.
*
* Interface (public methods)
* - Constructors:
*     Fraction()
*     Fraction(long n)
*     Fraction(long n, long d)
*     Fraction(Fraction other)
* - toString()
* - add, subtract, multiply, divide, negate, pow
*************************************************************************/
public class Fraction
{
    // -------------------- Data Members (private) --------------------
    private long numerator;   // Top value of the fraction (or sign for special values)
    private long denominator; // Bottom value; 0 indicates Infinity/-Infinity/NaN

    // -------------------- Constructors --------------------

    /*****************************Fraction*******************************
    * Description: Default constructor. Creates the fraction 0/1.
    *
    * Parameters: none
    *
    * Pre: none
    * Post: This fraction becomes 0/1.
    *
    * Returns: none
    *
    * Called by: user code
    * Calls: setToNormalForm
    ************************************************************************/
    public Fraction()
    {
        numerator = 0;     // Represents 0
        denominator = 1;   // Represents 0/1
        setToNormalForm();
    }

    /*****************************Fraction*******************************
    * Description: Constructs a fraction from an integer n as n/1.
    *
    * Parameters:
    *   n (input) - integer numerator
    *
    * Pre: none
    * Post: This fraction becomes n/1.
    *
    * Returns: none
    *
    * Called by: user code
    * Calls: setToNormalForm
    ************************************************************************/
    public Fraction(long n)
    {
        numerator = n;    // Store numerator
        denominator = 1;  // Integer as n/1
        setToNormalForm();
    }

    /*****************************Fraction*******************************
    * Description: Constructs a fraction from numerator and denominator.
    * If denominator is 0, creates Infinity, -Infinity, or NaN based on n.
    *
    * Parameters:
    *   n (input) - numerator
    *   d (input) - denominator
    *
    * Pre: none
    * Post:
    *   - If d != 0, fraction is reduced and denominator is positive.
    *   - If d == 0, special value is stored.
    *
    * Returns: none
    *
    * Called by: user code
    * Calls: setToNormalForm
    ************************************************************************/
    public Fraction(long n, long d)
    {
        numerator = n;     // Store numerator
        denominator = d;   // Store denominator (may be 0 for special values)
        setToNormalForm();
    }

    /*****************************Fraction*******************************
    * Description: Copy constructor. Makes a deep copy of another Fraction.
    *
    * Parameters:
    *   other (input) - fraction to copy
    *
    * Pre: other is not null
    * Post: This fraction becomes a copy of other.
    *
    * Returns: none
    *
    * Called by: user code
    * Calls: none
    ************************************************************************/
    public Fraction(Fraction other)
    {
        numerator = other.numerator;     // Copy numerator
        denominator = other.denominator; // Copy denominator
    }

    // -------------------- Public Methods --------------------

    /*****************************toString*******************************
    * Description: Converts the fraction into a string.
    * Prints normal fractions as "n/d" except:
    * - 0/1 prints as "0"
    * - integers print as "n"
    * Special values print as:
    * - "Infinity", "-Infinity", or "NaN"
    *
    * Parameters: none
    *
    * Pre: none
    * Post: none
    *
    * Returns: String representation of this fraction.
    *
    * Called by: user code
    * Calls: isNaN, isPosInfinity, isNegInfinity
    ************************************************************************/
    public String toString()
    {
        if (isNaN())
        {
            return "NaN";
        }
        if (isPosInfinity())
        {
            return "Infinity";
        }
        if (isNegInfinity())
        {
            return "-Infinity";
        }

        // Normal fraction cases
        if (numerator == 0 && denominator == 1)
        {
            return "0";
        }
        if (denominator == 1)
        {
            return Long.toString(numerator);
        }
        return numerator + "/" + denominator;
    }

    /*****************************add************************************
    * Description: Returns a new Fraction that is this + other.
    *
    * Parameters:
    *   other (input) - fraction to add
    *
    * Pre: other is not null
    * Post: this and other are unchanged
    *
    * Returns: new Fraction equal to this + other
    *
    * Called by: user code
    * Calls: Fraction
    ************************************************************************/
    public Fraction add(Fraction other)
    {
        // If either is NaN => NaN
        if ((denominator == 0 && numerator == 0) || (other.denominator == 0 && other.numerator == 0))
        {
            return new Fraction(0, 0);
        }

        // Infinity rules
        boolean thisPosInf = (denominator == 0 && numerator > 0);
        boolean thisNegInf = (denominator == 0 && numerator < 0);
        boolean otherPosInf = (other.denominator == 0 && other.numerator > 0);
        boolean otherNegInf = (other.denominator == 0 && other.numerator < 0);

        // +Inf + -Inf or -Inf + +Inf => NaN
        if ((thisPosInf && otherNegInf) || (thisNegInf && otherPosInf))
        {
            return new Fraction(0, 0);
        }

        // Inf + normal => Inf (same sign)
        if (thisPosInf || otherPosInf)
        {
            return new Fraction(1, 0);
        }
        if (thisNegInf || otherNegInf)
        {
            return new Fraction(-1, 0);
        }

        // Normal addition: a/b + c/d = (ad + bc) / bd
        long n = (numerator * other.denominator) + (other.numerator * denominator); // new numerator
        long d = denominator * other.denominator;                                   // new denominator
        return new Fraction(n, d);
    }


    /*****************************subtract*******************************
    * Description: Returns a new Fraction that is this - other.
    *
    * Parameters:
    *   other (input) - fraction to subtract
    *
    * Pre: other is not null
    * Post: this and other are unchanged
    *
    * Returns: new Fraction equal to this - other
    *
    * Called by: user code
    * Calls: add, negate
    ************************************************************************/
    public Fraction subtract(Fraction other)
    {
        // this - other = this + (-other)
        return this.add(other.negate());
    }


    /*****************************multiply*******************************
    * Description: Returns a new Fraction that is this * other.
    *
    * Parameters:
    *   other (input) - fraction to multiply by
    *
    * Pre: other is not null
    * Post: this and other are unchanged
    *
    * Returns: new Fraction equal to this * other
    *
    * Called by: user code
    * Calls: Fraction
    ************************************************************************/
    public Fraction multiply(Fraction other)
    {
        // If either is NaN => NaN
        if ((denominator == 0 && numerator == 0) || (other.denominator == 0 && other.numerator == 0))
        {
            return new Fraction(0, 0);
        }

        // Handle Infinity cases
        boolean thisInf = (denominator == 0 && numerator != 0);
        boolean otherInf = (other.denominator == 0 && other.numerator != 0);

        // 0 * Infinity or Infinity * 0 => NaN
        if ((thisInf && other.numerator == 0 && other.denominator != 0) ||
            (otherInf && numerator == 0 && denominator != 0))
        {
            return new Fraction(0, 0);
        }

        // Infinity * normal (nonzero) => Infinity with sign
        if (thisInf || otherInf)
        {
            long sign = 1;

            // Sign from this
            if (thisInf)
            {
                if (numerator < 0)
                {
                    sign = -sign;
                }
            }
            else
            {
                if (numerator < 0)
                {
                    sign = -sign;
                }
            }

            // Sign from other
            if (otherInf)
            {
                if (other.numerator < 0)
                {
                    sign = -sign;
                }
            }
            else
            {
                if (other.numerator < 0)
                {
                    sign = -sign;
                }
            }

            return new Fraction(sign, 0);
        }

        // Normal multiplication
        long n = numerator * other.numerator;       // New numerator
        long d = denominator * other.denominator;   // New denominator
        return new Fraction(n, d);
    }


    /*****************************divide*********************************
    * Description: Returns a new Fraction that is this / other.
    *
    * Parameters:
    *   other (input) - fraction to divide by
    *
    * Pre: other is not null
    * Post: this and other are unchanged
    *
    * Returns: new Fraction equal to this / other
    *
    * Called by: user code
    * Calls: Fraction
    ************************************************************************/
    public Fraction divide(Fraction other)
    {
        // If either is NaN => NaN
        if ((denominator == 0 && numerator == 0) || (other.denominator == 0 && other.numerator == 0))
        {
            return new Fraction(0, 0);
        }

        boolean thisInf = (denominator == 0 && numerator != 0);
        boolean otherInf = (other.denominator == 0 && other.numerator != 0);

        // Infinity / Infinity => NaN
        if (thisInf && otherInf)
        {
            return new Fraction(0, 0);
        }

        // Normal / Infinity => 0 (including 0/Infinity)
        if (!thisInf && otherInf)
        {
            return new Fraction(0, 1);
        }

        // Infinity / normal
        if (thisInf && !otherInf)
        {
            // Infinity / 0 => NaN
            if (other.numerator == 0)
            {
                return new Fraction(0, 0);
            }

            // Sign depends on this infinity sign and other sign
            long sign = 1;
            if (numerator < 0)
            {
                sign = -sign;
            }
            if (other.numerator < 0)
            {
                sign = -sign;
            }
            return new Fraction(sign, 0);
        }

        // At this point both are normal fractions.
        // Division by 0 => Infinity with sign (or NaN if 0/0)
        if (other.numerator == 0)
        {
            // 0 / 0 => NaN
            if (numerator == 0)
            {
                return new Fraction(0, 0);
            }

            // Nonzero / 0 => Infinity with sign of numerator * denominator(positive)
            if (numerator > 0)
            {
                return new Fraction(1, 0);
            }
            else
            {
                return new Fraction(-1, 0);
            }
        }

        // Normal division: (a/b) / (c/d) = (a*d) / (b*c)
        long n = numerator * other.denominator;   // new numerator
        long d = denominator * other.numerator;   // new denominator
        return new Fraction(n, d);
    }


    /*****************************negate*********************************
    * Description: Returns a new Fraction that is the negation of this.
    *
    * Parameters: none
    *
    * Pre: none
    * Post: this is unchanged
    *
    * Returns: new Fraction equal to -this
    *
    * Called by: user code
    * Calls: Fraction
    ************************************************************************/
    public Fraction negate()
    {
        // NaN stays NaN
        if (denominator == 0 && numerator == 0)
        {
            return new Fraction(0, 0);
        }

        // Infinity changes sign
        if (denominator == 0 && numerator > 0)
        {
            return new Fraction(-1, 0);
        }
        if (denominator == 0 && numerator < 0)
        {
            return new Fraction(1, 0);
        }

        // Normal number: just negate numerator
        return new Fraction(-numerator, denominator);
    }

    /*****************************pow************************************
    * Description: Returns a new Fraction that is this raised to integer p.
    *
    * Parameters:
    *   p (input) - integer exponent
    *
    * Pre: none
    * Post: this is unchanged
    *
    * Returns: new Fraction equal to (this ^ p)
    *
    * Called by: user code
    * Calls: Fraction
    ************************************************************************/
    public Fraction pow(int p)
    {
        // NaN^p = NaN (for any p)
        if (denominator == 0 && numerator == 0)
        {
            return new Fraction(0, 0);
        }

        // Anything ^0 = 1 (including Infinity and normal numbers)
        if (p == 0)
        {
            return new Fraction(1, 1);
        }

        boolean thisInf = (denominator == 0 && numerator != 0);

        // Handle Infinity cases
        if (thisInf)
        {
            // Infinity^positive = Infinity (sign depends on odd/even power)
            if (p > 0)
            {
                if (numerator > 0)
                {
                    return new Fraction(1, 0);
                }
                else
                {
                    // (-Infinity)^even = +Infinity, (-Infinity)^odd = -Infinity
                    if (p % 2 == 0)
                    {
                        return new Fraction(1, 0);
                    }
                    else
                    {
                        return new Fraction(-1, 0);
                    }
                }
            }
            else
            {
                // Infinity^negative = 0
                return new Fraction(0, 1);
            }
        }

        // Normal fraction cases
        // 0^negative is undefined => Infinity? (we will return Infinity per division behavior)
        if (numerator == 0 && p < 0)
        {
            return new Fraction(1, 0);
        }

        long absP = p;
        if (absP < 0)
        {
            absP = -absP;
        }

        long nPow = 1; // numerator^absP
        long dPow = 1; // denominator^absP

        // Compute powers by repeated multiplication (simple beginner approach)
        for (int i = 0; i < absP; i++)
        {
            nPow = nPow * numerator;
            dPow = dPow * denominator;
        }

        if (p > 0)
        {
            return new Fraction(nPow, dPow);
        }
        else
        {
            // Negative power: invert
            return new Fraction(dPow, nPow);
        }
    }


    // -------------------- Private Helpers --------------------

    /*****************************setToNormalForm*************************
    * Description: Normalizes this fraction into standard form.
    * - If denominator == 0, store as special value (Infinity/-Infinity/NaN)
    * - If denominator != 0:
    *     - Reduce to lowest terms using gcd
    *     - Make denominator positive
    *
    * Parameters: none
    *
    * Pre: numerator and denominator have been set
    * Post: fraction is normalized
    *
    * Returns: none
    *
    * Called by: constructors
    * Calls: gcd
    ************************************************************************/
    private void setToNormalForm()
    {
        // Handle special values
        if (denominator == 0)
        {
            if (numerator > 0)
            {
                numerator = 1;  // +Infinity
            }
            else if (numerator < 0)
            {
                numerator = -1; // -Infinity
            }
            else
            {
                numerator = 0;  // NaN
            }
            return;
        }

        // If numerator is 0, force 0/1
        if (numerator == 0)
        {
            denominator = 1;
            return;
        }

        // Make denominator positive
        if (denominator < 0)
        {
            numerator = -numerator;
            denominator = -denominator;
        }

        // Reduce fraction
        long g = gcd(Math.abs(numerator), denominator); // Greatest common divisor
        numerator = numerator / g;
        denominator = denominator / g;
    }

    /*****************************gcd*************************************
    * Description: Computes gcd(a, b) using Euclid's algorithm.
    *
    * Parameters:
    *   a (input) - nonnegative integer
    *   b (input) - nonnegative integer
    *
    * Pre: a >= 0 and b >= 0
    * Post: returns gcd(a, b)
    *
    * Returns: greatest common divisor of a and b
    *
    * Called by: setToNormalForm
    * Calls: none
    ************************************************************************/
    private long gcd(long a, long b)
    {
        while (b != 0)
        {
            long r = a % b; // Remainder
            a = b;
            b = r;
        }
        return a;
    }

    /*****************************isNaN***********************************
    * Description: Checks whether this fraction is NaN.
    *
    * Parameters: none
    * Pre: none
    * Post: none
    *
    * Returns: true if NaN, otherwise false
    *
    * Called by: toString
    * Calls: none
    ************************************************************************/
    private boolean isNaN()
    {
        return denominator == 0 && numerator == 0;
    }

    /*****************************isPosInfinity****************************
    * Description: Checks whether this fraction is +Infinity.
    *
    * Parameters: none
    * Pre: none
    * Post: none
    *
    * Returns: true if +Infinity, otherwise false
    *
    * Called by: toString
    * Calls: none
    ************************************************************************/
    private boolean isPosInfinity()
    {
        return denominator == 0 && numerator > 0;
    }

    /*****************************isNegInfinity****************************
    * Description: Checks whether this fraction is -Infinity.
    *
    * Parameters: none
    * Pre: none
    * Post: none
    *
    * Returns: true if -Infinity, otherwise false
    *
    * Called by: toString
    * Calls: none
    ************************************************************************/
    private boolean isNegInfinity()
    {
        return denominator == 0 && numerator < 0;
    }
}

