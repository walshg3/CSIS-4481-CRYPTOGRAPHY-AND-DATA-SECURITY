## Miller-Rabin
## Author: Gregory Walsh
## Crytpography & Data Security

import random
def millerRabin( n ) :
    """Tests if n is a prime number.  Returns False if n is
    definitely a composite.  Returns True if n might be a prime
    number.

    Keyword arguments:
    n - the number to test for primality.
    """
    ## Algorithm From Slides see instructions.txt for algorithm

    k = 0
    x = n-1
    runner = False
    a = random.randint(1, n-1)

    ## if a number is even its composite
    if n == 2:
        return True

    if n % 2 == 0:
        return False

    if n == 1:
        return True

    while(runner == False):
        x = x >> 1
        k = k+1

        if(x & 1):
            runner = True

    q = (n-1) >> k

    value = pow(a, q, n)

    if value == 1:
        return True


    for j in range(0, k):
        # this is line 5 in the algorithm and somehow needs to be
        # written as: a^((2^j)
        if pow(a,(pow(2, j)*q), n) == n-1:
            return True

    return False

def isProbablyPrime( n, p ) :
    """Tests if n is a prime number.  If this function returns False,
    then n is definitely a prime number.  If this function returns True,
    then the probability that n is a prime number is at least p.

    Keyword arguments:
    n - the number to test for primality.
    p - target probability.
    """

    ## Following slides in notes from class
    ## t represents 't' calls from the slides
    ## was discussed in class on how to implement
    # when t = 10 the target probability will be  > 0.99999
    t = 1

    if n % 2 == 0:
        return False

    while((1-(1/(4 ** t))) < p):
          t = t+1

    for i in range(t-1):
          if(millerRabin(n) == False):
              return False

    return millerRabin(n)

if __name__ == "__main__" :
    ## Write some code here to test that your functions work
    ## Miller Rabin
    print("These numbers should all be prime and return TRUE:")

    print("Miller Rabin with number 677 : "+ str(millerRabin(677)))
    print("Miller Rabin with number 397 : "+ str(millerRabin(397)))
    print("Miller Rabin with number 983 : "+ str(millerRabin(983)))
    print("Miller Rabin with number 601 : "+ str(millerRabin(601)))
    print(" ")
    print("These numbers should not be prime and return FALSE:")

    print("Miller Rabin with number 677 : "+ str(millerRabin(30)))
    print("Miller Rabin with number 397 : "+ str(millerRabin(510)))
    print("Miller Rabin with number 983 : "+ str(millerRabin(220)))
    print("Miller Rabin with number 601 : "+ str(millerRabin(990)))
    print(" ")
    print("These numbers are random ones that I have no idea lol:")

    print("Miller Rabin with number 1029485 : "+ str(millerRabin(1029485)))
    print("Miller Rabin with number 1293890123480 : "+ str(millerRabin(1293890123480)))
    print("Miller Rabin with number 123854 : "+ str(millerRabin(123854)))
    print("Miller Rabin with number 9973 : "+ str(millerRabin(9973)))

    print(" ")

    ## Is Probably Prime
    print("Probably Prime")
    print("Here FALSE is definitely prime True is probability with 0.99999 accuracy")
    print("Is Probably Prime with number 12: "+str(isProbablyPrime(12, 0.99999)))
    print("Is Probably Prime with number 91237579: "+str(isProbablyPrime(91237579, 0.99999)))
    print("Is Probably Prime with number 91237577:"+str(isProbablyPrime(91237577, 0.99999)))
    print("Is Probably Prime with number 91237519:"+str(isProbablyPrime(91237519, 0.99999)))
