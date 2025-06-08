# Sportradar coding test

This is a Java project for the coding test provided by Sportradar.

# Author

**Aleksander Bakken**

# Assumptions

- Some form of specifying a match is necessary to update the match. Using UUID to store and retrieve specific matches.

# Mentions

- Finishing a match should as specified in spec remove the match completely. In a real world scenario this data would probably be stored somewhere externally.
- For the tests and running of code of the match ordering some sleep delays had to be added. They delay each match creation with 10ms. Otherwise, some matches could end up with the same time started, and would deviate from expected results (which is supposed to test which is created first).