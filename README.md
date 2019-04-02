# SCRABBLE
Non Spring Boot Java application with in memory HSQLDB.

## API Documentation

- To create a new game board use

 ```[POST]/board```
 
 Sample Response
 ```
 {
    "id": 1
 }
 ```
 
 - To add movement use
 
 ```[POST]/board/{id}/move```
 
 Sample Request Model
 ```
  [{
  	"direction":"Horizontal", -> or Vertical
  	"initialPoint":{
  		"x":1, -> [0,14]
  		"y":3  -> [0,14]
  	},
      "text":"at",
      "innerSequence":0 -> increase with each new move of the same request
  }]
  ```
  
  This will return the board response model. It will be given at the end of this doc.
  
  - To get the sentences on the board and their scores use
  
 ```[GET]/board/{id}/words```
  
 Sample Response
 ```
[
    {
        "word": "aba",
        "score": 5
    },
    {
        "word": "araba",
        "score": 7
    },
    ...
]
 ```
 
 - To get added moves until the given sequence number
 
 ```[GET]/board/{id}/moves/{sequence}```
 
 Sample Response
 ```
{
    "0": [
        {
            "direction": "Vertical",
            "initialPoint": {
                "x": 1,
                "y": 4
            },
            "text": "anne",
            "mainSequence": 0,
            "innersequence": 1
        }
    ],
    "1": [
        {
            "direction": "Horizontal",
            "initialPoint": {
                "x": 1,
                "y": 2
            },
            "text": "aba",
            "mainSequence": 1,
            "innersequence": 1
        },
        {
            "direction": "Vertical",
            "initialPoint": {
                "x": 1,
                "y": 2
            },
            "text": "araba",
            "mainSequence": 1,
            "innersequence": 1
        }
    ]
}
 ```
 
 - To deactivate a board use
 
 ```[POST]/board/{id}/deactivate```
 
 
 ### Board Response
 
 ```
{
    "boardId": 2,
    "instance": {},         -> Characters and their positions
    "moves": {},            -> each move grouped by sequence
    "activityStatus": true  -> activity status
}
```
 
 
## Usage
As a Java newbie I use IntelliJ IDEA and its maven support to test, build and run the project.