## Exercises on Inference in 2P-Kt

### Useful resources

- Lecture Slides: https://github.com/pikalab-unibo/ise-lab-inference
- 2P-Kt Documentation: https://github.com/tuProlog/2p-kt-presentation/
- Kotlin Documentation:
    - Sequences: https://kotlinlang.org/docs/sequences.html

### Order of exercises

1. Exercises 1, 2, 3 in `./substitutions` is about substitutions
    - 101 for substitution manipulation in 2P-Kt
    - small exercise about clause refresing
2. Exercise 4 in `./unification` is about unification
   - 101 for terms/clauses unification in 2P-Kt
3. Exercise 5 in `./theories` theories
   - 101 for clauses retrieval from theories, via unification
4. Exercise 6, 7 in `./resolution` is about resolution
    - exercise about how to perform resolution with a depth-first strategy
    - exercise about how to perform resolution with a (slightly) smarter strategy
5. Exercises 8, 9, 10, 11 in `./side-effects` is about resolution with side effects
   - exercise about how to support the assertion of clauses during resolution
   - exercise about how to support the retraction of clauses during resolution
   - exercise about how to support the negation (as failures) of goals in resolution
   - exercise about how to support the evaluation of (arithmetic) expressions during resolution

### Workflow

0. Listen to the teacher's explanation
1. Look for specific TODOs in the source code of each exercise
2. Inspect and understand the provided unit tests
    - each single line has a meaning: ask for help if a line/test/suite is unclear
3. Address the TODO and run the tests
4. When all tests pass, the exercise is done
