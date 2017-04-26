# Text analyser

RESTful API that analyses random text generated from http://www.randomtext.me/.
The text analyser service is based on the MapReduce technic, processing in parallel data and agreggating it in batches.

In order to scale this application Hadoop MapReduce using a cluster could be good idea.
For parallelizing the processes have been used latches. Other implementations like CompletionService and Future tasks would work too.

Technologies used are:
  - Spring Boot
  - H2 database (In order to make easy to execution of this application this in-memory database has been used);
  - Junit
  - Maven

## Usage

To start the application we need to run this command :
```
  mvn spring-boot:run
```

The RESTful API has two endpoints:

  ### Analyse text given the next parameters:
  
  - p_start : Paragraphs number start
  - p_end : Paragraps number end
  - w_count_min : Minimun number of words per paragraph
  - w_count_max : Maximun number of words per paragraph
    

    - Using cURL:
    ```sh
      curl -X 'http://localhost:8080/text?p_start=1&p_end=50&w_count_min=1&w_count_max=25'
    ```
  
    - Response for the above request:
  
    ```sh
      {
        "most_frequent_word" : "much",
        "average_paragraph_size" : 13.231372549019607,
        "average_paragraph_processing_time" : 0.02980392156862745,
        "total_processing_time" : 4147
      }
      ```
 ### Get results history:
 
  - Returns last 10 results.
    
    - Using cURL:
      ```sh
        curl -X 'http://localhost:8080/history'
      ```
    - Response for the above request:
      ```sh
        [
          {
            "most_frequent_word": "much",
            "average_paragraph_size": 13.231372549019607,
            "average_paragraph_processing_time": 0.02980392156862745,
            "total_processing_time": 4147
          },
          {
            "most_frequent_word": "without",
            "average_paragraph_size": 12.980392156862745,
            "average_paragraph_processing_time": 0.02431372549019608,
            "total_processing_time": 2449
          },
          {
            "most_frequent_word": "a",
            "average_paragraph_size": 12.663529411764706,
            "average_paragraph_processing_time": 0.01411764705882353,
            "total_processing_time": 5072
          },
          {
            "most_frequent_word": "far",
            "average_paragraph_size": 12.673725490196079,
            "average_paragraph_processing_time": 0.013333333333333334,
            "total_processing_time": 4975
          },
          {
            "most_frequent_word": "diabolic",
            "average_paragraph_size": 12.823529411764707,
            "average_paragraph_processing_time": 0.13098039215686275,
            "total_processing_time": 5392
          }
        ]
      ```





