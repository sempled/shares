# How long did you spend on the coding challenge?
6 hours (with interruptions)

# What would you add to your solution if you had more time?If you didn't spend much time on the coding test then use this as an opportunity to explain what you would add.
Unit Test with mocked api response containing malformed json 
(would give coverage to a catch clause not currently covered in tests)
ApiService to separate logic of api calls from MVC controller
Investigate if there is a way to get the nested json response to map cleanly on the Java record 
Reduce use of free text in code by use of constants
Improve logging

# What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.
Java records were added in Java 14 and Java 17 (this version) is the first long-term support version to contain this. 
ShareData in model is a Java record.  

# How would you track down a performance issue in production? Have you ever had to do this?
Review of results from profilers and logs to track down cause of the issue.  
Yes, had to use JProfiler on the Authentication Service while working in Global Dev to track down a memory leak.  
Also, frequently was involved in incidents where production would actually fall over.  Would swarm round the problem with other members of the squad.  

# Please describe yourself using JSON.
{
"name": "Doug",
"hair": null,
"children": 2,
"married": true,
"favourite beers": {
    "Belgium": "Westmalle Tripel",
    "Hungary": "Flying Rabbit",
    "Austria": "Stift Engelszell Benno"
},
"Ground-hops": [
    "Béke téri Stadion",
    "Pancho Aréna",
    "Aldo Gastaldi Stadium"
    ]
}
