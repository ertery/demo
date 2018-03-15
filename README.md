Start application by command
 mvn spring-boot:run

default port 8800
This demo project have next API:

GET:  /demo/records - return all records sorted in ASC order by dt field 

   
   /demo/records?page=<pagenNum>&size=<pageSize> - return records from <pageNum> with pageSize
        <pageNum> - number of pages
        <pageSize> - number of records on the page
        
   returning JSON structure:
                             {
                               dt: String, Date and Time
                               level: String, 
                               message: String,
                               author: String
                            }
        
POST: /demo/record - this request allow user save new record in to the DataBase
      This request has JSON payload with structure:
      
                              {
                                "dt": "1990-05-16T19:20:30+01:00", datetime type - String
                                "message": "TestMesaage", message type -String
         	                    "level": "ERROR" level type - String - allowed values  FATAL, ERROR, WARN, INFO, DEBUG, TRACE
                               }
                               
             in case of successfull send, new record Id will be returned. 
      
 
 