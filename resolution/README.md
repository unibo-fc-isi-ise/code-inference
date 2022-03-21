
### Order of exercises

1. Exercise `./src/main/kotlin/it/unibo/ise/lab/resolution/`**`DepthFirstSolver.kt`** is about depth-first resolution strategies
    - here you should implement a depth-first, Prolog-like solver (supporting __no side-effects__)

2. Exercise `./src/main/kotlin/it/unibo/ise/lab/resolution/`**`SmartSolver.kt`** is about smarter resolution strategies
   - here you should implement a Prolog-like solver (supporting __no side-effects__) ...
   - ... which avoids getting stuck in simple recursive definitions such as:
       ```prolog
       nat(s(X)) :- nat(X).
       nat(z).
       ```
